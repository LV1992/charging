package com.ChargePoint.Utils;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionTool {
	
	/**设置session
	 * @param request
	 * @param String name attribute—name
	 * @param object value attribute—value
	 */
	
	public static void addSession(HttpServletRequest request,String name,Object value){
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}
	
	 /**根据session值
	 * @param request
	 * @param name
	 * @return object
	 */
	public static Object getAttribute(HttpServletRequest request,String name){
		HttpSession session = request.getSession();
		return session.getAttribute(name);
	}
	
	/**根据session中用户名值
	 * @param request
	 * @return String
	 */
	public static String getSessionUserName(HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = (String)session.getAttribute("userName");
		if(null != userName){
			userName = TextUtils.TOUTF8(userName);
		}
		return userName;
	}
	
	/**根据access中用户名值
	 * @param request
	 * @return String
	 */
	public static String getSessionAccess(HttpServletRequest request){
		HttpSession session = request.getSession();
		String access;
		String accessT = (String)session.getAttribute("access");
		switch(accessT){
		case "0" :  access = "管理员";break;
		case "1" :  access = "运营公司管理者";break;
		default : access = "运营公司职员";
		}
		return access;
	}
	 
	 /**清空session
	 * @param request
	 */
	public static void clearSession(HttpServletRequest request){ 
		HttpSession session = request.getSession();
		  Enumeration<String> enumer = session.getAttributeNames();
			while(enumer.hasMoreElements()){
				session.removeAttribute(enumer.nextElement().toString());
			}
	}
}
