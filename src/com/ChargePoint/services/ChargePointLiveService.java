package com.ChargePoint.services;

import java.util.HashMap;

import com.ChargePoint.bean.ChargePointLive;

public interface ChargePointLiveService{

	/**获取chargePointLive最新数据
	 * @param tableName
	 * @return ChargePointLive 
	 */
	public ChargePointLive getFirstChargePointLive(String tableName);
	
	/**获取chargePointLive最新数据(手机使用)
	 * @param tableName
	 * @return HashMap<String,Object> 
	 */
	public HashMap<String,Object> getCurrentData(String tableName);
	
	/**更新chargePointLive记录
	 * @param chargePointLive
	 * @return boolean
	 */
	public boolean updatechargePointLive(ChargePointLive chargePointLive);
}
