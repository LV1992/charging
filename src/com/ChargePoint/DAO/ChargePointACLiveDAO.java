package com.ChargePoint.DAO;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.ChargePointACLive;

public interface ChargePointACLiveDAO{
	
	public HashMap<String,Object> selectCurrentData(@Param("tableName")String tableName);
	public ChargePointACLive selectFirstChargePointACLive(@Param("tableName")String tableName);
	public ChargePointACLive selectACLiveEndData(@Param("tableName")String tableName);
	public ChargePointACLive selectAutoEndData(@Param("tableName")String tableName);
	public Integer updateChargePointACLive(ChargePointACLive chargePointLive);

}
