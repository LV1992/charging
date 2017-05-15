package com.ChargePoint.bean;

import java.io.Serializable;

public class ChargePointStation  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private Integer company_id;
	private String name;
	private String place;
	private String location;//纬度，经度
	private Integer c_p_count;
	private Integer c_p_free_count;
	private Integer ac1_count;
	private Integer ac2_count;
	private Integer dc1_count;
	private Integer dc2_count;
	private String start_time;
	private String end_time;
	private String time;//建站时间
	private String parking_fee;//停车费
	private String service_fee;//服务费
	private String other_introduce;
	private String picture;//照片文件名
	private String tel;
	private String status;//充电站状态 0-未投运 1-已投运 2-维修中
	private String owner;//责任人
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return 纬度，经度
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location 纬度，经度
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Integer getC_p_count() {
		return c_p_count;
	}
	public void setC_p_count(Integer c_p_count) {
		this.c_p_count = c_p_count;
	}
	public Integer getC_p_free_count() {
		return c_p_free_count;
	}
	public void setC_p_free_count(Integer c_p_free_count) {
		this.c_p_free_count = c_p_free_count;
	}
	public Integer getAc1_count() {
		return ac1_count;
	}
	public void setAc1_count(Integer ac1_count) {
		this.ac1_count = ac1_count;
	}
	public Integer getAc2_count() {
		return ac2_count;
	}
	public void setAc2_count(Integer ac2_count) {
		this.ac2_count = ac2_count;
	}
	public Integer getDc1_count() {
		return dc1_count;
	}
	public void setDc1_count(Integer dc1_count) {
		this.dc1_count = dc1_count;
	}
	public Integer getDc2_count() {
		return dc2_count;
	}
	public void setDc2_count(Integer dc2_count) {
		this.dc2_count = dc2_count;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getParking_fee() {
		return parking_fee;
	}
	public void setParking_fee(String parking_fee) {
		this.parking_fee = parking_fee;
	}
	public String getService_fee() {
		return service_fee;
	}
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}
	public String getOther_introduce() {
		return other_introduce;
	}
	public void setOther_introduce(String other_introduce) {
		this.other_introduce = other_introduce;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String[] getPictureList() {
		if(null != this.picture){
			if(-1 != this.picture.indexOf("#")){
				return picture.split("#");
			}else{
				return picture.split(" ");
			}
		}else{
			return null;
		}
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**充电站状态 0-未投运 1-已投运 2-维修中
	 */
	public String getStatus() {
		return status;
	}
	/**充电站状态 0-未投运 1-已投运 2-维修中
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	@Override
	public String toString() {
		return "ChargePointStation [id=" + id + ", company_id=" + company_id + ", name=" + name + ", place=" + place
				+ ", location=" + location + ", c_p_count=" + c_p_count + ", c_p_free_count=" + c_p_free_count
				+ ", ac1_count=" + ac1_count + ", ac2_count=" + ac2_count + ", dc1_count=" + dc1_count + ", dc2_count="
				+ dc2_count + ", start_time=" + start_time + ", end_time=" + end_time + ", time=" + time
				+ ", parking_fee=" + parking_fee + ", service_fee=" + service_fee + ", other_introduce="
				+ other_introduce + ", picture=" + picture + ", tel=" + tel + ", status=" + status + ", owner=" + owner
				+ "]";
	}
}
