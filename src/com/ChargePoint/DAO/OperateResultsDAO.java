package com.ChargePoint.DAO;

import com.ChargePoint.bean.OperateResults;

public interface OperateResultsDAO{
	
	public OperateResults selectLastOperateResult(OperateResults operate);
	public boolean updateOperateResults(OperateResults operateResults);

}
