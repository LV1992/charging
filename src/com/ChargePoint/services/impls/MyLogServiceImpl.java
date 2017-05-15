package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.MyLogDAO;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.MyLog;
import com.ChargePoint.services.MyLogService;

@Transactional
@Service
public class MyLogServiceImpl implements MyLogService {

	@Autowired
	private MyLogDAO myLogDao;
	
	/**获取所有日志信息
	 * @param MyLog MyLog
	 * @return List MyLog
	 */
	public List<MyLog> getMyLogList(MyLog MyLog){
		return myLogDao.selectMyLogList(MyLog);
	}
	
	
	/**分页获日志售信息
	 * @param int startIndex ,int pageSize,String startTime,String endTime, String sortName,String order
	 * @return List MyLog
	 */
	public List<MyLog> getMyLogByPage(Integer startIndex,Integer pageSize,String startTime,String endTime,String searchText,String sortName,String order){
		 Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("limitStart", startIndex);
		 parameterMap.put("limitCount", pageSize);
		 parameterMap.put("startTime", startTime);
		 parameterMap.put("endTime", endTime);
		 parameterMap.put("searchText", searchText);
		 parameterMap.put("sortName", sortName);
		 parameterMap.put("order", order);
		return myLogDao.selectMyLogByPage(parameterMap);
	}
	
	/**添加日志记录表
	 * @param MyLog MyLog
	 * @return Integer(主键id)
	 */
	public Integer addMyLog(MyLog myLog) {
		if(null != myLog){
			myLog.setTime(TimeFormatUtil.getFormattedNow());
		}
		Integer id = null;
		try{
		id =  myLogDao.insertMyLog(myLog); 
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("添加MyLog数据失败");
		}
		return id;
	}
	
	/**根据MyLog myLog删除日志
	 * @param Integer id
	 * @return
	 */
	public boolean deleteMyLog(Integer id) {
		boolean f = false;
		int refC = -1;
		try {
			refC = myLogDao.deleteMyLog(id);
			if(refC > 0 ){
				f = true;
			}
		} catch (Exception e) {
			System.out.println("删除myLog为" + id + "数据失败");
			e.printStackTrace();
		}
		return f;
	}
	
	/**修改日志信息
	 * @param MyLog MyLog
	 * @return boolean
	 */
	public boolean updateMyLog(MyLog myLog) {
		boolean result = false;
		int refC = -1;
		try {
			refC = myLogDao.updateMyLog(myLog);
			if(refC > 0 ){
				result = true;
			}
			result = true;
		} catch (Exception e) {
			System.out.println("修改日志出错");
			e.printStackTrace();
		}
		return result;
	}
}
