package com.ChargePoint.services.impls;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.ChargePointLiveDAO;
import com.ChargePoint.bean.ChargePointLive;
import com.ChargePoint.services.ChargePointLiveService;
@Transactional
@Service
public class ChargePointLiveServiceImpl implements ChargePointLiveService{
	
	@Autowired
	ChargePointLiveDAO chargePointLiveDAO;
	
	/**更新chargePointLive记录
	 * @param chargePointLive
	 * @return boolean
	 */
	@Override
	public boolean updatechargePointLive(ChargePointLive chargePointLive){
		boolean res = false;
		int refC = -1;
		try{
			refC = chargePointLiveDAO.updateChargePointLive(chargePointLive);
			if(refC > 0 ){
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("更新chargePointLive数据失败");
		}
	return res;
	}

	@Override
	public HashMap<String, Object> getCurrentData(String tableName) {
		return chargePointLiveDAO.selectCurrentData(tableName);
	}

	@Override
	public ChargePointLive getFirstChargePointLive(String tableName) {
		return chargePointLiveDAO.selectFirstChargePointLive(tableName);
	}
}
