package com.ChargePoint.services;

import java.util.List;

public interface CommonService{
	
	/**获取数据个数
	 * @param String tableName
	 * @return int 
	 */
	public int getTotalCount(String tableName);
	
	/**根据表名获取最大值数据
	 * @param String tableName, String column
	 * @return String 
	 */
	public String getMaxData(String tableName, String column);
	
	/**查询数据库类似表名
	 * @param tableName
	 * @return list String
	 */
	public List<String> getTableNames(String tableName);
	/**查询数据库类似表名(正则表达式)
	 * @param tableName
	 * @return list String
	 */
	public List<String> getRegxpTableNames(String tableName);
	
	/**删除记录表语句charge_money_records_ charge_records_ operation_ operation_results_
	 * @param String id
	 * @return boolean 
	 */
	public boolean dropRecordsTables(Integer id) ;
	
	/**根据类型获取充电桩数据个数
	 * @param String type
	 * @return int 
	 */
	public int getCPTotalCount(String type);
	
	/**执行动态sql语句
	 * @param String sql
	 * @return boolean 
	 */
	public boolean dynamicSql(String sql);
	
	/**建立预约记录表
	 * @param uid
	 * @return
	 */
	public boolean createAppointmentRecords(int uid);
	
	/**建立用户记录表
	 * @param id
	 * @return
	 */
	public boolean createChargeRecord(int id);
	
	/**建立用户操作表
	 * @param id
	 * @return
	 */
	public boolean createOperation(int id);
	
	/**建立用户操作返回表
	 * @param id
	 * @return
	 */
	public boolean createOperationResults(int id);
	
}
