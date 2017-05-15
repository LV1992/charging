package com.ChargePoint.bean;

import java.io.Serializable;

public class AppointmentRecords  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;
	private String c_p_id;
	private Integer user_id;
	private String start_time;
	private String end_time;
	private String status;//预约状态 0-预约中 1-已失效 2 -成功
	
	public String getTable_name() {
		return table_name;
	}
	/**"appointment_records_"+uid
	 * @param uid
	 */
	public void setTable_name(Integer uid) {
		this.table_name = "appointment_records_"+uid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStatus() {
		return status;
	}
	/**预约状态 0-预约中 1-已失效 2 -预约成功
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AppointmentRecords [table_name=" + table_name + ", id=" + id + ", c_p_id=" + c_p_id + ", user_id="
				+ user_id + ", start_time=" + start_time + ", end_time=" + end_time + ", status=" + status + "]";
	}
	
}
