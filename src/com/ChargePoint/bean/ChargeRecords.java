package com.ChargePoint.bean;

import java.io.Serializable;

public class ChargeRecords implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;		
	private Integer station_id;
	private String user_name;
	private String c_p_id;
	private String start_time;		
	private String end_time;
	private String time_count;// total time
	private Float degree; //度数
	private Float total_degree;//累计充电
	private Integer total_count;//累计充电次数
	private String place;//充电位置
	private Float money;//充电花费金额
	private String trade_no;
	private String trade_status;//0 - 未支付 1 - 已支付
	private String is_update;//0 - 未更新 1 - 已更新
	private String charge_method;//0 - app充电 1 - 刷卡充电
	
	public Integer getStation_id() {
		return station_id;
	}
	public void setStation_id(Integer station_id) {
		this.station_id = station_id;
	}
	public String getTable_name() {
		return table_name;
	}
	/**"charge_records_"+uid
	 * @param table_name
	 */
	public void setTable_name(Integer uid) {
		this.table_name = "charge_records_"+uid;
	}
	
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
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public String getStart_time() {
		if(null != start_time && -1 != start_time.lastIndexOf("."))
		{
			return start_time.substring(0,start_time.lastIndexOf("."));
		}else{
			return start_time;
		}
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		if(null != end_time && -1 != end_time.lastIndexOf("."))
		{
			return end_time.substring(0,end_time.lastIndexOf("."));
		}else{
			return end_time;
		}
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getTime_count() {
		return time_count;
	}
	public void setTime_count(String time_count) {
		this.time_count = time_count;
	}
	public Float getDegree() {
		return degree;
	}
	public void setDegree(Float degree) {
		this.degree = degree;
	}
	public Float getTotal_degree() {
		return total_degree;
	}
	public void setTotal_degree(Float total_degree) {
		this.total_degree = total_degree;
	}
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	/**
	 * @return 0 - 未支付 1 - 已支付
	 */
	public String getTrade_status() {
		return trade_status;
	}
	/**0 - 未支付 1 - 已支付
	 * @param trade_status
	 */
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	/**
	 * @return 0 - 未更新 1 - 已更新
	 */
	public String getIs_update() {
		return is_update;
	}
	/**0 - 未更新 1 - 已更新
	 */
	public void setIs_update(String is_update) {
		this.is_update = is_update;
	}
	/**
	 * @return 0 - app充电 1 - 刷卡充电
	 */
	public String getCharge_method() {
		return charge_method;
	}
	/**0 - app充电 1 - 刷卡充电
	 */
	public void setCharge_method(String charge_method) {
		this.charge_method = charge_method;
	}
	@Override
	public String toString() {
		return "ChargeRecords [table_name=" + table_name + ", id=" + id + ", station_id=" + station_id + ", user_name="
				+ user_name + ", c_p_id=" + c_p_id + ", start_time=" + start_time + ", end_time=" + end_time
				+ ", time_count=" + time_count + ", degree=" + degree + ", total_degree=" + total_degree
				+ ", total_count=" + total_count + ", place=" + place + ", money=" + money + ", trade_no=" + trade_no
				+ ", trade_status=" + trade_status + ", is_update=" + is_update + ", charge_method=" + charge_method
				+ "]";
	}
	
}
