package com.ChargePoint.services;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.AppointmentRecords;

public interface AppointmentRecordsService {
	
	/**获取AppointmentRecords列表
	 * @param AppointmentRecords
	 * @return List AppointmentRecords
	 */
	public List<AppointmentRecords> getAppointmentRecordsList(AppointmentRecords appointmentRecords);
	
	/**获取AppointmentRecords列表预约状态数量
	 * @param uid
	 * @param status
	 * @return
	 */
	public int getAppointmentStatusCount(Integer uid,String status);
	
	
	/**手机分页获取AppointmentRecords列表
	 * @param String tableName
	 * @param status
	 * @param int limitStart
	 * @param int limitCount
	 * @return List Map<String,Object>
	 */
	public List<Map<String,Object>> getAppointmentRecordsByPage(String tableName,String status,Integer limitStart , Integer limitCount);
	
	/**添加AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	public boolean addAppointmentRecords(AppointmentRecords appointmentRecords);
	
	/**删除AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	public boolean deleteAppointmentRecords(AppointmentRecords appointmentRecords);
	
	/**更新AppointmentRecords记录
	 * @param AppointmentRecords
	 * @return boolean
	 */
	public boolean updateAppointmentRecords(AppointmentRecords appointmentRecords);
	
	/**更新AppointmentRecords记录为预约成功
	 * @param Integer uid
	 * @return boolean
	 */
	public boolean updateAppointmentRecordsToSuccess(Integer uid);
	
	/**更新AppointmentRecords记录为预约失败
	 * @param Integer uid
	 * @return boolean
	 */
	public boolean updateAppointmentRecordsToFail(Integer uid);
}
