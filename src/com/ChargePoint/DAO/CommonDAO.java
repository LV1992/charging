package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonDAO{
	
	public int selectCount(@Param(value = "tableName")String tableName);
	public int selectCPCount(@Param(value = "type")String type);
	public List<String> selectTableNames(@Param(value = "tableName")String tableName);
	public List<String> selectRegxpTableNames(@Param(value = "regxp")String regxp);
	public String selectMaxData(Map<String,String> map);
	public void dynamicSql(@Param(value = "sql")String sql);
//	update/delete，返回值是：更新或删除的行数；
	public void createChargeRecord(@Param(value = "tableName")String tableName);
	public void createOperation(@Param(value = "tableName")String tableName);
	public void createOperationResults(@Param(value = "tableName")String tableName);
	public void createAppointmentRecords(@Param(value = "tableName")String tableName);
	/**
	 * emoji表情支持
	 */
	public void updateUTF8mb();
	
}