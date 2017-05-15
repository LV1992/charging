package com.ChargePoint.Utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	static Properties pro = null;
	public static String getProtityValueAsUrl(String loadUrl,String input) {
		pro = new Properties();
		String res = "";
		try {
			pro.load(PropertiesUtil.class.getResourceAsStream(loadUrl));
		} catch (IOException e) {
			System.out.println(loadUrl+"文件未找到");
			e.printStackTrace();
		}
		if(null != pro){
			res = pro.getProperty(input);
		}
		return res;
	}
	
	public static String getWXConfig(String propertyKey) {
		pro = new Properties();
		String res = "";
		try {
			pro.load(PropertiesUtil.class.getResourceAsStream("/resources/wx-configuration.properties"));
		} catch (IOException e) {
			System.out.println("WX-configuration.properties文件未找到");
			e.printStackTrace();
		}
		if(null != pro){
			res = pro.getProperty(propertyKey);
		}
		return res;
	}
	
}
