package com.ChargePoint.services;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.ChargeRecords;

public interface ChargeRecordsService {
	/**获取唯一充电记录信息
	 * @param uid
	 * @param id
	 * @return ChargeRecords
	 */
	public ChargeRecords getChargeRecordByID(Integer uid,Integer id);
	
	/**获取充电记录信息
	 * @param ChargeRecords c
	 * @return List ChargeRecords
	 */
	public List<ChargeRecords> getChargeRecords(ChargeRecords c);
	
	/**根据充电记录id获取充电记录信息（获取充电支付详情）
	 * @param uid
	 * @param id
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getChargeRecordsByID(Integer uid,Integer id);
	
	
	/**获取下位机更新的充电记录信息
	 * @param uid
	 * @return ChargeRecords
	 */
	public ChargeRecords getUpdateRecord(Integer uid);
	
	/**获取未更新订单记录（is_update = 0，trade_status = null ..,method = 1）
	 * @param uid
	 * @return ChargeRecord
	 */
	public ChargeRecords getUnUpdateRecord(Integer uid);
	
	/**手机分页获取充电记录信息
	 * @param uid
	 * @param int limitStart 页码
	 * @param int limitCount 每页数量
	 * @param trade_status 订单状态
	 * @return List Map<String, Object>
	 */
	public List<Map<String, Object>> getChargeRecordsByPage(Integer uid,Integer limitStart , Integer limitCount ,String trade_status);
	
	/**添加chargeRecords记录(无需设置total_count与total_degree)
	 * @param chargeMoneyRecords
	 * @return boolean
	 */
	public boolean addChargeRecords(ChargeRecords chargeRecords);
	
	/**搜索充电记录信息
	 * @param int id
	 * @param searchText
	 * @return List ChargeRecords
	 */
	public List<ChargeRecords> searchChargeRecords(int id,String searchText);
	
	/**获取电记录信息所充电站的电站id
	 * @param int id Integer limit(充电站个数)
	 * @return List Integer
	 */
	public List<Integer> getChargeRecordsWithDisStationID(int id,Integer limit);
	
	/**是否存在此电站的充电记录
	 * @param stationid
	 * @return boolean true - 存在 false - 不存在
	 */
	public boolean isExistsChargeRecords(Integer uid,Integer stationid);
	
	/**未支付的充电记录(订单状态为未支付且订单编号不为空)
	 * @param uid
	 * @return ChargeRecord
	 */
	public ChargeRecords getNONPaymentChargeRecords(Integer uid);
	
	/**修改充电记录
	 * @param chargeRecord
	 * @return boolean
	 */
	public boolean updateChargeRecords(ChargeRecords chargeRecord);

	/**更新充电记录订单状态为已支付（支付回调使用）
	 * @param uid,tradeNO
	 * @return boolean
	 */
	public boolean updateChargeRecordToPay(String tradeNO);
	
}
