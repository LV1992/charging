package com.ChargePoint.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.bean.AddHeart;
import com.ChargePoint.bean.Review;
import com.ChargePoint.bean.WriteBack;
import com.ChargePoint.controller.union.ControllerSupport;
import com.ChargePoint.services.ReviewService;

@Controller
@RequestMapping("/review")
public class getReview extends ControllerSupport{
	
	/**车友评论(根据电站id获取该站点下的评论列表)
	 * @param sid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value="getReviewListByStationID", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getReviewListByStationID(Integer sid,@RequestParam(defaultValue="1")Integer currentPage){
		List<Map<String,Object>> reviewList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> reviewListItem = new ArrayList<Map<String,Object>>();
		Map<String,Object> reviewMap = null;
		Map<String, Object> modelMap = new HashMap<String, Object>();
//		获取平均数
		int avgScore = reviewService.getReviewAvgScoreByStationID(sid);
//		获取评论分页数据
		reviewList = reviewService.getReviewByPage((currentPage-1)*10, 10 , sid);
		for(Map<String,Object> review : reviewList){
			reviewMap = new HashMap<String,Object>();
			reviewMap.put("review", review);
			Integer review_id = (Integer) review.get("id");
			Integer station_id = (Integer) review.get("station_id");
//		获取回复数量
			int writeBackCount = writeBackService.getWriteBackCountByReview(station_id,review_id);
//		获取点赞数量
			int addHeartCount = addHeartService.getAddHeartCountByReview(station_id,review_id);
			reviewMap.put("writeBackCount", writeBackCount);
			reviewMap.put("addHeartCount", addHeartCount);
			reviewListItem.add(reviewMap);
		}
		modelMap.put("reviewListItem", reviewListItem);
		modelMap.put("avgScore", avgScore);
		return modelMap;
	}
	
	/**点赞
	 * @param addHeartUid
	 * @param reviewid
	 * @param sid
	 * @return
	 */
	@RequestMapping(value="addOrCancelHeart", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> addOrCancelHeart(Integer addHeartUid,Integer reviewid,Integer sid){
		boolean flag = false;
		String addOrCancel = "add";
		Map<String, Object> modelMap = new HashMap<String, Object>();
		AddHeart addHeart = new AddHeart();
		addHeart.setStation_id(sid);
		addHeart.setReview_id(reviewid);
		addHeart.setAdd_heart_uid(addHeartUid);
		addHeart.setStation_id(sid);
//		点赞+1
		if(0 == addHeartService.getAddHeartCount(addHeart)){
			flag = addHeartService.addAddHeart(addHeart);
		}else{
//		取消点赞-1
			flag = addHeartService.deleteAddHeart(addHeart);
			addOrCancel = "cancel";
		}
		modelMap.put("addOrCancel", addOrCancel);
		modelMap.put("flag", flag);
		return modelMap;
	}
	
	/**根据评论获取回复列表
	 * @param sid
	 * @param reviewid
	 * @return
	 */
	@RequestMapping(value="getWriteBackListByReview", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getWriteBackListByReview(Integer sid,Integer reviewid){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Map<String,Object>> writeBackList = new ArrayList<Map<String,Object>>();
		WriteBack writeBack = new WriteBack();
		writeBack.setStation_id(sid);
		writeBack.setReview_id(reviewid);
		writeBackList = writeBackService.getWriteBackList(writeBack);
		modelMap.put("writeBackList", writeBackList);
		return modelMap;
	}
	
	/**根据用户获取（评论列表和电站数据）（我的消息中心首页）
	 * @param uid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value="getReviewListByUserID", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getReviewListByUserID(Integer uid,Integer currentPage){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Map<String,Object>> reviewList = new ArrayList<Map<String,Object>>();
		currentPage = currentPage == null ? 1 : currentPage;
		reviewList = reviewService.getReviewListByPageByUserID(uid,(currentPage-1)*10,10);
		modelMap.put("reviewList", reviewList);
		return modelMap;
	}
	
	/**电站评论
	 * @param request
	 * @return
	 */
	@RequestMapping(value="addReview", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> addReview(String jsonStr){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			jsonStr = URLDecoder.decode(jsonStr.trim(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Review review = null;
		try {
			review = JsonUtil.json2Class(jsonStr, Review.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelMap.put("flag", reviewService.addReview(review));
		return modelMap;
	}
	
	/**根据用户评论获取回复列表（包含电站数据）（车友互动）
	 * @param uid
	 * @param sid
	 * @param reviewid
	 * @param currentPage
	 * @return
	 */
	@RequestMapping(value="getWriteBackListByUserID", method= RequestMethod.GET)
	@ResponseBody//返回json对象
	public Map<String,Object> getWriteBackListByUserID(Integer uid,Integer sid,Integer reviewid,Integer currentPage){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Map<String,Object>> writeBackList = new ArrayList<Map<String,Object>>();
		currentPage = currentPage == null ? 1 : currentPage;
		//获取电站和评论信息
		Map<String,Object> reviewStation = reviewService.getReviewStationByID(reviewid);
		String picture = (String)reviewStation.get("picture");
		picture = picture == null ? null : picture.split("#")[0];
		reviewStation.put("picture", picture);
		modelMap.put("reviewStation", reviewStation);
		//获取该评论的回复列表
		writeBackList = writeBackService.getWriteBackByPageByUserID(uid,sid,reviewid,(currentPage-1)*10,10);
		modelMap.put("writeBackList", writeBackList);
		if(!writeBackList.isEmpty()){
		List<Integer> ids = new LinkedList<Integer>();
		for(Map<String,Object> writeBack : writeBackList){
				ids.add((Integer)writeBack.get("id"));
		}
		//修改为已读回复
		writeBackService.updateWriteBackToRead(ids);
		}
		return modelMap;
	}
	
	
	/**回复
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="addWriteBack", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> addWriteBack(String jsonStr){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try {
			jsonStr = URLDecoder.decode(jsonStr.trim(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		WriteBack writeBack = null;
		try {
			writeBack = JsonUtil.json2Class(jsonStr, WriteBack.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean addFlag = writeBackService.addWriteBack(writeBack);
		modelMap.put("addFlag", addFlag);
		return modelMap;
	}
	
}
