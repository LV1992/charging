package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.CommonDAO;
import com.ChargePoint.DAO.ReviewDAO;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.Review;
import com.ChargePoint.services.ReviewService;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO reviewDao;
	@Autowired
	private CommonDAO commonDao;

	/**获取所有评论信息
	 * @param Review review
	 * @return List Review
	 */
	public List<Review> getReviewList(Review review){
		return reviewDao.selectReviewList(review);
	}
	
	/**根据站id获取所有评论平均数
	 * @param Integer stationID
	 * @return float
	 */
	public int getReviewAvgScoreByStationID(Integer stationID) {
		Float avg = reviewDao.selectReviewAvgScoreByStationID(stationID);
//		没有评论，平均分为0
		if(null == avg){
			return 0;
		}else{
			return (int) Math.ceil(avg);
		}
	}
	
	/**分页获取评论信息
	 * @param int startIndex pageSize stationID
	 * @return List Map String,Object
	 */
	public List<Map<String,Object>> getReviewByPage(int startIndex,int pageSize,Integer stationID){
		 Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("limitStart", startIndex);
		 parameterMap.put("limitCount", pageSize);
		 parameterMap.put("stationID", stationID);
		return reviewDao.selectReviewByPage(parameterMap);
	}
	
	@Override
	public List<Map<String, Object>> getReviewListByPageByUserID( Integer uid,int startIndex, int pageSize) {
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		parameterMap.put("limitStart", startIndex);
		parameterMap.put("limitCount", pageSize);
		parameterMap.put("uid", uid);
		return reviewDao.selectReviewListByPageByUserID(parameterMap);
	}
	
	@Override
	public Map<String, Object> getReviewStationByID( Integer id) {
		return reviewDao.selectReviewStationByID(id);
	}
	
	/**添加评论
	 * @param Review review
	 * @return boolean
	 */
	public boolean addReview(Review review) {
		review.setTime(TimeFormatUtil.getFormattedNow());
		boolean res = false;
		Integer re = -1;
		try {
			commonDao.updateUTF8mb();
			re = reviewDao.insertReview(review);
			if(re > 0){
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("insertReview wrong!!!");
		}
		return res;
	}
	
	/**删除评论
	 * @param Review review
	 * @return
	 */
	public boolean deleteReview(Review review) {
		boolean f = false;
		int res = -1;
		try {
			res = reviewDao.deleteReview(review);
			if(res > 0){
				 f = true;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除review为" + review + "数据失败");
		}
		return f;
	}
	
	/**修改评论
	 * @param Review review
	 * @return boolean
	 */
	public boolean updateReview(Review review) {
		boolean result = false;
		int res = -1;
		try {
			res = reviewDao.updateReview(review);
			if(res > 0){
				 result = true;
			 }
		} catch (Exception e) {
			System.out.println("修改评论出错");
			e.printStackTrace();
		}
		return result;
	}

}
