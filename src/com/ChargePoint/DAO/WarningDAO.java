package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.Warning;

public interface WarningDAO{
	
	public List<Warning> selectWarningList(Warning Warning);
	public List<Warning> selectWarningByPage(Map<String, Object> map);
	public List<Warning> selectAllWarningByPage(Map<String, Object> map);
	public Integer updateWarning(Warning Warning);

}
