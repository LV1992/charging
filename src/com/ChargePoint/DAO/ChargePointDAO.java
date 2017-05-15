package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.ChargePoint;

public interface ChargePointDAO{
	
	public List<ChargePoint> selectChargePointList(ChargePoint chargePoint);
	public Integer selectChargePointListCount(ChargePoint chargePoint);
	public ChargePoint selectChargePoint(@Param("c_p_id")String c_p_id);
	public List<ChargePoint> selectChargePointByPage(Map<String, Object> map);
	public Integer updateChargePoint(ChargePoint chargePoint);
}
