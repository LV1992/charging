package com.ChargePoint.bean;

import java.io.Serializable;

public class ChargePoint  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String c_p_id;//充电桩编号
	private Integer station_id;//充电站编号
	private String c_p_type;//充电桩类型   0-直流(DC)，1-交流(AC)
	private String w;//桩功率
	private Float v;//额定电压
	private Float i;//额定电流
	private Float in_v;//额定输入电流
	private Float e_price;//电价
	private Float e_price1;//电价
	private Float e_price2;//电价
	private Float e_price3;//电价
	private String price_time;//时段	
	private String price_time1;//时段	
	private String price_time2;//时段	
	private String price_time3;//时段	
	private String is_free;//是否空闲	0-空闲，1-被预约，2-充电中	
	private String status;//充电桩状态 0-未投运 1-已投运 2-维修中
	private String dtu_id;//心跳时发送DTU编号（8）
	private String v_max_charge_out;		//最高输出电压（V）
	private String v_min_charge_out;//最低输出电压（V)
	private String i_max_charge_out;		//最大输出电流（A)
	private String i_min_charge_out;//最小输出电流（A）
	private String total_degree;//累计充电量（度）
	private String time_count;//累计充电时间
	private Integer available_count;//可用充电枪个数
	private String company_id;//公司id
	private String inner_no;//站内编号
	private String time;//建桩时间
	
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public Integer getStation_id() {
		return station_id;
	}
	public void setStation_id(Integer station_id) {
		this.station_id = station_id;
	}
	/**
	 * @return 充电桩类型   0-直流(DC)，1-交流(AC)
	 */
	public String getC_p_type() {
		return c_p_type;
	}
	/**充电桩类型   0-直流(DC)，1-交流(AC)
	 * @param c_p_type
	 */
	public void setC_p_type(String c_p_type) {
		this.c_p_type = c_p_type;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public Float getV() {
		return v;
	}
	public void setV(Float v) {
		this.v = v;
	}
	public Float getI() {
		return i;
	}
	public void setI(Float i) {
		this.i = i;
	}
	public Float getIn_v() {
		return in_v;
	}
	public void setIn_v(Float in_v) {
		this.in_v = in_v;
	}
	public Float getE_price() {
		return e_price;
	}
	public void setE_price(Float e_price) {
		this.e_price = e_price;
	}
	public Float getE_price1() {
		return e_price1;
	}
	public void setE_price1(Float e_price1) {
		this.e_price1 = e_price1;
	}
	public Float getE_price2() {
		return e_price2;
	}
	public void setE_price2(Float e_price2) {
		this.e_price2 = e_price2;
	}
	public Float getE_price3() {
		return e_price3;
	}
	public void setE_price3(Float e_price3) {
		this.e_price3 = e_price3;
	}
	public String getPrice_time() {
		return price_time;
	}
	public void setPrice_time(String price_time) {
		this.price_time = price_time;
	}
	public String getPrice_time1() {
		return price_time1;
	}
	public void setPrice_time1(String price_time1) {
		this.price_time1 = price_time1;
	}
	public String getPrice_time2() {
		return price_time2;
	}
	public void setPrice_time2(String price_time2) {
		this.price_time2 = price_time2;
	}
	public String getPrice_time3() {
		return price_time3;
	}
	public void setPrice_time3(String price_time3) {
		this.price_time3 = price_time3;
	}
	/**
	 * @return 是否空闲	0-空闲，1-被预约，2-充电中	
	 */
	public String getIs_free() {
		return is_free;
	}
	/**是否空闲	0-空闲，1-被预约，2-充电中	
	 * @param String is_free
	 */
	public void setIs_free(String is_free) {
		this.is_free = is_free;
	}
	public String getDtu_id() {
		return dtu_id;
	}
	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}
	
	public String getV_max_charge_out() {
		return v_max_charge_out;
	}
	public void setV_max_charge_out(String v_max_charge_out) {
		this.v_max_charge_out = v_max_charge_out;
	}
	public String getV_min_charge_out() {
		return v_min_charge_out;
	}
	public void setV_min_charge_out(String v_min_charge_out) {
		this.v_min_charge_out = v_min_charge_out;
	}
	public String getI_max_charge_out() {
		return i_max_charge_out;
	}
	public void setI_max_charge_out(String i_max_charge_out) {
		this.i_max_charge_out = i_max_charge_out;
	}
	public String getI_min_charge_out() {
		return i_min_charge_out;
	}
	public void setI_min_charge_out(String i_min_charge_out) {
		this.i_min_charge_out = i_min_charge_out;
	}
	public String getTotal_degree() {
		return total_degree;
	}
	public void setTotal_degree(String total_degree) {
		this.total_degree = total_degree;
	}
	public String getTime_count() {
		return time_count;
	}
	public void setTime_count(String time_count) {
		this.time_count = time_count;
	}
	public Integer getAvailable_count() {
		return available_count;
	}
	public void setAvailable_count(Integer available_count) {
		this.available_count = available_count;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getInner_no() {
		return inner_no;
	}
	public void setInner_no(String inner_no) {
		this.inner_no = inner_no;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ChargePoint [c_p_id=" + c_p_id + ", station_id=" + station_id + ", c_p_type=" + c_p_type + ", w=" + w
				+ ", v=" + v + ", i=" + i + ", in_v=" + in_v + ", e_price=" + e_price + ", e_price1=" + e_price1
				+ ", e_price2=" + e_price2 + ", e_price3=" + e_price3 + ", price_time=" + price_time + ", price_time1="
				+ price_time1 + ", price_time2=" + price_time2 + ", price_time3=" + price_time3 + ", is_free=" + is_free
				+ ", status=" + status + ", dtu_id=" + dtu_id + ", v_max_charge_out=" + v_max_charge_out
				+ ", v_min_charge_out=" + v_min_charge_out + ", i_max_charge_out=" + i_max_charge_out
				+ ", i_min_charge_out=" + i_min_charge_out + ", total_degree=" + total_degree + ", time_count="
				+ time_count + ", available_count=" + available_count + ", company_id=" + company_id + ", inner_no="
				+ inner_no + ", time=" + time + "]";
	}
	
}
