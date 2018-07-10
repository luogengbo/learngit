package com.szgentech.metro.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.mv.JModelAndView;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroPhoto;
import com.szgentech.metro.service.ICommonService;
import com.szgentech.metro.service.IMetroCityService;
import com.szgentech.metro.service.IMetroLineIntervalLrService;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.service.IMetroLineService;
import com.szgentech.metro.service.IMetroPhotoService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.Jstree.State;

/**
 * 项目概况管理控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/project-info/basic")
public class ProjectInfoBasicController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoBasicController.class);

	@Autowired
	private IMetroCityService cityService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IMetroLineService lineService;
	@Autowired
	private IMetroLineIntervalService intervalService;
	@Autowired
	private IMetroLineIntervalLrService lineIntervalLrService;
	@Autowired
	private IMetroPhotoService photoService;
	@Autowired
	private ISysRightService rightService;

	/**
	 * 项目概况管理首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String basicIndex() {
		return "/project-info/item_generalize";
	}

	/**
	 * 城市信息
	 * 
	 * @return
	 */
	@RequestMapping("/cityinfo")
	public String cityinfo(@RequestParam("cityId") Long cityId) {
		MetroCity city = cityService.findObjById(cityId);
		request.setAttribute("projectPdfUrl", city.getProjectPdfUrl());
		request.setAttribute("cityId", String.valueOf(city.getId()));
		return "/project-info/item_generalize_city";
	}

	/**
	 * 保存城市线路信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "新建线路")
	@RequestMapping(value = "/lineinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveLineinfo(MetroLine line) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineService.insertObj(line);
			if (result != null) { // 保存成功
				rightService.setRightDatasByUserId(getCurrentUser().getId());
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存城市线路信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存城市线路信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 保存城市线路区间信息
	 * 
	 * @param interval
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "新建区间")
	@RequestMapping(value = "/intervalinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveIntervalinfo(MetroLineInterval interval) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			interval.setStatus(1);
			Long currentUserId = getCurrentUser().getId();
			Long result = intervalService.insertObj(interval, currentUserId);
			if (result != null) { // 保存成功
				// 刷新本用户的数据权限缓存
				rightService.setRightDatasByUserId(currentUserId);
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存城市线路信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存城市线路信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新城市线路信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "线路编辑")
	@RequestMapping(value = "/lineinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLineinfo(MetroLine line) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineService.updateObj(line);
			if (result) { // 更新保存成功
				rightService.setRightDatasByUserId(getCurrentUser().getId());
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新城市线路信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新城市线路信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 更新城市线路区间信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间编辑")
	@RequestMapping(value = "/intervalinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLineIntervalInfo(MetroLineInterval interval) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = intervalService.updateObj(interval);
			if (result) { // 更新保存成功
				rightService.setRightDatasByUserId(getCurrentUser().getId());
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新城市线路区间信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新城市线路区间信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 检查地址坐标格式
	 * 
	 * @param mapXy
	 * @return 返回true表示格式正确；返回false表示格式不正确
	 */
	private boolean checkMapXyFormat(String mapXy) {
		if (!"".equals(mapXy)) {
			Pattern pattern = Pattern.compile(
					"^\\[\\s*(\\{\"x\":\\d+\\.\\d+\\,\"y\":\\d+\\.\\d+\\})(,\\s*\\{\"x\":\\d+\\.\\d+\\,\"y\":\\d+\\.\\d+\\})*\\s*\\]$");
			Matcher matcher = pattern.matcher(mapXy);
			if (!matcher.find()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 更新城市线路区间左右线信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间左右线编辑")
	@RequestMapping(value = "/interval-lrinfo/save-or-update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLineIntervalLrInfo(
			@RequestParam(value = "saveOrUpdate", required = false) String saveOrUpdate,
			@RequestParam(value = "intervalId", required = false) Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "intervalColor", required = false) String intervalColor,
			@RequestParam(value = "mapXy", required = false) String mapXy,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "buildStatus", required = false) Integer buildStatus,
			@RequestParam(value = "buildDate", required = false) String buildDate,
			@RequestParam(value = "throughDate", required = false) String throughDate,
			@RequestParam(value = "ringNum", required = false) Integer ringNum,
			@RequestParam(value = "machineNo", required = false) String machineNo,
			@RequestParam(value = "machineCompany", required = false) String machineCompany,
			@RequestParam(value = "machineType", required = false) String machineType,
			@RequestParam(value = "machineProductDate", required = false) String machineProductDate,
			@RequestParam(value = "machineContractor", required = false) String machineContractor,
			@RequestParam(value = "machineReviewDate", required = false) String machineReviewDate,
			@RequestParam(value = "remark", required = false) String remark,
			@RequestParam(value = "cutterPhotoId", required = false) Long cutterPhotoId,
			@RequestParam(value = "speedQualifiedRange", required = false) Integer speedQualifiedRange,
			@RequestParam(value = "direction", required = false) String direction,
			@RequestParam(value = "slurryPhotoId", required = false) Long slurryPhotoId,
			@RequestParam(value = "screwPhotoId", required = false) Long screwPhotoId) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			MetroLineIntervalLr intervalLr = new MetroLineIntervalLr();
			if (CommonUtils.isNotNull(intervalId)) {
				intervalLr.setIntervalId(intervalId);
			}
			if (CommonUtils.isNotNull(leftOrRight)) {
				intervalLr.setLeftOrRight(leftOrRight);
			}
			if (CommonUtils.isNotNull(intervalColor)) {
				intervalLr.setIntervalColor(intervalColor);
			}
			if (CommonUtils.isNotNull(mapXy)) {
				if (checkMapXyFormat(mapXy)) {
					intervalLr.setMapXy(mapXy);
				} else {
					RuntimeException e = new RuntimeException("MapXY格式不正确");
					throw e;
				}
			}
			if (CommonUtils.isNotNull(status)) {
				intervalLr.setStatus(status);
			}
			if (CommonUtils.isNotNull(buildStatus)) {
				intervalLr.setBuildStatus(buildStatus);
			}
			if (CommonUtils.isNotNull(buildDate)) {
				intervalLr.setBuildDate(df.parse(buildDate));
			}
			if (CommonUtils.isNotNull(throughDate)) {
				intervalLr.setThroughDate(df.parse(throughDate));
			}
			if (CommonUtils.isNotNull(ringNum)) {
				intervalLr.setRingNum(ringNum);
			}
			if (CommonUtils.isNotNull(machineNo)) {
				intervalLr.setMachineNo(machineNo);
			}
			if (CommonUtils.isNotNull(machineCompany)) {
				intervalLr.setMachineCompany(machineCompany);
			}
			if (CommonUtils.isNotNull(machineType)) {
				intervalLr.setMachineType(machineType);
			}
			if (CommonUtils.isNotNull(machineProductDate)) {
				intervalLr.setMachineProductDate(df.parse(machineProductDate));
			}
			if (CommonUtils.isNotNull(machineContractor)) {
				intervalLr.setMachineContractor(machineContractor);
			}
			if (CommonUtils.isNotNull(machineReviewDate)) {
				intervalLr.setMachineReviewDate(df.parse(machineReviewDate));
			}
			if (CommonUtils.isNotNull(cutterPhotoId)) {
				intervalLr.setCutterPhotoId(cutterPhotoId);
			}
			if (CommonUtils.isNotNull(speedQualifiedRange)) {
				intervalLr.setSpeedQualifiedRange(speedQualifiedRange);
			}
			if (CommonUtils.isNotNull(direction)) {
				intervalLr.setDirection(direction);
			}
			if (CommonUtils.isNotNull(slurryPhotoId)) {
				intervalLr.setSlurryPhotoId(slurryPhotoId);
			}
			if (CommonUtils.isNotNull(screwPhotoId)) {
				intervalLr.setScrewPhotoId(screwPhotoId);
			}
			if (CommonUtils.isNotNull(remark)) {
				intervalLr.setRemark(remark);
			}
			rightService.setRightDatasByUserId(getCurrentUser().getId());
			if ("1".equals(saveOrUpdate)) { // 更新
				String updateId = request.getParameter("updateId");
				intervalLr.setId(Long.parseLong(updateId));
				boolean result1 = lineIntervalLrService.updateObj(intervalLr);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新城市线路区间左右线信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else if ("2".equals(saveOrUpdate)) { // 保存
				Long result2 = lineIntervalLrService.insertObj(intervalLr);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存城市线路区间左右线信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新城市线路区间信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;
	}

	/**
	 * 删除城市线路信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "线路删除")
	@RequestMapping(value = "/lineinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLineinfo(@RequestParam("lineId") Long lineId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineService.deleteObj(lineId);
			if (result) { // 删除成功
				rightService.setRightDatasByUserId(getCurrentUser().getId());
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除城市线路信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除城市线路信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 区间编辑页面
	 * 
	 * @param intervalId
	 * @return
	 */
	@RequestMapping("/intervalinfo/to-edit")
	public ModelAndView lrinfoToEdit(@RequestParam("intervalId") Long intervalId) {

		MetroLineInterval interval = intervalService.findObjById(intervalId);
		JModelAndView mv = new JModelAndView("/project-info/item_generalize_interval_edit", request, response);
		mv.addObject("model", interval);

		return mv;
	}

	/**
	 * 删除城市线路区间信息
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间删除")
	@RequestMapping(value = "/intervalinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteIntervalinfo(@RequestParam("intervalId") Long intervalId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = intervalService.deleteObj(intervalId);
			if (result) { // 删除成功
				rightService.setRightDatasByUserId(getCurrentUser().getId());
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除城市线路区间信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除城市线路区间信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 线路信息
	 * 
	 * @return
	 */
	@RequestMapping("/lineinfo")
	public String lineinfo(@RequestParam("lineId") Long lineId,
			@RequestParam(value = "active", required = false) String active) {
		MetroLine line = lineService.findObjById(lineId);
		request.setAttribute("line", line);
		request.setAttribute("active", active);
		return "/project-info/item_generalize_line";
	}

	/**
	 * 区间信息
	 * 
	 * @param intervalId
	 * @param active
	 * @return
	 */
	@RequestMapping("/intervalinfo")
	public String intervalinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "active", required = false) String active) {

		MetroLineInterval interval = intervalService.findObjById(intervalId);
		List<MetroPhoto> cutterPhotos = photoService.findByType(1);
		List<MetroPhoto> screwPhotos = photoService.findByType(2);
		List<MetroPhoto> slurryPhotos = photoService.findByType(3);

		request.setAttribute("active", active);
		request.setAttribute("interval", interval);
		request.setAttribute("cutterPhotos", cutterPhotos);
		request.setAttribute("screwPhotos", screwPhotos);
		request.setAttribute("slurryPhotos", slurryPhotos);

		return "/project-info/item_generalize_area";
	}

	/**
	 * 城市工程文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "城市工程文件上传")
	@RequestMapping(value = "/city-pdf/upload", method = RequestMethod.POST)
	public String uploadCityProjectPdf(@RequestParam("cityId") String cityId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// CommonResponse r = new CommonResponse();
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String pdfUrl = (String) uploadResult.getResult();
			boolean result = cityService.editProjectPdfUrl(Long.parseLong(cityId), pdfUrl);
			if (result) { // 入库成功
				/*
				 * r.setCode(Constants.CODE_SUCCESS); r.setResult("上传成功");
				 */
			} else { // 上传失败
				logger.error("文件上传成功，入库失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				return "文件上传成功，入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/basic/cityinfo?cityId=" + cityId;
	}

	/**
	 * 线路工程文件上传
	 * 
	 * @param lineId
	 * @param file
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "线路工程文件上传")
	@RequestMapping(value = "/line-pdf/upload", method = RequestMethod.POST)
	public String uploadLineProjectPdf(@RequestParam("lineId") String lineId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String pdfUrl = (String) uploadResult.getResult();
			MetroLine line = new MetroLine();
			line.setId(Long.parseLong(lineId));
			line.setProjectPdfUrl(pdfUrl);
			boolean result = lineService.updateObj(line);
			if (result) { // 更新成功
				/*
				 * r.setCode(Constants.CODE_SUCCESS); r.setResult("上传成功");
				 */
			} else { // 上传失败
				logger.error("文件上传成功，更新入库失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				return "文件上传成功，更新入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/basic/lineinfo?lineId=" + lineId + "&active=2";
	}

	/**
	 * 线路区间工程文件上传
	 * 
	 * @param intervalId
	 * @param file
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间工程文件上传")
	@RequestMapping(value = "/interval-pdf/upload", method = RequestMethod.POST)
	public String uploadLineIntervalProjectPdf(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// CommonResponse r = new CommonResponse();
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String pdfUrl = (String) uploadResult.getResult();
			MetroLineInterval interval = new MetroLineInterval();
			interval.setId(Long.parseLong(intervalId));
			interval.setProjectPdfUrl(pdfUrl);
			boolean result = intervalService.updateObj(interval);
			if (result) { // 更新成功
				/*
				 * r.setCode(Constants.CODE_SUCCESS); r.setResult("上传成功");
				 */
			} else { // 上传失败
				logger.error("文件上传成功，更新入库失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				return "文件上传成功，更新入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/basic/intervalinfo?intervalId=" + intervalId + "&active=3";
	}
	
	/**
	 * 线路区间参数表文件上传
	 * 
	 * @param intervalId
	 * @param file
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间参数表文件上传")
	@RequestMapping(value = "/interval/parameter-pdf/upload", method = RequestMethod.POST)
	public String uploadLineIntervalParameterPdf(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String pdfUrl = (String) uploadResult.getResult();
			MetroLineInterval interval = new MetroLineInterval();
			interval.setId(Long.parseLong(intervalId));
			interval.setParameterPdfUrl(pdfUrl);
			boolean result = intervalService.updateObj(interval);
			if (result) { // 更新成功
				/*
				 * r.setCode(Constants.CODE_SUCCESS); r.setResult("上传成功");
				 */
			} else { // 上传失败
				logger.error("文件上传成功，更新入库失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				return "文件上传成功，更新入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/basic/intervalinfo?intervalId=" + intervalId + "&active=4";
	}

	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public Jstree getTreeData() {
		Long cityId = 1l; // 默认广州
		MetroCity city = cityService.findObjById(cityId);
		Jstree tree = new Jstree();
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			tree.setUrl(request.getContextPath() + "/project-info/basic/cityinfo" + "?cityId=" + city.getId());
			State cityState = tree.new State();
			cityState.setOpened(true);
			tree.setState(cityState);
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree cityChild = new Jstree();
					cityChild.setId("line_" + line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					cityChild.setUrl(
							request.getContextPath() + "/project-info/basic/lineinfo" + "?lineId=" + line.getId());
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					cityChild.setState(lineState);
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_" + interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							lineChild.setUrl(request.getContextPath() + "/project-info/basic/intervalinfo"
									+ "?intervalId=" + interval.getId());
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							lineChild.setState(intervalState);
							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		return tree;
	}

	/**
	 * 城市工程文件删除
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "城市工程文件删除")
	@RequestMapping(value = "/city-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteCityProjectPdf(@RequestParam("cityId") Long cityId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = cityService.editProjectPdfUrl(cityId, null);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else { // 删除失败
				logger.error("删除失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除失败");
			}
		} catch (Exception e) {
			logger.error("城市工程文件删除异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("城市工程文件删除异常");
		}
		return commonResponse;
	}

	/**
	 * 线路工程文件删除
	 * 
	 * @param lineId
	 * @return
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "线路工程文件删除")
	@RequestMapping(value = "/line-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLineProjectPdf(@RequestParam("lineId") Long lineId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroLine line = new MetroLine();
			line.setId(lineId);
			line.setProjectPdfUrl("");
			boolean result = lineService.updateObj(line);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else { // 删除失败
				logger.error("删除失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除失败");
			}
		} catch (Exception e) {
			logger.error("线路工程文件删除异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("线路工程文件删除异常");
		}
		return commonResponse;
	}

	/**
	 * 线路区间工程文件删除
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间工程文件删除")
	@RequestMapping(value = "/interval-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLineIntervalProjectPdf(@RequestParam("intervalId") Long intervalId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroLineInterval interval = new MetroLineInterval();
			interval.setId(intervalId);
			interval.setProjectPdfUrl("");
			boolean result = intervalService.updateObj(interval);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else { // 删除失败
				logger.error("删除失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除失败");
			}
		} catch (Exception e) {
			logger.error("线路区间工程文件删除异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("线路区间工程文件删除异常");
		}
		return commonResponse;
	}
	
	/**
	 * 线路区间参数表文件删除
	 */
	@SysControllorLog(menu = "项目概况管理", operate = "区间参数表文件删除")
	@RequestMapping(value = "/interval/parameter-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLineIntervalParameterPdf(@RequestParam("intervalId") Long intervalId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroLineInterval interval = new MetroLineInterval();
			interval.setId(intervalId);
			interval.setParameterPdfUrl("");
			boolean result = intervalService.updateObj(interval);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else { // 删除失败
				logger.error("删除失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除失败");
			}
		} catch (Exception e) {
			logger.error("线路区间参数表文件删除异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("线路区间参数表文件删除异常");
		}
		return commonResponse;
	}
}
