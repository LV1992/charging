package com.ChargePoint.services.impls;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.CommonDAO;
import com.ChargePoint.services.CommonService;
@Transactional
@Service
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private CommonDAO commonDao; 

	/**获取数据个数
	 * @param String tableName
	 * @return int 
	 */
	@Override
	public int getTotalCount(String tableName){
		return commonDao.selectCount(tableName);
	}
	
	/**根据表名获取最大值数据
	 * @param String tableName, String column
	 * @return String 
	 */
	@Override
	public String getMaxData(String tableName, String column){
		Map<String, String> map = new HashMap<String, String>();
		map.put("tableName", tableName);
		map.put("column", column);
		return commonDao.selectMaxData(map);
	}
	
	/**查询数据库类似表名
	 * @param tableName
	 * @return list String
	 */
	@Override
	public List<String> getTableNames(String tableName){
		return commonDao.selectTableNames(tableName);
	}
	
	/**查询数据库类似表名(正则表达式)
	 * @param tableName
	 * @return list String
	 */
	@Override
	public List<String> getRegxpTableNames(String tableName){
		return commonDao.selectRegxpTableNames(tableName);
	}
	
	
	/**删除记录表语句 charge_records_ appointment_records_ operation_ operation_results_
	 * @param String id
	 * @return boolean 
	 */
	@Override
	public boolean dropRecordsTables(Integer id) {
		return dynamicSql("drop table if exists charge_records_"+id+",appointment_records_"+id
				+",operation_"+id+",operation_results_"+id);
	}
	
	/**根据类型获取充电桩数据个数
	 * @param String type
	 * @return int 
	 */
	@Override
	public int getCPTotalCount(String type){
		return commonDao.selectCPCount(type);
	}
	
	/**执行动态sql语句
	 * @param String sql
	 * @return boolean 
	 */
	@Override
	public boolean dynamicSql(String sql) {
		try{
			commonDao.dynamicSql(sql);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("执行语句："+sql+" 失败");
		}
		return false;
	}
	
	/**建立预约记录表
	 * @param uid
	 * @return
	 */
	@Override
	public boolean createAppointmentRecords(int id) {
		boolean res = false;
		try{
			commonDao.createAppointmentRecords("appointment_records_"+id);
			res = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("建表："+"appointment_records_"+id+"失败");
		}
	return res;
	}
	
	
	/**建立用户记录表
	 * @param id
	 * @return
	 */
	@Override
	public boolean createChargeRecord(int id){
	boolean res = false;
	try{
		commonDao.createChargeRecord("charge_records_"+id);
		res = true;
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("建表："+"charge_records_"+id+"失败");
	}
	return res;
	}
	
	/**建立用户操作表
	 * @param id
	 * @return
	 */
	@Override
	public boolean createOperation(int id){
		boolean res = false;
		try{
			commonDao.createOperation("operation_"+id);
			res = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("建表："+"operation_"+id+"失败");
		}
	return res;
	}
	
	/**建立用户操作返回表
	 * @param id
	 * @return
	 */
	@Override
	public boolean createOperationResults(int id){
		boolean res = false;
		try{
			commonDao.createOperationResults("operation_results_"+id);
			res = true;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("建表："+"operation_results_"+id+"失败");
		}
		return res;
	}

}
