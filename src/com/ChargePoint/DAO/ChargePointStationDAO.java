package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.ChargePointStation;

public interface ChargePointStationDAO{
	
	public List<ChargePointStation> selectChargePointStationList(ChargePointStation chargePointStation);
	public List<ChargePointStation> searchCPSList(@Param("searchText")String searchText);
	public Map<String,Object> selectMobileStationByStationID(@Param("sid")Integer sid);
	public List<ChargePointStation> selectUnchargeChargePointStation(Map<String, Object> map);
	public Integer updateChargePointStation(ChargePointStation chargePointStation);

}
