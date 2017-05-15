package com.ChargePoint.services;

import java.util.List;
import com.ChargePoint.bean.ChargePoint;

public interface ChargePointService {
	
	/**获取所有充电桩列表
	 * @return List ChargePoint
	 */
	public List<ChargePoint> getChargePointList(ChargePoint chargePoint);
	
	/**获取充电桩数量（直流交流、空闲等）
	 * @param chargePoint
	 * @return Integer
	 */
	public Integer getChargePointListCount(ChargePoint chargePoint);
	
	public ChargePoint getChargePoint(String c_p_id);
	
	public List<ChargePoint> getChargePointByPage(Integer limitStart ,Integer limitCount ,String c_p_id,String c_p_type,String sortName,String order);
	
	public boolean updateChargePoint(ChargePoint chargePoint);
	
}
