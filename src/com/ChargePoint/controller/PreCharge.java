package com.ChargePoint.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.TextUtils;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.bean.TempAppointment;
import com.ChargePoint.bean.TempCharge;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.TempChargeService;

@Controller
@RequestMapping("/preCharge")
public class PreCharge extends ControllerSupport{
	
	Logger logger = Logger.getLogger(PreCharge.class);
	
	/**开始充电前(手动输入/扫码点状编号)的准备
	 * @param request userName，cpid
	 * @return res false ／failCase －1  用户不存在 －2 充电桩不存在
	 * res true／ forwardPage -0 支付页面 -1 监控（可以结束）-2 开启充电 -3该用户有其他正在充电 4-电桩正在充电
	 */
	@RequestMapping(value="scanQrcode", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> preCharge(@RequestParam()Integer uid,
	@RequestParam()String cpid){
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		默认值
		boolean res = true;
		String forwardPage = "-1";
		if(null == mobileUserService.getMobileUser(uid)){
			res = false;
			modelMap.put("failCase", 1);
		}else{
		ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
//		判断电桩是否存在
		if(chargePoint != null){
//		充电桩存在
		ChargeRecords unpayChargeRecords = chargeRecordsService.getNONPaymentChargeRecords(uid); 
		if(null != unpayChargeRecords){//存在未完成支付(跳转到结算界面)
			forwardPage = "0";
		}else{//不存在未支付
//		充电缓存表数据
//			充电缓存表数据
			TempCharge tempCharge = new TempCharge();
			tempCharge.setUser_id(uid);
			TempCharge tc = tempChargeService.getTempCharge(tempCharge); 
//		预约缓存表数据
			TempAppointment tempAppointment = new TempAppointment();
			tempAppointment.setC_p_id(cpid);
			tempAppointment.setUser_id(uid);
			boolean tapp = tempAppointmentService.haveTempAppointment(tempAppointment); 
//			数据获手机所需数据
			ChargePointStation cps = chargePointStationService.getChargePointStation(chargePoint.getStation_id());
//			已经有缓存
			if(null != tc){
//				是输入的充电桩(且正在充电状态)
				if(tc.getC_p_id().equals(cpid) && "2".equals(chargePoint.getIs_free())){
//			进入监控（有结束按钮）
					forwardPage = "1";
				}else{
//					该用户有其他正在充电的
					forwardPage = "3";
				}
			}else
			if(tapp || "0".equals(chargePoint.getIs_free())){
//			空闲或有预约数据，进入开始充电界面
				forwardPage = "2";
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("v", chargePoint.getV());
				data.put("i", chargePoint.getI());
				data.put("w", chargePoint.getW());
				data.put("type", chargePoint.getC_p_type());
				data.put("inner_no", chargePoint.getInner_no());
				data.put("price", chargePoint.getE_price());
				data.put("parking_fee", cps.getParking_fee());
				data.put("service_fee", cps.getService_fee());
				modelMap.put("data", data);
				
				if(null != cps.getPictureList()){
					modelMap.put("picture", cps.getPictureList()[0]);
				}else{
					modelMap.put("picture", null);
				}
//				平均分
				modelMap.put("avgScore", reviewService.getReviewAvgScoreByStationID(cps.getId()));
				}
			else if("2".equals(chargePoint.getIs_free())){
				forwardPage = "4";
			}
			}//有为支付订单
		
		modelMap.put("cpType", chargePoint.getC_p_type());
		modelMap.put("forwardPage", forwardPage);
		}else{//电桩不存在或者被占用、被预约
			res = false;
			modelMap.put("failCase", 2);
		}
		}//end user is null
		modelMap.put("res", res);
		System.out.println("扫码充电结果 ： "+modelMap);
		logger.warn("扫码充电结果 ： "+modelMap);
		return modelMap;
	}
	
	/**超时为确认充点电
	 * @param request
	 * @return
	 */
	@RequestMapping(value="cancelPreCharge", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> cancelPreCharge(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String cpid = request.getParameter("cpid");
		String userName = null;
		userName = TextUtils.TOUTF8(request.getParameter("userName"));
		MobileUser user = mobileUserService.getMobileUser(userName);
		int uID = user.getId();
		TempCharge cancelTempCharge = new TempCharge();
		cancelTempCharge.setC_p_id(cpid);
		cancelTempCharge.setUser_id(uID);
		boolean res = tempChargeService.deleteTempCharge(cancelTempCharge);
		modelMap.put("res", res);
		return modelMap;
	}
}
