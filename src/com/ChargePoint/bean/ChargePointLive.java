package com.ChargePoint.bean;

import java.io.Serializable;

public class ChargePointLive  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;
	private String c_p_id;//充电桩编号
	private String time;// send protocal time
	private String v_require;//电压需求（V）
	private String i_require;//电流需求（A）
	private String mode_charge;//充电模式
	private Float v_charge;//充电电压测量值（V）
	private Float i_charge;		//充电电流测量值（A）
	private String v_max;//最高单体动力蓄电池电压及其组号
	private String soc_now;		//当前荷电状态SOC（%）
	private String time_left;//估算剩余充电时间（min）
	private String v_out;		//电压输出值（V)
	private String i_out;//电流输出值（A）
	private String charged_time;		//累计充电时间（min）
	private String charge_allow;//充电机充电允许（<00>:=暂停；<01>：=允许）
	private String v_max_no;		//最高单体动力蓄电池电压所在编号
	private String t_max;//最高动力蓄电池温度
	private String t_max_no;		//最高温度检测点编号
	private String t_min;//最低动力蓄电池温度
	private String t_min_no;	//最低动力蓄电池温度检测点编号	
	private String s_v_cell;//单体动力蓄电池电压过高/过低
	private String s_soc;		//整车动力蓄电池荷电状态SOC过高/过低
	private String o_i_charge;//动力蓄电池充电过电流
	private String o_t;		//动力蓄电池温度过高
	private String orins;//动力蓄电池绝缘状态
	private String s_contact;		//动力蓄电池组输出连接器连接状态
	private String allow_charge;//动力电池充电允许
	private String status;//充电状态0- 充电中 1- 结束充电
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String c_p_id) {
		this.table_name = "charge_point_live_"+c_p_id;
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
	public String getV_require() {
		return v_require;
	}
	public void setV_require(String v_require) {
		this.v_require = v_require;
	}
	public String getI_require() {
		return i_require;
	}
	public void setI_require(String i_require) {
		this.i_require = i_require;
	}
	public String getMode_charge() {
		return mode_charge;
	}
	public void setMode_charge(String mode_charge) {
		this.mode_charge = mode_charge;
	}

	public String getV_max() {
		return v_max;
	}
	public void setV_max(String v_max) {
		this.v_max = v_max;
	}
	public String getSoc_now() {
		return soc_now;
	}
	public void setSoc_now(String soc_now) {
		this.soc_now = soc_now;
	}
	public String getTime_left() {
		return time_left;
	}
	public void setTime_left(String time_left) {
		this.time_left = time_left;
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
	public String getCharge_allow() {
		return charge_allow;
	}
	public void setCharge_allow(String charge_allow) {
		this.charge_allow = charge_allow;
	}
	public String getV_max_no() {
		return v_max_no;
	}
	public void setV_max_no(String v_max_no) {
		this.v_max_no = v_max_no;
	}
	public String getT_max() {
		return t_max;
	}
	public void setT_max(String t_max) {
		this.t_max = t_max;
	}
	public String getT_max_no() {
		return t_max_no;
	}
	public void setT_max_no(String t_max_no) {
		this.t_max_no = t_max_no;
	}
	public String getT_min() {
		return t_min;
	}
	public void setT_min(String t_min) {
		this.t_min = t_min;
	}
	public String getT_min_no() {
		return t_min_no;
	}
	public void setT_min_no(String t_min_no) {
		this.t_min_no = t_min_no;
	}
	public String getS_v_cell() {
		return s_v_cell;
	}
	public void setS_v_cell(String s_v_cell) {
		this.s_v_cell = s_v_cell;
	}
	public String getS_soc() {
		return s_soc;
	}
	public void setS_soc(String s_soc) {
		this.s_soc = s_soc;
	}
	public String getO_i_charge() {
		return o_i_charge;
	}
	public void setO_i_charge(String o_i_charge) {
		this.o_i_charge = o_i_charge;
	}
	public String getO_t() {
		return o_t;
	}
	public void setO_t(String o_t) {
		this.o_t = o_t;
	}
	public String getOrins() {
		return orins;
	}
	public void setOrins(String orins) {
		this.orins = orins;
	}
	public String getS_contact() {
		return s_contact;
	}
	public void setS_contact(String s_contact) {
		this.s_contact = s_contact;
	}
	public String getAllow_charge() {
		return allow_charge;
	}
	public void setAllow_charge(String allow_charge) {
		this.allow_charge = allow_charge;
	}
	public Float getV_charge() {
		return v_charge;
	}
	public void setV_charge(Float v_charge) {
		this.v_charge = v_charge;
	}
	public Float getI_charge() {
		return i_charge;
	}
	public void setI_charge(Float i_charge) {
		this.i_charge = i_charge;
	}
	
	/**
	 * @return 0- 充电中 1- 结束充电
	 */
	public String getStatus() {
		return status;
	}
	/**0- 充电中 1- 结束充电
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ChargePointLive [table_name=" + table_name + ", id=" + id + ", c_p_id=" + c_p_id + ", time=" + time
				+ ", v_require=" + v_require + ", i_require=" + i_require + ", mode_charge=" + mode_charge
				+ ", v_charge=" + v_charge + ", i_charge=" + i_charge + ", v_max=" + v_max + ", soc_now=" + soc_now
				+ ", time_left=" + time_left + ", v_out=" + v_out + ", i_out=" + i_out + ", charged_time="
				+ charged_time + ", charge_allow=" + charge_allow + ", v_max_no=" + v_max_no + ", t_max=" + t_max
				+ ", t_max_no=" + t_max_no + ", t_min=" + t_min + ", t_min_no=" + t_min_no + ", s_v_cell=" + s_v_cell
				+ ", s_soc=" + s_soc + ", o_i_charge=" + o_i_charge + ", o_t=" + o_t + ", orins=" + orins
				+ ", s_contact=" + s_contact + ", allow_charge=" + allow_charge + "]";
	}
	
}
