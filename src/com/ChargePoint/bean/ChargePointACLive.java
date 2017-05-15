package com.ChargePoint.bean;

import java.io.Serializable;

public class ChargePointACLive implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;
	private String c_p_id;//充电桩编号
	private String time;// send protocal time
	private String v_out;		//电压输出值（V)
	private String i_out;//电流输出值（A）
	private String charged_time;		//累计充电时间（min）
	private String charged_energy;		//累计充电电量（kwh）
	private String charged_money;		//累计充电金额（元）
	private String allow_charge;//充电机充电允许（<00>:=暂停；<01>：=允许）
	private Integer charger_no;//充电桩状态 1或2
	private String method;//0 空闲1代表刷卡 2手机充电
	private String status;//1代表空闲 2预约  3充电中 4停止充电
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = "charge_point_live_"+table_name;
	}
	public final Integer getId() {
		return id;
	}
	public final void setId(Integer id) {
		this.id = id;
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
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public String getV_out() {
		return v_out;
	}
	public void setV_out(String v_out) {
		this.v_out = v_out;
	}
	public String getI_out() {
		return i_out;
	}
	public void setI_out(String i_out) {
		this.i_out = i_out;
	}
	public String getCharged_time() {
		return charged_time;
	}
	public void setCharged_time(String charged_time) {
		this.charged_time = charged_time;
	}
	public String getAllow_charge() {
		return allow_charge;
	}
	public void setAllow_charge(String allow_charge) {
		this.allow_charge = allow_charge;
	}
	public String getCharged_energy() {
		return charged_energy;
	}
	public void setCharged_energy(String charged_energy) {
		this.charged_energy = charged_energy;
	}
	
	public String getCharged_money() {
		return charged_money;
	}
	public void setCharged_money(String charged_money) {
		this.charged_money = charged_money;
	}
	public Integer getCharger_no() {
		return charger_no;
	}
	public void setCharger_no(Integer charger_no) {
		this.charger_no = charger_no;
	}
	
	/**0 空闲1代表刷卡 2手机充电
	 * @return
	 */
	public String getMethod() {
		return method;
	}
	/**0 空闲1代表刷卡 2手机充电
	 * @param method
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**1代表空闲 2预约  3充电中 4停止充电
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ChargePointACLive [table_name=" + table_name + ", id=" + id + ", c_p_id=" + c_p_id + ", time=" + time
				+ ", v_out=" + v_out + ", i_out=" + i_out + ", charged_time=" + charged_time + ", charged_energy="
				+ charged_energy + ", charged_money=" + charged_money + ", allow_charge=" + allow_charge
				+ ", charger_no=" + charger_no + ", method=" + method + ", status=" + status + "]";
	}

}
