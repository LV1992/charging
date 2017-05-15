package com.ChargePoint.controller.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.ChargePoint.services.AddHeartService;
import com.ChargePoint.services.AppointmentRecordsService;
import com.ChargePoint.services.ChargePointACLiveService;
import com.ChargePoint.services.ChargePointLiveService;
import com.ChargePoint.services.ChargePointService;
import com.ChargePoint.services.ChargePointStationService;
import com.ChargePoint.services.ChargeRecordsService;
import com.ChargePoint.services.CommonService;
import com.ChargePoint.services.FeedbacksService;
import com.ChargePoint.services.MobileUserService;
import com.ChargePoint.services.OperateService;
import com.ChargePoint.services.ReviewService;
import com.ChargePoint.services.TempAppointmentService;
import com.ChargePoint.services.TempChargeService;
import com.ChargePoint.services.WriteBackService;
import com.ChargePoint.services.OperateResultsService;

@Component
public class ControllerSupport {
	
	@Autowired
	protected OperateService operateService;
	@Autowired
	protected OperateResultsService operateResultsService;
	@Autowired
	protected AppointmentRecordsService appointmentRecordsService;
	@Autowired
	protected ChargePointService chargePointService;
	@Autowired
	protected ChargePointStationService chargePointStationService;
	@Autowired
	protected TempAppointmentService tempAppointmentService;
	@Autowired
	protected ChargeRecordsService chargeRecordsService;
	@Autowired
	protected TempChargeService tempChargeService;
	@Autowired
	protected MobileUserService mobileUserService;
	@Autowired
	protected ChargePointACLiveService chargePointACLiveService;
	@Autowired
	protected ChargePointLiveService chargePointLiveService;
	@Autowired
	protected ReviewService reviewService;
	@Autowired
	protected CommonService commonService;
	@Autowired
	protected AddHeartService addHeartService;
	@Autowired
	protected WriteBackService writeBackService;
	@Autowired
	protected FeedbacksService feedbacksService;
	
}
