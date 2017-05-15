package com.ChargePoint.bean;

import java.io.Serializable;

public class OperateResults  implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private String table_name;
	private Integer id;
	private String operation_result;
	private String back_time;
	private String result_details;		
	private String failure_case;
	private String c_p_id;
	private String user_id;
	private String is_send;

	public String getTable_name() {
		return table_name;
	}
	/**"operation_results_"+user_id
	 * @param user_id
	 */
	public void setTable_name(String user_id) {
		this.table_name = "operation_results_"+user_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
	 * @return
	 */
	public String getOperation_result() {
		return operation_result;
	}
	/**0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
	 * @param operation_result
	 */
	public void setOperation_result(String operation_result) {
		this.operation_result = operation_result;
	}
	public String getBack_time() {
		return back_time;
	}
	public void setBack_time(String back_time) {
		this.back_time = back_time;
	}
	/**0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
	 * @return
	 */
	public String getResult_details() {
		return result_details;
	}
	/**0:失败1:开启充电成功 2:停止充电成功 3:预约成功 4:取消预约成功
	 * @param result_details
	 */
	public void setResult_details(String result_details) {
		this.result_details = result_details;
	}
	/**
	 * @return 0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约
	 */
	public String getFailure_case() {
		return failure_case;
	}
	/**0:通信超时 1:充电机故障 2:未插充电枪 3:充电桩正在充电（预约失败）4:充电桩被预约
	 * @param failure_case
	 */
	public void setFailure_case(String failure_case) {
		this.failure_case = failure_case;
	}
	public String getC_p_id() {
		return c_p_id;
	}
	public void setC_p_id(String c_p_id) {
		this.c_p_id = c_p_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**Y - 已发送 N- 未发送
	 * @return
	 */
	public String getIs_send() {
		return is_send;
	}
	/**Y - 已发送 N- 未发送
	 * @param is_send
	 */
	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	@Override
	public String toString() {
		return "OperateResults [table_name=" + table_name + ", id=" + id + ", operation_result=" + operation_result
				+ ", back_time=" + back_time + ", result_details=" + result_details + ", failure_case=" + failure_case
				+ ", c_p_id=" + c_p_id + ", user_id=" + user_id + ", is_send=" + is_send + "]";
	}
	
}
