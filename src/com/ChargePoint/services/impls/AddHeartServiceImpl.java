package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.AddHeartDAO;
import com.ChargePoint.bean.AddHeart;
import com.ChargePoint.services.AddHeartService;
@Transactional
@Service
public class AddHeartServiceImpl implements AddHeartService {

	@Autowired
	private AddHeartDAO addHeartDao;
	
	/**获取所有点赞信息（第一次使用会调用此方法，下次将不会调用此方法，从缓存中取数据）
	 * @param AddHeart addHeart
	 * @return List AddHeart
	 */
	@Override
	public int getAddHeartCount(AddHeart addHeart){
		return this.addHeartDao.selectAddHeartCount(addHeart);
	}
	
	/**根据评论获取点赞个数
	 * @param Integer stationID,Integer reviewID
	 * @return int 
	 */
	@Override
	public int getAddHeartCountByReview(Integer stationID,Integer reviewID){
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("station_id", stationID);
		 parameterMap.put("review_id", reviewID);
		return this.addHeartDao.selectAddHeartCountByReview(parameterMap);
	}
	
	/**添加点赞
	 * @param AddHeart addHeart
	 * @return boolean
	 */
	@Override
	@Transactional
	public boolean addAddHeart(AddHeart addHeart) {
		boolean res = false;
		Integer inid = null;
		try {
			inid = this.addHeartDao.insertAddHeart(addHeart);
			if(null != inid){
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("insertAddHeart wrong!!!");
		}
		return res;
	}
	
	/**删除点赞
	 * @param AddHeart addHeart
	 * @return
	 */
	@Override
	public boolean deleteAddHeart(AddHeart addHeart) {
		boolean res = false;
		int delcount = -1;
		try {
			delcount = this.addHeartDao.deleteAddHeart(addHeart);
			if(delcount > 0){
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("deleteAddHeart wrong!!!");
		}
		return res;
	}
	
	/**修改点赞
	 * @param AddHeart addHeart
	 * @return boolean
	 */
	@Override
	public  boolean updateAddHeart(AddHeart addHeart) {
		boolean res = false;
		int updatecount = -1;
		try {
			updatecount = this.addHeartDao.updateAddHeart(addHeart);
			if(updatecount > 0){
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("updateAddHeart wrong!!!");
		}
		return res;
	}

}
