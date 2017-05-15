package com.ChargePoint.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.bean.AddHeart;
import com.ChargePoint.services.AddHeartService;

@Controller
@RequestMapping("/tt")
public class TestC {
	
	@Autowired
	private AddHeartService addHeartService;
	
	@RequestMapping(value="addHeart", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> addHeart(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		AddHeart addHeart = new AddHeart();
		addHeart.setId(1);
		addHeart.setStation_id(75);
		addHeart.setAdd_heart_uid(10127);
		addHeartService.addAddHeart(addHeart);
		
		AddHeart addHeart1 = new AddHeart();
		addHeart1.setId(1);
		addHeart1.setStation_id(75);
		addHeart1.setAdd_heart_uid(10127);
		addHeartService.addAddHeart(addHeart1);
		return modelMap;
	}

}
