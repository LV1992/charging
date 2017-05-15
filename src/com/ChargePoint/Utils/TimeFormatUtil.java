package com.ChargePoint.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatUtil {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); 
	
	/**获取当前时间的正常格式
	 * @return String now
	 */
	public static String getFormattedNow(){
		Date time = new Date();
		return formatter.format(time);
	}
	
	/**
	 * @param formatedTime
	 * @return -2 error 返回 0 表示时间日期相同 ,返回 1 表示日期1>日期2(预约失效),返回 -1 表示日期1<日期2
	 */
	public static int compare2Now(String formatedTime){
		Date now = new Date();
		Date inDate = null;
		int res = -2;
		try {
			inDate = formatter.parse(formatedTime);
			res = now.compareTo(inDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
