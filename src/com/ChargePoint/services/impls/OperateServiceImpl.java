package com.ChargePoint.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.OperateDAO;
import com.ChargePoint.bean.Operate;
import com.ChargePoint.services.OperateService;
@Transactional
@Service
public class OperateServiceImpl implements OperateService{

	@Autowired
	private OperateDAO operateDao;
	
	/**添加operate记录
	 * @param operate
	 * @return boolean
	 */
	public boolean addOperate(Operate operate){
		boolean result = false;
		int reC = -1;
		try{
			reC = this.operateDao.insertOperate(operate);
			if(-1 != reC && 0 != reC){
				result = true;
			}
		}catch(Exception e){
			System.out.println("insertOperate wrong!!!");
			e.printStackTrace();
		}
		return result;
	}
	
	/**更新operate记录
	 * @param operate
	 * @return boolean
	 */
	public boolean updateoperate(Operate operate){
		boolean result = false;
		int reC = -1;
		try{
			reC = this.operateDao.updateOperate(operate);
			if(-1 != reC && 0 != reC){
				result = true;
			}
		}catch(Exception e){
			System.out.println("修改Operate出错");
			e.printStackTrace();
		}
		return result;
	}
}
