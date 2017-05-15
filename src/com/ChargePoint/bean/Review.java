package com.ChargePoint.bean;

import java.io.Serializable;

public class Review  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private Integer station_id;
	private Integer review_uid;
	private String content;
	private Integer score;
	private String time;
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
	public Integer getReview_uid() {
		return review_uid;
	}
	public void setReview_uid(Integer review_uid) {
		this.review_uid = review_uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", station_id=" + station_id + ", review_uid=" + review_uid + ", content=" + content
				+ ", score=" + score + ", time=" + time + "]";
	}
	
}
