package com.ChargePoint.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ChargePoint.Utils.ThreadUtils;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.bean.ChargePointACLive;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.bean.Operate;
import com.ChargePoint.bean.OperateResults;
import com.ChargePoint.bean.TempAppointment;
import com.ChargePoint.bean.TempCharge;
import com.ChargePoint.controller.union.ControllerSupport;
import com.tenpay.util.WXUtil;

@Controller
@RequestMapping("/charge")
public class ChargeControll extends ControllerSupport{
	Integer tcId = -1;
	Logger logger = Logger.getLogger(ChargeControll.class);
	/**
	 * 开始充电
	 * @param uid
	 * @param cpid
	 * @return  startRes：true-开启成功：false-开启失败 -failCase-失败原因0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约5：充电桩已停止
	 */
	@RequestMapping(value = "startCharging", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> startCharging(@RequestParam(required = true)final Integer uid,@RequestParam(required = true) Integer count,
			@RequestParam(required = true)final String cpid, @RequestParam(required = false) Integer chargeNO,
			@RequestParam(required = false) String chargeMethod, @RequestParam(required = false) String forEnergy,
			@RequestParam(required = false) String forTime, @RequestParam(required = false) String forMoney) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		if(count == 1){
		// 写缓存表
		TempCharge tempCharge = new TempCharge();
		tempCharge.setC_p_id(cpid);
		tempCharge.setUser_id(uid);
//		缓存表没有这条数据才写数据
		if(!tempChargeService.haveTempCharge(tempCharge)){
			tempCharge.setCharger_no(chargeNO);
			tcId = tempChargeService.addTempCharge(tempCharge);
		}
		// 写操作表
		Operate operate = new Operate();
		operate.setTable_name("" + uid);
		operate.setUser_id(uid);
		operate.setC_p_id(cpid);
		String now = TimeFormatUtil.getFormattedNow();
		operate.setOperate_time(now);
		operate.setOperate_type("1");
		operate.setCharger_no(chargeNO);
		operate.setCharge_method(chargeMethod);
		operate.setFor_time(forTime);
		operate.setFor_energy(forEnergy);
		operate.setFor_money(forMoney);
		operateService.addOperate(operate);
		}
		//循环获取返回数据
			boolean start = false;
			//-1 为无返回结果
			String failCase = "-1";
				// 获取反馈结果
				OperateResults opres = new OperateResults();
				opres.setTable_name("" + uid);
				opres.setC_p_id(cpid);
				opres.setUser_id("" + uid);
				opres.setIs_send("N");
				ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
						OperateResults op = operateResultsService.getLastOperateResult(opres);
						if (op != null) {
//							0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
							if("1".equals(op.getOperation_result())){
										ChargeRecords chargeRecord = new ChargeRecords();
										chargeRecord.setTable_name(uid);
										chargeRecord.setStation_id(chargePoint.getStation_id());
										chargeRecord.setC_p_id(cpid);
										String now = TimeFormatUtil.getFormattedNow();
										chargeRecord.setStart_time(now);
										chargeRecord.setUser_name(mobileUserService.getMobileUser(uid).getUser_name());
										chargeRecordsService.addChargeRecords(chargeRecord);
								start = true;
								//开启线程修改预约／下发取消预约操作
								updateAppointmentToSuccess(uid,cpid);
								
								// 开启成功将电桩状态改为已占用
								chargePoint.setIs_free("2");
								synchronized (chargePoint) {
									chargePointService.updateChargePoint(chargePoint);
								}
								if("1".equals(chargePoint.getC_p_type())){
									//开启监控自动结束线程
									watchAutoThread(cpid,uid);
								}//为交流电桩
								
							}else if("0".equals(op.getOperation_result())){//开启失败获取开启失败原因
								// 失败获取 失败原因0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约
								failCase = op.getFailure_case();
							}
							// 有反馈结果修改为已接收
							opres.setIs_send("Y");
							synchronized (opres) {
								operateResultsService.updateOperateResults(opres);
							}
							
						}//有返回结果
						else{//没有返回结果且为交流获取长发数据
							if("1".equals(chargePoint.getC_p_type())){
								ChargePointACLive chargeACPointLive = chargePointACLiveService.getAutoEndData("charge_point_ac_live_"+cpid);
//								已经开启成功则返回
								if(null != chargeACPointLive && "3".equals(chargeACPointLive.getStatus()) && "1".equals(chargeACPointLive.getMethod())){
									start = true;
									//开启线程修改预约／下发取消预约操作
									updateAppointmentToSuccess(uid,cpid);
									// 开启成功将电桩状态改为已占用
									chargePoint.setIs_free("2");
									synchronized (chargePoint) {
										chargePointService.updateChargePoint(chargePoint);
									}
									//  获取长发数据结束修改为已接收
									OperateResults lastOper = operateResultsService.getLastOperateResult(opres);
									lastOper.setIs_send("Y");
									synchronized (lastOper) {
										operateResultsService.updateOperateResults(lastOper);
									}
								}
							}
						}
						if(count == 49 && start == false && failCase.equals("-1")){//超过本次循环删除缓存表
							TempCharge tempCharge = new TempCharge();
							tempCharge.setId(tcId);
							tempChargeService.deleteTempCharge(tempCharge);
						}
//						返回结果为失败时获取失败原因
							modelMap.put("failCase", failCase);
							modelMap.put("startRes", start);
		System.out.println("第"+count+"次开启充电 开启充电返回结果"+modelMap);
		logger.warn("开启充电返回结果"+modelMap);
		return modelMap;
	}

	/**充电监控
	 * @param cpid
	 * @return data
	 */
	@RequestMapping(value = "getChargingLiveData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getChargingLiveData(@RequestParam(required = true) String cpid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		HashMap<String, Object> data = null;
		// 获取电桩交直流类型0-直流(DC)，1-交流(AC)
		ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
		if (null != chargePoint) {
			String cptype = chargePoint.getC_p_type();
			if (cptype.equals("0")) {// 直流桩
				data = chargePointLiveService.getCurrentData("charge_point_live_" + cpid);
			} else {// 交流桩
				data = chargePointACLiveService.getCurrentData("charge_point_ac_live_" + cpid);
			}
		} else {// 电桩未知
			return null;
		}
		modelMap.put("data", data);
		return modelMap;
	}

	/**结束充电
	 * @param uid
	 * @param cpid
	 * @return endRes：true-成功 false-失败 failCase 失败原因0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约5：充电桩已停止
	 */
	@RequestMapping(value = "endCharging", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> endCharging(@RequestParam(required = true) Integer uid,@RequestParam(required = true) Integer count,
			@RequestParam(required = true) String cpid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if(count == 1){
		String end_time = TimeFormatUtil.getFormattedNow();
		// 写操作表
		Operate operate = new Operate();
		operate.setTable_name("" + uid);
		operate.setUser_id(uid);
		operate.setC_p_id(cpid);
		operate.setOperate_time(end_time);
		operate.setOperate_type("2");
		operateService.addOperate(operate);
		}
		// --等待反馈数据-
			
				//-1 为无返回结果
				String failCase = "-1";
				boolean endRes = false;
				// 获取反馈结果
				OperateResults opres = new OperateResults();
				opres.setTable_name("" + uid);
				opres.setC_p_id(cpid);
				opres.setUser_id("" + uid);
				opres.setIs_send("N");
				// 查找该充电桩
				ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
				float money = 0.0f;
				Float degree = 0.0f;
				String startTime = null;
						OperateResults op = operateResultsService.getLastOperateResult(opres);
						if (op!= null) {// 有返回结果
//							0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
							if ("0".equals(op.getOperation_result())) {// 返回结果为停止失败
								// 失败获取 失败原因0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约
								failCase = op.getFailure_case();
							} else if("2".equals(op.getOperation_result())){// 返回结果为停止成功
								endRes = true;
								//停止成功，修改该电桩的状态为空闲
								synchronized (chargePoint) {
									chargePoint.setIs_free("0");
									chargePointService.updateChargePoint(chargePoint);
								}

								// 停止操作成功删除存表
								TempCharge tempCharge = new TempCharge();
								tempCharge.setC_p_id(cpid);
								tempCharge.setUser_id(uid);
								tempChargeService.deleteTempCharge(tempCharge);
								
									String cpType = chargePoint.getC_p_type();
									// 将该充电记录的订单编号更新以便微信回调更改状态
									ChargeRecords updateItem = new ChargeRecords();
									updateItem.setTable_name(uid);
									// 生成订单号
									String trade_no = WXUtil.getTradeNO();
									updateItem.setTrade_no(trade_no);
									if ("0".equals(cpType)) {// 直流电桩，算好金额返回给手机
										// 获取上发的充电记录详情（充电时长、电量等）
										ChargeRecords chargeRecordsDetails = chargeRecordsService.getUpdateRecord(uid);
										if (null != chargeRecordsDetails) {
											startTime = chargeRecordsDetails.getStart_time();
											updateItem.setId(chargeRecordsDetails.getId());
											// 充电电量
										degree = chargeRecordsDetails.getDegree();
										Float price = chargePoint.getE_price();
										degree = degree == null ? 0 : degree;
										price = price == null ? 1 : price;
										money = degree * price;
										}
									} else {// 交流电桩，直接获取金额给手机
										//根据长发数据更新充电记录
										ChargePointACLive chargeACPointLive = chargePointACLiveService.getACLiveEndData("charge_point_ac_live_"+cpid);
										ChargeRecords unUpdatechargeRecords = chargeRecordsService.getUnUpdateRecord(uid);
										startTime = unUpdatechargeRecords.getStart_time();
										updateItem.setId(unUpdatechargeRecords.getId());
										updateItem.setIs_update("1");
										money = Float.parseFloat(chargeACPointLive.getCharged_money());
										degree = Float.parseFloat(chargeACPointLive.getCharged_energy());
										degree = degree == null ? 0 : degree;
										/****充电修改，加入停车费，服务费****/
										ChargePointStation chargePointStation =	chargePointStationService.getChargePointStation(chargePoint.getStation_id());
										String serviceFee = chargePointStation.getService_fee();
										String parkingFee = chargePointStation.getParking_fee();
										money += Float.parseFloat(serviceFee) + Float.parseFloat(parkingFee);
										
									}
									// 更新金额到充电记录
									updateItem.setMoney(money);
									String end_time = TimeFormatUtil.getFormattedNow();
									//更新结束时间到记录表
									updateItem.setDegree(degree);
									updateItem.setEnd_time(end_time);
									updateItem.setStation_id(chargePoint.getStation_id());
									updateItem.setTrade_status("0");
									synchronized (updateItem) {
										chargeRecordsService.updateChargeRecords(updateItem);
									}

									modelMap.put("startTime", startTime);
									modelMap.put("endTime", updateItem.getEnd_time());
									modelMap.put("tradeNO", trade_no);
									modelMap.put("degree", degree);
									modelMap.put("money", money);
									modelMap.put("unpayid", updateItem.getId());
							}
							// 有反馈结果修改为已接收
							opres.setId(op.getId());
							opres.setIs_send("Y");
							synchronized (opres) {
								operateResultsService.updateOperateResults(opres);
							}

						}//有返回结果
						else{//没有返回结果且为交流桩
							
							if("1".equals(chargePoint.getC_p_type())){
								ChargePointACLive chargeACPointLive = chargePointACLiveService.getAutoEndData("charge_point_ac_live_"+cpid);
//								已经结束成功则返回
								if(null != chargeACPointLive && "1".equals(chargeACPointLive.getStatus()) && "0".equals(chargeACPointLive.getMethod())){
									endRes = true;
									//  获取长发数据结束修改为已接收
									OperateResults lastOper = operateResultsService.getLastOperateResult(opres);
									lastOper.setIs_send("Y");
									synchronized (lastOper) {
										operateResultsService.updateOperateResults(lastOper);
									}
									// 将该充电记录的订单编号更新以便微信回调更改状态
									ChargeRecords updateItem = new ChargeRecords();
									updateItem.setTable_name(uid);
									// 生成订单号
									String trade_no = WXUtil.getTradeNO();
									updateItem.setTrade_no(trade_no);
									//根据长发数据更新充电记录
									ChargeRecords unUpdatechargeRecords = chargeRecordsService.getUnUpdateRecord(uid);
									updateItem.setStart_time(unUpdatechargeRecords.getStart_time());
									startTime = unUpdatechargeRecords.getStart_time();
									updateItem.setId(unUpdatechargeRecords.getId());
									updateItem.setIs_update("1");
									//根据长发数据更新充电记录
									ChargePointACLive chargeACEnd = chargePointACLiveService.getACLiveEndData("charge_point_ac_live_"+cpid);
									money = Float.parseFloat(chargeACEnd.getCharged_money());
									degree = Float.parseFloat(chargeACEnd.getCharged_energy());
									degree = degree == null ? 0 : degree;
									/****充电修改，加入停车费，服务费****/
									ChargePointStation chargePointStation =	chargePointStationService.getChargePointStation(chargePoint.getStation_id());
									String serviceFee = chargePointStation.getService_fee();
									String parkingFee = chargePointStation.getParking_fee();
									money += Float.parseFloat(serviceFee) + Float.parseFloat(parkingFee);
									// 更新金额到充电记录
									updateItem.setMoney(money);
									//更新结束时间到记录表
									String end_time = TimeFormatUtil.getFormattedNow();
									updateItem.setEnd_time(end_time);
									updateItem.setStation_id(chargePoint.getStation_id());
									updateItem.setTrade_status("0");
									synchronized (updateItem) {
										chargeRecordsService.updateChargeRecords(updateItem);
									}

									modelMap.put("startTime", startTime);
									modelMap.put("endTime", updateItem.getEnd_time());
									modelMap.put("tradeNO", trade_no);
									modelMap.put("degree", degree);
									modelMap.put("money", money);
									modelMap.put("unpayid", updateItem.getId());
								}
							}
						}
//						返回结果
							modelMap.put("failCase", failCase);
							modelMap.put("endRes", endRes);
		System.out.println("结束充电返回结果"+modelMap);
		logger.warn("结束充电返回结果"+modelMap);
		return modelMap;
	}
	
	
	/**自动结束（交流电桩）调用
	 * @param uid
	 * @param cpid
	 * @return endRes：true成功-unpayid;false-失败
	 */
	@RequestMapping(value = "autoEnd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> autoEnd(@RequestParam(required = true)final Integer uid,
			@RequestParam(required = true)final String cpid) {
		final Map<String, Object> modelMap = new HashMap<String, Object>();
		// 生成订单号
//			final String trade_no = WXUtil.getTradeNO();
			// --等待反馈数据-
			ThreadUtils.createCachedThread(new Runnable() {
				boolean f = true;
				boolean endRes = false;
				@Override
				public void run() {
					while(f){
						synchronized(modelMap){
							
					// 获取上发的充电记录详情（充电时长、电量等）
					ChargeRecords chargeRecordsDetails = chargeRecordsService.getUpdateRecord(uid);
					if (null != chargeRecordsDetails) {
						/*
						ChargeRecords recordsItem = chargeRecordsDetails;
						// 将该充电记录的订单编号更新以便微信回调更改状态
						ChargeRecords updateItem = new ChargeRecords();
						updateItem.setTable_name(uid);
						updateItem.setId(recordsItem.getId());
						updateItem.setTrade_no(trade_no);
						// 充电电量
						Float degree = recordsItem.getDegree();
						// 查找该充电记录所充电桩
						ChargePoint chargePoint = chargePointService.getChargePoint(cpid);
						//修改该电桩的状态为空闲（上位机已修改）
						synchronized (chargePoint) {
							chargePoint.setIs_free("0");
							chargePointService.updateChargePoint(chargePoint);
						}
						float money = 0.0f;
						// 交流电桩，直接获取金额给手机
							money = chargeRecordsDetails.getMoney();
						*/	
							/****充电修改，加入停车费，服务费****/
							/*
							ChargePointStation chargePointStation =	chargePointStationService.getChargePointStation(chargePoint.getStation_id());
							String serviceFee = chargePointStation.getService_fee();
							String parkingFee = chargePointStation.getParking_fee();
							money += Float.parseFloat(serviceFee) + Float.parseFloat(parkingFee);
							
							degree = chargeRecordsDetails.getDegree();
							degree = degree == null ? 0 : degree;
						updateItem.setStation_id(chargePoint.getStation_id());
						updateItem.setTrade_status("0");
						synchronized (updateItem) {
							chargeRecordsService.updateChargeRecords(updateItem);
						}
						*/
						endRes = true;
						f = false;
//						modelMap.put("startTime", recordsItem.getStart_time());
//						modelMap.put("endTime", updateItem.getEnd_time());
//						modelMap.put("tradeNO", trade_no);
//						modelMap.put("degree", degree);
//						modelMap.put("money", money);
						modelMap.put("unpayid", chargeRecordsDetails.getId());
						modelMap.notify();
					}//end have records update
					else{
						
					}
					modelMap.put("endRes", endRes);
						}//end synchronized
						//隔500毫秒获取一次（获取10次）数据
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}//end while
				}//end run function
			});
			synchronized (modelMap) {
				try {
					modelMap.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		return modelMap;
	}
	
	/**获取支付详情
	 * @param type 0-失败 1- 成功 2-详情
	 * @param uid
	 * @param cpid
	 * @return
	 */
	@RequestMapping(value = "getPayDetails/{type}", method = RequestMethod.POST)
	public ModelAndView getPayDetails(@PathVariable()int type,@RequestParam(required = true) Integer uid,@RequestParam(required = true) Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> res = chargeRecordsService.getChargeRecordsByID(uid, id);
		modelMap.put("res", res);
		String viewPage = "";
		switch(type){
		case 0 : viewPage = "charge/payBust";break;
		case 1 : viewPage = "charge/payDone";break;
		default : viewPage = "charge/lineItem";
		}
		return new ModelAndView(viewPage,modelMap);
	}

	/**根据id获取电站视图(去评价所需的电站数据)
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "getMobileStationByStationID", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMobileStationByStationID(@RequestParam(required = true) Integer sid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> dataMap = null;
		dataMap = chargePointStationService.getMobileStationByStationID(sid);
		Integer id = (Integer) dataMap.get("id");
		Integer avgScore = reviewService.getReviewAvgScoreByStationID(id);
		dataMap.put("avgScore", avgScore);
		String picture = (String) dataMap.get("picture");
		if (null != picture && -1 != picture.indexOf("#")) {
			picture = picture.substring(0, picture.indexOf("#"));
		} else {
			picture = null;
		}
		dataMap.put("picture", picture);
		modelMap.put("data", dataMap);
		return modelMap;
	}
	
	/**开启监控自动结束线程（开启充电成功后使用）
	 * @param cpid
	 */
	private void watchAutoThread(final String cpid,final Integer uid){
		// 开启充电成功(且为交流电桩)，同时开启一个线程监控自动结束（监控长发数据表）
		ThreadUtils.createCachedThread(new Runnable() {
			boolean flag = true;
			// 设置状态值记住上次充电状态（-1为初始值，1代表空闲 2预约  3充电中  4 充电完成	）
			String pervStatus = "-1";
			// 设置方法值记住上次充电状态（-1为初始值，0空闲，1一刷卡 2-手机充电）
			String pervMethod = "-1";			
			@Override
			public void run() {
				while(flag){
					// 手机充电情况(交流)自动结束情况处理
					ChargePointACLive chargePointACLive = chargePointACLiveService
							.getAutoEndData("charge_point_ac_live_" + cpid);
					
					if (null != chargePointACLive) {
						// 1代表空闲 2预约 3充电中 4停止充电
						String currentStatus = chargePointACLive.getStatus();
						String currentMethod = chargePointACLive.getMethod();
						// 上次为充电中状态，手机付费方式变为 这次都空闲状态情况（自动结束情况）
						if ("3".equals(pervStatus) && "1".equals(pervMethod)
								&& "1".equals(currentStatus) && "0".equals(currentMethod)
								) {
							// 将该充电记录的订单编号更新以便微信回调更改状态
							ChargeRecords updateItem = new ChargeRecords();
							updateItem.setTable_name(uid);
							String trade_no = WXUtil.getTradeNO();
							updateItem.setTrade_no(trade_no);
							//根据长发数据更新充电记录
							ChargeRecords unUpdatechargeRecords = chargeRecordsService.getUnUpdateRecord(uid);
							if(null != unUpdatechargeRecords){
								
							updateItem.setId(unUpdatechargeRecords.getId());
							updateItem.setIs_update("1");
							Float money = Float.parseFloat(chargePointACLive.getCharged_money());
							Float degree = Float.parseFloat(chargePointACLive.getCharged_energy());
							degree = degree == null ? 0 : degree;
							/****充电修改，加入停车费，服务费****/
							ChargePointStation chargePointStation =	chargePointStationService.getChargePointStation(unUpdatechargeRecords.getStation_id());
							String serviceFee = chargePointStation.getService_fee();
							String parkingFee = chargePointStation.getParking_fee();
							money += Float.parseFloat(serviceFee) + Float.parseFloat(parkingFee);
							// 更新金额到充电记录
							updateItem.setMoney(money);
							updateItem.setDegree(degree);
							//更新结束时间到记录表
							String end_time = TimeFormatUtil.getFormattedNow();
							updateItem.setEnd_time(end_time);
							updateItem.setStation_id(chargePointService.getChargePoint(cpid).getStation_id());
							updateItem.setTrade_status("0");
							synchronized (updateItem) {
								//获取长发数据结束
								chargeRecordsService.updateChargeRecords(updateItem);
							}
							}//end if null != unUpdatechargeRecords
							
							// 删除此条充电缓存数据并更新电桩状态为空闲
							TempCharge tempCharge = new TempCharge();
							tempCharge.setC_p_id(cpid);
							//自动结束缓存删除
							tempChargeService.deleteTempCharge(tempCharge);
							ChargePoint chargePoint = new ChargePoint();
							chargePoint.setC_p_id(cpid);
							chargePoint.setIs_free("0");
							// 重置充电状态
							chargePointService.updateChargePoint(chargePoint);
							//结束循环退出线程
							flag = false;
						}
//						System.out.println("上次stuts "+pervStatus+""+" 上次method "+pervMethod);
						System.out.println("本次stuts "+currentStatus+""+" 本次method "+currentMethod);
						pervStatus = currentStatus;
						pervMethod = currentMethod;
					}else{
						flag = false;
					};
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}//end while
			}
		});
	}
	
	/**修改预约记录预约状态为预约成功线程
	 * @param uid
	 * @param cpid
	 */
	private void updateAppointmentToSuccess(final Integer uid,final String cpid){
		ThreadUtils.createCachedThread(new Runnable() {
			
			@Override
			public void run() {
				// 修改预约记录预约状态为预约成功
				appointmentRecordsService.updateAppointmentRecordsToSuccess(uid);
						// 写操作表(下发取消预约指令)
						Operate operate = new Operate();
						operate.setTable_name("" + uid);
						operate.setUser_id(uid);
						operate.setC_p_id(tempAppointmentService.getTemappingCPID(uid));
						operate.setOperate_time(TimeFormatUtil.getFormattedNow());
						operate.setOperate_type("4");
						operateService.addOperate(operate);
						// --等待上发数据-
						boolean f = true;
						int count = 1;
						// 获取反馈结果
						OperateResults opres = new OperateResults();
						opres.setTable_name("" + uid);
						opres.setUser_id("" + uid);
						opres.setIs_send("N");
						while (f) {
							OperateResults op = operateResultsService.getLastOperateResult(opres);

							// 0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
							if (op != null) {
								if ("4".equals(op.getOperation_result())) {// 取消预约成功
									// 删除预约缓存
									TempAppointment tempAppointment = new TempAppointment();
									tempAppointment.setUser_id(uid);
									tempAppointmentService.deleteTempAppointment(tempAppointment);
									// 释放电桩
									ChargePoint chargePoint = new ChargePoint();
									synchronized (chargePoint) {
										chargePoint.setC_p_id(op.getC_p_id());
										chargePoint.setIs_free("0");
										chargePointService.updateChargePoint(chargePoint);
									}
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
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						} // end while
			}
		});
	}
	
}
