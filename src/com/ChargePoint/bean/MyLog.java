package com.ChargePoint.bean;

import java.io.Serializable;

import com.ChargePoint.Utils.TimeFormatUtil;

public class MyLog  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private String operator;
	private String details;
	private String result;
	private String time;
	
	public MyLog(Integer id, String operator, String details, String result, String time) {
		super();
		this.id = id;
		this.operator = operator;
		this.details = details;
		this.result = result;
		this.time = time;
	}
	
	public MyLog() {
		super();
	}

	public MyLog(String operator, String details, String result) {
		super();
		this.operator = operator;
		this.details = details;
		this.result = result;
		this.time = TimeFormatUtil.getFormattedNow();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTime() {
		if(time != null && -1 != time.indexOf(":")){
			time = time.substring(0,time.lastIndexOf(":"));
		}
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "MyLog [id=" + id + ", operator=" + operator + ", details=" + details + ", result=" + result + ", time="
				+ time + "]";
	}
}
