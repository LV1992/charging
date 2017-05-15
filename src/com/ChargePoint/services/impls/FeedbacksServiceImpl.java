package com.ChargePoint.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.FeedbacksDAO;
import com.ChargePoint.bean.Feedbacks;
import com.ChargePoint.services.FeedbacksService;
@Transactional
@Service
public class FeedbacksServiceImpl implements FeedbacksService{

	@Autowired
	private FeedbacksDAO feedbacksDao;

	/**添加Feedbacks记录
	 * @param Feedbacks
	 * @return boolean
	 */
	public boolean addFeedbacks(Feedbacks Feedbacks){
		boolean res = false;
		int refC = -1;
	try{
		refC = this.feedbacksDao.insertFeedbacks(Feedbacks);
		if(refC > 0 ){
			res = true;
		}
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("insertFeedbacks wrong!!!");
	}
	return  res;
	}
	
	/**删除Feedbacks记录
	 * @param id
	 * @return boolean
	 */
	public boolean deleteFeedbacks(Feedbacks feedback){
		boolean res = false;
		int refC = -1;
	try{
		refC = this.feedbacksDao.deleteFeedbacks(feedback);
		if(refC > 0 ){
			res = true;
		}
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("删除Feedbacks为"+feedback+"数据失败");
	}
	return  res;
		
	}
	
}
