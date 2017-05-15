package com.ChargePoint.services.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.TempAppointmentDAO;
import com.ChargePoint.bean.TempAppointment;
import com.ChargePoint.services.TempAppointmentService;
@Transactional
@Service
public class TempAppointmentServiceImpl implements TempAppointmentService{

	@Autowired
	private TempAppointmentDAO tempAppointmentDao;

	public boolean haveTempCharge(TempAppointment tempAppointment){
		String res = tempAppointmentDao.haveTempAppointment(tempAppointment);
		if("true".equals(res)){
			return true;
		}
		return false;
	}
	/**查找是否存在此充电缓存
	 * @param tempAppointment
	 * @return true 存在 false 不存在
	 */
	@Override
	public boolean haveTempAppointment(TempAppointment tempAppointment) {
		String res = tempAppointmentDao.haveTempAppointment(tempAppointment);
		if("true".equals(res)){
			return true;
		}
		return false;
	}
	
	/**添加tempAppointment记录
	 * @param tempAppointment
	 * @return Integer
	 */
	public Integer addTempAppointment(TempAppointment tempAppointment){
		int reC = -1;
		try{
			tempAppointmentDao.insertTempAppointment(tempAppointment);
			reC = tempAppointment.getId();
		}catch(Exception e){
			System.out.println("添加tempAppointment出错");
			e.printStackTrace();
		}
		return reC;
	}
	
	/**删除tempAppointment记录
	 * @param tempAppointment
	 * @return boolean
	 */
	public boolean deleteTempAppointment(TempAppointment tempAppointment){
		boolean result = false;
		int reC = -1;
		try{
			reC = tempAppointmentDao.deleteTempAppointment(tempAppointment);
			if(-1 != reC && 0 != reC){
				result = true;
			}
		}catch(Exception e){
			System.out.println("删除tempAppointment出错");
			e.printStackTrace();
		}
		return result;
	}
	
	/**更新tempAppointment记录
	 * @param tempAppointment
	 * @return boolean
	 */
	public boolean updateTempAppointment(TempAppointment tempAppointment){
		boolean result = false;
		int reC = -1;
		try{
			reC = tempAppointmentDao.updateTempAppointment(tempAppointment);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改tempAppointment出错");
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public String getTemappingCPID(Integer uid) {
		return tempAppointmentDao.selectTemappingCPID(uid);
	}

}
