package com.ChargePoint.DAO;

import java.util.Map;

import com.ChargePoint.bean.AddHeart;

public interface AddHeartDAO{
	
	public int selectAddHeartCount(AddHeart addHeart);
	public int selectAddHeartCountByReview(Map<String, Object> map) ;
	public Integer insertAddHeart(AddHeart addHeart);
	public Integer deleteAddHeart(AddHeart addHeart);
	public Integer updateAddHeart(AddHeart addHeart);

}
