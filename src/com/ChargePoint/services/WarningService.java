package com.ChargePoint.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ChargePoint.bean.Warning;

public interface WarningService{

	/**获取所有告警信息
	 * @param WriteBack writeBack
	 * @return List WriteBack
	 */
	public List<Warning> selectWarningList(Warning Warning);
	/**分页获取告警信息
	 * @param int startIndex pageSize 
	 * @return List WriteBack
	 */
	public List<Warning> getWarningByPage(Map<String, Object> map);
	/**分页获取告警信息
	 * @param int startIndex pageSize 
	 * @return List Warning
	 */
	public List<Warning> getAllWarningByPage(Map<String, Object> map);
	
	/**修改告警信息
	 * @param warning
	 * @return boolean
	 */
	public boolean updateWarning(Warning warning);
}
