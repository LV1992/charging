package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.ChargeRecords;

public interface ChargeRecordsDAO{
	
	public List<ChargeRecords> selectChargeRecordsList(ChargeRecords ChargeRecords);

	public Map<String, Object> selectChargeRecordsByID(Map<String,Object> map);
	
	public ChargeRecords selectChargeRecordByID(Map<String,Object> map);

	public ChargeRecords selectUpdateRecord(@Param("tableName")String tableName);

	public ChargeRecords selectUnUpdateRecord(@Param("tableName")String tableName);

	public List<Map<String, Object>> selectChargeRecordsByPage(Map<String,Object> map);

	public List<ChargeRecords> searchChargeRecords(Map<String,String> parameterObject);
	
	public ChargeRecords selectNONPaymentChargeRecord(@Param("tableName")String tableName);
	
	public List<Integer> selectChargeRecordsWithDisStationID(Map<String,Object> parameterObject);
	
	public int selectChargeRecordsCountByStationID(Map<String,Object> parameterObject);

	public Integer insertChargeRecords(ChargeRecords ChargeRecords);

	public Integer deleteChargeRecords(ChargeRecords ChargeRecords);
	
	public Integer updateChargeRecords(ChargeRecords chargeRecords);
	
	public Integer updateChargeRecordToPay(Map<String,Object> map);
	
	
}
