package com.ChargePoint.services;

import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;

import com.ChargePoint.bean.ChargePointStation;

public interface ChargePointStationService{
	
	/**获取chargePointStation列表
	 * @param chargePointStation
	 * @return List ChargePointStation
	 */
	public List<ChargePointStation> getChargePointStationList(ChargePointStation chargePointStation);
	
	/**查询地图页面电站列表
	 * @param searchText
	 * @return
	 */
	public List<ChargePointStation> searchCPSList(String searchText); 
	/**获取chargePointStation
	 * @param chargePointStationName
	 * @return List ChargePointStation
	 */
	public ChargePointStation getChargePointStation(Integer sid);
	
	/**根据id获取手机电站视图
	 * @return Map<String,Object>
	 */
	public Map<String,Object> getMobileStationByStationID(Integer sid);
	
	/**获取未充过电chargePointStation列表
	 *List Integer ids(已充电站id列表),Integer limit
	 */
	public List<ChargePointStation> getUnchargeChargePointStation(List<Integer> ids,Integer limit);
		
	
	/**更新chargePointStation记录
	 * @param chargePointStation
	 * @return boolean
	 */
	public boolean updateChargePointStation(ChargePointStation chargePointStation);
	
}
