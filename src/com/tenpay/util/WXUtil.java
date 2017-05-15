package com.tenpay.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.springframework.stereotype.Component;

import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.Utils.PropertiesUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.redis.framework.MyCache;
import com.sun.net.httpserver.HttpsParameters;

@Component
public class WXUtil {
	@Autowired
	MyCache myCache;

	static Logger logger = Logger.getLogger(WXUtil.class);

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	/**
	 * 获取时间戳
	 * 
	 * @return String
	 */
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取token(APP使用、微信接口调用使用)超时才重新获取token
	 * 
	 * @param appid
	 * @param secret
	 * @return Map {"access_token":},{"time":},{"expires_in":}
	 */
	public Map<String, Object> getToken(String appid, String secret) {
		Map<String, Object> accessTokenMap = (Map<String, Object>) myCache.getObj("accessToken");
		if (null != accessTokenMap) {
			Date cachedTime = (Date) accessTokenMap.get("time");
			Date now = new Date();
			long subTime = (now.getTime() - cachedTime.getTime()) / 1000;
			// 在expires_in秒之内则取缓存值
			if (subTime < Long.parseLong((String)accessTokenMap.get("expires_in"))) {
				return accessTokenMap;
			} else {// 超时则重新获取accessToken
			}
		} else {// 没有缓存也重新获取token
		}

		// 获取access
		String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
		// 拼接地址https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
		String turl = String.format("%s?grant_type=client_credential&appid=%s&secret=%s", GET_TOKEN_URL, appid, secret);
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(turl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpResponse res = client.execute(get);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			// 将json字符串转换为json对象
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid
													// appid"}
					result = null;
					logger.warn("调用接口获取access_token出错，错误原因：" + json.toString());
				} else {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
					result.put("access_token", json.get("access_token").getAsString());
					result.put("expires_in", json.get("expires_in").getAsString());
					result.put("time", new Date());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			// 将返回的access_token 和 time 保存到缓存
			if (null != result) {
				myCache.put("accessToken", result);
				logger.warn("已将accessToken缓存--" + result);
			}
			return result;
		}
	}

	/**
	 * 获取token(微信网页获取用户信息使用)
	 * 
	 * @param code(获取code，前端获取
	 *            String callwxurl =
	 *            "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+PropertiesUtil.getWXConfig("APP_ID_COMMON")+"&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	 * @return {access_token:,openid:,unionid:}
	 */
	private static Map<String, Object> getToken(String code) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 通过code换取网页授权access_token
		String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ PropertiesUtil.getWXConfig("APP_ID_COMMON") + "&secret=" + PropertiesUtil.getWXConfig("SECRET_COMMON")
				+ "&code=" + code + "&grant_type=authorization_code";
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(getAccessTokenUrl);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String access_token = null;
		String openid = null;
		String unionid = null;
		try {
			HttpResponse res = client.execute(get);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			// 将json字符串转换为json对象
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode") != null) {// 错误时微信会返回错误码等信息，{"errcode":40029,"errmsg":"invalid
													// code"}
					logger.warn("微信授权获取accessToken失败，原因 " + json);
				} else {/*
						 * 正确时返回的JSON数据包 {
						 * "access_token":"ACCESS_TOKEN",网页授权接口调用凭证,注意：
						 * 此access_token与基础支持的access_token不同
						 * "expires_in":7200,access_token接口调用凭证超时时间，单位（秒）
						 * "refresh_token":"REFRESH_TOKEN",用户刷新access_token
						 * "openid":"OPENID",用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，
						 * 也会产生一个用户和公众号唯一的OpenID
						 * "scope":"SCOPE",用户授权的作用域，使用逗号（,）分隔 "unionid":
						 * "o6_bmasdasdsad6_2sgVt7hMZOPfL" }
						 */
					access_token = json.get("access_token").getAsString();
					openid = json.get("openid").getAsString();
					unionid = json.get("unionid").getAsString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			dataMap.put("access_token", access_token);
			dataMap.put("openid", openid);
			dataMap.put("unionid", unionid);
			System.out.println("获取的accessToken等" + dataMap + " code " + code);
			return dataMap;
		}
	}

	/**
	 * 获取用户数据
	 * 
	 * @return Map String,Object "openid":" OPENID", " nickname": NICKNAME,
	 *         "sex":"1", "province":"PROVINCE" "city":"CITY",
	 *         "country":"COUNTRY", "headimgurl": "privilege":[ "PRIVILEGE1",
	 *         "PRIVILEGE2"], "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
	 */
	public static Map<String, Object> getUserInfo(String code) {
		Map<String, Object> dataMap = null;
		Map<String, Object> responseMap = getToken(code);
		HttpClient client = new DefaultHttpClient();
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + responseMap.get("access_token")
				+ "&openid=" + responseMap.get("openid") + "&lang=zh_CN";
		try {
			HttpGet get = new HttpGet(getUserInfoUrl);
			HttpResponse res = client.execute(get);
			String responseContent = null; // 响应内容
			HttpEntity entity = res.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
			// 将json字符串转换为json对象
			JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				if (json.get("errcode") == null) {
					/*
					 * 正确时返回的JSON数据包{ "openid":" OPENID", " nickname": NICKNAME,
					 * "sex":"1", "province":"PROVINCE" "city":"CITY",
					 * "country":"COUNTRY", "headimgurl":
					 * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
					 * "privilege":[ "PRIVILEGE1" "PRIVILEGE2" ], "unionid":
					 * "o6_bmasdasdsad6_2sgVt7hMZOPfL" }
					 */
					dataMap = JsonUtil.jsonToMap(json.toString());
				} else {
					logger.warn("微信授权获取用户信息失败，原因 " + json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接 ,释放资源
			client.getConnectionManager().shutdown();
			return dataMap;
		}
	}

	/**
	 * 生成随机订单号
	 * 
	 * @return String
	 */
	public static String getTradeNO() {
		// ---------------生成订单号 开始------------------------
		// 当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 12位序列号,可以自行调整。
		String out_trade_no = strTime + strRandom;
		// ---------------生成订单号 结束------------------------
		return out_trade_no;
	}

}
