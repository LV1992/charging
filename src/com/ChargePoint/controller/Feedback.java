package com.ChargePoint.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.bean.Feedbacks;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.FeedbacksService;

@Controller
@RequestMapping("/feedback")
public class Feedback extends ControllerSupport{

	@RequestMapping("addFeedback")
	@ResponseBody//返回json对象
	public Map<String,Object> addFeedbacks(@RequestParam()String jsonStr){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			jsonStr = URLDecoder.decode(jsonStr,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		Feedbacks feedback = null;
		try {
			feedback = JsonUtil.json2Class(jsonStr,Feedbacks.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean res = feedbacksService.addFeedbacks(feedback);
		modelMap.put("res", res);
		return modelMap;
	}
	
}
