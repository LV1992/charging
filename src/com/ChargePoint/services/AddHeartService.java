package com.ChargePoint.services;

import com.ChargePoint.bean.AddHeart;

public interface AddHeartService {
	
	public int getAddHeartCount(AddHeart addHeart);
	
	public  int getAddHeartCountByReview(Integer stationID,Integer reviewID);
	
	public  boolean addAddHeart(AddHeart addHeart);
	
	public  boolean deleteAddHeart(AddHeart addHeart);
	
	public  boolean updateAddHeart(AddHeart addHeart);
	
}
