package com.ChargePoint.services;

import java.util.List;
import com.ChargePoint.bean.MyLog;

public interface MyLogService {
	
	public List<MyLog> getMyLogList(MyLog MyLog);
	public List<MyLog> getMyLogByPage(Integer startIndex,Integer pageSize,String startTime,String endTime,String searchText,String sortName,String order);
	public Integer addMyLog(MyLog myLog);
	public boolean deleteMyLog(Integer id);
	public boolean updateMyLog(MyLog myLog);
	
}
