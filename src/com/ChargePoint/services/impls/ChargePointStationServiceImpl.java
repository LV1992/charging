package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.ChargePointStationDAO;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.services.ChargePointStationService;
@Transactional
@Service
public class ChargePointStationServiceImpl implements ChargePointStationService{

	@Autowired
	ChargePointStationDAO chargePointStationDao; 

	/**获取chargePointStation列表
	 * @param chargePointStation
	 * @return List ChargePointStation
	 */
	@Override
	public List<ChargePointStation> getChargePointStationList(ChargePointStation chargePointStation){
		return chargePointStationDao.selectChargePointStationList(chargePointStation);
	}
	
	public List<ChargePointStation> searchCPSList(String searchText){
		return chargePointStationDao.searchCPSList(searchText);
	} 
	
	/**获取chargePointStation
	 * @param chargePointStationName
	 * @return List ChargePointStation
	 */
	@Override
	public ChargePointStation getChargePointStation(Integer chargePointStationID){
		ChargePointStation chargePointStation = new ChargePointStation();
		chargePointStation.setId(chargePointStationID);
		return chargePointStationDao.selectChargePointStationList(chargePointStation).get(0);
	}
	
	/**获取未充过电chargePointStation列表
	 *List Integer ids(已充电站id列表),Integer limit
	 */
	@Override
	public List<ChargePointStation> getUnchargeChargePointStation(List<Integer> ids,Integer limit){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("limit", limit);
		return chargePointStationDao.selectUnchargeChargePointStation(map);
	}

	@Override
	public Map<String, Object> getMobileStationByStationID(Integer sid) {
		return chargePointStationDao.selectMobileStationByStationID(sid);
	}

	@Override
	public boolean updateChargePointStation(ChargePointStation chargePointStation) {
		boolean result = false;
		int refC = -1;
		try {
			refC = chargePointStationDao.updateChargePointStation(chargePointStation);
			if(refC > 0 ){
				result = true;
			}
			result = true;
		} catch (Exception e) {
			System.out.println("修改电站出错");
			e.printStackTrace();
		}
		return result;
	}
}
