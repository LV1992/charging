package com.ChargePoint.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.TextUtils;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.services.MobileUserService;
import com.redis.framework.MyCache;
import com.tenpay.util.WXUtil;

@Controller
@RequestMapping("/login")
public class LoginNOut {
	@Autowired
	MyCache myCache;
	@Autowired
	private MobileUserService mobileUserService;
	private Logger logger = Logger.getLogger(LoginNOut.class);
	@RequestMapping(value="getMobileUserPicture", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getMobileUserPicture(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String userName = null;
		String protrait = "";
		userName = TextUtils.TOUTF8(request.getParameter("userName"));
		MobileUser user = mobileUserService.getMobileUser(userName);
		if(null != user){
			protrait = user.getHead_portrait();
		}
//		System.out.println(protrait);
		modelMap.put("protrait", protrait);
		modelMap.put("user", user);
		return modelMap;
	}
	
	@RequestMapping(value="Login", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> Login(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String un = TextUtils.TOUTF8(request.getParameter("userName").trim());
		String pw =	request.getParameter("password").trim();
		MobileUser user = mobileUserService.getMobileUser(un);
		String msg = "";
		if(null != user){
//			解码余额
			MobileUser cU = mobileUserService.getMobileUser(un, pw);
			if(cU != null){
				msg = "成功";
				request.getSession().setAttribute("user", cU);
//				cU.setMoney(new TradeUtils().getMoney(cU.getId()));
				modelMap.put("user", cU);
			}else{
				msg = "密码错误";
			}
		}else{
			msg = "账号不存在";
		}
		modelMap.put("msg", msg);
		return modelMap;
	}

	/**微信自动登录(获取调用微信(获取用户信息并写数据库)授权登录)
	 * 缓存为 key - unionid value - openid
	 * @param String code
	 * @return
	 */
	@RequestMapping(value="autoLogin", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> autoLogin(@RequestParam()String code){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Map<String, Object> userMap = WXUtil.getUserInfo(code);
		MobileUser aU = null;
		System.out.println("前端传来的 code "+code);
		System.out.println("微信接口返回的 userMap "+userMap);
		//微信返回用户信息时写数据库
				if(null != userMap){
				
				//0- 男 1-女   (WX-1时是男性，值为2时是女性，值为0时是未知)
				Integer sex = (Integer) userMap.get("sex");
				String gender = "";
				String nickname = (String) userMap.get("nickname");
				String headimgurl = (String) userMap.get("headimgurl");
				String unionid = (String) userMap.get("unionid");
				//获取openid并存入缓存key - unionid value - openid（js支付使用）
				String openid = (String)userMap.get("openid");
//				不存在缓存数据则写缓存
				if(null == myCache.get(unionid)){
					myCache.put(unionid, openid);
					logger.warn("緩存存入数据 key="+unionid+" value="+openid);
				}
				switch (sex){
				case 1: gender = "0";break;
				case 2: gender = "1";break;
				default : gender = "0";
				}
				//已有数据不再添加数据
				aU = mobileUserService.getMobileUserByUnionID(unionid);
				if(aU == null){
					//将数据写入数据库(默认用户名和unionid相同)，下次自动登录
					MobileUser user = new MobileUser();
					user.setGender(gender);
					user.setNick_name(nickname);
					user.setUser_name(unionid);
					user.setUnionid(unionid);
					user.setOpenid(openid);
					user.setHead_portrait(headimgurl);
					mobileUserService.addMobileUser(user);
					aU = mobileUserService.getMobileUser(user.getUser_name());
				}
				}
				aU.setPassword(null);
		modelMap.put("user", aU);
		System.out.println("服务器返回的 数据 "+modelMap.toString());
		return modelMap;
	}
	
	@RequestMapping(value="checkMobileUser", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> checkMobileUser(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean res = false;
		int userID = Integer.parseInt(request.getParameter("uid"));
		String identity = request.getParameter("identity");
		String trueName = request.getParameter("trueName");
		MobileUser mi = mobileUserService.getMobileUser(userID);
//		验证真实姓名和身份证都正确
		if(mi != null && mi.getIdentity().equals(identity) && mi.getTrue_name().equals(trueName)){
			res = true;
		}
		modelMap.put("res", res);
		return modelMap;
	}
	
	@RequestMapping(value="getMobileUserProfile/{userName}", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getUserProfile(HttpServletRequest request,@PathVariable("userName")String userName){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		userName = TextUtils.TOUTF8(userName);
		MobileUser user = mobileUserService.getMobileUser(userName);
//		user.setMoney(new TradeUtils().getMoney(user.getId()));
		user.setPassword(null);
		modelMap.put("user", user);
		return modelMap;
	}
}
