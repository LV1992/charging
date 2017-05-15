package com.ChargePoint.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.TextUtils;
import com.ChargePoint.bean.ChargePoint;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.controller.union.ControllerSupport;

@Controller
@RequestMapping("/cps")
public class getChargePointStations extends ControllerSupport{
	@RequestMapping(value="getChargePointStationMap", method= RequestMethod.GET)
//	@Cacheable(value="commonCache",key="#root.methodName")
//	@CachePut 注释，这个注释可以确保方法被执行，同时方法的返回值也被记录到缓存中，实现缓存与数据库的同步更新
//	@CachePut(value="commonCache",key="#root.methodName")
	@ResponseBody//返回json对象
	public Map<String,Object> getChargePointStationMap(HttpServletRequest request){
		List<ChargePointStation> chargePointStationList = new ArrayList<ChargePointStation>();
		List<Map<String, Object>> mapData = new LinkedList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		Map<String, Object> modelMap = new HashMap<String, Object>();
		chargePointStationList = chargePointStationService.getChargePointStationList(null);
		if(null != chargePointStationList){
//			获取所有充电站数据				
			for(ChargePointStation chargePointStation : chargePointStationList){
//			获取map所需数据
			Integer id = chargePointStation.getId();
			String location = chargePointStation.getLocation();
			String place = chargePointStation.getPlace();
			String name = chargePointStation.getName();
			int avgScore = reviewService.getReviewAvgScoreByStationID(id);
//			获取充电桩状态
			ChargePoint cp = new ChargePoint();
			cp.setStation_id(id);
//			获取该站点下所有充电桩数量
			int totalSize = chargePointService.getChargePointListCount(cp);
//			获取该站点下空闲充电桩数量
			cp.setIs_free("0");
			int freeSize = chargePointService.getChargePointListCount(cp);
			dataMap = new HashMap<String, Object>();
			dataMap.put("id", id);
			dataMap.put("avgScore", avgScore);
			dataMap.put("location", location);
			dataMap.put("place", place);
			dataMap.put("name", name);
			dataMap.put("totalSize", totalSize);
			dataMap.put("freeSize", freeSize);
			mapData.add(dataMap);
			}
			modelMap.put("mapData", mapData);
		}else{
			modelMap.put("mapData", null);
		}
		return modelMap;
	}
	
	@RequestMapping(value="getCPSList", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getCPSList(String currentLocation,@RequestParam(required = false)String searchText){
		List<ChargePointStation> chargePointStationList = new ArrayList<ChargePointStation>();
		List<Map<String, Object>> mapData = new LinkedList<Map<String, Object>>();
		Map<String, Object> dataMap = null;
		Map<String, Object> modelMap = new HashMap<String, Object>();
		chargePointStationList = chargePointStationService.searchCPSList(searchText);
		if(null != chargePointStationList){
//			获取所有充电站数据				
			for(ChargePointStation chargePointStation : chargePointStationList){
//			获取map所需数据
				Integer id = chargePointStation.getId();
				String location = chargePointStation.getLocation();
				String place = chargePointStation.getPlace();
				String name = chargePointStation.getName();
				int avgScore = reviewService.getReviewAvgScoreByStationID(id);
//			获取充电桩状态
				ChargePoint cp = new ChargePoint();
				cp.setStation_id(id);
//			获取该站点下所有充电桩数量
				int totalSize = chargePointService.getChargePointListCount(cp);
//			获取该站点下空闲充电桩数量
				cp.setIs_free("0");
				int freeSize = chargePointService.getChargePointListCount(cp);
				dataMap = new HashMap<String, Object>();
				dataMap.put("id", id);
				dataMap.put("avgScore", avgScore);
				dataMap.put("location", location);
				Double distance = null;
				if(location != null && -1 != location.indexOf(",")){
					String[] locationItem = location.split(",");
					String lng = locationItem[1];
					String lat = locationItem[0];
					if(currentLocation != null){
						String[] cItem = currentLocation.split(",");
						String clng = cItem[1];
						String clat = cItem[0];
						BigDecimal db = new BigDecimal(lng);
						Double dlng = db.doubleValue();
						BigDecimal db1 = new BigDecimal(lat);
						Double dlat = db1.doubleValue();
						BigDecimal db2 = new BigDecimal(clng);
						Double dclng = db2.doubleValue();
						BigDecimal db3 = new BigDecimal(clat);
						Double dclat = db3.doubleValue();
						distance = TextUtils.getDistance(dlng, dlat,dclng,dclat);
					}
				}
				dataMap.put("distance", distance);
				dataMap.put("place", place);
				dataMap.put("name", name);
				dataMap.put("totalSize", totalSize);
				dataMap.put("freeSize", freeSize);
				mapData.add(dataMap);
			}
			//由小到大排序
			Collections.sort(mapData, new Comparator<Map<String, Object>>() {

				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					Double distance =(Double)(o1.get("distance"));
					Double distanceNext =(Double)(o2.get("distance"));
					return distance.compareTo(distanceNext);
				}
				
			});
			modelMap.put("mapData", mapData);
		}else{
			modelMap.put("mapData", null);
		}
		return modelMap;
	}
	
	/**根据sid获取电站详情
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="getChargePointStationMapDetails", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getChargePointStationMapDetials(Integer sid){
		List<ChargePoint> chargePointList = null;
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ChargePointStation chargePointStationDetials = chargePointStationService.getChargePointStation(sid);
			ChargePoint cp = new ChargePoint();
			cp.setStation_id(sid);
			chargePointList = chargePointService.getChargePointList(cp);
//			获取该站点下空闲充电桩数量
			cp.setIs_free("0");
			int freeSize = chargePointService.getChargePointListCount(cp);
//			获取该站点下直流空闲充电桩数量
			cp.setC_p_type("0");
			int dcfreeSize = chargePointService.getChargePointListCount(cp);
//			获取该站点下交流空闲充电桩数量
			cp.setC_p_type("1");
			int acfreeSize = chargePointService.getChargePointListCount(cp);
			ChargePoint temcp = new ChargePoint();
			temcp.setStation_id(sid);
			temcp.setC_p_type("0");
			int dcSize = chargePointService.getChargePointListCount(temcp);
			temcp.setC_p_type("1");
			int acSize = chargePointService.getChargePointListCount(temcp);
			modelMap.put("freeSize", freeSize);
			modelMap.put("dcfreeSize", dcfreeSize);
			modelMap.put("acfreeSize", acfreeSize);
			modelMap.put("dcSize", dcSize);
			modelMap.put("acSize", acSize);
			modelMap.put("chargePointList", chargePointList);
			modelMap.put("chargePointStationDetails", chargePointStationDetials);
		return modelMap;
	}
	
	
	/**根据ｓｉｄ获取电站信息（评论电站使用）
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="getStationDetails", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getStationDetails(@RequestParam()Integer sid){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ChargePointStation cps = chargePointStationService.getChargePointStation(sid);
		int avg_score = reviewService.getReviewAvgScoreByStationID(sid);
		String picture = cps.getPicture();
		picture = picture == null ? null : picture.split("#")[0];
		modelMap.put("picture", picture);
		modelMap.put("name", cps.getName());
		modelMap.put("place", cps.getPlace());
		modelMap.put("avg_score", avg_score);
		return modelMap;
	}
	
}
