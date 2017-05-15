package com.ChargePoint.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MobileUser implements Serializable{

	private static final long serialVersionUID = -6011241820070393952L;
	private Integer id;
	private String user_name;//用户名	
	private String openid;//对应公众号的唯一标识	
	private String unionid;//第三方登录唯一标识	
	private String nick_name;	
	private String true_name;	
	private String password;	
	private String gender;//0- 男 1-女
	private String plate_no;
	private String car_type;//车品牌
	private String car_frame_no;
	private String engine_no;
	private String interface_type;
	private String license;
	private String tel;
	private Integer age;
	private String reg_time;
	private String money;
	private String identity;
	private String head_portrait;
	private String approve_status;//认证状态(true/false)
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMoney() {
//		try {
//			if(null != money && !"".equals(money)){
//			money = DESUtils.decrypt(money, user_name);
//			}
//		} catch (Exception e) {
//			money ="--";
//			e.printStackTrace();
//		}
		return money;
	}
	
	public void setMoney(String money) {
		this.money = money;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	/**
	 * @return 对应公众号的唯一标识
	 */
	public String getOpenid() {
		return openid;
	}
	/**对应公众号的唯一标识
	 * @param openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return //0- 男 1-女
	 */
	public String getGender() {
		return gender;
	}
	/**0- 男 1-女
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	public String getCar_type() {
		return car_type;
	}
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}
	public String getCar_frame_no() {
		return car_frame_no;
	}
	public void setCar_frame_no(String car_frame_no) {
		this.car_frame_no = car_frame_no;
	}
	public String getEngine_no() {
		return engine_no;
	}
	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}
	public String getInterface_type() {
		return interface_type;
	}
	public void setInterface_type(String interface_type) {
		this.interface_type = interface_type;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getReg_time() {
		if(null != reg_time && -1 != reg_time.lastIndexOf("."))
		{
			return reg_time.substring(0,reg_time.lastIndexOf("."));
		}else{
			return reg_time;
		}
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	
	public String getHead_portrait() {
		return head_portrait;
	}
	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}
	
	public String getApprove_status() {
		return approve_status;
	}
	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}
	@Override
	public String toString() {
		return "MobileUser [id=" + id + ", user_name=" + user_name + ", openid=" + openid + ", unionid=" + unionid
				+ ", nick_name=" + nick_name + ", true_name=" + true_name + ", password=" + password + ", gender="
				+ gender + ", plate_no=" + plate_no + ", car_type=" + car_type + ", car_frame_no=" + car_frame_no
				+ ", engine_no=" + engine_no + ", interface_type=" + interface_type + ", license=" + license + ", tel="
				+ tel + ", age=" + age + ", reg_time=" + reg_time + ", money=" + money + ", identity=" + identity
				+ ", head_portrait=" + head_portrait + ", approve_status=" + approve_status + "]";
	}
	

}
