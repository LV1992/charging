package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.Review;

public interface ReviewDAO{
	
	public List<Review> selectReviewList(Review review);
	public Float selectReviewAvgScoreByStationID(@Param("sid")Integer sid);
	public List<Map<String,Object>> selectReviewByPage(Map<String, Object> map);
	public List<Map<String,Object>> selectReviewListByPageByUserID(Map<String, Object> map);
	public Map<String,Object> selectReviewStationByID(@Param("id")Integer id);
	public Integer insertReview(Review review);
	public Integer deleteReview(Review review);
	public Integer updateReview(Review review);

}
