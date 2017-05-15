package com.ChargePoint.bean;

import java.io.Serializable;

public class Warning  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;//表名以充电桩编号结尾	
	private Integer id;	
	private String time;//告警时间	
	private String v_over;//过压0：正常1级过压2:2级过压	
	private String v_short;//欠压0：正常1级欠压2:2级欠压	
	private String i_over;//过流0：正常1级过流2:2级过流	
	private String t_over;//过温0：正常1级过温2:2级过温	
	private String hz_over;//过频0：正常1级过频2:2级过频	
	private String hz_short;//欠频0：正常1级欠频2:2级欠频	
	private String emergency_status;//紧急状态0-正常 1-急停
	private String dump_status;//倾倒状态0-正常 1-倾倒
	private String cp_status;//充电桩状态0-正常1-故障
	public String getTable_name() {
		return table_name;
	}
	/**"warning_"+cpid
	 * @param cpid
	 */
	public void setTable_name(String cpid) {
		this.table_name = "warning_"+cpid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getV_over() {
		return v_over;
	}
	public void setV_over(String v_over) {
		this.v_over = v_over;
	}
	public String getV_short() {
		return v_short;
	}
	public void setV_short(String v_short) {
		this.v_short = v_short;
	}
	public String getI_over() {
		return i_over;
	}
	public void setI_over(String i_over) {
		this.i_over = i_over;
	}
	public String getT_over() {
		return t_over;
	}
	public void setT_over(String t_over) {
		this.t_over = t_over;
	}
	public String getHz_over() {
		return hz_over;
	}
	public void setHz_over(String hz_over) {
		this.hz_over = hz_over;
	}
	public String getHz_short() {
		return hz_short;
	}
	public void setHz_short(String hz_short) {
		this.hz_short = hz_short;
	}
	public String getEmergency_status() {
		return emergency_status;
	}
	public void setEmergency_status(String emergency_status) {
		this.emergency_status = emergency_status;
	}
	public String getDump_status() {
		return dump_status;
	}
	public void setDump_status(String dump_status) {
		this.dump_status = dump_status;
	}
	public String getCp_status() {
		return cp_status;
	}
	public void setCp_status(String cp_status) {
		this.cp_status = cp_status;
	}
	@Override
	public String toString() {
		return "Warning [table_name=" + table_name + ", id=" + id + ", time=" + time + ", v_over=" + v_over
				+ ", v_short=" + v_short + ", i_over=" + i_over + ", t_over=" + t_over + ", hz_over=" + hz_over
				+ ", hz_short=" + hz_short + ", emergency_status=" + emergency_status + ", dump_status=" + dump_status
				+ ", cp_status=" + cp_status + "]";
	}

}
