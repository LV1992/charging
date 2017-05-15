package com.ChargePoint.services;

import com.ChargePoint.bean.OperateResults;

public interface OperateResultsService {
	
	/**获取最后一条OperateResult
	 * @param operate
	 * @return OperateResults
	 */
	public OperateResults getLastOperateResult(OperateResults operate);
	
	/**更新operate记录
	 * @param operate
	 * @return boolean
	 */
	public boolean updateOperateResults(OperateResults operate);
}
