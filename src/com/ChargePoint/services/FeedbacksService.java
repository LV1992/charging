package com.ChargePoint.services;

import com.ChargePoint.bean.Feedbacks;

public interface FeedbacksService {
	
	public boolean addFeedbacks(Feedbacks Feedbacks);
	public boolean deleteFeedbacks(Feedbacks feedback);
}
