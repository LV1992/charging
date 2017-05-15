package com.ChargePoint.bean;

import java.io.Serializable;

public class AddHeart implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private Integer station_id;//站id
	private Integer add_heart_uid;//点赞者用户id
	private Integer review_id;//评论列表id
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStation_id() {
		return station_id;
	}
	public void setStation_id(Integer station_id) {
		this.station_id = station_id;
	}
	
	public Integer getAdd_heart_uid() {
		return add_heart_uid;
	}
	public void setAdd_heart_uid(Integer add_heart_uid) {
		this.add_heart_uid = add_heart_uid;
	}
	public Integer getReview_id() {
		return review_id;
	}
	public void setReview_id(Integer review_id) {
		this.review_id = review_id;
	}
	@Override
	public String toString() {
		return "AddHeart [id=" + id + ", station_id=" + station_id + ", add_heart_uid=" + add_heart_uid + ", review_id="
				+ review_id + "]";
	}
}
