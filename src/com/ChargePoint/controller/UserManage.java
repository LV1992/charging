package com.ChargePoint.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
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
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.MobileUserService;

@Controller
@RequestMapping("/user")
public class UserManage extends ControllerSupport {
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	// 返回json对象
	public Map<String, Object> updateMobileUser(@RequestParam() String jsonStr) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		MobileUser mobileUser = null;
		boolean res = false;
		try {
			jsonStr = URLDecoder.decode(jsonStr, "UTF-8");
			mobileUser = JsonUtil.json2Class(jsonStr, MobileUser.class);
			//有车牌照、车类型的数据则为车主认证提交（修改认证状态）
			if(null != mobileUser.getPlate_no() && null != mobileUser.getCar_type()){
				mobileUser.setApprove_status("true");
			}
			res = mobileUserService.updateMobileUser(mobileUser);
			MobileUser user = mobileUserService.getMobileUser(mobileUser.getId());
			modelMap.put("user", user);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelMap.put("res", res);
		return modelMap;
	}

	@RequestMapping(value = "getUserByID", method = RequestMethod.GET)
	@ResponseBody
	// 返回json对象
	public Map<String, Object> getMobileUserByID(@RequestParam() Integer uid) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		MobileUser mi = mobileUserService.getMobileUser(uid);
		modelMap.put("user", mi);
		return modelMap;
	}

	/**
	 * 微信绑定手机账号
	 * @param wxuid
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "bindUser", method = RequestMethod.POST)
	@ResponseBody
	// 返回json对象
	public Map<String, Object> bindUser(@RequestParam() Integer wxuid,
			@RequestParam() String userName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean res = false;
		// 更新(unionid等微信信息)手机账号信息
		MobileUser wxuser = mobileUserService.getMobileUser(wxuid);
		MobileUser binduser = mobileUserService.getMobileUser(userName);
		if (null != wxuser && null != binduser) {
			synchronized (binduser) {
				// 将微信的unionid、昵称等信息更新到手机用户
				MobileUser m = new MobileUser();
				m.setId(binduser.getId());
				m.setUnionid(wxuser.getUnionid());
				m.setOpenid(wxuser.getOpenid());
				m.setNick_name(wxuser.getNick_name());
				m.setGender(wxuser.getGender());
				m.setHead_portrait(wxuser.getHead_portrait());
				// 微信绑定app用户
				if(res = mobileUserService.bindMobileUser(m, wxuid)){
					binduser.setPassword(null);
					modelMap.put("user", binduser);
				}
			}
		}
		modelMap.put("res", res);
		return modelMap;
	}

}
