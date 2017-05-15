package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.MobileUser;

public interface MobileUserDAO{
	
	public List<MobileUser> selectMobileUserList(MobileUser mobileUser);
	public MobileUser selectMobileUser(@Param("userName")String userName);
	public MobileUser selectMobileUserByID(@Param("uid")Integer uid);
	public MobileUser selectMobileUserByUnionID(@Param("unionid")String unionid);
	public Integer selectMobileFiledCount(Map<String, Object> map);
	public MobileUser checkMobileUser(MobileUser mobileUser);
	public Integer validateMobileUserPW(Map<String,Object> map);
	public Integer insertMobileUser(MobileUser mobileUser);
	public Integer deleteMobileUser(@Param("uid")Integer uid);
	public Integer resetMobileUserPW(Map<String,Object> map);
	public Integer updateMobileUser(MobileUser mobileUser);	
}
