package com.ChargePoint.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.ThreadUtils;
import com.ChargePoint.bean.AppointmentRecords;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.bean.OperateResults;
import com.ChargePoint.bean.TempAppointment;
import com.ChargePoint.bean.TempCharge;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.ChargeRecordsService;
import com.ChargePoint.services.OperateResultsService;
import com.ChargePoint.services.TempAppointmentService;
import com.ChargePoint.bean.Operate;

@Controller
@RequestMapping("/appointment")
public class Appointment extends ControllerSupport{
	Integer tappId = -1;
	/**
	 * 发起预约
	 * @param cpid
	 * @param uid
	 * @param startTime
	 * @param endTime
	 * @return 
	 * -1 - 充电桩未响应 
	 * 0-预约成功
	 *  1-存在未完成支付(跳转到结算界面)
	 *   2-完成当前充电再预约 
	 *   3-通信超时
	 *   4-充电机故障
	 *   5-未插充电枪
	 *   6- 充电桩正在充电（预约失败）
	 *   7-  充电桩被预约
	 *   8-有未完成的预约
	 *   9-电桩不存在或在充电
	 */
	@RequestMapping(value = "makeAppointment", method = RequestMethod.POST)
	@ResponseBody // 返回json对象
	public Map<String, Object> makeAppointment(int count,final String cpid, final Integer uid,
			Integer orderTime) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 充电桩未响应
		final int[] result = { -1 };
		ChargeRecords unpayChargeRecords = chargeRecordsService.getNONPaymentChargeRecords(uid); 
		if(null != unpayChargeRecords){
			result[0] = 1;//存在未完成支付(跳转到结算界面)
		}else{//不存在未支付
		ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
		// 当充电桩状态为空闲时才可以预约充电
		if (null != chargePoint && "0".equals(chargePoint.getIs_free())) {
			// 充电缓存表数据
			TempCharge tempCharge = new TempCharge();
			tempCharge.setUser_id(uid);
			if (tempChargeService.haveTempCharge(tempCharge)) {
				result[0] = 2;// 有其他充电中的状态不让充电
			} else {

				int listCount = appointmentRecordsService.getAppointmentStatusCount(uid, "0");
				// 限制预约中只有一条数据
				if (listCount == 0) {
					// 计算起始时间、结束时间
					String startTime = "";
					String endTime = "";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					long dateTime = date.getTime();
					// 算成秒值
					dateTime += orderTime * 60000;
					Date endDate = new Date(dateTime);
					startTime = formatter.format(date);
					endTime = formatter.format(endDate);
					if(count == 1){
						
					// 写预约缓存表
					TempAppointment tempAppointment = new TempAppointment();
					tempAppointment.setC_p_id(cpid);
					tempAppointment.setUser_id(uid);
					// 不存在则添加充电缓存
					if (!tempAppointmentService.haveTempAppointment(tempAppointment)) {
						tempAppointment.setStart_time(startTime);
						tempAppointment.setEnd_time(endTime);
						tappId = tempAppointmentService.addTempAppointment(tempAppointment);
					};
					}

					if ("0".equals(chargePoint.getC_p_type())) {// 直流电桩无需获取预约反馈结果
						result[0] = 0;// 预约成功
					} else {// 交流电桩(获取反馈数据)
						if(count == 1){
							
						// 添加操作表记录(预约起始时间、时长)
						Operate operate = new Operate();
						operate.setTable_name("" + uid);
						operate.setC_p_id(cpid);
						operate.setUser_id(uid);
						operate.setOperate_type("3");
						operate.setOrder_start_time(startTime);
						operate.setOrder_end_time(endTime);
						operate.setOperate_time(startTime);
						operate.setOrder_time("" + orderTime);
						operateService.addOperate(operate);
						}

								// 获取反馈结果
								OperateResults opres = new OperateResults();
								opres.setTable_name("" + uid);
								opres.setC_p_id(cpid);
								opres.setUser_id("" + uid);
								opres.setIs_send("N");

										OperateResults op = operateResultsService.getLastOperateResult(opres);

										// 0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
										if (null != op) {// 有反馈结果

											if ("0".equals(op.getOperation_result())) {// 返回结果为停止失败
									// 失败获取 失败原因0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约
												String failCase = op.getFailure_case();
												switch (failCase) {
												case "0":
													result[0] = 3;//通信超时
													break;
												case "1":
													result[0] = 4;//充电机故障
													break;
												case "2":
													result[0] = 5;//未插充电枪
													break;
												case "3":
													result[0] = 6;//充电桩正在充电（预约失败）
													break;
												case "4":
													result[0] = 7;//充电桩被预约
													break;}
											} else if ("3".equals(op.getOperation_result())) {
												result[0] = 0;// 预约成功
											}
											// 有反馈结果修改为已接收
											opres.setId(op.getId());
											opres.setIs_send("Y");
											synchronized (opres) {
												operateResultsService.updateOperateResults(opres);
											}
										} // 有反馈数据
										else{//没有返回结果超时结束线程
											
										}
					} // end 交流电桩

					if (result[0] == 0) {// 预约成功
					
						// 更新充电桩状态为已预约
						ChargePoint c = new ChargePoint();
						synchronized (c) {
							c.setC_p_id(cpid);
							c.setIs_free("1");// 0-空闲，1-被预约，2-充电中
							chargePointService.updateChargePoint(c);
						} // end synchronized

						// 添加预约记录(默认状态预约中)
						AppointmentRecords appointmentRecords = new AppointmentRecords();
						appointmentRecords.setTable_name(uid);
						appointmentRecords.setC_p_id(cpid);
						appointmentRecords.setUser_id(uid);
						appointmentRecords.setStart_time(startTime);
						appointmentRecords.setEnd_time(endTime);
						appointmentRecordsService.addAppointmentRecords(appointmentRecords);

					}else{// 没有预约成功删除预约缓存
						if (count == 49) {
							// 删预约缓存表
							TempAppointment tempAppointment = new TempAppointment();
							tempAppointment.setId(tappId);
							tempAppointmentService.deleteTempAppointment(tempAppointment);
						}
					}
				} // end预约中没有数据（已有预约）result = -1
				else {
					result[0] = 8;// 有未完成的预约
				}
			} // 用户在充电不能预约
		} else {
			result[0] = 9;// 电桩不存在或正在充电
		}
		}//有未支付订单
		modelMap.put("res", result);
		System.out.println("预约返回结果 " + result[0]);
		return modelMap;
	}

	@RequestMapping(value = "cancelAppointment", method = RequestMethod.POST)
	@ResponseBody // 返回json对象
	public Map<String, Object> cancelAppointment(String cpid, Integer uid,
			String startTime, String endTime) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		boolean result = false;

		ChargePoint cp = chargePointService.getChargePoint(cpid);
		if ("0".equals(cp.getC_p_type())) {// 直流电桩无需获取预约反馈结果
			result = true;// 取消预约成功
		} else {// 交流电桩(获取反馈数据)

			// 添加操作表记录
			Operate operate = new Operate();
			operate.setTable_name("" + uid);
			operate.setC_p_id(cpid);
			operate.setUser_id(uid);
			operate.setOperate_type("4");
			operate.setOrder_start_time(startTime);
			operate.setOrder_end_time(endTime);
			operate.setOperate_time(startTime);
			operateService.addOperate(operate);
			// --等待上发数据-
			boolean f = true;
			int count = 1;
			// 获取反馈结果
			OperateResults opres = new OperateResults();
			opres.setTable_name("" + uid);
			opres.setC_p_id(cpid);
			opres.setUser_id("" + uid);
			opres.setIs_send("N");
			while (f) {
				OperateResults op = operateResultsService.getLastOperateResult(opres);

				// 0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
				if (op != null) {
					if ("4".equals(op.getOperation_result())) {// 取消预约成功
						result = true;// 取消预约成功
					}
					// 有反馈结果修改为已接收
					opres.setId(op.getId());
					opres.setIs_send("Y");
					synchronized (opres) {
						operateResultsService.updateOperateResults(opres);
					}
					f = false;
				}else{//没有返回结果超时结束线程
					count ++;
					if(count > 100){
						f = false;
					}else{
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} // end while
		} // 交流桩
		if (result) {
			// 更改预约记录为失效
			appointmentRecordsService.updateAppointmentRecordsToFail(uid);
			// 删除预约缓存
			TempAppointment tempAppointment = new TempAppointment();
			tempAppointment.setC_p_id(cpid);
			tempAppointment.setUser_id(uid);
			tempAppointment.setStart_time(startTime);
			tempAppointment.setEnd_time(endTime);
			tempAppointmentService.deleteTempAppointment(tempAppointment);
			// 释放电桩
			ChargePoint chargePoint = new ChargePoint();
			synchronized (chargePoint) {
				chargePoint.setC_p_id(cpid);
				chargePoint.setIs_free("0");
				result = chargePointService.updateChargePoint(chargePoint);
			}
		}
		modelMap.put("res", result);
		return modelMap;
	}

}
