package com.tenpay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ChargePoint.Utils.PropertiesUtil;
import com.redis.framework.MyCache;
import com.tenpay.client.TenpayHttpClient;
import com.tenpay.util.JsonUtil;
import com.tenpay.util.MD5Util;
import com.tenpay.util.Sha1Util;
import com.tenpay.util.TenpayUtil;
import com.tenpay.util.WXUtil;
import com.tenpay.util.XMLUtil;
public class PrepayIdRequestHandler extends RequestHandler {

	Logger logger = Logger.getLogger(PrepayIdRequestHandler.class);
	
	public PrepayIdRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}


	/**
	 * 创建签名SHA1（app支付使用）
	 * 
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	private String createSHA1Sign(SortedMap<Object,Object> parameters) {
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + PropertiesUtil.getWXConfig("APP_KEY"));  
        System.out.println("sign: "+sb.toString());
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();  
        return sign;  
	}
	
	/**
	 * 创建签名（js支付使用）
	 * 
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	private String createSign(SortedMap<Object,Object> parameters) {
		StringBuffer sb = new StringBuffer();  
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
		Iterator it = es.iterator();  
		while(it.hasNext()) {  
			Map.Entry entry = (Map.Entry)it.next();  
			String k = (String)entry.getKey();  
			Object v = entry.getValue();  
			if(null != v && !"".equals(v)   
					&& !"sign".equals(k) && !"key".equals(k)) {  
				sb.append(k + "=" + v + "&");  
			}  
		}  
		sb.append("key=" + PropertiesUtil.getWXConfig("APP_KEY_COMMON"));  
		System.out.println("sign: "+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();  
		return sign;  
	}

	// 提交预支付（app使用返回prepayid）
	public String sendPrepay(String jsonStr) throws JSONException {
		
		//---------------生成订单号 开始------------------------
		//当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
		String out_trade_no = strReq;
		//---------------生成订单号 结束------------------------
		
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("appid", PropertiesUtil.getWXConfig("APP_ID"));  
        parameters.put("body", JsonUtil.getJsonValue(jsonStr, "body")); 
        parameters.put("mch_id", PropertiesUtil.getWXConfig("MCH_ID")); 
        String noncestr = WXUtil.getNonceStr();
        parameters.put("nonce_str", noncestr); 
        parameters.put("notify_url", "http://mobile.chinajune.com/charging/notifyUrl"); 
        parameters.put("out_trade_no", out_trade_no);  
        parameters.put("spbill_create_ip", "127.0.0.1");  
        parameters.put("total_fee", JsonUtil.getJsonValue(jsonStr, "money"));  
        parameters.put("trade_type", "APP"); 
        //组合xml需要sign
        String sign=this.createSHA1Sign(parameters);
        parameters.put("sign", sign);
		
		String prepayid = "";
		
		StringBuffer sb = new StringBuffer("<xml>");
		Set es = parameters.entrySet();
		System.out.println(es);
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) ) {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("<sign>"+sign+"</sign></xml>");
		String params = "";
		try {
			params = new String(sb.toString().getBytes("UTF-8"), "GBK");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		System.out.println("支付参数 详情：  "+params);

		String requestUrl = super.getGateUrl();
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
				+ requestUrl);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
			Map m=new HashMap();
			try {
				m=XMLUtil.doXMLParse(resContent);
				System.out.println(m);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(m.get("result_code")!=null){
				if (m.get("result_code").toString().equals("SUCCESS")) {
					prepayid = m.get("prepay_id").toString();
				}
			}
			try {
				resContent = new String(resContent.getBytes("GBK"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:"
					+ resContent);
		}
		return prepayid;
	}
	
	
	/**提交预支付（js支付公众号使用返回prepayid）
	 * @param paramMap
	 *  body : "",
	 *  noncestr : "",
	 *  out_trade_no : "",
	 *  money : ""
	 *  openid : ""
	 * @return String prepayId
	 */
	private String getPrepayId(Map<String,Object> paramMap) {
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
		parameters.put("appid", PropertiesUtil.getWXConfig("APP_ID_COMMON"));
		parameters.put("body", paramMap.get("body")); 
		parameters.put("mch_id", PropertiesUtil.getWXConfig("MCH_ID_COMMON")); 
		parameters.put("nonce_str", paramMap.get("noncestr")); 
		parameters.put("notify_url", "http://120.76.65.195/ChargePile/mobile/notifyUrl"); 
		parameters.put("out_trade_no", paramMap.get("out_trade_no"));  
		parameters.put("spbill_create_ip", "127.0.0.1");  
		parameters.put("total_fee", paramMap.get("money"));  
		parameters.put("openid", paramMap.get("openid"));  
		parameters.put("trade_type", "JSAPI"); 
		//组合xml需要sign
		String sign=this.createSign(parameters);
		
		String prepayid = "";
		
		StringBuffer sb = new StringBuffer("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) ) {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("<sign>"+sign+"</sign></xml>");
		String params = "";
		try {
			params = new String(sb.toString().getBytes("UTF-8"),"GBK");
			logger.warn("请求的xml：  "+params);
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		System.out.println("支付参数 详情：  "+params);
		logger.warn("支付参数 详情：  "+params);
		String requestUrl = super.getGateUrl();
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
				+ requestUrl);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
			Map m=new HashMap();
			try {
				m=XMLUtil.doXMLParse(resContent);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(m.get("result_code")!=null){
				if (m.get("result_code").toString().equals("SUCCESS")) {
					prepayid = m.get("prepay_id").toString();
				}
			}
			try {
				resContent = new String(resContent.getBytes("GBK"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:"
					+ resContent);
			System.out.println("resContent--"+resContent);
		}
		System.out.println("prepayid "+prepayid);
		logger.warn("提交预支付（js支付公众号使用返回的prepayid）  "+prepayid);
		return prepayid;
	}
	
	/**生成微信js支付所需
		 "appId" : "1105709980", //公众号名称，由商户传入     
           "timeStamp" : " 1395712654",  //时间戳，自1970年以来的秒数     
           "nonceStr" : "e61463f8efa94090b1f366cccfbbb444",//随机串     
           "package" : "prepay_id=u802345jgfjsdfgsdg888",     
           "signType" : "MD5",//微信签名方式：     
           "paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
		等数据
	 * @param paramMap
	 * @return
	 */
	public SortedMap<Object,Object> getJSPayData(Map<String,Object> paramMap){
		Map<String, Object> resMap = new HashMap<String,Object>();
		SortedMap<Object,Object> sortedMap = new TreeMap<Object,Object>();  
		String prepayId = getPrepayId(paramMap);
		sortedMap.put("appId", PropertiesUtil.getWXConfig("APP_ID_COMMON"));
		sortedMap.put("timeStamp", WXUtil.getTimeStamp());
		sortedMap.put("nonceStr", WXUtil.getNonceStr());
		sortedMap.put("package" , "prepay_id="+prepayId);
		sortedMap.put("signType" , "MD5");
		String sign=this.createSign(sortedMap);
//		sortedMap.get("appId");
//		sortedMap.get("timeStamp");
//		sortedMap.get("nonceStr");
//		sortedMap.get("package");
//		sortedMap.get("signType");
//		sortedMap.put("appId", PropertiesUtil.getWXConfig("APP_ID_COMMON"));
//		resMap.put("paySign" , sign);
		sortedMap.put("paySign", sign);
		logger.warn("提交预支付（js支付公众号使用返回的json数据）  "+sortedMap);
		return sortedMap;
	}

}
