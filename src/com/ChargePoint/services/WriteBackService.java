package com.ChargePoint.services;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.WriteBack;

public interface WriteBackService {
	/**获取所有回复信息
	 * @param WriteBack writeBack
	 * @return List Map String,Object
	 */
	public List<Map<String,Object>> getWriteBackList(WriteBack writeBack);
	/**获取回复信息个数
	 * @param Integer stationID,Integer reviewID
	 * @return int 
	 */
	public int getWriteBackCountByReview(Integer stationID,Integer reviewID);
	/**分页获取回复信息
	 * @param int startIndex pageSize 
	 * @return List Map<String, Object>
	 */
	public List<Map<String, Object>> getWriteBackByPage(int startIndex,int pageSize);

	/**分页获取回复信息(包含电站数据)
	 * @param uid
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getWriteBackByPageByUserID(Integer uid,Integer sid,Integer reviewid,int startIndex,int pageSize);
	/**添加回复
	 * @param WriteBack writeBack
	 * @return boolean
	 */
	public boolean addWriteBack(WriteBack writeBack);
	/**删除回复
	 * @param WriteBack writeBack
	 * @return
	 */
	public boolean deleteWriteBack(WriteBack writeBack);
	/**修改回复
	 * @param WriteBack writeBack
	 * @return boolean
	 */
	public boolean updateWriteBack(WriteBack writeBack);
	
	/**批量更新未读回复信息
	 * @param ids
	 * @return
	 */
	public boolean updateWriteBackToRead(List<Integer> ids);
	
}
