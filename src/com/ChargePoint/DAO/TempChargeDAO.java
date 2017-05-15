package com.ChargePoint.DAO;

import com.ChargePoint.bean.TempCharge;

public interface TempChargeDAO{
	
	public String haveTempCharge(TempCharge tempCharge);
	public TempCharge selectTempCharge(TempCharge tempCharge);
	public Integer insertTempCharge(TempCharge tempCharge);
	public Integer deleteTempCharge(TempCharge tempCharge);
	public Integer updateTempCharge(TempCharge tempCharge);

}
