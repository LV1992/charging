package com.ChargePoint.services.impls;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.ChargePointACLiveDAO;
import com.ChargePoint.bean.ChargePointACLive;
import com.ChargePoint.services.ChargePointACLiveService;
@Transactional
@Service
public class ChargePointACLiveServiceImpl implements ChargePointACLiveService{
	
	@Autowired
	ChargePointACLiveDAO chargePointACLiveDao;
	
	/**更新chargePointLive记录
	 * @param chargePointLive
	 * @return boolean
	 */
	@Override
	public boolean updateChargePointACLive(ChargePointACLive chargePointLive){
		boolean res = false;
		int refC = -1;
		try{
			refC = this.chargePointACLiveDao.updateChargePointACLive(chargePointLive);
			if(refC > 0 ){
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("更新chargePointACLive数据失败");
		}
	return res;
	}

	@Override
	public HashMap<String, Object> getCurrentData(String tableName) {
		return chargePointACLiveDao.selectCurrentData(tableName);
	}

	@Override
	public ChargePointACLive getFirstChargePointACLive(String tableName) {
		return chargePointACLiveDao.selectFirstChargePointACLive(tableName);
	}
	
	@Override
	public ChargePointACLive getACLiveEndData(String tableName) {
		return chargePointACLiveDao.selectACLiveEndData(tableName);
	}
	@Override
	public ChargePointACLive getAutoEndData(String tableName) {
		return chargePointACLiveDao.selectAutoEndData(tableName);
	}
}
