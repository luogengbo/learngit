package com.szgentech.metro.controller;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.HttpClientUtil;
import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.HomepageResponse;
import com.szgentech.metro.model.IntervalQualityEntry;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroUpdateApp;
import com.szgentech.metro.model.SettlingName;
import com.szgentech.metro.model.SettlingParticulars;
import com.szgentech.metro.service.ICommonService;
import com.szgentech.metro.service.IMetroSettlingService;



@Controller
@RequestMapping("/settling")
public class MetroSettlingController {
	private static Logger logger = Logger.getLogger(MetroSettlingController.class);
	private final static String URL = ConfigProperties.getValueByKey("SEGMENT_VISIT_URL");

	@Autowired
	private IMetroSettlingService SettlingService;
	@Autowired
	private ICommonService commonService;
	
	/**
	 * 操作记录管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/sysm/app_update_version";
	}
	
	/**
	 * APP沉降查询（一次性返回所有数据）
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/get/settlingdata")
	public HomepageResponse getfindhomepage(Long userId) {
		HomepageResponse homepageResponse = new HomepageResponse();
		if (userId == null) {
			homepageResponse.setCode(500);
		} else {
			List<Homepage> city = SettlingService.getfindSettling(userId);
			homepageResponse.setHomepages(city);
			homepageResponse.setCode(200);
		}
		return homepageResponse;
	}

	/**
	 * APP沉降点名称查询
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/get/settlingname")
	public CommonResponse findSettlingName(Long intervalid) {
		CommonResponse commonResponse = new CommonResponse();
		if (intervalid == null) {
			commonResponse.setCode(500);
		} else {
			List<SettlingName> city = SettlingService.findSettlingName(intervalid);
			commonResponse.setResult(city);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * APP沉降点详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/app/get/settlingParticulars")
	public CommonResponse findSettlingParticulars(String spname) {
		CommonResponse commonResponse = new CommonResponse();
		if (spname == null) {
			commonResponse.setCode(500);
		} else {
			SettlingParticulars city = SettlingService.findSettlingParticulars(spname);
			commonResponse.setResult(city);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * APP管片
	 * 
	 * @return
	 */
	@RequestMapping("/app/get/gitduct")
	@ResponseBody
	public List<IntervalQualityEntry> findgitduct(Long intervalId, String leftOrRight, String start, String end) {
		try {
			MetroLineInterval interval = SettlingService.findDuctId(intervalId);
			if (interval != null) {
				String line = null;
				if (leftOrRight.equals("l")) {
					line = "1";
				}
				if (leftOrRight.equals("r")) {
					line = "0";
				}
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				NameValuePair pvp = new BasicNameValuePair("sectionId", interval.getDuctIntervalId().toString());
				NameValuePair pvp2 = new BasicNameValuePair("line", line);
				NameValuePair pvp3 = new BasicNameValuePair("start", start);
				NameValuePair pvp4 = new BasicNameValuePair("end", end);
				params.add(pvp);
				params.add(pvp2);
				params.add(pvp3);
				params.add(pvp4);
				String res = HttpClientUtil.post(URL + "/records", params);
				ObjectMapper mapper = new ObjectMapper();
				List<IntervalQualityEntry> resMap = mapper.readValue(res, List.class);
				return resMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("APP管片:"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * APP版本更新
	 * @return
	 */
	@RequestMapping("/app/update/version")
	@ResponseBody
	public MetroUpdateApp MetroUpdateData(
			@RequestParam("facilityflag") Integer facilityflag) {
		MetroUpdateApp metro = SettlingService.MetroUpdateData(facilityflag);
		if(metro != null) {
			metro.setCode(200);
		}else{
			metro = new MetroUpdateApp();
			metro.setCode(500);
		}
		return metro;
	}
	
	/**
	 * APP历史上传版本
	 * @return
	 */
	@RequestMapping("/app/history/version")
	@ResponseBody
	public PageResultSet<MetroUpdateApp> ListMetroUpdateData(
			@RequestParam("facilityflag") Integer facilityflag,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroUpdateApp> metro = SettlingService.ListMetroUpdateData(facilityflag,pageNum, pageSize);
		return metro;
	}
	
	/**
	 * APP新版本发布
	 * @return
	 */
	@RequestMapping("/app/save/version")
	@ResponseBody
	public ModelAndView saveVersion(
			@RequestParam("upAppname") String upAppname,
			@RequestParam("upversioncode") Integer upversioncode,
			@RequestParam("upversionname") String upversionname,
			@RequestParam(value = "fileUpName", required = false) MultipartFile fileUpName,
			@RequestParam("upUpdatingContent") String upUpdatingContent,
			@RequestParam("facilityflag") Integer facilityflag,
			@RequestParam("upWhetherupdating") Integer upWhetherupdating) {


		ModelAndView modelAndView = new ModelAndView();
		
		
		boolean b = SettlingService.findMetroUpdateAppC(upversioncode);
		if (b) {
			modelAndView.addObject("code", 1);
		} else {
			CommonResponse uploadResult = commonService.fileUploadApp(fileUpName);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return modelAndView.addObject("code", 10);
			}
			String UpNameUrl = (String) uploadResult.getResult();
			SettlingService.addMetroUpdateApp(upAppname, upversioncode, upversionname, UpNameUrl,
					upUpdatingContent, facilityflag, upWhetherupdating);
		}
		modelAndView.setViewName("/sysm/app_update_version");
		return modelAndView;

	}
	
	/**
	 * APP历史版本删除
	 * @return
	 */
	@RequestMapping("/app/delete/version")
	@ResponseBody
	public CommonResponse delMetroUpdateData(@RequestParam("upid") Integer upid) {
		CommonResponse commonResponse = new CommonResponse();
		boolean result = SettlingService.deleteObj(upid);
		if (result) {
			 // 删除成功
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult("删除成功。");
		} else {
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除失败！");
		}
		return commonResponse;
	}
}
