package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.ChargePointDAO;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.services.ChargePointService;
@Transactional
@Service
public class ChargePointServiceImpl implements ChargePointService{

	@Autowired
	private ChargePointDAO chargePointDao;
	
	/**获取chargePoint列表
	 * @param chargePoint
	 * @return List ChargePoint
	 */
	@Override
	public List<ChargePoint> getChargePointList(ChargePoint chargePoint){
		return chargePointDao.selectChargePointList(chargePoint);
	}
	
	public Integer getChargePointListCount(ChargePoint chargePoint){
		return chargePointDao.selectChargePointListCount(chargePoint);
	}
	
	/**通过充电桩id获取充电桩数据
	 * @param c_p_id
	 * @return ChargePoint chargePoint
	 */
	@Override
	public ChargePoint getChargePoint(String c_p_id) {
		return chargePointDao.selectChargePoint(c_p_id);
	}
	
	/**分页获取交流、直流chargePoint列表
	 * @param int limitStart 页码
	 * @param int limitCount 每页数量
	 * @param String c_p_id 查询id条件
	 * @param String c_p_type 查询充电桩类型0-直流1-交流
	 * @param String sortName 排序字段
	 * @param String order 排序规则 asc desc
	 * @return List ChargePoint
	 */
	@Override
	public List<ChargePoint> getChargePointByPage(Integer limitStart ,Integer limitCount ,String c_p_id,String c_p_type,String sortName,String order){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("limitStart", limitStart);
		map.put("limitCount", limitCount);
		map.put("c_p_id", c_p_id);
		map.put("c_p_type", c_p_type);
		map.put("sortName", sortName);
		map.put("order", order);
		return chargePointDao.selectChargePointByPage(map);
	}
	
	/**更新chargePoint记录
	 * @param chargePoint
	 * @return boolean
	 */
	@Override
	public boolean updateChargePoint(ChargePoint chargePoint){
		boolean res = false;
		int refC = -1;
		try{
			refC = chargePointDao.updateChargePoint(chargePoint);
			if(refC > 0 ){
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("更新chargePoint数据失败");
		}
	return res;
	}

}
