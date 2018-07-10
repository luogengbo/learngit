package com.szgentech.metro.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.model.Homepage;
import com.szgentech.metro.model.HomepageResponse;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroDailyReportParam;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervalPp;
import com.szgentech.metro.model.MetroLineIntervalRp;
import com.szgentech.metro.model.MetroLineIntervalSa;
import com.szgentech.metro.model.MetroLineIntervalSp;
import com.szgentech.metro.model.MetroLineIntervalSt;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.MetroLineIntervaliLR;
import com.szgentech.metro.model.MetroLoopMark;
import com.szgentech.metro.model.MetroPhoto;
import com.szgentech.metro.model.MetroWeeklyReportParam;
import com.szgentech.metro.model.MonitorIntervalDataView;
import com.szgentech.metro.model.Pandect;
import com.szgentech.metro.service.ICommonService;
import com.szgentech.metro.service.IMetroDictionaryService;
import com.szgentech.metro.service.IMetroLineIntervalLrService;
import com.szgentech.metro.service.IMetroLineIntervalPpService;
import com.szgentech.metro.service.IMetroLineIntervalRpService;
import com.szgentech.metro.service.IMetroLineIntervalSaService;
import com.szgentech.metro.service.IMetroLineIntervalScheduleService;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.service.IMetroLineIntervalSpService;
import com.szgentech.metro.service.IMetroLineIntervalStService;
import com.szgentech.metro.service.IMetroLineService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroPhotoService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.IhistorianResponse;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;
import com.szgentech.metro.vo.MonitorIntervalLrStaticsView;
import com.szgentech.metro.vo.MonitorIntervalView;
import com.szgentech.metro.vo.MonitorLrAlldicView;
import com.szgentech.metro.vo.MonitorViewData;
import com.szgentech.metro.vo.SessionUser;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 盾构信息监控控制器
 * 
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/monitor/info")
public class MetroMonitorCityController extends BaseController {

	private static Logger logger = Logger.getLogger(MetroMonitorCityController.class);

	@Autowired
	private ICommonService commonService;

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@Autowired
	private IMetroLineService lineService;

	@Autowired
	private IMetroLineIntervalService intervalService;

	@Autowired
	private IMetroLineIntervalLrService intervalLrService;

	@Autowired
	private IMetroLineIntervalSpService spService;

	@Autowired
	private IMetroLineIntervalPpService lineIntervalPpService;

	@Autowired
	private IMetroLineIntervalRpService lineIntervalRpService;

	@Autowired
	private IMetroLineIntervalSaService lineIntervalSaService;

	@Autowired
	private IMetroLineIntervalStService lineIntervalStService;

	@Autowired
	private IMetroPhotoService photoService;

	@Autowired
	private IMetroWarningRecService warningRecService;
	
	@Autowired
	private IMetroLineIntervalScheduleService lineIntervalScheduleService;
	
	@Autowired
	private IMetroDictionaryService dictionaryService;

	/**
	 * 盾构信息监控主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/find-info/info_monitor";
	}

	/**
	 * 城市信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/city/index")
	public String cityMain(@RequestParam("cityId") String cityId) {
		modelMap.addAttribute("cityId", cityId);
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), null);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			modelMap.addAttribute("total0", total0);
			modelMap.addAttribute("total1", total1);
			modelMap.addAttribute("total2", total2);
			modelMap.addAttribute("totalSuccess", totalSuccess);
			modelMap.addAttribute("totalFail", totalFail);
			modelMap.addAttribute("totalm", total1 + total2);
		}
		return "/find-info/info_monitor_city";
	}

	/**
	 * 城市数据监测
	 * 
	 * @return
	 */
	@RequestMapping("/find/city/datas")
	@ResponseBody
	public PageResultSet<MonitorViewData> findMonitorCityAll(
			@RequestParam("cityId") Long cityId,
			@RequestParam("buildStatus") Integer buildStatus,
			@RequestParam(value="intervalName",required=false) String intervalName,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		SessionUser user = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(cityId, null, buildStatus, intervalName,
				pageNum, pageSize,user.getId());
		if (mvds == null) {
			mvds = new PageResultSet<MonitorViewData>();
			mvds.setPage(new Page(0, pageSize, pageNum));
			mvds.setList(new ArrayList<MonitorViewData>());
		}
		return mvds;
	}

	/**
	 * 线路信息主页（工程介绍）
	 * 
	 * @return
	 */
	@RequestMapping("/to/line/index")
	public String lineMain(@RequestParam("lineId") Long lineId) {
		modelMap.addAttribute("lineId", lineId);
		MetroLine line = lineService.findObjById(lineId);
		modelMap.addAttribute("lineUrl", line.getProjectPdfUrl());
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), lineId);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			modelMap.addAttribute("total0", total0);
			modelMap.addAttribute("total1", total1);
			modelMap.addAttribute("total2", total2);
			modelMap.addAttribute("totalSuccess", totalSuccess);
			modelMap.addAttribute("totalFail", totalFail);
			modelMap.addAttribute("totalm", total1 + total2);
		}
		return "/find-info/info_monitor_line";
	}

	/**
	 * 线路数据监测
	 * 
	 * @return
	 */
	@RequestMapping("/find/line/datas")
	@ResponseBody
	public PageResultSet<MonitorViewData> findMonitorLineAll(@RequestParam("lineId") Long lineId,
			@RequestParam("buildStatus") int buildStatus, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		SessionUser user = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(null, lineId, buildStatus,null,
				pageNum, pageSize,user.getId());
		if (mvds == null) {
			mvds = new PageResultSet<MonitorViewData>();
			mvds.setPage(new Page(0, pageSize, pageNum));
			mvds.setList(new ArrayList<MonitorViewData>());
		}
		return mvds;
	}

	/**
	 * 区间信息主页（工程介绍）
	 * 
	 * @return
	 */
	@RequestMapping("/to/area/index")
	public String areaMain(@RequestParam("intervalId") Long intervalId) {
		modelMap.addAttribute("intervalId", intervalId);
		MetroLineInterval interval = intervalService.findObjById(intervalId);
		modelMap.addAttribute("intervalUrl", interval.getProjectPdfUrl());
		modelMap.addAttribute("riskPdfUrl", interval.getRiskPdfUrl());
		modelMap.addAttribute("ParameterUrl", interval.getParameterPdfUrl());
		List<MetroLineIntervalSp> sps = spService.findLineIntervalSps(intervalId);
		modelMap.addAttribute("sps", sps);
		MetroLine line = lineService.findObjById(interval.getLineId());
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			modelMap.addAttribute("lRingNum", lRingNum);
			modelMap.addAttribute("rRingNum", rRingNum);
		}
		return "/find-info/info_monitor_area";
	}

	/**
	 * 区间信息主页（平面图）
	 * 
	 * @param intervalId
	 * @param type
	 * @param goNumAll
	 * @param pingLorR
	 * @return
	 */
	@RequestMapping("/to/area/project")
	public ModelAndView projectProcess(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("type") int type,
			@RequestParam(value = "goNumA", required = false, defaultValue = "0") int goNumAll,
			@RequestParam(value = "pingLorR", required = false, defaultValue = "") String pingLorR) {

		ModelAndView mv = new ModelAndView();

		if (goNumAll < 0) {
			goNumAll = 0;
		}

		MetroLineInterval interval = intervalService.findObjById(intervalId);
		mv.addObject("surveyPdfUrl", interval.getSurveyPdfUrl());
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(intervalId);

		List<MetroLineIntervalLr> lrList = interval.getIntervalLrList();

		MetroLine line = lineService.findObjById(interval.getLineId());
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			mv.addObject("lRingNum", lRingNum);
			mv.addObject("lCountRing", result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("count")));
			mv.addObject("rRingNum", rRingNum);
			mv.addObject("rCountRing", result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("count")));
		}

		if (CommonUtils.isNotNull(lrList)) {
			mv.addObject("lr_l", lrList.get(0).getRingNum());
			if (lrList.size() > 1) {
				mv.addObject("lr_r", lrList.get(1).getRingNum());
			}
		}

		String lLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l");
		String rLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r");
		mv.addObject("lLinekey", lLinekey);
		mv.addObject("rLinekey", rLinekey);
		mv.addObject("intervalId", intervalId);

		String url = "";
		String svgUrl = "";
		if (type == 1) {
			if (pingLorR.equals("l")) {
				mv.addObject("pingLorR", pingLorR);
			} else if (pingLorR.equals("r")) {
				mv.addObject("pingLorR", pingLorR);
			}
			if (goNumAll != 0) {
				mv.addObject("goNumAll", goNumAll);
			}
			if (null != pp)
				svgUrl = pp.getPpSvgUrl();
			url = "/find-info/project1";
		} else if (type == 2) {
			if (goNumAll != 0) {
				mv.addObject("goNumAll", goNumAll);
			}
			if (null != pp)
				svgUrl = pp.getSectionSvgUrl();
			url = "/find-info/projectL";
		} else if (type == 3) {
			if (goNumAll != 0) {
				mv.addObject("goNumAll", goNumAll);
			}
			if (null != pp)
				svgUrl = pp.getSection_svg_url_r();
			url = "/find-info/projectR";
		}
		mv.setViewName(url);
		mv.addObject("svgUrl", svgUrl);

		return mv;
	}

	/**
	 * 区间信息主页（平面图）周报表截图参数POST
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/to/screenshots", method=RequestMethod.POST)
	public ModelAndView screenshotsWeekly(@RequestBody MetroWeeklyReportParam param) {
		ModelAndView mv = new ModelAndView();
		if (param.getGoNumA() < 0) {
			param.setGoNumA(0);
		}
		MetroLineInterval interval = intervalService.findObjById(param.getIntervalId());
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(param.getIntervalId());
		List<MetroLineIntervalLr> lrList = interval.getIntervalLrList();
		MetroLine line = lineService.findObjById(interval.getLineId());
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			mv.addObject("lRingNum", lRingNum);
			mv.addObject("lCountRing", result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("count")));
			mv.addObject("rRingNum", rRingNum);
			mv.addObject("rCountRing", result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("count")));
		}

		if (CommonUtils.isNotNull(lrList)) {
			mv.addObject("lr_l", lrList.get(0).getRingNum());
			if (lrList.size() > 1) {
				mv.addObject("lr_r", lrList.get(1).getRingNum());
			}
		}

		String lLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l");
		String rLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r");
		mv.addObject("lLinekey", lLinekey);
		mv.addObject("rLinekey", rLinekey);
		mv.addObject("intervalId", param.getIntervalId());
		mv.addObject("leftOrRight", param.getLeftOrRight());
		mv.addObject("saveOrUpdate", param.getSaveOrUpdate());
		mv.addObject("id", param.getId());
		mv.addObject("summary", param.getSummary());
		mv.addObject("shieldTunneling", param.getShieldTunneling());
		mv.addObject("shieldTunnelingImg", param.getShieldTunnelingImg());
		mv.addObject("riskSituation", param.getRiskSituation());
		mv.addObject("riskSituationImg", param.getRiskSituationImg());
		mv.addObject("geology", param.getGeology());
		mv.addObject("geologyImg", param.getGeologyImg());
		mv.addObject("effect", param.getEffect());
		mv.addObject("effectImg", param.getEffectImg());
		mv.addObject("settlement", param.getSettlement());
		mv.addObject("settlementImg", param.getSettlementImg());
		mv.addObject("paramAnalysis", param.getParamAnalysis());
		mv.addObject("paramAnalysisImg", param.getParamAnalysisImg());
		mv.addObject("slagSamplesImg", param.getSlagSamplesImg());
		mv.addObject("horizontalAttitude", param.getHorizontalAttitude());
		mv.addObject("horizontalAttitudeImg", param.getHorizontalAttitudeImg());
		mv.addObject("verticalAttitude", param.getVerticalAttitude());
		mv.addObject("verticalAttitudeImg", param.getVerticalAttitudeImg());
		mv.addObject("grouting", param.getGrouting());
		mv.addObject("groutingImg", param.getGroutingImg());
		mv.addObject("oiltemperature", param.getOiltemperature());
		mv.addObject("oiltemperatureImg", param.getOiltemperatureImg());
		mv.addObject("earlyWarning", param.getEarlyWarning());
		mv.addObject("earlyWarningImg", param.getEarlyWarningImg());
		mv.addObject("auditOpinion", param.getAuditOpinion());
		mv.addObject("reportTime", param.getReportTime());
		mv.addObject("operator", param.getOperator());
		mv.addObject("reviewer", param.getReviewer());
		mv.addObject("startRingNum", param.getStartRingNum());
		mv.addObject("endRingNum", param.getEndRingNum());
		mv.addObject("startPressure", param.getStartPressure());
		mv.addObject("endPressure", param.getEndPressure());
		mv.addObject("startCutterTorque", param.getStartCutterTorque());
		mv.addObject("endCutterTorque", param.getEndCutterTorque());
		mv.addObject("startTotalThrust", param.getStartTotalThrust());
		mv.addObject("endTotalThrust", param.getEndTotalThrust());
		mv.addObject("startCutterSpeed", param.getStartCutterSpeed());
		mv.addObject("endCutterSpeed", param.getEndCutterSpeed());
		mv.addObject("startSpeed", param.getStartSpeed());
		mv.addObject("endSpeed", param.getEndSpeed());
		mv.addObject("startGroutingAmount", param.getStartGroutingAmount());
		mv.addObject("endGroutingAmount", param.getEndGroutingAmount());

		String url = "";
		String svgUrl = "";
		if (param.getType() == 1) {
			if (param.getPingLorR().equals("l")) {
				mv.addObject("pingLorR", param.getPingLorR());
			} else if (param.getPingLorR().equals("r")) {
				mv.addObject("pingLorR", param.getPingLorR());
			}
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getPpSvgUrl();
			url = "/project-info/capture_snap";
		} else if (param.getType() == 2) {
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getSectionSvgUrl();
			url = "/project-info/capture_snapL";
		} else if (param.getType() == 3) {
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getSection_svg_url_r();
			url = "/project-info/capture_snapR";
		}
		mv.setViewName(url);
		mv.addObject("svgUrl", svgUrl);

		return mv;
	}
	
	/**
	 * 区间信息主页（平面图）日报表截图参数POST
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/to/daily/screenshots", method=RequestMethod.POST)
	public ModelAndView screenshotsDaily(@RequestBody MetroDailyReportParam param) {
		ModelAndView mv = new ModelAndView();
		if (param.getGoNumA() < 0) {
			param.setGoNumA(0);
		}
		MetroLineInterval interval = intervalService.findObjById(param.getIntervalId());
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(param.getIntervalId());
		List<MetroLineIntervalLr> lrList = interval.getIntervalLrList();
		MetroLine line = lineService.findObjById(interval.getLineId());
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			mv.addObject("lRingNum", lRingNum);
			mv.addObject("lCountRing", result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("count")));
			mv.addObject("rRingNum", rRingNum);
			mv.addObject("rCountRing", result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("count")));
		}

		if (CommonUtils.isNotNull(lrList)) {
			mv.addObject("lr_l", lrList.get(0).getRingNum());
			if (lrList.size() > 1) {
				mv.addObject("lr_r", lrList.get(1).getRingNum());
			}
		}

		String lLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l");
		String rLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r");
		mv.addObject("lLinekey", lLinekey);
		mv.addObject("rLinekey", rLinekey);
		mv.addObject("intervalId", param.getIntervalId());
		mv.addObject("leftOrRight", param.getLeftOrRight());
		mv.addObject("saveOrUpdate", param.getSaveOrUpdate());
		mv.addObject("id", param.getId());
		mv.addObject("summary", param.getSummary());
		mv.addObject("progressStatistics", param.getProgressStatistics());
		mv.addObject("workingCondition", param.getWorkingCondition());
		mv.addObject("settlement", param.getSettlement());
		mv.addObject("settlementImg", param.getSettlementImg());
		mv.addObject("riskSituation", param.getRiskSituation());
		mv.addObject("riskSituationImg", param.getRiskSituationImg());
		mv.addObject("geology", param.getGeology());
		mv.addObject("geologyImg", param.getGeologyImg());
		mv.addObject("grouting", param.getGrouting());
		mv.addObject("groutingImg", param.getGroutingImg());
		mv.addObject("horizontalAttitude", param.getHorizontalAttitude());
		mv.addObject("horizontalAttitudeImg", param.getHorizontalAttitudeImg());
		mv.addObject("verticalAttitude", param.getVerticalAttitude());
		mv.addObject("verticalAttitudeImg", param.getVerticalAttitudeImg());
		mv.addObject("earlyWarning", param.getEarlyWarning());
		mv.addObject("earlyWarningImg", param.getEarlyWarningImg());
		mv.addObject("auditOpinion", param.getAuditOpinion());
		mv.addObject("reportTime", param.getReportTime());
		mv.addObject("operator", param.getOperator());
		mv.addObject("reviewer", param.getReviewer());
		mv.addObject("startA0004", param.getStartA0004());
		mv.addObject("endA0004", param.getEndA0004());
		mv.addObject("minA0004", param.getMinA0004());
		mv.addObject("maxA0004", param.getMaxA0004());
		mv.addObject("analysisA0004", param.getAnalysisA0004());
		mv.addObject("startA0013", param.getStartA0013());
		mv.addObject("endA0013", param.getEndA0013());
		mv.addObject("minA0013", param.getMinA0013());
		mv.addObject("maxA0013", param.getMaxA0013());
		mv.addObject("analysisA0013", param.getAnalysisA0013());
		mv.addObject("startB0001", param.getStartB0001());
		mv.addObject("endB0001", param.getEndB0001());
		mv.addObject("minB0001", param.getMinB0001());
		mv.addObject("maxB0001", param.getMaxB0001());
		mv.addObject("analysisB0001", param.getAnalysisB0001());
		mv.addObject("startB0002", param.getStartB0002());
		mv.addObject("endB0002", param.getEndB0002());
		mv.addObject("minB0002", param.getMinB0002());
		mv.addObject("maxB0002", param.getMaxB0002());
		mv.addObject("analysisB0002", param.getAnalysisB0002());
		mv.addObject("startB0004", param.getStartB0004());
		mv.addObject("endB0004", param.getEndB0004());
		mv.addObject("minB0004", param.getMinB0004());
		mv.addObject("maxB0004", param.getMaxB0004());
		mv.addObject("analysisB0004", param.getAnalysisB0004());
		mv.addObject("startB0006", param.getStartB0006());
		mv.addObject("endB0006", param.getEndB0006());
		mv.addObject("minB0006", param.getMinB0006());
		mv.addObject("maxB0006", param.getMaxB0006());
		mv.addObject("analysisB0006", param.getAnalysisB0006());
		mv.addObject("startB0015", param.getStartB0015());
		mv.addObject("endB0015", param.getEndB0015());
		mv.addObject("minB0015", param.getMinB0015());
		mv.addObject("maxB0015", param.getMaxB0015());
		mv.addObject("analysisB0015", param.getAnalysisB0015());
		mv.addObject("startR0026", param.getStartR0026());
		mv.addObject("endR0026", param.getEndR0026());
		mv.addObject("minR0026", param.getMinR0026());
		mv.addObject("maxR0026", param.getMaxR0026());
		mv.addObject("analysisR0026", param.getAnalysisR0026());
		mv.addObject("startR0028", param.getStartR0028());
		mv.addObject("endR0028", param.getEndR0028());
		mv.addObject("minR0028", param.getMinR0028());
		mv.addObject("maxR0028", param.getMaxR0028());
		mv.addObject("analysisR0028", param.getAnalysisR0028());
		mv.addObject("startR0025", param.getStartR0025());
		mv.addObject("endR0025", param.getEndR0025());
		mv.addObject("minR0025", param.getMinR0025());
		mv.addObject("maxR0025", param.getMaxR0025());
		mv.addObject("analysisR0025", param.getAnalysisR0025());
		mv.addObject("startR0004", param.getStartR0004());
		mv.addObject("endR0004", param.getEndR0004());
		mv.addObject("minR0004", param.getMinR0004());
		mv.addObject("maxR0004", param.getMaxR0004());
		mv.addObject("analysisR0004", param.getAnalysisR0004());	
		String url = "";
		String svgUrl = "";
		if (param.getType() == 1) {
			if (param.getPingLorR().equals("l")) {
				mv.addObject("pingLorR", param.getPingLorR());
			} else if (param.getPingLorR().equals("r")) {
				mv.addObject("pingLorR", param.getPingLorR());
			}
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getPpSvgUrl();
			url = "/project-info/capture_snap2";
		} else if (param.getType() == 2) {
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getSectionSvgUrl();
			url = "/project-info/capture_snapL2";
		} else if (param.getType() == 3) {
			if (param.getGoNumA() != 0) {
				mv.addObject("goNumAll", param.getGoNumA());
			}
			if (null != pp)
				svgUrl = pp.getSection_svg_url_r();
			url = "/project-info/capture_snapR2";
		}
		mv.setViewName(url);
		mv.addObject("svgUrl", svgUrl);

		return mv;
	}
	
	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/datas", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findMonitorLineData(String line) {
		CommonResponse commonResponse = new CommonResponse();

		IhistorianResponse ir = IhistorianUtil.getDataByLine(line);
		if (ir != null && ir.getCode() == 200) {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(ir.getResult());
		} else {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(null);
		}
		return commonResponse;
	}

	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/risk", method = RequestMethod.GET)
	@ResponseBody
	public List<MetroLineIntervalRp> findMonitorLineRisk(
			@RequestParam("intervalId") Long intervalId) {

		List<MetroLineIntervalRp> rps = lineIntervalRpService.findLineIntervalRpInfo(intervalId);

		return rps;
	}

	/**
	 * 获取区间沉降点(Sinking Point)
	 */
	@RequestMapping(value = "/find/line/monitor/sinkingpoints", method = RequestMethod.GET)
	@ResponseBody
	public List<MetroLineIntervalSp> findLineIntervalSinkingPoints(
			@RequestParam("intervalId") Long intervalId) {

		List<MetroLineIntervalSp> sinkingPoints = spService.findLineIntervalSps(intervalId);

		return sinkingPoints;
	}

	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/coordinates", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findoordinatesData(@RequestParam("line") String line) {
		CommonResponse commonResponse = new CommonResponse();

		IhistorianResponse ir = IhistorianUtil.getCoordinatesByLine(line);
		if (ir != null && ir.getCode() == 200) {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(ir.getResult());
		} else {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(null);
		}
		return commonResponse;
	}

	/**
	 * 区间信息主页（检测数据）
	 * 
	 * @return
	 */
	@RequestMapping("/find/interval/monitor/datas")
	@ResponseBody
	public MonitorIntervalView findMonitorIntervalData(@RequestParam("intervalId") Long intervalId,
			@RequestParam("intervalSpId") Long intervalSpId) {
		MonitorIntervalView monitorIntervalView = infoCityService.findMonitorIntervalDatas(intervalId, intervalSpId);
		return monitorIntervalView;
	}

	/**
	 *  区间信息主页（检测数据）
	 * @return 
	 */
	@RequestMapping("/find/interval/monitor/data")
	@ResponseBody
	public MonitorIntervalDataView findIntervalMonitorData(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("date") String date,
			@RequestParam("leftOrRight") String leftOrRight) {
		MonitorIntervalDataView monitorIntervalDataView = infoCityService.findIntervalMonitorData(intervalId, date, leftOrRight);
		return monitorIntervalDataView;
	}

	/**
	 * 上传风险组段划分信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo")
	public String riskinfo(@RequestParam("intervalId") Long intervalId) {

		MetroLineInterval metroline = intervalService.findObjById(intervalId);
		request.setAttribute("riskPdfUrl", metroline.getRiskPdfUrl());
		request.setAttribute("intervalId", String.valueOf(metroline.getId()));
		return "/find-info/risk";
	}

	/**
	 * 风险组段划分文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "盾构远程监控", operate = "风险组段划分文件上传")
	@RequestMapping(value = "/risk-pdf/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadRiskPdf(@RequestParam("iId") Long intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// TODO 输出格式需要统一
		// ?? 输出类型
		// CommonResponse r = new CommonResponse();
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
			}
			String pdfUrl = (String) uploadResult.getResult();
			boolean result = intervalService.editRiskPdfUrl(intervalId, pdfUrl);
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
		MetroLineInterval metroline = intervalService.findObjById(intervalId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskPdfUrl", metroline.getRiskPdfUrl());
		map.put("intervalId", metroline.getId());

		return map;
	}

	/**
	 * 风险组段划分文件删除
	 */
	@SysControllorLog(menu = "盾构远程监控", operate = "风险组段划分文件删除")
	@RequestMapping(value = "/risk-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteRiskPdf(@RequestParam("intervalId") Long intervalId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = intervalService.editRiskPdfUrl(intervalId, null);
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
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/index")
	public String lrMain(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(intervalId, leftOrRight);
		modelMap.addAttribute("machineType", lr.getMachineType());
		modelMap.addAttribute("communiStatus", infoCityService.getCommuniStatus(intervalId, leftOrRight));
		return "/find-info/info_monitor_LRline";
	}

	/**
	 * 区间左右线信息主页（刀盘）
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/knife")
	public ModelAndView lrMainToKnife(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		ModelAndView mv = new ModelAndView("/find-info/knife");

		MetroPhoto knifePhotos = photoService.findKnifePhoto(intervalId, leftOrRight);
		if (knifePhotos != null) {
			mv.addObject("dpUrl", knifePhotos.getPhotoUrl());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页（螺旋）
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/spiral")
	public ModelAndView lrMainToSpiral(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		ModelAndView mv = new ModelAndView("/find-info/spiral");

		MetroPhoto spiralPhotos = photoService.findSpiralPhoto(intervalId, leftOrRight);
		if (spiralPhotos != null) {
			mv.addObject("lxUrl", spiralPhotos.getPhotoUrl());
		}
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页（泥水）
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/slurry")
	public ModelAndView lrMainToslurry(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		ModelAndView mv = new ModelAndView("/find-info/slurry");

		MetroPhoto slurryPhotos = photoService.findSlurryPhoto(intervalId, leftOrRight);
		if (slurryPhotos != null) {
			mv.addObject("nsUrl", slurryPhotos.getPhotoUrl());
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页（导向）
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/guide")
	public ModelAndView lrMainToGuide(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		ModelAndView mv = new ModelAndView("/find-info/guide");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		// mv.addObject("maps", map);
		// mv.addObject("head", map.get("head"));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 刀盘螺旋导向基础数据
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/datas/now")
	@ResponseBody
	public Map<String, Object> getLrMonitorDatasNow(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		map.put("sufix", ".F_CV");
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}

	/**
	 * 泥水SVG图形数据
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/slurry")
	@ResponseBody
	public Map<String, Object> getSlurryData(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		Map<String, Object> map = infoCityService.getSlurryData(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		map.put("sufix", ".F_CV");
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}

	/**
	 * 刀盘SVG图形数据
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/knife")
	@ResponseBody
	public Map<String, Object> getKnifeData(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		Map<String, Object> map = infoCityService.getKnifeData(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		map.put("sufix", ".F_CV");
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}

	/**
	 * 螺旋SVG图形数据
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/spiral")
	@ResponseBody
	public Map<String, Object> getSpiralData(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		Map<String, Object> map = infoCityService.getSpiralData(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		map.put("sufix", ".F_CV");
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}

	/**
	 * 导向当前盾位置
	 * 
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/datas")
	@ResponseBody
	public Map<String, Object> getLrMonitorDatas(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		Map<String, Object> map = infoCityService.findIntervalLrDaoxDatas(intervalId, leftOrRight);
		return map;
	}

	/**
	 * 综合数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/all/dic/datas")
	@ResponseBody
	public PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrAll(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		PageResultSet<MonitorLrAlldicView> mvds = infoCityService.findMonitorIntervalLrDics(intervalId, leftOrRight);
		if (mvds == null) {
			mvds = new PageResultSet<MonitorLrAlldicView>();
			mvds.setPage(new Page(0, 100, 1));
			mvds.setList(new ArrayList<MonitorLrAlldicView>());
		}
		return mvds;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	@RequestMapping("/to/lr/static/index")
	public String lrStaticMain(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -7);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginDate", beginTime);
		modelMap.addAttribute("endDate", endTime);
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(intervalId, leftOrRight);
		modelMap.addAttribute("machineType", lr.getMachineType());
		try {
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		modelMap.addAttribute("dics",dicSet.getList());
		int currRingNum = infoCityService.findCurrRingNum(intervalId, leftOrRight);
			if (currRingNum - 5 >= 0) {
				modelMap.addAttribute("beginRing", currRingNum - 5);
			} else {
				modelMap.addAttribute("beginRing", 0);
			}
			modelMap.addAttribute("endRing", currRingNum);
		} catch (Exception e) {
			modelMap.addAttribute("beginRing", 0);
			modelMap.addAttribute("endRing", 0);
			logger.error("区间左右线信息主页lrStaticMain:"+e.getMessage());
		}
		return "/find-info/info_statistic";
	}

	/**
	 * 区间左右线信息主页
	 * 周报表截图参数POST
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/to/lr/screenshots", method=RequestMethod.POST)
	public  String screenshotslrStatic(@RequestBody MetroWeeklyReportParam param) {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -7);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginDate", beginTime);
		modelMap.addAttribute("endDate", endTime);
		modelMap.addAttribute("intervalId", param.getIntervalId());
		modelMap.addAttribute("leftOrRight", param.getLeftOrRight());
		modelMap.addAttribute("saveOrUpdate", param.getSaveOrUpdate());
		modelMap.addAttribute("id", param.getId());
		modelMap.addAttribute("summary", param.getSummary());
		modelMap.addAttribute("shieldTunneling", param.getShieldTunneling());
		modelMap.addAttribute("shieldTunnelingImg", param.getShieldTunnelingImg());
		modelMap.addAttribute("riskSituation", param.getRiskSituation());
		modelMap.addAttribute("riskSituationImg", param.getRiskSituationImg());
		modelMap.addAttribute("geology", param.getGeology());
		modelMap.addAttribute("geologyImg", param.getGeologyImg());
		modelMap.addAttribute("effect", param.getEffect());
		modelMap.addAttribute("effectImg", param.getEffectImg());
		modelMap.addAttribute("settlement", param.getSettlement());
		modelMap.addAttribute("settlementImg", param.getSettlementImg());
		modelMap.addAttribute("paramAnalysis", param.getParamAnalysis());
		modelMap.addAttribute("paramAnalysisImg", param.getParamAnalysisImg());
		modelMap.addAttribute("slagSamplesImg", param.getSlagSamplesImg());
		modelMap.addAttribute("horizontalAttitude", param.getHorizontalAttitude());
		modelMap.addAttribute("horizontalAttitudeImg", param.getHorizontalAttitudeImg());
		modelMap.addAttribute("verticalAttitude", param.getVerticalAttitude());
		modelMap.addAttribute("verticalAttitudeImg", param.getVerticalAttitudeImg());
		modelMap.addAttribute("grouting", param.getGrouting());
		modelMap.addAttribute("groutingImg", param.getGroutingImg());
		modelMap.addAttribute("oiltemperature", param.getOiltemperature());
		modelMap.addAttribute("oiltemperatureImg", param.getOiltemperatureImg());
		modelMap.addAttribute("earlyWarning", param.getEarlyWarning());
		modelMap.addAttribute("earlyWarningImg", param.getEarlyWarningImg());
		modelMap.addAttribute("auditOpinion", param.getAuditOpinion());
		modelMap.addAttribute("reportTime", param.getReportTime());
		modelMap.addAttribute("operator", param.getOperator());
		modelMap.addAttribute("reviewer", param.getReviewer());
		modelMap.addAttribute("startRingNum", param.getStartRingNum());
		modelMap.addAttribute("endRingNum", param.getEndRingNum());
		modelMap.addAttribute("startPressure", param.getStartPressure());
		modelMap.addAttribute("endPressure", param.getEndPressure());
		modelMap.addAttribute("startCutterTorque", param.getStartCutterTorque());
		modelMap.addAttribute("endCutterTorque", param.getEndCutterTorque());
		modelMap.addAttribute("startTotalThrust", param.getStartTotalThrust());
		modelMap.addAttribute("endTotalThrust", param.getEndTotalThrust());
		modelMap.addAttribute("startCutterSpeed", param.getStartCutterSpeed());
		modelMap.addAttribute("endCutterSpeed", param.getEndCutterSpeed());
		modelMap.addAttribute("startSpeed", param.getStartSpeed());
		modelMap.addAttribute("endSpeed", param.getEndSpeed());
		modelMap.addAttribute("startGroutingAmount", param.getStartGroutingAmount());
		modelMap.addAttribute("endGroutingAmount", param.getEndGroutingAmount());
		try {
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		modelMap.addAttribute("dics",dicSet.getList());
		int currRingNum = infoCityService.findCurrRingNum(param.getIntervalId(), param.getLeftOrRight());
			if (currRingNum - 5 >= 0) {
				modelMap.addAttribute("beginRing", currRingNum - 5);
			} else {
				modelMap.addAttribute("beginRing", 0);
			}
			modelMap.addAttribute("endRing", currRingNum);
		} catch (Exception e) {
			modelMap.addAttribute("beginRing", 0);
			modelMap.addAttribute("endRing", 0);
			logger.error("区间左右线信息主页lrStatic:"+e.getMessage());
		}
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(param.getIntervalId(), param.getLeftOrRight());
		modelMap.addAttribute("machineType", lr.getMachineType());

		return "/project-info/capture_statistic";
	}
	
	/**
	 * 材料消耗统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab1")
	@ResponseBody
	public Map<String, Object> getStaticTab1(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("beginRing") String beginRing,
			@RequestParam("endRing") String endRing, @RequestParam("paramName") String paramName) {

		List<List<Object>> list = infoCityService.findMonitorStaticTab1(intervalId, leftOrRight, beginRing, endRing,
				paramName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	/**
	 * 时间统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab2")
	@ResponseBody
	public Map<String, Object> getStaticTab2(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginRing") String beginRing,
			@RequestParam("endRing") String endRing,
			@RequestParam("type") String type) {

		List<List<Object>> result = infoCityService.findMonitorStaticTab2(intervalId, leftOrRight, beginRing, endRing, type);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		return map;
	}

	/**
	 * 进度统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab3")
	@ResponseBody
	public Map<String, Object> getStaticTab3(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date beginDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date endDate) {

		List<List<Object>> result = infoCityService.findMonitorStaticTab3(intervalId, leftOrRight, beginDate, endDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		return map;
	}
	
	/**
	 * 左右线进度统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/statistics/schedule")
	@ResponseBody
	public Map<String, Object> getStaticsSchedule(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginDate") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date beginDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date endDate,
			@RequestParam("cycleType") String cycleType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", lineIntervalScheduleService.getIntervalLrSchedule(intervalId, leftOrRight, beginDate, endDate, cycleType));
		return map;
	}
	
	/**
	 * 左右线进度统计基础数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/statistics/baseprogress")
	@ResponseBody
	public Map<String, Object> getStaticsSchedule(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		return lineIntervalScheduleService.getIntervalLrBase(intervalId, leftOrRight);
	}
	
	
	/**
	 * 综合数据（汇总统计）
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab4")
	@ResponseBody
	public PageResultSet<MonitorIntervalLrStaticView> getStaticTab4(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date beginTime,
			@RequestParam("endTime") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date endTime,
			@RequestParam("excelType") String excelType) {

		PageResultSet<MonitorIntervalLrStaticView> mvds = infoCityService.findMonitorStaticTab4(intervalId, leftOrRight,
				pageNum, pageSize, beginTime, endTime, excelType);

		if (mvds == null || mvds.getList() == null || mvds.getList().size() <= 0) {
			mvds = new PageResultSet<MonitorIntervalLrStaticView>();
			mvds.setPage(new Page(0, 10, 1));
			mvds.setList(new ArrayList<MonitorIntervalLrStaticView>());
		}
		return mvds;
	}

	@RequestMapping("/find/static/tab4d")
	@ResponseBody
	public CommonResponse getStaticTab4d(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date beginTime,
			@RequestParam("endTime") @DateTimeFormat(pattern = "yyyy年MM月dd日") Date endTime,
			@RequestParam("excelType") String excelType) {

		PageResultSet<MonitorIntervalLrStaticView> mvds = infoCityService.findMonitorStaticTab4(intervalId, leftOrRight,
				pageNum, pageSize, beginTime, endTime, excelType);

		CommonResponse commonResponse = new CommonResponse();
		try {
			String excelFileName = writeExcel(mvds.getList(), excelType);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
			logger.error("导出异常getStaticTab4d:"+e.getMessage());
		}
		return commonResponse;
	}

	/**
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginRing
	 * @param endRing
	 * @param model
	 * @param type
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/find/static/tab5")
	@ResponseBody
	public MonitorIntervalLrStaticsView getStaticTab5(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginRing") Integer beginRing,
			@RequestParam("endRing") Integer endRing,
			@RequestParam("model") Integer model,
			@RequestParam("type") String type,
			@RequestParam("datamodel") Integer dataModel,
			@RequestParam("datatype") Integer dataType,
			@RequestParam("beginTime") @DateTimeFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒") Date beginTime,
			@RequestParam("endTime") @DateTimeFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒") Date endTime) {
	
		String[] ks = request.getParameter("ks") != null ? request.getParameter("ks").split(",") : null;
		String[] kns = request.getParameter("kns") != null ? request.getParameter("kns").split(",") : null;
		String[] indxs = request.getParameter("indxs") != null ? request.getParameter("indxs").split(",") : null;

		MonitorIntervalLrStaticsView misv = infoCityService.findMonitorStaticTab5(intervalId, leftOrRight, model, type, dataModel, dataType, beginTime, endTime, beginRing, endRing, ks, kns, indxs);
		return misv;
	}
	
	/**
	 * 管片姿态信息列表
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 为空则不区分
	 * @param beginRing 开始环号，为0则从第1环开始
	 * @param endRing   结束环号    为0则查到最大环号
	 * @return
	 */
	@RequestMapping("/find/static/tab6")
	@ResponseBody
	public Map<String,Object> getStaticTab6(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam(value="beginRing",required=false, defaultValue="0") Integer beginRing,
			@RequestParam(value="endRing",required=false, defaultValue="0") Integer endRing) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MetroLineIntervalSa> saList = lineIntervalSaService.findLineIntervalSas(intervalId, leftOrRight, beginRing,
				endRing, true);
		map.put("list", saList);
		return map;
	}

	/**
	 * 获取数据权限树
	 * 
	 * @return
	 */
	@RequestMapping("/get/data/tree")
	@ResponseBody
	public List<Jstree> getDataRight() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		String[] urls = new String[4];
		urls[0] = "/monitor/info/to/city/index";
		urls[1] = "/monitor/info/to/line/index";
		urls[2] = "/monitor/info/to/area/index";
		urls[3] = "/monitor/info/to/lr/index";
		Boolean[] diss = new Boolean[4];
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}

	public String writeExcel(List<MonitorIntervalLrStaticView> datas, String type) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMdd");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String t = "日";
			if ("1".equals(type)) {// 日
				t = "日";
			} else if ("2".equals(type)) {
				t = "周";
			} else {
				t = "月";
			}
			String filename = generationStr + "_" + t + "汇总统计.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableCellFormat format = new WritableCellFormat();
			format.setAlignment(Alignment.CENTRE);
			WritableSheet sheet = book.createSheet(t + "汇总统计", 0);
			String[] rtitle = { "盾构掘进" + t + "报表", "工程名称", "统计时间", "本" + t + "掘进进度", "正常掘进时间", "管片拼装时间", "停机时间", "注浆量",
					"盾尾油脂量" };
			MonitorIntervalLrStaticView msv = datas.get(0);
			for (int i = 0; i < 9; i++) {
				if (i == 0) {
					sheet.mergeCells(0, i, 4, i);
					sheet.addCell(new Label(0, i, rtitle[0], format));
				} else if (i == 1) {
					sheet.mergeCells(1, i, 4, i);
					sheet.addCell(new Label(0, i, rtitle[1]));
					sheet.addCell(new Label(1, i, msv.getLineMark()));
				} else if (i == 2) {
					sheet.addCell(new Label(0, i, rtitle[2]));
					sheet.addCell(new Label(1, i, "起始日期"));
					if ("1".equals(type)) {// 日
						sheet.addCell(new Label(2, i, msv.getDate()));
					} else {
						sheet.addCell(new Label(2, i, msv.getBeginDate()));
					}
					sheet.addCell(new Label(3, i, "结束日期"));
					if ("1".equals(type)) {// 日
						sheet.addCell(new Label(4, i, msv.getDate()));
					} else {
						sheet.addCell(new Label(4, i, msv.getEndDate()));
					}
				} else if (i == 3) {
					sheet.addCell(new Label(0, i, rtitle[3]));
					sheet.addCell(new Label(1, i, "起始环号"));
					sheet.addCell(new Number(2, i, msv.getBeginRing()));
					sheet.addCell(new Label(3, i, "结束环号"));
					sheet.addCell(new Number(4, i, msv.getEndRing()));
				} else {
					sheet.mergeCells(1, i, 4, i);
					if (i == 4) {
						sheet.addCell(new Label(0, i, rtitle[4]));
						sheet.addCell(new Label(1, i, msv.getK0001() + "小时"));
					} else if (i == 5) {
						sheet.addCell(new Label(0, i, rtitle[5]));
						sheet.addCell(new Label(1, i, msv.getK0002() + "小时"));
					} else if (i == 6) {
						sheet.addCell(new Label(0, i, rtitle[6]));
						sheet.addCell(new Label(1, i, msv.getK0003() + "小时"));
					} else if (i == 7) {
						sheet.addCell(new Label(0, i, rtitle[7]));
						sheet.addCell(new Label(1, i, msv.getD0023() + "m³"));
					} else {
						sheet.addCell(new Label(0, i, rtitle[8]));
						sheet.addCell(new Label(1, i, msv.getG0001() + "L"));
					}
				}
			}
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 10);
			book.write();
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("writeExcel:"+e.getMessage());
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("writeExcel:"+e.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 盾尾间隙详情
	 * 
	 * @param intervalId
	 *            区间ID
	 * @param leftOrRight
	 *            左右线，左线："l",右线："r"
	 * @param ringNum
	 *            环号
	 * @return
	 */
	@RequestMapping("/find/static/stdetail")
	@ResponseBody
	public MetroLineIntervalSt getStaticTab6(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam(value = "ringNum") Integer ringNum) {
		MetroLineIntervalSt st = lineIntervalStService.findLineIntervalSt(Long.parseLong(intervalId), leftOrRight, ringNum);
		return st;
	}

	/**
	 * 城市数据监测（APP根据传入施工状态查找地铁施工信息）
	 * 
	 * @return
	 */
	@RequestMapping("/app/find/city/datas")
	@ResponseBody
	public CommonResponse findMonitorCityAllAPP(
			@RequestParam("cityId") Long cityId,
			@RequestParam("buildStatus") Integer buildStatus, 
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		CommonResponse commonResponse = new CommonResponse();
		SessionUser su = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER2);
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(cityId, null, buildStatus,null,
				pageNum, pageSize,su.getId());
		if (mvds == null) {
			mvds = new PageResultSet<MonitorViewData>();
			mvds.setPage(new Page(0, pageSize, pageNum));
			mvds.setList(new ArrayList<MonitorViewData>());
			commonResponse.setResult(mvds);
			commonResponse.setCode(500);
		}
		if (mvds != null) {
			commonResponse.setResult(mvds);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * APP盾构施工监控信息管理（点击查询详情）
	 * 
	 * @return linename  
	 * @return intervalname
	 * @return leftorright
	 */
	@RequestMapping("/app/get/data/intervalLr")
	@ResponseBody
	public CommonResponse getfindLineIntervalLr(String linename, String intervalname, String leftorright) {
		CommonResponse commonResponse = new CommonResponse();
		if (linename == null || intervalname == null || leftorright == null || "".equals(linename)
				|| "".equals(intervalname) || "".equals(leftorright)) {
			commonResponse.setCode(500);
		} else {
			List<MetroLineIntervalLr> city = rightService.getfindLineIntervalLr(linename, intervalname, leftorright);
			commonResponse.setResult(city);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * APP登录后主页（查询线路）
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/get/data/circuit")
	@ResponseBody
	public CommonResponse getCirCuit(Long userId) {
		CommonResponse commonResponse = new CommonResponse();
		List<MetroLine> city = rightService.getCircuitUserId(userId);
		if (city != null) {
			commonResponse.setResult(city);
			commonResponse.setCode(200);
		} else {
			commonResponse.setCode(500);
		}
		return commonResponse;
	}

	/**
	 * APP登录后主页（线路左右区间）
	 * 
	 * @param userId
	 * @param linename
	 * @return
	 */
	@RequestMapping("/app/get/data/intervalilr")
	@ResponseBody
	public CommonResponse getfindIntervaliLR(Long userId, String linename) {
		CommonResponse commonResponse = new CommonResponse();
		if (userId == null || linename == null) {
			commonResponse.setCode(500);
		} else {

			List<MetroLineIntervaliLR> city = rightService.getfindIntervaliLR(userId, linename);
			commonResponse.setResult(city);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * APP登录后主页（线路左右区间,一次性查询全部的）
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/app/get/data/homepage")
	@ResponseBody
	public HomepageResponse getfindhomepage(Long userId) {
		HomepageResponse homepageResponse = new HomepageResponse();
		if (userId == null) {
			homepageResponse.setCode(500);
		} else {
			List<Homepage> city = rightService.getfindHomepage(userId);
			homepageResponse.setHomepages(city);
			homepageResponse.setCode(200);
		}
		return homepageResponse;
	}

	/**
	 * APP综合数据
	 * 
	 * @return
	 */
	@RequestMapping("/app/find/all/dic/datas")
	@ResponseBody
	public CommonResponse findMonitorIntervalLrAllAPP(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		if (leftOrRight.equals("") || intervalId == null || leftOrRight == null
				|| intervalId.equals(null) || leftOrRight.equals(null)) {
			commonResponse.setCode(500);
		} else {
			PageResultSet<MonitorLrAlldicView> mvds = infoCityService.findMonitorIntervalLrDics2(intervalId,
					leftOrRight);
			if (mvds == null) {
				mvds = new PageResultSet<MonitorLrAlldicView>();
				mvds.setPage(new Page(0, 100, 1));
				mvds.setList(new ArrayList<MonitorLrAlldicView>());
				commonResponse.setResult(mvds);
				commonResponse.setCode(200);
			} else {
				commonResponse.setResult(mvds);
				commonResponse.setCode(200);
			}
		}
		return commonResponse;
	}

	/**
	 * APP导向当前盾位置
	 * 
	 * @return
	 */
	@RequestMapping("/app/find/lr/monitor/datas")
	@ResponseBody
	public CommonResponse getLrMonitorDatasAPP(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		if (intervalId == null || leftOrRight == null || "".equals(leftOrRight)) {
			commonResponse.setCode(500);
		} else {
			Map<String, Object> map = infoCityService.findIntervalLrDaoxDatas(intervalId, leftOrRight);
			commonResponse.setResult(map);
			commonResponse.setCode(200);
		}
		return commonResponse;
	}

	/**
	 * 
	 * APP总揽
	 * 
	 */
	@RequestMapping("/app/find/overall")
	@ResponseBody
	public CommonResponse findOverallAPP(
			@RequestParam("lineName") String lineName,
			@RequestParam("intervalName") String intervalName, 
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			if (lineName == null || intervalName == null || leftOrRight == null || "".equals(lineName)
					|| "".equals(intervalName) || "".equals(leftOrRight)) {
				commonResponse.setCode(500);
			} else {
				List<Pandect> mvds = infoCityService.findPandect(lineName, intervalName, leftOrRight);
				commonResponse.setCode(200);
				commonResponse.setResult(mvds);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("findOverallAPP:"+e.getMessage());
		}
		return commonResponse;
	}
	
	/**
	 * 线路进度信息
	 * 
	 * @return
	 */
	@RequestMapping("/find/line/process")
	@ResponseBody
	public Map<String, Object> getLineProcess(
			@RequestParam("lineId") Long lineId,
			@RequestParam(value="beginDate",required=false) @DateTimeFormat(pattern = "yyyy年MM月dd日") Date beginDate,
			@RequestParam(value="endDate" ,required=false) @DateTimeFormat(pattern = "yyyy年MM月dd日") Date endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", infoCityService.getLineProcess(lineId, beginDate, endDate));
		return map;
	}

	/**
	 * 查询按单位分组的字典信息
	 * 
	 * @return
	 */
	@RequestMapping("/find/dictgroup")
	@ResponseBody
	public Map<String, Object> getDictList() {
		return infoCityService.getDictionaryGroup();
	}
	
	/**
	 * APP查询当前环
	 * @return
	 */
	@RequestMapping("/app/LoopMark")
	@ResponseBody
	public CommonResponse findgitduct2(
			@RequestParam("lineName") String lineName,
			@RequestParam("intervalName") String intervalName, 
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		
		try {
			if (lineName == null || intervalName == null || leftOrRight == null || "".equals(lineName)
					|| "".equals(intervalName) || "".equals(leftOrRight)) {
				commonResponse.setCode(500);
			} else {
				MetroLoopMark mvds = infoCityService.findMonitorInfoCityLoop(lineName, intervalName, leftOrRight);
				commonResponse.setCode(200);
				commonResponse.setResult(mvds);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("findgitduct2:"+e.getMessage());
		}
		
		return commonResponse;
	}
	
	/**
	 * 区间左右线APP（分组基础参数）
	 * @param intervalId 区间
	 * @param leftOrRight 左右线
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/base")
	@ResponseBody
	public Map<String, Object> getBaseData(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {

		Map<String, Object> map = infoCityService.getBaseData(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(intervalId, leftOrRight);
		map.put("machineType", lr.getMachineType());
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}
	
	/**
	 * 取区间左右线指定环号参数的最大值和最小值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param beginRing   开始环
	 * @param endRing     结束环
	 * @return
	 */
	@RequestMapping("/find/static/maxormin")
	@ResponseBody
	public Map<String, Object> getStaticMaxAndMin(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginRing") Integer beginRing,
			@RequestParam("endRing") Integer endRing) {
		return infoCityService.getMaxAndMinValue(Long.parseLong(intervalId), leftOrRight, beginRing, endRing);
	}
	
	/**
	 * 取区间左右线一周指定环号参数的最大值和最小值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param beginRing   开始环
	 * @param endRing     结束环
	 * @return
	 */
	@RequestMapping("/find/static/weekmaxormin")
	@ResponseBody
	public Map<String, Object> getStatiWeekMaxAndMin(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("beginRing") Integer beginRing,
			@RequestParam("endRing") Integer endRing) {
		return infoCityService.getWeekMaxAndMinValue(Long.parseLong(intervalId), leftOrRight, beginRing, endRing);
	}
	
}
