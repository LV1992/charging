package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.AppointmentRecordsDAO;
import com.ChargePoint.bean.AppointmentRecords;
import com.ChargePoint.services.AppointmentRecordsService;
@Transactional
@Service
public class AppointmentRecordsServiceImpl implements AppointmentRecordsService{
	
	@Autowired
	private AppointmentRecordsDAO appointmentRecordsDao;

	/**获取AppointmentRecords列表
	 * @param AppointmentRecords
	 * @return List AppointmentRecords
	 */
	@Override
	public List<AppointmentRecords> getAppointmentRecordsList(AppointmentRecords appointmentRecords){
		return appointmentRecordsDao.selectAppointmentRecordsList(appointmentRecords);
	}
	
	/**获取AppointmentRecords列表预约状态数量
	 * @param uid
	 * @param status
	 * @return
	 */
	@Override
	public int getAppointmentStatusCount(Integer uid,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "appointment_records_"+uid);
		map.put("status", status);
		return appointmentRecordsDao.selectAppointmentStatusCount(map);
	}
	
	/**手机分页获取AppointmentRecords列表
	 * @param String tableName
	 * @param status
	 * @param int limitStart
	 * @param int limitCount
	 * @return List Map<String,Object>
	 */
	@Override
	public List<Map<String,Object>> getAppointmentRecordsByPage(String tableName,String status,Integer limitStart , Integer limitCount){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("status", status);
		map.put("limitStart", limitStart);
		map.put("limitCount", limitCount);
		return appointmentRecordsDao.selectAppointmentRecordsByPage(map);
	}
	
	/**添加AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	@Override
	public boolean addAppointmentRecords(AppointmentRecords appointmentRecords){
		boolean result = false;
		int reC = -1;
		try{
			reC = appointmentRecordsDao.insertAppointmentRecords(appointmentRecords);
			if(-1 != reC && 0 != reC){
				result = true;
			}
		}catch(Exception e){
			System.out.println("添加AppointmentRecords出错");
			e.printStackTrace();
		}
		return result;
	}
	
	/**删除AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	@Override
	public boolean deleteAppointmentRecords(AppointmentRecords appointmentRecords){
		boolean result = false;
		int reC = -1;
		try{
			reC = appointmentRecordsDao.deleteAppointmentRecords(appointmentRecords);
			if(-1 != reC && 0 != reC){
				result = true;
			}
		}catch(Exception e){
			System.out.println("删除AppointmentRecords出错");
			e.printStackTrace();
		}
		return result;
	}

	/**更新AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	@Override
	public boolean updateAppointmentRecords(AppointmentRecords appointmentRecords){
		boolean result = false;
		int reC = -1;
		try{
			reC = appointmentRecordsDao.updateAppointmentRecords(appointmentRecords);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改AppointmentRecords出错");
			e.printStackTrace();
		}
		return result;
	};
	
	/**更新AppointmentRecords记录为预约成功
	 * @param Integer uid
	 * @return boolean
	 */
	@Override
	public boolean updateAppointmentRecordsToSuccess(Integer uid){
		boolean result = false;
		int reC = -1;
		try{
			reC = appointmentRecordsDao.updateAppointmentRecordsToSuccess("appointment_records_"+uid);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改AppointmentRecords出错");
			e.printStackTrace();
		}
		return result;		
	}
	
	/**更新AppointmentRecords记录为预约失败
	 * @param Integer uid
	 * @return boolean
	 */
	@Override
	public boolean updateAppointmentRecordsToFail(Integer uid){
		boolean result = false;
		int reC = -1;
		try{
			reC = appointmentRecordsDao.updateAppointmentRecordsToFail("appointment_records_"+uid);
			if(reC > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改AppointmentRecords出错");
			e.printStackTrace();
		}
		return result;		
	}

}