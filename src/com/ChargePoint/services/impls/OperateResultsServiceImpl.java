package com.ChargePoint.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.OperateResultsDAO;
import com.ChargePoint.bean.OperateResults;
import com.ChargePoint.services.OperateResultsService;
@Transactional
@Service
public class OperateResultsServiceImpl implements OperateResultsService{

	@Autowired
	private OperateResultsDAO operateResultsDao;
	
	/**获取最后一条OperateResult
	 * @param operate
	 * @return OperateResults
	 */
	public OperateResults getLastOperateResult(OperateResults operate){
		return operateResultsDao.selectLastOperateResult(operate);
	}
	
	/**更新operate记录
	 * @param operate
	 * @return boolean
	 */
	public boolean updateOperateResults(OperateResults operate){
		return operateResultsDao.updateOperateResults(operate);
	}
}
