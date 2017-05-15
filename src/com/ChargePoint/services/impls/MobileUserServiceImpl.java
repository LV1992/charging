package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.MobileUserDAO;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.services.CommonService;
import com.ChargePoint.services.MobileUserService;

@Service
public class MobileUserServiceImpl implements MobileUserService{

	@Autowired
	private MobileUserDAO mobileUserDao;
	@Autowired
	private CommonService commonService;
	
	/**获取所有用户信息
	 * @param MobileUser mobileUser
	 * @return List MobileUser
	 */
	@Override
	public List<MobileUser> getMobileUserList(MobileUser mobileUser){
		return mobileUserDao.selectMobileUserList(mobileUser);
	}
	
	/**根据用户名获取用户信息
	 * @param String MobileUserName
	 * @return  MobileUser
	 */
	@Override
	public MobileUser getMobileUser(String MobileUserName){
		return mobileUserDao.selectMobileUser(MobileUserName);
	}
	
	/**根据用户id获取用户信息
	 * @param Integer userid
	 * @return  MobileUser
	 */
	@Override
	public MobileUser getMobileUser(Integer userid){
		return mobileUserDao.selectMobileUserByID(userid);
	}
	
	/**根据用户unionid获取用户信息
	 * @param Integer unionid
	 * @return  MobileUser
	 */
	@Override
	public MobileUser getMobileUserByUnionID(String unionid){
		return mobileUserDao.selectMobileUserByUnionID(unionid);
	}
	
	/**检查用户获取用户账号密码
	 * @param String MobileUserName
	 * @return  MobileUser
	 */
	@Override
	public MobileUser getMobileUser(String mobileUserName,String password){
		MobileUser user = new MobileUser();
		user.setUser_name(mobileUserName);
		user.setPassword(password);
		return mobileUserDao.checkMobileUser(user);
	}

	/**检查用户用户名、电话号码、身份证信息是否已用
	 * @param String field,String value
	 * @return  boolean true(可注册) false(不可注册)
	 */
	@Override
	public boolean getMobileFiledCount(String field,String value){
		Map<String, Object> map = new HashMap<String,Object>();
		String mapKey;
		switch (field){
		case "userName" : mapKey = "userName"; break;
		case "tel" : mapKey = "tel";break;
		default : mapKey = "identity";
		}
		map.put(mapKey, value);
		Integer count = mobileUserDao.selectMobileFiledCount(map); 
		if(count == 0){//没有记录
			return true;
		}else{
			return false;
		}
		}
	
	/**添加用户(同时新建操作记录表、新建操作反馈记录表、充电记录表)
	 * @param MobileUser mobileUser
	 * @return boolean
	 */
	@Transactional
	@Override
	public boolean addMobileUser(MobileUser mobileUser) {
		mobileUser.setReg_time(TimeFormatUtil.getFormattedNow());
		Integer id = mobileUserDao.insertMobileUser(mobileUser);
		if(-1 != id){
			Integer uid = mobileUser.getId();
			boolean def = commonService.createChargeRecord(uid) && commonService.createAppointmentRecords(uid) 
					&& commonService.createOperation(uid) && commonService.createOperationResults(uid);
			if(!def){
				deleteMobileUser(id);
			}
			return def;
		}else{
			System.out.println("insertMobileUser wrong!!!");			
			return false;
		}
	}
	
	/**根据用户名删除用户(同时删除用户下的所有记录)
	 * @param String MobileUserName
	 * @return
	 */
	@Override
	public boolean deleteMobileUser(Integer uid) {
		Integer rf = -1; 
			boolean r1 = commonService.dropRecordsTables(uid);
			boolean r2 = false;
			try{
				rf = mobileUserDao.deleteMobileUser(uid);
				if(rf > 0){
					r2 = true;
				}
			}catch(Exception e){
				System.out.println("删除MobileUser为id"+uid+"数据失败");
				e.printStackTrace();
			}
			if(r1 && r2){
				return true;
			}else{
				return false;
			}
		
	}
	
	/**修改用户
	 * @param MobileUser mobileUser
	 * @return boolean
	 */
	@Override
	public boolean updateMobileUser(MobileUser mobileUser) {
		boolean result = false;
		int res = -1;
		try{
			 res = mobileUserDao.updateMobileUser(mobileUser);
			 if(res > 0){
				 result = true;
			 }
		}catch(Exception e){
			System.out.println("修改用户出错");
			e.printStackTrace();
		}
		return result;
	}
	
	/**修改用户密码
	 * @param String userName，String password
	 * @return boolean
	 */
	@Override
	public boolean resetMobileUserPW(String userName,String password) {
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("userName", userName);
		 parameterMap.put("password", password);
		 boolean result = false;
			int res = -1;
			try{
				res = mobileUserDao.resetMobileUserPW(parameterMap);
				if(-1 != res && 0 != res){
					result = true;
				}
			}catch(Exception e){
				System.out.println("修改用户密码出错");
				e.printStackTrace();
			}
			return result;
	}
	
	/**修改用户密码
	 * @param String userName,String identity,String tel
	 * @return boolean
	 */
	@Override
	public boolean validateMobileUserPW(String userName,String identity,String tel) {
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		boolean result = false;
		Integer res = -1;
		parameterMap.put("userName", userName);
		parameterMap.put("identity", identity);
		parameterMap.put("tel", tel);
		try{
			res = mobileUserDao.validateMobileUserPW(parameterMap);
			if(res > 0){
				result = true;
			}
		}catch(Exception e){
			System.out.println("验证用户信息出错");
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	@Override
	public boolean bindMobileUser(MobileUser updateUser, Integer wxuid) throws RuntimeException{
		return updateMobileUser(updateUser) && deleteMobileUser(wxuid);
	}
}
