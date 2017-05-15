package com.ChargePoint.bean;

import java.io.Serializable;

public class WriteBack  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private Integer station_id;
	private Integer review_uid;//评论者用户id
	private String content;
	private String time;
	private Integer write_back_uid;//回复者用户id
	private Integer review_id;//回复列表id
	private String is_read;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getWrite_back_uid() {
		return write_back_uid;
	}
	public void setWrite_back_uid(Integer write_back_uid) {
		this.write_back_uid = write_back_uid;
	}
	public Integer getReview_id() {
		return review_id;
	}
	public void setReview_id(Integer review_id) {
		this.review_id = review_id;
	}
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	@Override
	public String toString() {
		return "WriteBack [id=" + id + ", station_id=" + station_id + ", review_uid=" + review_uid + ", content="
				+ content + ", time=" + time + ", write_back_uid=" + write_back_uid + ", review_id=" + review_id
				+ ", is_read=" + is_read + "]";
	}
	
}
