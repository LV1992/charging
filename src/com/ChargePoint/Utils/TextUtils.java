package com.ChargePoint.Utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Administrator
 *
 */
public class TextUtils
{

	/**
	 * 编码格式转换
	 * 
	 * @param oldStr
	 * @return
	 */
	public static String TOUTF8(String oldStr)
	{
		String newStr = oldStr;
		if(null != newStr && !"".equals(newStr)){
		try
		{
			newStr = new String(oldStr.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		}
		return newStr;
	}
	
	/**获取随机字符串
	 * @param int strLength 随机字符串长度
	 * @return String
	 */
	public static String getRandomString(int strLength)
	{
		/****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
		String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";    
		int maxPos = chars.length();
		String newStr = "";
		for (int i = 0; i < strLength; i++) {
			newStr += chars.charAt((int) Math.floor(Math.random() * maxPos));
		}
		return newStr;
	}
	
	/**获取两位小数的String
	 * @param double inMoney
	 * @return String
	 */
	public static String getStringMoney(double inMoney){
		  DecimalFormat df = new DecimalFormat("#.00");
	      if(0.0 != inMoney){
	    	  return df.format(inMoney);
	      }else{
	    	  return "0.00";
	      }
	}
	
	/**获取两位小数的double
	 * @param double inMoney
	 * @return String
	 */
	public static double getDoubleMoney(double inMoney){
			BigDecimal bd = new BigDecimal(inMoney);
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	 private static double rad(double d)
     {
        return d * Math.PI / 180.0;
     }
     
     /** double distance = GetDistance(121.491909,31.233234,121.411994,31.206134);
      * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
      * @param lng1
      * @param lat1
      * @param lng2
      * @param lat2
      * @return
      */
     public static double getDistance(double lng1, double lat1, double lng2, double lat2)
   {
    	final double EARTH_RADIUS = 6378137;
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = (double)Math.round(s * 10000) / 10000000;
        BigDecimal big = new BigDecimal(s);
        return s == 0 ? 0 : big.setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
     }
	
}
