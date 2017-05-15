package com.ChargePoint.DAO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.bean.WriteBack;

public interface WriteBackDAO{
	
	public List<Map<String,Object>> selectWriteBackList(WriteBack writeBack);
	public int selectWriteBackCountByReview(Map<String, Object> map);
	public List<Map<String, Object>> selectWriteBackByPage(Map<String, Object> map);
	public List<Map<String, Object>> selectWriteBackByPageByUserID(Map<String, Object> map);
	public Integer insertWriteBack(WriteBack writeBack);
	public Integer deleteWriteBack(WriteBack writeBack);
	public Integer updateWriteBack(WriteBack writeBack);
	public Integer updateWriteBackToRead(@Param("ids")List<Integer> ids);
}
