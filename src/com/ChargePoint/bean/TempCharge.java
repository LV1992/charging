package com.ChargePoint.bean;

import java.io.Serializable;

public class TempCharge  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private String c_p_id;
	private Integer user_id;
	private Integer charger_no;
	
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
	public Integer getCharger_no() {
		return charger_no;
	}
	public void setCharger_no(Integer charger_no) {
		this.charger_no = charger_no;
	}
	@Override
	public String toString() {
		return "TempCharge [id=" + id + ", c_p_id=" + c_p_id + ", user_id=" + user_id + ", charger_no=" + charger_no
				+ "]";
	}
}
