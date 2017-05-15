package com.ChargePoint.services.impls;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ChargePoint.DAO.WarningDAO;
import com.ChargePoint.bean.Warning;
import com.ChargePoint.services.WarningService;

@Transactional
@Service
public class WarningServiceImpl implements WarningService {
	
	@Autowired
	private WarningDAO warningDao;
	@Override
	public List<Warning> selectWarningList(Warning warning) {
		return warningDao.selectWarningList(warning);
	}
	
	@Override
	public List<Warning> getWarningByPage(Map<String, Object> map) {
		return warningDao.selectWarningByPage(map);
	}
	
	@Override
	public List<Warning> getAllWarningByPage(Map<String, Object> map) {
		return warningDao.selectAllWarningByPage(map);
	}

	@Override
	public boolean updateWarning(Warning warning) {
		boolean result = false;
		int res = -1;
		try {
			res = warningDao.updateWarning(warning);
			if(res > 0){
				 result = true;
			 }
		} catch (Exception e) {
			System.out.println("修改告警信息出错");
			e.printStackTrace();
		}
		return result;
	}

}
