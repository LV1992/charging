package com.ChargePoint.DAO;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.ChargePointLive;

public interface ChargePointLiveDAO{
	
	public HashMap<String, Object> selectCurrentData(@Param(value = "tableName")String tableName);
	public ChargePointLive selectFirstChargePointLive(@Param(value = "tableName")String tableName);
	public Integer insertChargePointLive(ChargePointLive chargePointLive);
	public Integer deleteChargePointLive(ChargePointLive chargePointLive);
	public Integer updateChargePointLive(ChargePointLive chargePointLive);
}
