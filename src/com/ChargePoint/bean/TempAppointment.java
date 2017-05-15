package com.ChargePoint.bean;

import java.io.Serializable;

public class TempAppointment  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private String start_time;
	private String c_p_id;
	private Integer user_id;
	private String end_time;
	
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
	@Override
	public String toString() {
		return "TempAppointment [id=" + id + ", start_time=" + start_time + ", c_p_id=" + c_p_id + ", user_id="
				+ user_id + ", end_time=" + end_time + "]";
	}
	
}
