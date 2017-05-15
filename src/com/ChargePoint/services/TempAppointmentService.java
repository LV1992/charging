package com.ChargePoint.services;

import com.ChargePoint.bean.TempAppointment;

public interface TempAppointmentService {
	/**是否存在TempAppointment
	 * @param tempAppointment
	 * @return boolean true 存在 false 不存在
	 */
	public boolean haveTempAppointment(TempAppointment tempAppointment);
	public Integer addTempAppointment(TempAppointment tempAppointment);
	public boolean deleteTempAppointment(TempAppointment tempAppointment);
	public boolean updateTempAppointment(TempAppointment tempAppointment);
	/**查找正在预约中的充电桩id，取消预约操作使用
	 * @param uid
	 * @return cpid
	 */
	public String getTemappingCPID(Integer uid);
}
