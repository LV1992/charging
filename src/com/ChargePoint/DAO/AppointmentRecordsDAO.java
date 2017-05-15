package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.AppointmentRecords;

public interface AppointmentRecordsDAO{
	
	public List<AppointmentRecords> selectAppointmentRecordsList(AppointmentRecords appointmentRecords);
	public Integer selectAppointmentStatusCount(Map<String, Object> map);
	public List<Map<String,Object>> selectAppointmentRecordsByPage(Map<String, Object> map);
	public Integer insertAppointmentRecords(AppointmentRecords appointmentRecords);
	public Integer deleteAppointmentRecords(AppointmentRecords appointmentRecords);
	public Integer updateAppointmentRecords(AppointmentRecords appointmentRecords);
	public Integer updateAppointmentRecordsToSuccess(@Param("tableName")String tableName);
	public Integer updateAppointmentRecordsToFail(@Param("tableName")String tableName);
}
