package com.ChargePoint.services;

import java.util.List;
import java.util.Map;
import com.ChargePoint.bean.MobileUser;

public interface MobileUserService {
	
	public List<MobileUser> getMobileUserList(MobileUser mobileUser);
	public MobileUser getMobileUser(String MobileUserName);
	public MobileUser getMobileUser(Integer userid);
	public MobileUser getMobileUserByUnionID(String unionid);
	public MobileUser getMobileUser(String MobileUserName,String password);
	public boolean getMobileFiledCount(String field,String value);
	/**添加用户(同时新建操作记录表、新建操作反馈记录表、充电记录表)
	 * @param MobileUser mobileUser
	 * @return boolean
	 */
	public boolean addMobileUser(MobileUser mobileUser) ;
	public boolean deleteMobileUser(Integer uid) ;
	/**微信绑定app用户
	 * @param updateUser（待更新的用户数据）
	 * @param wxuid（待删除的微信用户id）
	 * @return boolean
	 */
	public boolean bindMobileUser(MobileUser updateUser,Integer wxuid) ;
	public boolean updateMobileUser(MobileUser mobileUser) ;
	public boolean resetMobileUserPW(String userName,String password) ;
	public boolean validateMobileUserPW(String userName,String identity,String tel) ;
	
}
