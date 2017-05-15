package com.ChargePoint.controller.union;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ChargePoint.Utils.CreateValidateImage;
import com.ChargePoint.Utils.JsonUtil;
import com.ChargePoint.Utils.MailUtil;
import com.ChargePoint.Utils.SessionTool;
import com.ChargePoint.Utils.TextUtils;
import com.ChargePoint.bean.ChargePointStation;
import com.ChargePoint.bean.MobileUser;
import com.ChargePoint.services.ChargePointStationService;
import com.ChargePoint.services.CommonService;
import com.ChargePoint.services.MobileUserService;

@Controller
@RequestMapping("/common")
public class Common extends ControllerSupport{
	
	@RequestMapping(value="getTotalCount",method = RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> getTotalCount(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageCount = 0;
		String tableName = request.getParameter("tableName").trim();
		pageCount = commonService.getTotalCount(tableName);
		modelMap.put("pageCount", pageCount);  
		modelMap.put("success", "true");
		return modelMap;
	}
	
	@RequestMapping(value="getCPTotalCount/{type}",method = RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> getCPTotalCount(HttpServletRequest request,@PathVariable("type")String type){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int pageCount = 0;
		pageCount = commonService.getCPTotalCount(type);
		modelMap.put("pageCount", pageCount);  
		return modelMap;
	}
	
	/**上传用户头像
	 * @param request
	 * @param type
	 * @param file
	 * @return
	 */
	@RequestMapping(value="uploadUserPhoto", method= RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> uploadUserPhoto(HttpServletRequest request,
			@RequestParam("file")MultipartFile file,@RequestParam()Integer uid){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		boolean res = false;
		String fileName = "";
//		数据库存储路径使用
		String sqlPath = "";
		// 获取应用目录
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
//		获取应用目录
		String path = request.getSession().getServletContext().getRealPath("/");
		String applicationName = request.getContextPath();
		applicationName = applicationName.substring(1, applicationName.length());
//		截取应用目录上层目录
		path = path.substring(0, path.indexOf(applicationName));  
		// 手机上传头像
		// 拼接文件保存目录（webapps/userPictures）
		path += "userPictures";
	    if(null != file){
	    String ofileName = file.getOriginalFilename();  
		fileName = uid + ofileName.substring(ofileName.lastIndexOf("."), ofileName.length());
//		System.out.println("文件保存目錄"+path);
		File targetFile = new File(path, fileName);
		if(!targetFile.exists()){  
	        targetFile.mkdirs();  
	    }  
	    //保存  
	    try {  
	        file.transferTo(targetFile);
	        sqlPath = basePath + "userPictures/" +fileName;
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } 
	    
	    }//end if file null
	 // 将上传路径保存至数据库
	    MobileUser mu = new MobileUser();
	    mu.setId(uid);
	    mu.setHead_portrait(sqlPath);
	    res = mobileUserService.updateMobileUser(mu);
	    modelMap.put("filePath", sqlPath);//将上传文件路径返回供手机预览使用
		modelMap.put("success", res);
		SessionTool.addSession(request, "operateContent", "上传用户ID： "+uid+" 的手机头像资料");
		SessionTool.addSession(request, "operateResult", ""+res);
		return modelMap;
	}
	
	/**
	 * 车主认证上传照片
	 * @param request
	 * @param uid
	 * @param step 0 - 1 上传第几张图片
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "uploadLicensePhoto", method = RequestMethod.POST)
	@ResponseBody // 返回json对象
	public Map<String, Object> uploadLicensePhoto(@RequestParam()Integer uid,@RequestParam()Integer step,
			HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		MobileUser mobileUser = null;
		boolean res = false;
		char separator = File.separatorChar;
//			数据库存储路径使用
			String sqlPath = "";
			String fileName = "";
			// 获取应用目录
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
//			获取应用目录
			String path = request.getSession().getServletContext().getRealPath("/");
			String applicationName = request.getContextPath();
			applicationName = applicationName.substring(1, applicationName.length());
//			截取应用目录上层目录
			path = path.substring(0, path.indexOf(applicationName));
			// 拼接文件保存目录（webapps/licensePictures/目录）
			path += "licensePictures" + separator + uid;
					// 获取图片后缀
					String ofileName = file.getOriginalFilename();
					// 将uid和step拼接命名文件
					fileName = uid + step
							+ ofileName.substring(ofileName.lastIndexOf("."), ofileName.length());
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 保存
					try {
						file.transferTo(targetFile);
						sqlPath += basePath + "licensePictures/" +uid+"/"+ fileName + "#";
					} catch (Exception e) {
						e.printStackTrace();
					}
			
					//返回照片的地址
					modelMap.put("path", sqlPath);
				// 只保存相对目录到数据库(以#隔开)、
				mobileUser = new MobileUser();
				mobileUser.setId(uid);
				String license = mobileUserService.getMobileUser(uid).getLicense();
				//之前上传过照片地址
				if(license != null && !license.equals("")){
					//该文件地址不存在
					if(-1 == license.indexOf(sqlPath)){
						sqlPath += license;
						mobileUser.setLicense(sqlPath);
					}else{
						res = true;
					}
				}else{//没有照片
					mobileUser.setLicense(sqlPath);
				}
				if(!res){
					res = mobileUserService.updateMobileUser(mobileUser);
				}
		modelMap.put("res", res);
		return modelMap;
	}
	
	@RequestMapping("/getCodeImg")
	public String getImg(HttpServletRequest request, HttpServletResponse response){ 
		//设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg"); 
		//设置响应头信息，告诉浏览器不要缓存此内容 
		response.setHeader("Pragma", "No-cache");   
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);  
		CreateValidateImage createValidateImage = new CreateValidateImage(); 
		try {  
			//输出图片方法 
			createValidateImage.getRandcode(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			}  
		//必须返回null
		return null;
			}
	
	@RequestMapping(value="sendEmailCode",method = RequestMethod.POST)
	@ResponseBody//返回json对象
	public Map<String,Object> sendEmail(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String sendTo = request.getParameter("emailAddr").trim();
		String emailCode = TextUtils.getRandomString(6); 
		String contents = "【<a href='http://www.chinajune.com/' target='_blank'>中工巨能</a>】"
		+"您正在修改登录密码，验证码为：<strong style='color: #0087ff;'>"
//				获取六位随机字符串
				+emailCode+
				"</strong>，请在1小时之内按页面提示提交验证码，切勿将验证码泄露于他人。";
//		String contents = ;
		boolean res = false;
		res = MailUtil.sendEmail(sendTo, "巨能充后台管理系统重置密码", contents);
		modelMap.put("success", res);
		modelMap.put("emailCode", emailCode);
		return modelMap;
	}
	
}

