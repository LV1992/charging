package com.ChargePoint.services.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.CommonDAO;
import com.ChargePoint.DAO.WriteBackDAO;
import com.ChargePoint.Utils.TimeFormatUtil;
import com.ChargePoint.bean.WriteBack;
import com.ChargePoint.services.WriteBackService;

@Transactional
@Service
public class WriteBackServiceImpl implements WriteBackService {

	@Autowired
	WriteBackDAO writeBackDao;
	@Autowired
	CommonDAO commonDao;
	
	@Override
	public List<Map<String,Object>> getWriteBackList(WriteBack writeBack){
		return writeBackDao.selectWriteBackList(writeBack);
	}

	@Override
	public int getWriteBackCountByReview(Integer stationID,Integer reviewID){
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("station_id", stationID);
		 parameterMap.put("review_id", reviewID);
		return writeBackDao.selectWriteBackCountByReview(parameterMap);
	}
	
	@Override
	public List<Map<String, Object>> getWriteBackByPage(int startIndex,int pageSize){
		 Map<String,Object> parameterMap= new HashMap<String,Object>();
		 parameterMap.put("limitStart", startIndex);
		 parameterMap.put("limitCount", pageSize);
		return writeBackDao.selectWriteBackByPage(parameterMap);
	}
	
	@Override
	public List<Map<String, Object>> getWriteBackByPageByUserID(Integer uid, Integer sid,Integer reviewid,int startIndex, int pageSize) {
		Map<String,Object> parameterMap= new HashMap<String,Object>();
		parameterMap.put("uid", uid);
		parameterMap.put("sid", sid);
		parameterMap.put("reviewid", reviewid);
		parameterMap.put("limitStart", startIndex);
		parameterMap.put("limitCount", pageSize);
		return writeBackDao.selectWriteBackByPageByUserID(parameterMap);
	}
	
	@Override
	public boolean addWriteBack(WriteBack writeBack) {
		boolean f = false;
		int res = -1;
		try {
			//更新表情支持
			commonDao.updateUTF8mb();
			writeBack.setTime(TimeFormatUtil.getFormattedNow());
			res = writeBackDao.insertWriteBack(writeBack);
			if(res > 0){
					f = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("添加writeBack为" + writeBack + "数据失败");
			}
			return f;
	}
	
	
	@Override
	public boolean deleteWriteBack(WriteBack writeBack) {
		boolean f = false;
		int res = -1;
		try {
			res = writeBackDao.deleteWriteBack(writeBack);
			if(res > 0){
				 f = true;
			 }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除writeBack为" + writeBack + "数据失败");
		}
		return f;
	}
	
	@Override
	public boolean updateWriteBack(WriteBack writeBack) {
		boolean result = false;
		int res = -1;
		try {
			res = writeBackDao.updateWriteBack(writeBack);
			if(res > 0){
				 result = true;
			 }
		} catch (Exception e) {
			System.out.println("修改回复出错");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean updateWriteBackToRead(List<Integer> ids) {
		boolean result = false;
		int res = -1;
		try {
			res = writeBackDao.updateWriteBackToRead(ids);
			if(res > 0){
				 result = true;
			 }
		} catch (Exception e) {
			System.out.println("修改回复出错");
			e.printStackTrace();
		}
		return result;
	}

}
