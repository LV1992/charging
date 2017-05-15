package com.ChargePoint.services.impls;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.ChargeRecordsDAO;
import com.ChargePoint.bean.ChargeRecords;
import com.ChargePoint.services.ChargeRecordsService;
import com.ChargePoint.services.CommonService;
@Transactional
@Service
public class ChargeRecordsServiceImpl implements ChargeRecordsService{

	@Autowired
	private ChargeRecordsDAO chargeRecordsDao;
	@Autowired
	private CommonService commonService;
	
	/**获取唯一充电记录信息
	 * @param uid
	 * @param id
	 * @return ChargeRecords
	 */
	@Override
	public ChargeRecords getChargeRecordByID(Integer uid,Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "charge_records_"+uid);
		map.put("id", id);
		return chargeRecordsDao.selectChargeRecordByID(map);
	}
	/**获取充电记录信息
	 * @param ChargeRecords c
	 * @return List ChargeRecords
	 */
	@Override
	public List<ChargeRecords> getChargeRecords(ChargeRecords c){
		return chargeRecordsDao.selectChargeRecordsList(c);
	}
	/**获取下位机更新的充电记录信息
	 * @param uid
	 * @return ChargeRecords
	 */
	@Override
	public ChargeRecords getUpdateRecord(Integer uid) {
		return chargeRecordsDao.selectUpdateRecord("charge_records_"+uid);
	}
	
	/**获取未更新订单记录（is_update = 0，trade_status = null ..,method = 1）
	 * @param uid
	 * @return ChargeRecord
	 */
	public ChargeRecords getUnUpdateRecord(Integer uid){
		return chargeRecordsDao.selectUnUpdateRecord("charge_records_"+uid);
	}
	
	/**手机分页获取充电记录信息
	 * @param int limitStart 页码
	 * @param int limitCount 每页数量
	 * @param String trade_status 订单状态
	 * @return List Map<String, Object> 
	 */
	public List<Map<String, Object>> getChargeRecordsByPage(Integer uid,Integer limitStart , Integer limitCount ,String trade_status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", "charge_records_"+uid);
		map.put("limitStart", limitStart);
		map.put("limitCount", limitCount);
		map.put("trade_status", trade_status);
		return chargeRecordsDao.selectChargeRecordsByPage(map);
	}
	
	/**添加chargeRecords记录(无需设置total_count与total_degree)
	 * @param chargeMoneyRecords
	 * @return boolean
	 */
	public boolean addChargeRecords(ChargeRecords chargeRecords){
		boolean res = false;
		int refC = -1;
	try{
		refC = chargeRecordsDao.insertChargeRecords(chargeRecords);
		if(refC > 0 ){
			res = true;
		}
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("写入充电记录信息出错");
	}
	return  res;
	}
	
	/**搜索充电记录信息
	 * @param int id
	 * @param searchText
	 * @return List ChargeRecords
	 */
	public List<ChargeRecords> searchChargeRecords(int id,String searchText){
		Map<String,String> map = new HashMap<String,String>();
		map.put("tableName", "charge_records_"+id);
		map.put("searchText", searchText);
		return chargeRecordsDao.searchChargeRecords(map);
	}
	
	/**是否存在此电站的充电记录
	 * @param stationid
	 * @return boolean true - 存在 false - 不存在
	 */
	public boolean isExistsChargeRecords(Integer uid,Integer stationid){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", "charge_records_"+uid);
		map.put("sid", stationid);
		int res = chargeRecordsDao.selectChargeRecordsCountByStationID(map);
		if(res == 0){
			return false;
		}else{
			return true;
		}
	}
	
	/**未支付的充电记录
	 * @param uid
	 * @return ChargeRecord
	 */
	public ChargeRecords getNONPaymentChargeRecords(Integer uid){
		return chargeRecordsDao.selectNONPaymentChargeRecord("charge_records_"+uid);
	}
	
	/**修改充电记录
	 * @param chargeRecord
	 * @return boolean
	 */
	@Override
	public boolean updateChargeRecords(ChargeRecords chargeRecord){
		boolean res = false;
		int refC = -1;
		try{
			refC = chargeRecordsDao.updateChargeRecords(chargeRecord);
			if(refC > 0 ){
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("修改ChargeRecord数据失败");
		}
		return res;
	}

	/**更新充电记录订单状态为已支付（支付回调使用）
	 * @param uid,tradeNO
	 * @return boolean
	 */
	@Override
	public boolean updateChargeRecordToPay(String tradeNO){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tableNames", commonService.getTableNames("charge_records_"));
		map.put("tradeNO", tradeNO);
		boolean res = false;
		int refC = -1;
		try{
			refC = chargeRecordsDao.updateChargeRecordToPay(map);
			if(refC > 0 ){
				res = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("修改ChargeRecord数据失败");
		}
		return res;
	}
	
	/**获取电记录信息所充电站的电站id
	 * @param int id Integer limit(充电站个数)
	 * @return List Integer
	 */
	@Override
	public List<Integer> getChargeRecordsWithDisStationID(int id,Integer limit){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tableName", "charge_records_"+id);
		map.put("limit", limit);
		return chargeRecordsDao.selectChargeRecordsWithDisStationID(map);
	}
	/**获取电记录信息根据id
	 * @param Integer uid
	 * @param Integer id
	 * @return ChargeRecords
	 */
	@Override
	public Map<String, Object> getChargeRecordsByID(Integer uid,Integer id) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tableName", "charge_records_"+uid);
		map.put("id", id);
		return chargeRecordsDao.selectChargeRecordsByID(map);
	}

}
