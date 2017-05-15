package com.ChargePoint.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.controller.union.ControllerSupport;

@Controller
@RequestMapping("/records")
public class getRecords extends ControllerSupport{
	
	@RequestMapping(value="getChargeRecords/{userID}/{currentPage}/{tradeStatus}", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getChargeRecords(HttpServletRequest request,@PathVariable("userID")Integer userID,@PathVariable("currentPage")int currentPage,@PathVariable("tradeStatus")String tradeStatus){
		List<Map<String,Object>> chargeRecordsList = new ArrayList<Map<String,Object>>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		currentPage = currentPage == 0 ? 1 : currentPage;
		chargeRecordsList = chargeRecordsService.getChargeRecordsByPage(userID, (currentPage-1)*10, 10 ,tradeStatus);
		modelMap.put("chargeRecordsList", chargeRecordsList);
		return modelMap;
	}
	@RequestMapping(value="getAppointmentRecords/{userID}/{currentPage}/{status}", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getAppointmentRecords(@PathVariable("userID")String userID,@PathVariable("currentPage")Integer currentPage,@PathVariable("status")String status){
		List<Map<String,Object>> appointmentRecordsList = new ArrayList<Map<String,Object>>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		currentPage = currentPage == null ? 1 : currentPage;
		appointmentRecordsList = appointmentRecordsService.getAppointmentRecordsByPage("appointment_records_"+userID, status, (currentPage-1)*10,10);
		modelMap.put("appointmentRecordsList", appointmentRecordsList);
		return modelMap;
	}
}
