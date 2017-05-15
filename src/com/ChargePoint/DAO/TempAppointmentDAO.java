package com.ChargePoint.DAO;

import com.ChargePoint.bean.TempAppointment;

public interface TempAppointmentDAO{
	
	public String haveTempAppointment(TempAppointment tempAppointment);
	public Integer insertTempAppointment(TempAppointment tempAppointment);
	public Integer deleteTempAppointment(TempAppointment tempAppointment);
	public Integer updateTempAppointment(TempAppointment tempAppointment);
	public String selectTemappingCPID(Integer uid);
	
}
