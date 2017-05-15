package com.ChargePoint.services;

import java.util.List;
import java.util.Map;

import com.ChargePoint.bean.Review;

public interface ReviewService {
	
	public List<Review> getReviewList(Review review);

	/**根据电站id获取该电站的平均分（已向上取整）
	 * @param stationID
	 * @return
	 */
	public int getReviewAvgScoreByStationID(Integer stationID) ;
	
	public List<Map<String,Object>> getReviewByPage(int startIndex,int pageSize,Integer stationID);
	
	public List<Map<String,Object>> getReviewListByPageByUserID(Integer uid,int startIndex,int pageSize);
	
	/**根据id查询评论和电站内容（个人中心按评论获取电站/名、图片、平均分等信息）
	 * @param id
	 * @return
	 */
	public Map<String, Object> getReviewStationByID( Integer id);
	
	public boolean addReview(Review review);
	
	public boolean deleteReview(Review review) ;
	
	public boolean updateReview(Review review);
	
}
