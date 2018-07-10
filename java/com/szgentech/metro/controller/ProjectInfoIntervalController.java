package com.szgentech.metro.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.mv.JModelAndView;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.DateUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroDailyreportRec;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.model.MetroIntervalRiskSegmentation;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervalMdRe;
import com.szgentech.metro.model.MetroLineIntervalPp;
import com.szgentech.metro.model.MetroLineIntervalRp;
import com.szgentech.metro.model.MetroLineIntervalSa;
import com.szgentech.metro.model.MetroLineIntervalSchedule;
import com.szgentech.metro.model.MetroLineIntervalSp;
import com.szgentech.metro.model.MetroLineIntervalSt;
import com.szgentech.metro.model.MetroWeeklyReportRec;
import com.szgentech.metro.service.ICommonService;
import com.szgentech.metro.service.IMetroDailyReportRecService;
import com.szgentech.metro.service.IMetroDictionaryService;
import com.szgentech.metro.service.IMetroLineIntervalMdReService;
import com.szgentech.metro.service.IMetroLineIntervalPpService;
import com.szgentech.metro.service.IMetroLineIntervalRpService;
import com.szgentech.metro.service.IMetroLineIntervalSaService;
import com.szgentech.metro.service.IMetroLineIntervalScheduleService;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.service.IMetroLineIntervalSpService;
import com.szgentech.metro.service.IMetroLineIntervalStService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroWeeklyReportRecService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.service.impl.MetroIntervalRiskSegmentationService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.Jstree.State;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 区间数据管理控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/project-info/interval")
public class ProjectInfoIntervalController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoIntervalController.class);

	@Autowired
	private IMetroLineIntervalMdReService lineIntervalMdReService;
	@Autowired
	private ICommonService commonService;
	@Autowired
	private IMetroLineIntervalPpService lineIntervalPpService;
	@Autowired
	private IMetroLineIntervalRpService lineIntervalRpService;
	@Autowired
	private IMetroLineIntervalSpService lineIntervalSpService;
	@Autowired
	private IMetroLineIntervalService lineIntervalService;
	@Autowired
	private IMetroDictionaryService dictionaryService;
	@Autowired
	private IMetroLineIntervalSaService lineIntervalSaService;
	@Autowired
	private IMetroLineIntervalStService lineIntervalStService;
	@Autowired
	private IMetroLineIntervalScheduleService lineIntervalScheduleService;
	@Autowired
	private IMetroLineIntervalService intervalService;
	@Autowired
	private IMetroDailyReportRecService dailyReportRecService;
	@Autowired
	private IMetroWeeklyReportRecService weeklyReportRecService;
	@Autowired
	private IMetroMonitorInfoCityService infoCityService;
	@Autowired
	private MetroIntervalRiskSegmentationService riskSegmentationService;
	@Autowired
	private ISysRightService rightService;
	/**
	 * 区间数据管理首页
	 */
	@RequestMapping("/index")
	public String intervalIndex() {
		return "/project-info/item_section";
	}
	// 沉降点编辑删除

	/**
	 * 沉降点信息编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/spinfo/to-edit")
	public ModelAndView spinfoToEdit(@RequestParam("id") Long id) {
		MetroLineIntervalSp sp = lineIntervalSpService.findObjById(id);
		JModelAndView mv = new JModelAndView("/project-info/item_section_right_edit", request, response);
		mv.addObject("model", sp);
		return mv;
	}

	/**
	 * 删除沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除沉降点")
	@RequestMapping(value = "/spinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSpinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSpService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除沉降点信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除沉降点信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	
	/**
	 * 删除区间左右线管片姿态全部信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除左右线管片姿态")
	@RequestMapping(value = "/sainfoall/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSainfoall(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.deleteSaInfo(intervalId, leftOrRight);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除左右线管片姿态信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除左右线管片姿态信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除区间左右线盾尾间隙全部信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除左右线盾尾间隙")
	@RequestMapping(value = "/stinfoall/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteStinfoall(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.deleteStInfo(intervalId, leftOrRight);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除左右线盾尾间隙信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除左右线盾尾间隙信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除管片姿态")
	@RequestMapping(value = "/sainfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSainfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除管片姿态信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除管片姿态信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除盾尾间隙")
	@RequestMapping(value = "/stinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteStinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除盾尾间隙信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除盾尾间隙信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除沉降点监测上传记录信息 删除文件的同时，需要删除文件对应的数据
	 * 
	 * @param id
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除沉降点监测文件")
	@RequestMapping(value = "/mdreinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteMdreinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			// 需要获取文件解析并删除两个表记录
			boolean result = lineIntervalMdReService.deleteLineIntervalMdReInfo(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除沉降点监测上传记录信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除沉降点监测上传记录信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 加载区间数据信息页面
	 * 
	 * @param intervalId
	 * @param desc
	 * @return
	 */
	@RequestMapping("/intervalinfo")
	public String intervalinfo(@RequestParam Long intervalId, @RequestParam(required = false) String desc) {

		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(intervalId);
		MetroLineInterval interval = lineIntervalService.findObjById(intervalId);
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);

		request.setAttribute("pp", pp);
		request.setAttribute("intervalId", intervalId);
		request.setAttribute("interval", interval);
		request.setAttribute("desc", desc);
		request.setAttribute("riskPdfUrl", interval.getRiskPdfUrl());
		request.setAttribute("surveyPdfUrl", interval.getSurveyPdfUrl());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		request.setAttribute("beginTime", sdf.format(Calendar.getInstance().getTime()));
		request.setAttribute("dics", dicSet.getList());

		return "/project-info/item_section_right";
	}

	/**
	 * 上传svg文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ppinfo/svg-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadFile(MultipartHttpServletRequest request) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				return result;
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}
	
	/**
	 * 上传剖面图大地坐标文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/img-map-xy-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadImgMapXyFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			String intervalId = request.getParameter("intervalId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				MetroLineInterval interval = new MetroLineInterval();
				interval.setId(Long.parseLong(intervalId));
				interval.setImgMapXyUrl((String) result.getResult());
				boolean updateR = lineIntervalService.updateObj(interval);
				if (updateR) {
					return result;
				} else {
					logger.error("文件上传成功，更新数据库失败");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("上传失败");
				}
			} else {
				logger.error("文件上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 地质详勘文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "地质详勘文件上传")
	@RequestMapping(value = "/survey-pdf/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadRiskPdf(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String surveyPdfUrl = (String) uploadResult.getResult();
			boolean result = intervalService.editSurveyPdfUrl(intervalId, surveyPdfUrl);
			if (result) { // 入库成功
			} else { // 上传失败
				logger.error("文件上传成功，入库失败");
				return "文件上传成功，入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
		}
		MetroLineInterval metroline = intervalService.findObjById(intervalId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("surveyPdfUrl", metroline.getSurveyPdfUrl());
		map.put("intervalId", metroline.getId());

		return map;
	}

	/**
	 * 上传沉降点监测记录文件
	 * 
	 * @param request
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "上传沉降点监测文件")
	@RequestMapping(value = "/mdre-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadMdreFile(
			@RequestParam(value = "mdreFile") MultipartFile mf, 
			@RequestParam Long intervalId)
	{
		CommonResponse commonResponse = new CommonResponse();
		try {

			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				CommonResponse updateR = lineIntervalMdReService.uploadLineIntervalMdReData(intervalId, (String) result.getResult());

				if (updateR.getCode() == Constants.CODE_SUCCESS) {
					return result;
				} else {
					logger.error(updateR.getResult());
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult(updateR.getResult());
				}
			} else {
				logger.error("文件上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 分页查找区间沉降点信息
	 * 
	 * @param intervalId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/spinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSp> findSpinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalSp> resultSet = lineIntervalSpService.findLineIntervalSpInfo(intervalId, pageNum,
				pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间盾尾间隙信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/stinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSt> findStinfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam("pageNum") int pageNum, 
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalSt> resultSet = lineIntervalStService.findLineIntervalStInfo(intervalId, leftOrRight, pageNum,
				pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间管片姿态信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/sainfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSa> findSainfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam("pageNum") int pageNum, 
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalSa> resultSet = lineIntervalSaService.findLineIntervalSaInfo(intervalId, leftOrRight, pageNum,
				pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间沉降点监测数据上传记录信息
	 * 
	 * @return
	 */
	@RequestMapping("/mdreinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalMdRe> findMdreinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalMdRe> resultSet = lineIntervalMdReService.findLineIntervalMdReInfo(intervalId,
				pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间风险位置信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalRp> findRiskinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalRp> resultSet = lineIntervalRpService.findLineIntervalRpInfo(intervalId, pageNum,
				pageSize);
		return resultSet;
	}

	/**
	 * 获取风险位置信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo/to-edit")
	@ResponseBody
	public MetroLineIntervalRp riskinfoToEdit(@RequestParam("id") Long id) {
		MetroLineIntervalRp rp = lineIntervalRpService.findObjById(id);
		return rp;
	}

	/**
	 * 保存风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "新增风险位置")
	@RequestMapping(value = "/riskinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savRiskinfo(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalRpService.insertObj(rp);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存风险位置信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存风险位置信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "更新风险位置")
	@RequestMapping(value = "/riskinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRiskinfo(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalRpService.updateObj(rp);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 删除风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除风险位置")
	@RequestMapping(value = "/riskinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteRiskinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalRpService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除风险位置信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除风险位置信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 上传风险位置文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/riskinfo/file-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadRiskFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				return result;
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传风险位置失败");
			}
		} catch (Exception e) {
			logger.error("风险位置文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("风险位置文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置图片
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "更新风险位置图片")
	@RequestMapping(value = "/riskImg-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadRiskImg(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();

		try {
			boolean result = lineIntervalRpService.updateRiskImg(rp.getId(), rp.getRiskImgUrl());
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置图片出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置图片异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置文档
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "更新风险位置文档")
	@RequestMapping(value = "/riskPdf-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadRiskPdf(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalRpService.updateRiskPdf(rp.getId(), rp.getRiskPdf1Url(), rp.getRiskPdf2Url(),
					rp.getRiskPdf3Url());
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置文档出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置文档异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "新增沉降点")
	@RequestMapping(value = "/spinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savSpinfo(MetroLineIntervalSp sp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalSpService.insertObj(sp);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存沉降点信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存沉降点信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "编辑沉降点")
	@RequestMapping(value = "/spinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateSpinfo(MetroLineIntervalSp sp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSpService.updateObj(sp);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新沉降点出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新沉降点异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "新增管片姿态")
	@RequestMapping(value = "/sainfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savSainfo(MetroLineIntervalSa sa) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalSaService.insertObj(sa);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存管片姿态信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存管片姿态信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "编辑管片姿态")
	@RequestMapping(value = "/sainfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateSainfo(MetroLineIntervalSa sa) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.updateObj(sa);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新管片姿态出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新管片姿态异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "新增盾尾间隙")
	@RequestMapping(value = "/stinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savStinfo(MetroLineIntervalSt st) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalStService.insertObj(st);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存盾尾间隙信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存盾尾间隙信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "编辑盾尾间隙")
	@RequestMapping(value = "/stinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateStinfo(MetroLineIntervalSt st) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.updateObj(st);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新盾尾间隙出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新盾尾间隙异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 导出沉降点数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "导出沉降点数据")
	@RequestMapping(value = "/spinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportLrinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "desc", required = false) String desc) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroLineIntervalSp> datas = lineIntervalSpService.findLineIntervalSps(intervalId);
			String excelFileName = writeExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出沉降点数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出盾构数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "导出盾构数据")
	@RequestMapping(value = "/sdinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportShieldinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ring") String ring,
			@RequestParam("type") String type, @RequestParam("key") String key,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date date) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			Map<String, Object> datas = lineIntervalService.getShieldData(intervalId, leftOrRight, date, ring, key,
					type);
			MetroLineInterval metroLineInterval = lineIntervalService.findObjById(intervalId);
			String intervalName = metroLineInterval.getIntervalName();
			String machineNo = "";
			for (MetroLineIntervalLr lr : metroLineInterval.getIntervalLrList()) {
				if (leftOrRight.equals(lr.getLeftOrRight())) {
					machineNo = lr.getMachineNo();
					break;
				}
			}
			String excelFileName = writeShieldToExcel(datas, key, intervalName, machineNo);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出盾构数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出盾尾间隙数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "导出盾尾间隙数据")
	@RequestMapping(value = "/stinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportStinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "desc", required = false) String desc) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroLineIntervalSt> datas = lineIntervalStService.findLineIntervalSts(intervalId);
			String excelFileName = writeStExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出沉降点数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出沉降点数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "导出管片姿态数据")
	@RequestMapping(value = "/sainfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportSainfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "desc", required = false) String desc) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroLineIntervalSa> datas = lineIntervalSaService.findLineIntervalSas(intervalId);
			String excelFileName = writeSaExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出管片姿态数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 将沉降点数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeExcel(List<MetroLineIntervalSp> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "沉降点数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("沉降点数据", 0);
			String[] title = { "沉降点名称", "线路", "初始里程", "大地坐标X", "大地坐标Y", "累计沉降正值", "累计沉降负值", "沉降速率预警值" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSp data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getSpName()));
					if (data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getOriginMileage())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getMapY().floatValue())));
					sheet.addCell(new Label(4, i + 1, String.valueOf(data.getMapX().floatValue())));
					sheet.addCell(
							new Label(5, i + 1, String.valueOf(data.getSpSumAdd() != null ? data.getSpSumAdd() : "")));
					sheet.addCell(
							new Label(6, i + 1, String.valueOf(data.getSpSumSub() != null ? data.getSpSumSub() : "")));
					sheet.addCell(new Label(7, i + 1,
							String.valueOf(data.getSpSpeedWarningVal() != null ? data.getSpSpeedWarningVal() : "")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("沉降点数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("沉降点数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将盾构数据信息写入到excel
	 * 
	 * @param datas
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeShieldToExcel(Map<String, Object> datas, String key, String intervalName, String machineNo) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + intervalName + "_" + machineNo + "_" + key + "_盾构数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet(key + "_盾构数据", 0);
			String[] title = { "字段值", "日期" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				int i = 0;
				for (Map.Entry<String, Object> entry : datas.entrySet()) {
					sheet.addCell(
							new Label(0, i + 1, String.valueOf(entry.getValue() != null ? entry.getValue() : "")));
					sheet.addCell(new Label(1, i + 1, StringUtil.timeToString(entry.getKey())));
					i++;
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("盾构数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("盾构数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将盾尾间隙数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeStExcel(List<MetroLineIntervalSt> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "盾尾间隙数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("盾尾间隙数据", 0);
			String[] title = { "环号", "线路", "盾尾间隙上", "盾尾间隙下", "盾尾间隙左", "盾尾间隙右", "日期时间" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSt data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getRingNum()));
					if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getStUp())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getStDown())));
					sheet.addCell(new Label(4, i + 1, String.valueOf(data.getStLeft())));
					sheet.addCell(new Label(5, i + 1, String.valueOf(data.getStRight())));
					sheet.addCell(new Label(6, i + 1, DateUtil.format(data.getDateTime(), "YYYY/MM/dd HH:mm:ss")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("盾尾间隙数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("盾尾间隙数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将管片姿态数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeSaExcel(List<MetroLineIntervalSa> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "管片姿态数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("管片姿态数据", 0);
			String[] title = { "环号", "线路", "水平偏差", "垂直偏差", "日期时间" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSa data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getRingNum()));
					if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getHorizontalDev())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getVerticalDev())));
					sheet.addCell(new Label(4, i + 1, DateUtil.format(data.getDateTime(), "YYYY/MM/dd HH:mm:ss")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("管片姿态数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("管片姿态数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 导入盾尾间隙数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "上传盾尾间隙数据")
	@RequestMapping(value = "/stinfo/import", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importStinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导入失败");
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导入异常");
		}
		return commonResponse;
	}

	/**
	 * 导入管片姿态数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "上传管片姿态数据")
	@RequestMapping(value = "/sainfo/import", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importSainfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");

				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导入失败");

				/*
				 * request.setAttribute("msg", "导入失败"); return "/common/error";
				 */
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			/*
			 * request.setAttribute("msg", "导入异常"); return "/common/error";
			 */

			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导入异常");

		}
		return commonResponse;
	}

	/**
	 * 导入沉降点数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "上传沉降点数据")
	@RequestMapping(value = "/spinfo/import", method = RequestMethod.POST)
	public String importLrinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSpService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				request.setAttribute("msg", "导入失败");
				return "/common/error";
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			request.setAttribute("msg", "导入异常");
			return "/common/error";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/interval/intervalinfo?intervalId=" + intervalId + "&desc=tab2";
	}

	/**
	 * 保存或更新工程进度信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "编辑工程进度信息")
	@RequestMapping(value = "/ppinfo/save-or-update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveOrUpdateLineIntervalPpInfo(MetroLineIntervalPp pp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroLineIntervalPp oriPp = null;
			//为空先判断原来是否存在, 原来存在则用原来id放进当前ID中
			if (!CommonUtils.isNotNull(pp.getId())) { 
				oriPp = lineIntervalPpService.findByIntervalId(pp.getIntervalId());
			}
			if(oriPp != null){
				pp.setId(oriPp.getId());
			}
			if (CommonUtils.isNotNull(pp.getId())) { // 更新
				boolean result1 = lineIntervalPpService.updateObj(pp);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新城市线路区间工程进度出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else { // 保存
				Long result2 = lineIntervalPpService.insertObj(pp);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存城市线路区间工程进度出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新城市线路区间工程进度异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;
	}

	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public Jstree getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		Jstree tree = new Jstree();
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			// tree.setUrl(request.getContextPath()+"/project-info/interval/cityinfo"+"?cityId="+city.getId());
			State cityState = tree.new State();
			cityState.setOpened(true);
			cityState.setDisabled(true);
			tree.setState(cityState);
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree cityChild = new Jstree();
					cityChild.setId("line_" + line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					// cityChild.setUrl(request.getContextPath()+"/project-info/interval/lineinfo"+"?lineId="+line.getId());
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					lineState.setDisabled(true);
					cityChild.setState(lineState);
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_" + interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							lineChild.setUrl(request.getContextPath() + "/project-info/interval/intervalinfo"
									+ "?intervalId=" + interval.getId());
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							if (cityChilds.size() == 0 && lineChilds.size() == 0) {
								intervalState.setSelected(true);
							}
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
	 * 沉降点监测数据上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "/md-re/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadLineIntervalMdReData(@RequestParam("intervalId") Long intervalId) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			CommonResponse uploadResult = commonService.fileUpload(request);
			if (Constants.CODE_SUCCESS != uploadResult.getCode()) { // 上传成功
				return uploadResult;
			}
			String uploadFileUrl = (String) uploadResult.getResult();
			CommonResponse result = lineIntervalMdReService.uploadLineIntervalMdReData(intervalId, uploadFileUrl);
			if (result.getCode() == Constants.CODE_SUCCESS) { // 上传成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("上传成功");
			} else { // 上传失败
				logger.error("沉降点监测数据上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("沉降点监测数据上传失败");
			}
		} catch (Exception e) {
			logger.error("沉降点监测数据上传异常", e);
			commonResponse.setCode(0);
			commonResponse.setResult("沉降点监测数据上传异常");
		}
		return commonResponse;
	}

	/**
	 * 分页查找区间左右计划进度信息
	 * 
	 * @return
	 */
	@RequestMapping("/schedule/findPage")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSchedule> findPageScheduleInfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight, 
			@RequestParam(value = "startDateStr", required = false) String startDateStr,
			@RequestParam(value = "endDateStr", required = false)String endDateStr, 
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalSchedule> resultSet = lineIntervalScheduleService
				.findPageScheduleInfo(intervalId, leftOrRight,startDateStr,endDateStr, pageNum, pageSize);
		return resultSet;
	}
	
	/**
	 * 查找区间左右线计划进度信息
	 * 
	 * @return
	 */
	@RequestMapping("/findLrScheduleByType")
	@ResponseBody
	public List<MetroLineIntervalSchedule> findIntervalLrSchedule(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("startDateStr") String startDateStr, 
			@RequestParam("endDateStr") String endDateStr,
			@RequestParam("cycleType") String cycleType) {
		boolean isFill = true;
		if(Constants.CYCLE_TYPE_DAILY.equals(cycleType)){
			isFill = false;
		}
		List<MetroLineIntervalSchedule> resultSet = lineIntervalScheduleService
				.findIntervalLrSchedule(intervalId, leftOrRight,startDateStr, endDateStr, cycleType, isFill);
		return resultSet;
	}

	/**
	 * 更新保存区间计划进度信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "区间计划进度编辑")
	@RequestMapping(value = "/addschedule", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addSchedule(
			@RequestParam(value = "intervalId", required = true) Long intervalId,
			@RequestParam(value = "leftOrRight", required = true) String leftOrRight,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "scheduleRingNum", required = false) Integer scheduleRingNum) {
		return lineIntervalScheduleService.saveSchedule(intervalId, leftOrRight, startDate, endDate, scheduleRingNum);
	}

	/**
	 * 删除区间计划进度信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除区间计划进度")
	@RequestMapping(value = "/schedule/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteScheduleInfo(
			@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalScheduleService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除区间计划进度出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除区间计划进度数据异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 查询日报表上传记录
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @param reportTime 报表时间
	 * @return
	 */
	@RequestMapping("/findReporRec")
	@ResponseBody
	public MetroDailyreportRec getReportRec(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, 
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		MetroDailyreportRec reportRec =  dailyReportRecService.findDailyReportRec(intervalId, leftOrRight, reportTime);
		return reportRec;
	}
	
	/**
	 * 日报表文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "日报表文件上传")
	@RequestMapping(value = "/dailyreport-pdf/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveDailyReportPdf(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, 
			@RequestParam("uploadPeople") String uploadPeople, 
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			CommonResponse saveResult = commonService.fileUpload(file);
			if (saveResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			MetroDailyreportRec dailyreportRec= new MetroDailyreportRec();
			dailyreportRec.setIntervalId(intervalId);
			dailyreportRec.setLeftOrRight(leftOrRight);
			dailyreportRec.setReportTime(reportTime);
			dailyreportRec.setReportName(file.getOriginalFilename());//文件名
			dailyreportRec.setReportUrl((String) saveResult.getResult());
			dailyreportRec.setUploadPeople(uploadPeople);
			int result = dailyReportRecService.addDailyReportRec(dailyreportRec);
			if (result>0) { // 入库成功
			} else { // 上传失败
				logger.error("文件上传成功，入库失败");
				return "文件上传成功，入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
		}
		MetroDailyreportRec reportRec = dailyReportRecService.findDailyReportRec(intervalId, leftOrRight, reportTime);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intervalId", reportRec.getIntervalId());
	    map.put("leftOrRight", reportRec.getLeftOrRight());
	    map.put("reportTime", reportRec.getReportTime());
	    map.put("reportUrl", reportRec.getReportUrl());
	    map.put("uploadPeople", reportRec.getUploadPeople());
		return map;
	}
	
	/**
	 * 分页查找日报表记录信息
	 * @param intervalId
	 * @param leftOrRight 区间左右线
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/dailyreportinfo/find")
	@ResponseBody
	public PageResultSet<MetroDailyreportRec> findDailyReportInfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight, 
			@RequestParam(value = "reportTime", required = false) String reportTime, 
			@RequestParam("pageNum") int pageNum, 
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroDailyreportRec> resultSet = dailyReportRecService.findDailyReportInfo(intervalId,leftOrRight, reportTime, pageNum,
				pageSize);
		return resultSet;
	}
	
	/**
	 * 删除日报表记录信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除日报表记录信息")
	@RequestMapping(value = "/dailyreportinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deletedailyreportinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = dailyReportRecService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除日报表记录信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除日报表记录信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	
	/**
	 * 查询周报表上传记录
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @param reportTime 报表时间
	 * @return
	 */
	@RequestMapping("/findWeeklyReporRec")
	@ResponseBody
	public MetroWeeklyReportRec getWeeklyReportRec(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, 
			@RequestParam("reportTime") String reportTime ) {
		MetroWeeklyReportRec reportRec =  weeklyReportRecService.findWeeklyReportRec(intervalId, leftOrRight, reportTime);
		return reportRec;
	}
	
	/**
	 * 周报表文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "周报表文件上传")
	@RequestMapping(value = "/weeklyreport-pdf/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveWeeklyReportPdf(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, 
			@RequestParam("uploadPeople") String uploadPeople,
			@RequestParam("reportTime") String reportTime,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			CommonResponse saveResult = commonService.fileUpload(file);
			if (saveResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			MetroWeeklyReportRec weeklyreportRec= new MetroWeeklyReportRec();
			weeklyreportRec.setIntervalId(intervalId);
			weeklyreportRec.setLeftOrRight(leftOrRight);
			weeklyreportRec.setReportName(file.getOriginalFilename());
			weeklyreportRec.setReportTime(reportTime);
			weeklyreportRec.setReportUrl((String) saveResult.getResult());
			weeklyreportRec.setUploadPeople(uploadPeople);
			int result = weeklyReportRecService.addWeeklyReportRec(weeklyreportRec);
			if (result>0) { // 入库成功
			} else { // 上传失败
				logger.error("文件上传成功，入库失败");
				return "文件上传成功，入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
		}
		MetroWeeklyReportRec weeklyreportRec = weeklyReportRecService.findWeeklyReportRec(intervalId, leftOrRight, reportTime);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intervalId", weeklyreportRec.getIntervalId());
	    map.put("leftOrRight", weeklyreportRec.getLeftOrRight());
	    map.put("reportTime", weeklyreportRec.getReportTime());
	    map.put("reportUrl", weeklyreportRec.getReportUrl());
	    map.put("uploadPeople", weeklyreportRec.getUploadPeople());
		return map;
	}
	
	/**
	 * 分页查找周报表记录信息
	 * @param intervalId
	 * @param leftOrRight 区间左右线
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/weeklyreportinfo/find")
	@ResponseBody
	public PageResultSet<MetroWeeklyReportRec> findWeeklyReportInfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight, 
			@RequestParam("pageNum") int pageNum, 
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroWeeklyReportRec> resultSet = weeklyReportRecService.findWeeklyReportInfo(intervalId,leftOrRight, pageNum,
				pageSize);
		return resultSet;
	}
	
	/**
	 * 删除周报表记录信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "删除周报表记录信息")
	@RequestMapping(value = "/weeklyreportinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteweeklyreportinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = weeklyReportRecService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除周报表记录信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除周报表记录信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	/**
	 * 导出盾构数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", operate = "导出盾构数据")
	@RequestMapping(value = "/sdinfo/export2", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportShieldinfo2(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("startRing") String startRing,
			@RequestParam("endRing") String endRing, @RequestParam("type") String type, @RequestParam("dics") String dics,
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date date) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			// 处理参数
			dics = (dics ==null)? "":dics;
			List<String> dicNameList = new ArrayList<String>();
			String[] dicNameArr = dics.split(",");
			for (String dicName:dicNameArr) {
				dicNameList.add(dicName);
			}
			//参数没有环号和工作状态，加上这两列
			if(!dicNameList.contains("A0001")){
				dicNameList.add("A0001");
			}
			if(!dicNameList.contains("A0002")){
				dicNameList.add("A0002");
			}
			int iStartRing = 0;
			if(CommonUtils.isNotNull(startRing)){
				iStartRing = Integer.parseInt(startRing);
			}
			int iEndRing = 0;
			if(CommonUtils.isNotNull(endRing)){
				iEndRing = Integer.parseInt(endRing);
			}
			// 查询获取数据列表
			List<Map<String, Object>> dataList = lineIntervalService.getShieldData1(intervalId, leftOrRight, date, 
					iStartRing, iEndRing, dicNameList ,type);
			// 按照时间排序
			Collections.sort(dataList, new Comparator<Map<String, Object>>() {
	            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	                return o1.get("time").toString().compareTo(o2.get("time").toString());
	            }
	        });
			// 获取表头信息
			List<Map<String, String>> columnList = getHeaderList(dicNameList);
			// 获取区间和盾构机信息
			MetroLineInterval metroLineInterval = lineIntervalService.findObjById(intervalId);
			String intervalName = metroLineInterval.getIntervalName();
			String machineNo = "";
			for (MetroLineIntervalLr lr : metroLineInterval.getIntervalLrList()) {
				if (leftOrRight.equals(lr.getLeftOrRight())) {
					machineNo = lr.getMachineNo();
				}
			}
			// 按规则生成文件名
			Date now = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(now) + (new Random().nextInt(900) + 100);
			String fileName = generationStr + "_" + intervalName + "_" + machineNo + "_盾构数据.xls";
			//将表头和数据写入表格中
			String excelFileName = writeShieldToExcel(dataList, columnList, fileName, "盾构数据");
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出盾构数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}
	
	/**
	 * 将盾构数据信息写入到excel
	 * 
	 * @param dataList  数据
	 * @param columnList 表头
	 * @param fileName 文件名
	 * @param sheetName 页 名称
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeShieldToExcel(List<Map<String, Object>> dataList, List<Map<String, String>> columnList
		, String fileName, String sheetName) {
		WritableWorkbook book = null;
		try {
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + fileName));
			WritableSheet sheet = book.createSheet(sheetName, 0);
			int row = 0;
			for (int i = 0; i <columnList.size(); i++) {
				 Map<String, String> columnMap = columnList.get(i);
				sheet.addCell(new Label(i, row, columnMap.get("title")));
			}
			if (dataList != null && dataList.size() > 0) {
				for (Map<String, Object> data : dataList) {
					for (int column = 0; column <columnList.size(); column++) {
						Map<String, String> columnMap = columnList.get(column);
						sheet.addCell(new Label(column, row + 1, String.valueOf(data.get(columnMap.get("key")))));
					}
					row++;
				}
			}
			book.write();
			return fileName;
		} catch (Exception e) {
			logger.error("盾构数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("盾构数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 返回表头信息
	 * @param dicNameList 参数列表
	 * @return
	 */
	private List<Map<String, String>> getHeaderList(List<String> dicNameList){
		List<Map<String, String>> headerList = new ArrayList<Map<String, String>>();
		// 时间
		Map<String, String> timeMap = new HashMap<String, String>();
		timeMap.put("key", "time");
		timeMap.put("title", "时间");
		headerList.add(timeMap);
		//各参数
		List<MetroDictionary> dicList = dictionaryService.findDictionaryList(dicNameList);
		if(dicList == null || dicList.size() < 1){
			return headerList;
		}
		for (MetroDictionary dic: dicList) {
			Map<String, String> dicNameMap = new HashMap<String, String>();
			dicNameMap.put("key", dic.getDicName());
			dicNameMap.put("title", dic.getDicMean());
			headerList.add(dicNameMap);
		}
		return headerList;
	}
	/**
	 * 当前环
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	@RequestMapping("/currRingNum")
	@ResponseBody
	public Map<String, Object> findCurrRingNum(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		
		Map<String, Object> Map = new HashMap<String, Object>();
		int currentRingNum= infoCityService.findCurrRingNum(intervalId, leftOrRight);
		Map.put("currentRingNum", currentRingNum);
		return Map;
	}
	
	/**
	 * 查询地质风险划分阻段划分数据
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @return
	 */
	@RequestMapping("/findRiskSegmentation")
	@ResponseBody
	public List<MetroIntervalRiskSegmentation> getRiskSegmentation(
			@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight) {
		List<MetroIntervalRiskSegmentation> riskList = riskSegmentationService.findriskSegmentation(Long.parseLong(intervalId),
				leftOrRight);
		return  riskList;
	}
	
	/**
	 * 保存更新地质风险划分阻段划分数据
	 */
	@SysControllorLog(menu = "风险数据管理", operate = "地质风险划分阻段划分数据")
	@RequestMapping(value = "/saverisk", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveRiskSegmentation(
			@RequestParam(value = "saveOrUpdate", required = false) String saveOrUpdate,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "intervalId", required = false) Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "geologicNo", required = false) String geologicNo,
			@RequestParam(value = "geologicDescription", required = false) String geologicDescription,
			@RequestParam(value = "hydrogeololgy", required = false) String hydrogeololgy,
			@RequestParam(value = "riskNo", required = false) String riskNo,
			@RequestParam(value = "riskPoint", required = false) String riskPoint,
			@RequestParam(value = "riskPhoto", required = false) String riskPhoto,
			@RequestParam(value = "riskStartRing", required = false) Integer riskStartRing,
			@RequestParam(value = "riskEndRing", required = false) Integer riskEndRing,
			@RequestParam(value = "startMileage", required = false) Float startMileage,
			@RequestParam(value = "endMileage", required = false) Float endMileage,
			@RequestParam(value = "earlyWarningRing", required = false) Integer earlyWarningRing,
			@RequestParam(value = "warningLevel", required = false) String warningLevel,
			@RequestParam(value = "riskDescription", required = false) String riskDescription,
			@RequestParam(value = "riskImg1Url", required = false) String riskImg1Url,
			@RequestParam(value = "riskImg2Url", required = false) String riskImg2Url,
	        @RequestParam(value = "riskImg3Url", required = false) String riskImg3Url ){
		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroIntervalRiskSegmentation riskSegmentation = new MetroIntervalRiskSegmentation();
			if(CommonUtils.isNotNull(intervalId)){
				riskSegmentation.setIntervalId(intervalId);
			}
			if(CommonUtils.isNotNull(leftOrRight)){
				riskSegmentation.setLeftOrRight(leftOrRight);
			}
			if(CommonUtils.isNotNull(geologicNo)){
				riskSegmentation.setGeologicNo(geologicNo);
			}
			if(CommonUtils.isNotNull(geologicDescription)){
				riskSegmentation.setGeologicDescription(geologicDescription);
			}
			if(CommonUtils.isNotNull(hydrogeololgy)){
				riskSegmentation.setHydrogeololgy(hydrogeololgy);
			}
			if(CommonUtils.isNotNull(riskNo)){
				riskSegmentation.setRiskNo(riskNo);
			}
			if(CommonUtils.isNotNull(riskPoint)){
				riskSegmentation.setRiskPoint(riskPoint);
			}
			if(CommonUtils.isNotNull(riskPhoto)){
				riskSegmentation.setRiskPhoto(riskPhoto);
			}
			if(CommonUtils.isNotNull(riskStartRing)){
				riskSegmentation.setRiskStartRing(riskStartRing);
			}
			if(CommonUtils.isNotNull(riskEndRing)){
				riskSegmentation.setRiskEndRing(riskEndRing);
			}
			if(CommonUtils.isNotNull(startMileage)){
				riskSegmentation.setStartMileage(startMileage);
			}
			if(CommonUtils.isNotNull(endMileage)){
				riskSegmentation.setEndMileage(endMileage);
			}
			if(CommonUtils.isNotNull(earlyWarningRing)){
				riskSegmentation.setEarlyWarningRing(earlyWarningRing);
			}
			if(CommonUtils.isNotNull(warningLevel)){
				riskSegmentation.setWarningLevel(warningLevel);
			}
			if(CommonUtils.isNotNull(riskDescription)){
				riskSegmentation.setRiskDescription(riskDescription);
			}
			if(CommonUtils.isNotNull(riskImg1Url)){
				riskSegmentation.setRiskImg1Url(riskImg1Url);
			}
			if(CommonUtils.isNotNull(riskImg2Url)){
				riskSegmentation.setRiskImg2Url(riskImg2Url);
			}
			if(CommonUtils.isNotNull(riskImg3Url)){
				riskSegmentation.setRiskImg3Url(riskImg3Url);
			}
			if ("1".equals(saveOrUpdate)) { // 更新
				riskSegmentation.setId(Long.parseLong(id));
				boolean result1 = riskSegmentationService.updateObj(riskSegmentation);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新地质风险划分阻段划分数据出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else if ("2".equals(saveOrUpdate)) { // 保存
				Long result2 = riskSegmentationService.insertObj(riskSegmentation);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存地质风险划分阻段划分数据出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新地质风险划分阻段划分数据异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;
	}
	
	/**
	 * 删除风险阻段划分数据
	 */
	@SysControllorLog(menu = "风险数据管理", operate = "删除地质风险划分阻段划分数据")
	@RequestMapping(value = "/riskSegmentation/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteriskSegmentation(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = riskSegmentationService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除地质风险划分阻段划分数据出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除地质风险划分阻段划分数据异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	/**
	 * 分页查找地质风险划分阻段划分数据
	 * 
	 * @return
	 */
	@RequestMapping("/riskSegmentation/find")
	@ResponseBody
	public PageResultSet<MetroIntervalRiskSegmentation> findRiskSegmentation(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight, 
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroIntervalRiskSegmentation> resultSet = riskSegmentationService
				.findriskSegmentationInfo(intervalId, leftOrRight, pageNum, pageSize);
		return resultSet;
	}
	
	/**
	 * 导出地质风险划分阻段划分数据
	 */
	@SysControllorLog(menu = "风险数据管理", operate = "导出地质风险划分阻段划分数据")
	@RequestMapping(value = "riskinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportRiskInfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam(value = "desc", required = false) String desc)  {

		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroIntervalRiskSegmentation> datas = riskSegmentationService.findriskSegmentation(intervalId,
					leftOrRight);
			String excelFileName = writeRecExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出地质风险划分阻段划分数据异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 将地质风险划分阻段划分数据写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeRecExcel(List<MetroIntervalRiskSegmentation> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "地质风险划分阻段划分数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("地质风险划分阻段划分数据", 0);
			String[] title = {"地质组段编号","隧道断面内地质组合描述","地下水温埋深", "风险编号", "风险点", "风险照片", "风险开始环"," 风险结束环" ,"风险开始里程", "风险结束里程", "提前预警环数", "预警等级", "风险描述情况" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroIntervalRiskSegmentation data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, String.valueOf(data.getGeologicNo())));
					sheet.addCell(new Label(1, i + 1, String.valueOf(data.getGeologicDescription())));
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getHydrogeololgy())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getRiskNo())));
					sheet.addCell(new Label(4, i + 1, String.valueOf(data.getRiskPoint())));
					sheet.addCell(new Label(5, i + 1, String.valueOf(data.getRiskPhoto())));
					sheet.addCell(new Label(6, i + 1, String.valueOf(data.getRiskStartRing())));
					sheet.addCell(new Label(7, i + 1, String.valueOf(data.getRiskEndRing())));
					sheet.addCell(new Label(8, i + 1, String.valueOf(data.getStartMileage().floatValue())));
					sheet.addCell(new Label(9, i + 1, String.valueOf(data.getEndMileage().floatValue())));
					sheet.addCell(new Label(10, i + 1, String.valueOf(data.getEarlyWarningRing())));
					sheet.addCell(new Label(11, i + 1, String.valueOf(data.getWarningLevel())));
					sheet.addCell(new Label(12, i + 1, String.valueOf(data.getRiskDescription())));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("地质风险划分阻段划分数据写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("地质风险划分阻段划分数据写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 导入风险阻段划分信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "风险数据管理", operate = "导入地质风险划分阻段划分数据")
	@RequestMapping(value = "/riskSegmentation/import", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importRiskInfo(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = riskSegmentationService.importExcelData(intervalId, leftOrRight, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导入失败");
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导入异常");
		}
		return commonResponse;
	}
	
	/**
	 * 上传地质风险划分阻段划分图片文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/riskSegmentationImg/file-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadriskSegmentationImg(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				return result;
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传风险图片文件失败");
			}
		} catch (Exception e) {
			logger.error("风险图片文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("风险图片文件上传异常");
		}
		return commonResponse;
	}
	
	/**
	 * 更新地质风险划分阻段划分图片
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "风险数据管理", operate = "更新地质风险划分阻段划分图片")
	@RequestMapping(value = "/riskSegmentationImg-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadRiskSegmentationImg(MetroIntervalRiskSegmentation rs) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = riskSegmentationService.updateRiskImg(rs.getId(), rs.getRiskImg1Url(), rs.getRiskImg2Url(), rs.getRiskImg3Url());
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新地质风险划分阻段划分图片出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新地质风险划分阻段划分图片异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}
	
	private void getRingReportData(Long intervalId, String leftOrRight){
		//1、查找该区间左右线数据库中最大的环号
		int iLastRing = 23;//ringReportService.getMaxRing(intervalId, leftOrRight);
		//2、获取区间、左右线的当前环号
		int currentRing = 50;//ringReportService.getMaxRing(intervalId, leftOrRight);
		//3、循环每个环号调接口获取不同参数的数据
		for(int i = iLastRing; i <= currentRing; i++){
			// 3.1 调接口获取第i环的数据
			List<Map<String, Object>> ringReportData = new ArrayList<Map<String, Object>>();//ringReportService.getReportData(intervalId, leftOrRight; i);
			// 3.2 剔除掉非推进状态的数据
			// 3.3 对数据进行排序（时间顺序）
			// 3.4 循环剔除掉后面推进行程比前面一条小的数据
			// 3.5 拿3.4后的记录数分成30份（不够三十份不处理）,获得31个整数即是目标数据的下标
			// 3.6 根据3.5得到的下标列表取ringReportData对应下标的数据并拆分成每个参数一条数据进行保存,保存之前需要先删掉数据库中这一环（同一区间左右线）的所有数据
			
		}
		
	}
}
