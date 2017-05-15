package com.ChargePoint.services;

import com.ChargePoint.bean.TempCharge;

public interface TempChargeService {

	
	/**查询是否有数据的方法，有 true 无 false
	 * @param tempCharge
	 * @return boolean
	 */
	public boolean haveTempCharge(TempCharge tempCharge);
	/**查询缓存数据
	 * @param tempCharge
	 * @return TempCharge
	 */
	public TempCharge getTempCharge(TempCharge tempCharge);
	/**
	 * 添加tempCharge记录
	 * @param tempCharge
	 * @return Integer 写入数据的逐渐ID
	 */
	public Integer addTempCharge(TempCharge tempCharge);
	
	public boolean deleteTempCharge(TempCharge tempCharge);
	
	public boolean updateTempCharge(TempCharge tempCharge);
}
