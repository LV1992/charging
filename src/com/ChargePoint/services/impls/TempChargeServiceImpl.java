package com.ChargePoint.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.TempChargeDAO;
import com.ChargePoint.bean.TempCharge;
import com.ChargePoint.services.TempChargeService;
@Transactional
@Service
public class TempChargeServiceImpl implements TempChargeService{

	@Autowired
	private TempChargeDAO tempChargeDao;


	@Override
	public boolean haveTempCharge(TempCharge tempCharge) {
		String res = tempChargeDao.haveTempCharge(tempCharge);
		if("true".equals(res)){
			return true;
		}
		return false;
	}
	
	@Override
	public TempCharge getTempCharge(TempCharge tempCharge) {
		return tempChargeDao.selectTempCharge(tempCharge);
	}
	
	public Integer addTempCharge(TempCharge tempCharge) {
		int reC = -1;
		try{
			tempChargeDao.insertTempCharge(tempCharge);
			reC = tempCharge.getId();
		}catch(Exception e){
			System.out.println("添加tempCharge出错");
			e.printStackTrace();
		}
		return reC;
	}

	/**
	 * 删除tempCharge记录
	 * 
	 * @param tempCharge
	 * @return boolean
	 */
	public boolean deleteTempCharge(TempCharge tempCharge) {
		boolean result = false;
		int reC = -1;
		try{
			reC = tempChargeDao.deleteTempCharge(tempCharge);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("删除tempCharge出错");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新tempCharge记录
	 * 
	 * @param tempCharge
	 * @return boolean
	 */
	public boolean updateTempCharge(TempCharge tempCharge) {
		boolean result = false;
		int reC = -1;
		try{
			reC = tempChargeDao.updateTempCharge(tempCharge);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改tempCharge出错");
			e.printStackTrace();
		}
		return result;
	}

}
