package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.MyLog;

public interface MyLogDAO{
	
	public List<MyLog> selectMyLogList(MyLog myLog);
	public List<MyLog> selectMyLogByPage(Map<String, Object> map);
	public Integer insertMyLog(MyLog myLog);
	public Integer deleteMyLog(Integer id) ;
	public Integer updateMyLog(MyLog myLog);

}
