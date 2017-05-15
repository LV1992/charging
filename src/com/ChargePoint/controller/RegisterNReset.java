package com.ChargePoint.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.Utils.RandomUtils;
import com.ChargePoint.Utils.TextUtils;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.ChargePointService;
import com.ChargePoint.services.ChargePointStationService;
import com.ChargePoint.services.ChargeRecordsService;
import com.ChargePoint.services.CommonService;
import com.ChargePoint.services.MobileUserService;

@Controller
@RequestMapping("/register")
public class RegisterNReset extends ControllerSupport{
	
	static Logger log = Logger.getLogger(RegisterNReset.class.getName());

	@RequestMapping(value="register", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> register(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean res = false;
		String jsonStr = "";
		MobileUser user = null;
		try {
			jsonStr = URLDecoder.decode(request.getParameter("jsonStr"),"UTF-8");
			user = JsonUtil.json2Class(jsonStr, MobileUser.class);
			if(!mobileUserService.getMobileFiledCount("userName",user.getUser_name())){
				modelMap.put("msg", "用户名已存在");
				modelMap.put("success", false);
			}else if(!mobileUserService.getMobileFiledCount("tel",user.getTel())){
				modelMap.put("msg", "手机号已注册");
				modelMap.put("success", false);
			}else if(!mobileUserService.getMobileFiledCount("identity",user.getIdentity())){
				modelMap.put("msg", "身份证号已注册");
				modelMap.put("success", false);
			}else{
				res = mobileUserService.addMobileUser(user);
				modelMap.put("success", res);
				modelMap.put("user", mobileUserService.getMobileUser(user.getId()));
				
			}
		}catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return modelMap;
	}
	
	@RequestMapping(value="validateMobileUserPW", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> validateMobileUserPW(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String jsonStr = null;
		Map<String,Object> parameterMap = null;
		try {
			jsonStr = URLDecoder.decode(request.getParameter("jsonStr"),"UTF-8");
			parameterMap = JsonUtil.jsonToMap(jsonStr);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean res = mobileUserService.validateMobileUserPW((String)parameterMap.get("userName"),
				(String)parameterMap.get("identity"),(String)parameterMap.get("tel"));
		modelMap.put("res", res);
		return modelMap;
	}
	
	@RequestMapping(value="resetMobileUserPW", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> resetMobileUserPW(HttpServletRequest request,
			@RequestParam()String userName,@RequestParam()String password){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		userName = TextUtils.TOUTF8(userName);
		MobileUser muser = mobileUserService.getMobileUser(userName, password);
		boolean res = false;
		if(null == muser){
		res = mobileUserService.resetMobileUserPW(userName,password);
		if(!res){
			modelMap.put("msg", "密码重置失败");
		}
		}else{
			modelMap.put("msg", "新密码与旧密码一致");
		}
		modelMap.put("res", res);
		return modelMap;
	}
	
	/**通过图片列表验证身份信息
	 * @param request/{type="get"(获取图片地址),"reg"（验证身份） }
	 * @param userName
	 * @param stationID
	 * @return json
	 */
	@RequestMapping(value="MUCPWP/{type}", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> MUCPWP(HttpServletRequest request,@PathVariable("type")String type,@RequestParam("userName")String userName,@RequestParam(value="jsonStr",required=false)String jsonStr){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Map<String,Object>> pictures = null;
		List<Map<String,Object>> unpictures = null;
		List<Map<String,Object>> responsePictures = new LinkedList<Map<String,Object>>();
		Integer uid = null;
		MobileUser mu = mobileUserService.getMobileUser(userName);
		if(null != mu){//账号存在
			uid = mu.getId();
		if("get".equalsIgnoreCase(type)){//获取图片地址
//		获取充电记录中所充过的充电站id
			List<Integer> stationIDs = chargeRecordsService.getChargeRecordsWithDisStationID(uid,6);
			Map<String, Object> pictureMap = null;
//			存已充电电站对应图片mapList
			pictures = new LinkedList<Map<String,Object>>();
//			存未充电电站对应图片mapList
			unpictures = new LinkedList<Map<String,Object>>();
//			获取所有已充电站id下的图片
			for(Integer iditem : stationIDs){
				ChargePointStation chargePointStation = chargePointStationService.getChargePointStation(iditem);
				String[] pictureList = chargePointStation.getPictureList();
//				获取每个站点下的图片地址
				if(null != pictureList){//该站点下有图片
				for(String purl : pictureList){
					pictureMap = new HashMap<String, Object>();
					pictureMap.put("sid", iditem);
					pictureMap.put("url", purl);
					pictures.add(pictureMap);
				}
				}
			}
//			获取所有未充电站id下的图片
			List<ChargePointStation> cps = chargePointStationService.getUnchargeChargePointStation(stationIDs, 30);
			for(ChargePointStation chargePointStation : cps){
				String[] unpictureList = chargePointStation.getPictureList();
				Integer unsid = chargePointStation.getId();
//				获取每个站点下的图片地址
				if(null != unpictureList){//该站点下有图片
				for(String unpurl : unpictureList){
					pictureMap = new HashMap<String, Object>();
					pictureMap.put("sid", unsid);
					pictureMap.put("url", unpurl);
					unpictures.add(pictureMap);
				}
				}
			}
//			已充电图片数量
			int pindex = pictures.size();
//			未充电图片数量
			int uindex = unpictures.size();
			if(pindex > 3){//最多只能取三个对的
				responsePictures.addAll(new RandomUtils().getObject(pictures, 3));
//				responsePictures.addAll(pictures.subList(0, 3));
				if(uindex > 6){//判断未充电图片
					responsePictures.addAll(new RandomUtils().getObject(unpictures, 6));
//					responsePictures.addAll(unpictures.subList(0, 6));
				}else{
					responsePictures.addAll(new RandomUtils().getObject(unpictures, uindex));
//					responsePictures.addAll(unpictures.subList(0, uindex));
				}
			}else{//已充电图片小于3个(全部加入集合)
				responsePictures.addAll(new RandomUtils().getObject(pictures, pindex));
//				responsePictures.addAll(pictures.subList(0, pindex));
				if(uindex > 9-pindex){//判断未充电图片（不全剩下的图片）
					responsePictures.addAll(new RandomUtils().getObject(unpictures, 9-pindex));
//					responsePictures.addAll(unpictures.subList(0, 9-pindex));
				}else{
					responsePictures.addAll(new RandomUtils().getObject(unpictures, uindex));
//					responsePictures.addAll(unpictures.subList(0, uindex));
				}
				
			}

//			打乱listMap顺序
			Collections.shuffle(responsePictures);
			modelMap.put("pictures", responsePictures);
		}else
		// end get
		if("reg".equalsIgnoreCase(type)){//验证身份
			boolean res = false;
			Map<String, Object> jmap = null;
			try {
			jmap = JsonUtil.jsonToMap(jsonStr);
			if(null != jmap){
			List<Integer> sids = (List<Integer>) jmap.get("stationID");
			String tel = (String) jmap.get("tel");
			MobileUser muser = new MobileUser();
			muser.setUser_name(userName);
			muser.setTel(tel);
			List<MobileUser> muList = mobileUserService.getMobileUserList(muser);
			if(0 != sids.size() && 0 != muList.size()){
			for(Integer iditem : sids){
				boolean exists = chargeRecordsService.isExistsChargeRecords(uid, iditem);
				if(!exists){//不存在
					res = false;
					break;
				}else{//存在
					res = true;
				}
			}
			
			}//  end if listSize != 0
			}// end null != Map 
			} catch (IOException e) {
				log.error("JSON 验证身份图片解析错误");
				e.printStackTrace();
			}
			modelMap.put("res", res);
		}
		}else{//账号不存在
			modelMap.put("res", false);
		}
		
		return modelMap;
	}
	
	
	@RequestMapping(value="checkRegister", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> checkRegister(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ChargePoint> chargePointList = chargePointService.getChargePointList(null);
		modelMap.put("chargePointList", chargePointList);
		modelMap.put("success", "true");
		return modelMap;
	}
	
	@RequestMapping(value="checkMobileUserName", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> checkMobileUserName(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String un = request.getParameter("name") == null ? "" : request.getParameter("name");
		MobileUser user = mobileUserService.getMobileUser(un);
		if(null != user){
			modelMap.put("valid", false);
		}else{
			modelMap.put("valid", true);
		}
//		{ "valid": true } or { "valid": false } to BootstrapValidator
		return modelMap;
	}
	
	@RequestMapping(value="rebindTel", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> rebindTel(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean res = false;
		String jsonStr = "";
		MobileUser user = null;
		Integer uid = null;
		try {
			jsonStr = URLDecoder.decode(request.getParameter("jsonStr"),"UTF-8");
			user = JsonUtil.json2Class(jsonStr, MobileUser.class);
			if(null != user){
				uid = mobileUserService.getMobileUser(user.getUser_name()).getId();
				user.setId(uid);
				res = mobileUserService.updateMobileUser(user);
			}
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelMap.put("updateResult", res);
		return modelMap;
	}
	
}
