package com.ChargePoint.bean;

import java.io.Serializable;

public class Operate  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;
	private String operate_time;
	private String operate_type;// 1：开启充电 2：停止充电 3：预约 4：取消预约 	
	private String charge_method;//0：非充电 1：自动充满 2：定量充 3：定金额充 4：定时间充		
	private Integer charger_no;//充电枪编号		
	private String for_energy;
	private String for_time;
	private String for_money;
	private String order_start_time;
	private String order_end_time;
	private String order_time;//预约时长
	private String c_p_id;
	private Integer user_id;
	private String is_send;//Y-已发送，N-未发送
	
	public String getTable_name() {
		return table_name;
	}
	/**"operation_"+user_id
	 * @param user_id
	 */
	public void setTable_name(String user_id) {
		this.table_name = "operation_"+user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	/**
	 * @return  1：开启充电 2：停止充电 3：预约 4：取消预约  
	 */
	public String getOperate_type() {
		return operate_type;
	}
	/** 1：开启充电 2：停止充电 3：预约 4：取消预约  
	 * @param operate_type
	 */
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	/**
	 * @return 0：非充电 1：自动充满 2：定量充 3：定金额充 4：定时间充
	 */
	public String getCharge_method() {
		return charge_method;
	}
	/**0：非充电 1：自动充满 2：定量充 3：定金额充 4：定时间充			
	 * @param charge_method
	 */
	public void setCharge_method(String charge_method) {
		this.charge_method = charge_method;
	}
	
	public Integer getCharger_no() {
		return charger_no;
	}
	public void setCharger_no(Integer charger_no) {
		this.charger_no = charger_no;
	}
	public String getFor_energy() {
		return for_energy;
	}
	public void setFor_energy(String for_energy) {
		this.for_energy = for_energy;
	}
	public String getFor_time() {
		return for_time;
	}
	public void setFor_time(String for_time) {
		this.for_time = for_time;
	}
	public String getFor_money() {
		return for_money;
	}
	public void setFor_money(String for_money) {
		this.for_money = for_money;
	}
	public String getOrder_start_time() {
		if(null != order_start_time && -1 != order_start_time.lastIndexOf("."))
		{
			return order_start_time.substring(0,order_start_time.lastIndexOf("."));
		}else{
			return order_start_time;
		}
	}
	public void setOrder_start_time(String order_start_time) {
		this.order_start_time = order_start_time;
	}
	public String getOrder_end_time() {
		if(null != order_end_time && -1 != order_end_time.lastIndexOf("."))
		{
			return order_end_time.substring(0,order_end_time.lastIndexOf("."));
		}else{
			return order_end_time;
		}
	}
	public void setOrder_end_time(String order_end_time) {
		this.order_end_time = order_end_time;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
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
	public String getIs_send() {
		return is_send;
	}
	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	@Override
	public String toString() {
		return "Operate [table_name=" + table_name + ", id=" + id + ", operate_time=" + operate_time
				+ ", operate_type=" + operate_type + ", charge_method=" + charge_method + ", charger_no=" + charger_no
				+ ", for_energy=" + for_energy + ", for_time=" + for_time + ", for_money=" + for_money
				+ ", order_start_time=" + order_start_time + ", order_end_time=" + order_end_time + ", order_time="
				+ order_time + ", c_p_id=" + c_p_id + ", user_id=" + user_id + ", is_send=" + is_send + "]";
	}
	
}
