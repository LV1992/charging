package com.ChargePoint.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.Utils.PropertiesUtil;
import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.ChargeRecordsService;
import com.ChargePoint.services.MobileUserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.redis.framework.MyCache;
import com.tenpay.PrepayIdRequestHandler;
import com.tenpay.util.Sha1Util;
import com.tenpay.util.WXUtil;

/**调用微信接口
 */
@Controller
@RequestMapping("/wx")
public class CallWXInterface extends ControllerSupport{
	
	static Logger logger = Logger.getLogger(CallWXInterface.class);
	
	@Autowired
	MyCache myCache;
	@Autowired
	WXUtil wxUtil;
	
	/**获取调用微信接口签名
	 */
	@RequestMapping(value="getSignature", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getSignature(@RequestParam()String url){
		Map<String, Object> dataMap = new HashMap<String, Object>();
//		获取signature等数据返回前端使用
		dataMap = getSignature(PropertiesUtil.getWXConfig("APP_ID_COMMON"), PropertiesUtil.getWXConfig("SECRET_COMMON"),url);
		dataMap.remove("token");
		return dataMap;
	}
	
	@Autowired
	ChargeRecordsService chargeRecordsService;
	/**获取js支付参数
	 * @param uid 用户id
	 * @param request
	 * @param response
	 * @return
	 * "appId" ： "wx2421b1c4370ec43b",     //公众号名称，由商户传入     
            "timeStamp"：" 1395712654",         //时间戳，自1970年以来的秒数     
            "nonceStr" ： "e61463f8efa94090b1f366cccfbbb444", //随机串     
            "package" ： "prepay_id=u802345jgfjsdfgsdg888",     
            "signType" ： "MD5",         //微信签名方式：     
            "paySign" ： "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
	 */
	@RequestMapping(value="getJSPayParam", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public SortedMap<Object,Object> getJSPayParam(@RequestParam()Integer payid,@RequestParam()Integer uid,HttpServletRequest request,HttpServletResponse response){
		SortedMap<Object, Object> dataMap = new TreeMap<Object, Object>();
		PrepayIdRequestHandler prepayIdRequestHandler = new PrepayIdRequestHandler(request, response);
			//查询该用户的未支付订单详情
			ChargeRecords chargeRecords = chargeRecordsService.getChargeRecordByID(uid, payid);
			if(null != chargeRecords){
			Map<String, Object> unpayMap = new HashMap<String, Object>();
			String nonceStr = WXUtil.getNonceStr();
			unpayMap.put("body", chargeRecords.getEnd_time()+"充电消费");
			unpayMap.put("noncestr", nonceStr);
			unpayMap.put("out_trade_no", chargeRecords.getTrade_no());
			String moneyStr = String.valueOf(chargeRecords.getMoney()*100);
			if(null != moneyStr){
				unpayMap.put("money", moneyStr.substring(0, moneyStr.lastIndexOf(".")));
			}
			String unionid = mobileUserService.getMobileUser(uid).getUnionid();
//			从缓冲中取该用户的openid
			String openid = (String) myCache.getObj(unionid);
			if(null == openid){
				openid = mobileUserService.getMobileUser(uid).getOpenid();
			}
			unpayMap.put("openid", openid);
			dataMap = prepayIdRequestHandler.getJSPayData(unpayMap);
			logger.warn("返回前端的js支付参数  :"+dataMap);
			System.out.println("返回前端的js支付参数  :"+dataMap);
//			myCache.put("userInfo", dataMap);
//			myCache.evict("userInfo");
//			myCache.getObj("");
			}
		return dataMap;
	}
	
	//------------------------调用微信方法-----------------------------------
	
	/**
	 * 获取签名（调用微信接口用到accesstoken）
	 * @param appid
	 * @param secret
	 * @param url
	 * @return Map<String,Object> token,signature,nonceStr,timestamp
	 */
	private Map<String, Object> getSignature(String appid, String secret, String url) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 1.获取accesstoken
		String token = (String) wxUtil.getToken(appid, secret).get("access_token");
		// 2.获取jsapi_ticket有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket
		String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		// 拼接地址https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
		String turl = String.format("%s?access_token=%s&type=jsapi", GET_JSAPI_TICKET_URL, token);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(turl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String ticket = null;
		String signature = null;
		String nonceStr = null;
		String timestamp = null;
		try {
			HttpResponse res = client.execute(get);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			// 将json字符串转换为json对象
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode").getAsInt() == 0) {
					/*
					 * 正常情况下{ "errcode":0, "errmsg":"ok", "ticket":
					 * "bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
					 * "expires_in":7200 }
					 */
					ticket = json.get("ticket").getAsString();
				} else {
					logger.warn("微信授权接口所需ticket失败，原因 " + json);
				}
			}
			// 3、时间戳和随机字符串
			nonceStr = WXUtil.getNonceStr();
			timestamp = WXUtil.getTimeStamp();
			String sha1String = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
					+ url;
			signature = Sha1Util.getSha1(sha1String);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			dataMap.put("token", token);
			dataMap.put("signature", signature);
			dataMap.put("nonceStr", nonceStr);
			dataMap.put("timestamp", timestamp);
			return dataMap;
		}
	}

	/**
	 * 向已关注的用户发送消息
	 * @param appid
	 * @param secret
	 * @param unionid
	 * @return String
	 */
	public Map<String, Object> sendMsgToUser(String appid, String secret, String unionid) {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// ---------------请求发送消息 开始------------------------
		HttpClient client = new DefaultHttpClient();
		String token = (String) wxUtil.getToken(appid, secret).get("access_token");
		if(null != unionid){
			// http请求方式: POST
			String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
			String openid = (String) myCache.getObj(unionid);
			//先从缓存中获取openid，没有再从数据库获取
			if(null == openid){
				MobileUser user = mobileUserService.getMobileUserByUnionID(unionid);
				openid = user.getOpenid();
			}
			System.out.println("openid : -- "+openid);
			HttpPost post = new HttpPost(sendMsgUrl);
			try {
				//请求参数
				List<NameValuePair> formParas = new ArrayList<NameValuePair>();
				formParas.add(new BasicNameValuePair("touser", openid));
				formParas.add(new BasicNameValuePair("msgtype", "text"));
				JsonObject jsonObj = new JsonObject();
				jsonObj.addProperty("content", "测试的消息");;
				formParas.add(new BasicNameValuePair("text", jsonObj.toString()));
				UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParas, Consts.UTF_8);
				//设置请求参数
				post.setEntity(postEntity);
				HttpResponse res = client.execute(post);
				String responseContent = null; // 响应内容
				HttpEntity entity = res.getEntity();
				responseContent = EntityUtils.toString(entity, "utf-8");
				// 将json字符串转换为json对象
				JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
				JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
				if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					if (json.get("errcode").getAsInt() == 0) {
						/*
						 * { "errcode" : 0, "errmsg" : "ok", }
						 */
						dataMap = JsonUtil.jsonToMap(json.toString());
					}
					dataMap = JsonUtil.jsonToMap(json.toString());
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
		// http请求方式: POST
		String addkfaccountUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=" + token;
//		String openid = (String) myCache.getObj(unionid);
		HttpPost post = new HttpPost(addkfaccountUrl);
		try {
			//请求参数
			List<NameValuePair> formParas = new ArrayList<NameValuePair>();
			formParas.add(new BasicNameValuePair("kf_account", "364620185"));
			formParas.add(new BasicNameValuePair("nickname", "巨能坑客服"));
			formParas.add(new BasicNameValuePair("password", "******"));
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(formParas, Consts.UTF_8);
			//设置请求参数
			post.setEntity(postEntity);
			HttpResponse res = client.execute(post);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "utf-8");
			// 将json字符串转换为json对象
			JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode").getAsInt() == 0) {
					/*
					 * { "errcode" : 0, "errmsg" : "ok", }
					 */
					dataMap = JsonUtil.jsonToMap(json.toString());
				}
				dataMap = JsonUtil.jsonToMap(json.toString());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}//end else
		return dataMap;
	}
	
}
