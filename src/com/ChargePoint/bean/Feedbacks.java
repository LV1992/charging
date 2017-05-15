package com.ChargePoint.bean;

import java.io.Serializable;

public class Feedbacks  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private String user_name;
	private String content;
	private String version;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", user_name=" + user_name + ", content=" + content + ", version=" + version
				+ "]";
	}
	
}
