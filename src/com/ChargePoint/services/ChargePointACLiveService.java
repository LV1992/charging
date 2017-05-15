package com.ChargePoint.services;

import java.util.HashMap;

import com.ChargePoint.bean.ChargePointACLive;

public interface ChargePointACLiveService {
	
	/**获取chargePointACLive最新数据（手机监控使用）
	 * @param tableName
	 * @return HashMap<String,Object> 
	 */
	public HashMap<String,Object> getCurrentData(String tableName);
	
	/**结束充电后获取表中最后一条数据(更新充电记录使用) 3，1
	 * @param tableName
	 * @return ChargePointACLive
	 */
	public ChargePointACLive getACLiveEndData(String tableName);
	
	/**获取chargePointACLive最新数据
	 * @param tableName
	 * @return ChargePointACLive
	 */
	public ChargePointACLive getFirstChargePointACLive(String tableName);
	
	/**获取表中最后一条数据(监听手机充电自动结束使用)
	 * @param tableName
	 * @return ChargePointACLive
	 */
	public ChargePointACLive getAutoEndData(String tableName);
	/**更新chargePointLive记录
	 * @param chargePointLive
	 * @return boolean
	 */
	public boolean updateChargePointACLive(ChargePointACLive chargePointLive);
}
