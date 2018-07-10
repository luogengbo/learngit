package com.szgentech.metro.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.DaiyWarnStatistics;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroDailyReport;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroDailyReportService;
import com.szgentech.metro.service.IMetroLineIntervalLrService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;
import com.szgentech.metro.vo.SessionUser;

/**
 * 日报表管理控制器
 * 
 * @author luohao
 *
 */
@Controller
@RequestMapping("/project-info/daily/report")
public class ProjectInfoDailyReportController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoDailyReportController.class);

	@Autowired
	private ISysRightService rightService;
	@Autowired
	private IMetroDailyReportService dailyReportService;
	@Autowired
	private IMetroLineIntervalLrService intervalLrService;
	@Autowired
	private IMetroWarningRecService warningRecService;
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;
	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@RequestMapping("/index")
	public String list() {
		return "/project-info/item_dailyreport";
	}

	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public List<Jstree> getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(getCurrentUser().getId());
		String[] urls = new String[4];
		urls[3] = "/project-info/daily/report/item_dailyreport";
		Boolean[] diss = new Boolean[4];
		diss[0] = true;
		diss[1] = true;
		diss[2] = true;
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}

	@RequestMapping("/item_dailyreport")
	public String toReport(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(intervalId, leftOrRight);
		modelMap.addAttribute("machineType", lr.getMachineType());
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		return "/project-info/item_dailyreport_right";
	}

	/**
	 * 查询风险分析日报表数据
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            报表时间
	 * @return
	 */
	@RequestMapping("/findDailyReport")
	@ResponseBody
	public MetroDailyReport getDailyReport(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		MetroDailyReport rp = (MetroDailyReport) dailyReportService.findDailyReport(Long.parseLong(intervalId),
				leftOrRight, reportTime);
		return rp;
	}

	/**
	 * 根据区间左右线报表时间查环信息
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            掘进时间
	 * @return
	 */
	@RequestMapping("/findDailyStatistics")
	@ResponseBody
	public Map<String, Object> getStatistics(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DaiyWarnStatistics> statisticsList = warningRecService.findDaiyWarnStatistics(intervalId, leftOrRight,
				reportTime, reportTime);
		int sum = 0;
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			sum += statistics.getSingleSum();
		}
		String warnList = "";
		String warnSum = "今日系统一共预警提示" + sum + "次、主要为：";
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			warnList += statistics.getDicMean() + "预警提示" + String.valueOf(statistics.getSingleSum()) + "次，预警确认"
					+ String.valueOf(statistics.getConfirmSum()) + "次，响应"
					+ String.valueOf(statistics.getPushSum() + "次;");
		}
		String warnStatisticsList = warnSum + warnList;
		MonitorIntervalLrStaticView ringNum = dailyReportService.getStaticViewByTime(intervalId, leftOrRight,
				reportTime);
		resultMap.put("warnStatisticsList", warnStatisticsList);
		resultMap.put("beginRing", Integer.valueOf(Math.round(ringNum.getBeginRing())));
		resultMap.put("endRing", Integer.valueOf(Math.round(ringNum.getEndRing())));
		resultMap.put("ringNum", Integer.valueOf(Math.round(ringNum.getRingNum())));
		MonitorInfoCity moi = monitorCityDao.findMonitorInfoCity(params);
		if (moi == null) {
			return resultMap;
		}
		// 项目总长
		int totalRingNum = moi.getRingNum() == null ? 0 : moi.getRingNum().intValue();
		// 文件名称
		String fileName = moi.getLineName() + moi.getIntervalName() + "【"
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进日分析报表";
		// 工程名称
		String projectName = moi.getCityName() + "地铁" + moi.getLineName() + moi.getIntervalName() + "【"
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构工程";
		// 施工单位
		String unit = moi.getMachineContractor() == null ? "" : moi.getMachineContractor();
		// 设备型号
		String equipment = moi.getMachineNo() == null ? "" : moi.getMachineNo();
		// 监理单位
		String supervision = "广州轨道交通建设监理有限公司";
		resultMap.put("totalRingNum", totalRingNum);
		resultMap.put("fileName", fileName);
		resultMap.put("projectName", projectName);
		resultMap.put("unit", unit);
		resultMap.put("equipment", equipment);
		resultMap.put("supervision", supervision);
		return resultMap;
	}

	/**
	 * 根据区间左右线报表时间查环信息
	 * 从ring_status表获取日报表的开始环、结束环与掘进环数
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            掘进时间
	 * @return
	 */
	@RequestMapping("/findDailyStatistics2")
	@ResponseBody
	public Map<String, Object> getStatistics2(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DaiyWarnStatistics> statisticsList = warningRecService.findDaiyWarnStatistics(intervalId, leftOrRight,
				reportTime, reportTime);
		int sum = 0;
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			sum += statistics.getSingleSum();
		}
		String warnList = "";
		String warnSum = "今日系统一共预警提示" + sum + "次、主要为：";
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			warnList += statistics.getDicMean() + "预警提示" + String.valueOf(statistics.getSingleSum()) + "次，预警确认"
					+ String.valueOf(statistics.getConfirmSum()) + "次，响应"
					+ String.valueOf(statistics.getPushSum() + "次;");
		}
		String warnStatisticsList = warnSum + warnList;
		Map<String, Object> ringNumMap = infoCityService.findRingNumInfo(intervalId, leftOrRight, reportTime, reportTime);
		resultMap.put("beginRing", ringNumMap.get("beginRing"));
		resultMap.put("endRing", ringNumMap.get("endRing"));
		resultMap.put("ringNum", ringNumMap.get("ringNum"));
		resultMap.put("warnStatisticsList", warnStatisticsList);
		MonitorInfoCity moi = monitorCityDao.findMonitorInfoCity(params);
		if (moi == null) {
			return resultMap;
		}
		// 项目总长
		int totalRingNum = moi.getRingNum() == null ? 0 : moi.getRingNum().intValue();
		// 文件名称
		String fileName = moi.getLineName() + moi.getIntervalName() + "【"
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进日分析报表";
		// 工程名称
		String projectName = moi.getCityName() + "地铁" + moi.getLineName() + moi.getIntervalName() + "【"
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构工程";
		// 施工单位
		String unit = moi.getMachineContractor() == null ? "" : moi.getMachineContractor();
		// 设备型号
		String equipment = moi.getMachineNo() == null ? "" : moi.getMachineNo();
		// 监理单位
		String supervision = "广州轨道交通建设监理有限公司";
		resultMap.put("totalRingNum", totalRingNum);
		resultMap.put("fileName", fileName);
		resultMap.put("projectName", projectName);
		resultMap.put("unit", unit);
		resultMap.put("equipment", equipment);
		resultMap.put("supervision", supervision);
		return resultMap;
	}
	
	/**
	 * 生成指定区间左右线、时间的PDF格式周报表
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            报表时间
	 * @return
	 */
	@RequestMapping("/exportDailyPdf")
	@ResponseBody
	public String exportDailyReportPdf(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		String fileName = dailyReportService.exportDailyReportPdf(Long.parseLong(intervalId), leftOrRight, reportTime);
		return fileName;
	}

	/**
	 * 更新保存风险分析日报表信息信息
	 */
	@SysControllorLog(menu = "日报表管理", operate = "区间日报表编辑")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLineIntervalLrReport(
			@RequestParam(value = "saveOrUpdate", required = false) String saveOrUpdate,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "intervalId", required = false) Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "summary", required = false) String summary,
			@RequestParam(value = "progressStatistics", required = false) String progressStatistics,
			@RequestParam(value = "workingCondition", required = false) String workingCondition,
			@RequestParam(value = "settlement", required = false) String settlement,
			@RequestParam(value = "settlementImg", required = false) String settlementImg,
			@RequestParam(value = "riskSituation", required = false) String riskSituation,
			@RequestParam(value = "riskSituationImg", required = false) String riskSituationImg,
			@RequestParam(value = "geology", required = false) String geology,
			@RequestParam(value = "geologyImg", required = false) String geologyImg,
			@RequestParam(value = "grouting", required = false) String grouting,
			@RequestParam(value = "groutingImg", required = false) String groutingImg,
			@RequestParam(value = "horizontalAttitude", required = false) String horizontalAttitude,
			@RequestParam(value = "horizontalAttitudeImg", required = false) String horizontalAttitudeImg,
			@RequestParam(value = "verticalAttitude", required = false) String verticalAttitude,
			@RequestParam(value = "verticalAttitudeImg", required = false) String verticalAttitudeImg,
			@RequestParam(value = "earlyWarning", required = false) String earlyWarning,
			@RequestParam(value = "earlyWarningImg", required = false) String earlyWarningImg,
			@RequestParam(value = "auditOpinion", required = false) String auditOpinion,
			@RequestParam(value = "reportTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime,
			@RequestParam(value = "operator", required = false) String operator,
			@RequestParam(value = "reviewer", required = false) String reviewer,
			@RequestParam(value = "startA0004", required = false) Float startA0004,
			@RequestParam(value = "endA0004", required = false) Float endA0004,
			@RequestParam(value = "minA0004", required = false) Float minA0004,
			@RequestParam(value = "maxA0004", required = false) Float maxA0004,
			@RequestParam(value = "analysisA0004", required = false) String analysisA0004,
			@RequestParam(value = "startA0013", required = false) Float startA0013,
			@RequestParam(value = "endA0013", required = false) Float endA0013,
			@RequestParam(value = "minA0013", required = false) Float minA0013,
			@RequestParam(value = "maxA0013", required = false) Float maxA0013,
			@RequestParam(value = "analysisA0013", required = false) String analysisA0013,
			@RequestParam(value = "startB0001", required = false) Float startB0001,
			@RequestParam(value = "endB0001", required = false) Float endB0001,
			@RequestParam(value = "minB0001", required = false) Float minB0001,
			@RequestParam(value = "maxB0001", required = false) Float maxB0001,
			@RequestParam(value = "analysisB0001", required = false) String analysisB0001,
			@RequestParam(value = "startB0002", required = false) Float startB0002,
			@RequestParam(value = "endB0002", required = false) Float endB0002,
			@RequestParam(value = "minB0002", required = false) Float minB0002,
			@RequestParam(value = "maxB0002", required = false) Float maxB0002,
			@RequestParam(value = "analysisB0002", required = false) String analysisB0002,
			@RequestParam(value = "startB0004", required = false) Float startB0004,
			@RequestParam(value = "endB0004", required = false) Float endB0004,
			@RequestParam(value = "minB0004", required = false) Float minB0004,
			@RequestParam(value = "maxB0004", required = false) Float maxB0004,
			@RequestParam(value = "analysisB0004", required = false) String analysisB0004,
			@RequestParam(value = "startB0006", required = false) Float startB0006,
			@RequestParam(value = "endB0006", required = false) Float endB0006,
			@RequestParam(value = "minB0006", required = false) Float minB0006,
			@RequestParam(value = "maxB0006", required = false) Float maxB0006,
			@RequestParam(value = "analysisB0006", required = false) String analysisB0006,
			@RequestParam(value = "startB0015", required = false) Float startB0015,
			@RequestParam(value = "endB0015", required = false) Float endB0015,
			@RequestParam(value = "minB0015", required = false) Float minB0015,
			@RequestParam(value = "maxB0015", required = false) Float maxB0015,
			@RequestParam(value = "analysisB0015", required = false) String analysisB0015,
			@RequestParam(value = "startR0026", required = false) Float startR0026,
			@RequestParam(value = "endR0026", required = false) Float endR0026,
			@RequestParam(value = "minR0026", required = false) Float minR0026,
			@RequestParam(value = "maxR0026", required = false) Float maxR0026,
			@RequestParam(value = "analysisR0026", required = false) String analysisR0026,
			@RequestParam(value = "startR0028", required = false) Float startR0028,
			@RequestParam(value = "endR0028", required = false) Float endR0028,
			@RequestParam(value = "minR0028", required = false) Float minR0028,
			@RequestParam(value = "maxR0028", required = false) Float maxR0028,
			@RequestParam(value = "analysisR0028", required = false) String analysisR0028,
			@RequestParam(value = "startR0025", required = false) Float startR0025,
			@RequestParam(value = "endR0025", required = false) Float endR0025,
			@RequestParam(value = "minR0025", required = false) Float minR0025,
			@RequestParam(value = "maxR0025", required = false) Float maxR0025,
			@RequestParam(value = "analysisR0025", required = false) String analysisR0025,
			@RequestParam(value = "startR0004", required = false) Float startR0004,
			@RequestParam(value = "endR0004", required = false) Float endR0004,
			@RequestParam(value = "minR0004", required = false) Float minR0004,
			@RequestParam(value = "maxR0004", required = false) Float maxR0004,
			@RequestParam(value = "analysisR0004", required = false) String analysisR0004) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroDailyReport dailyReport = new MetroDailyReport();
			if (CommonUtils.isNotNull(intervalId)) {
				dailyReport.setIntervalId(intervalId);
			}
			if (CommonUtils.isNotNull(leftOrRight)) {
				dailyReport.setLeftOrRight(leftOrRight);
			}
			if (CommonUtils.isNotNull(summary)) {
				dailyReport.setSummary(summary);
			}
			if (CommonUtils.isNotNull(progressStatistics)) {
				dailyReport.setProgressStatistics(progressStatistics);
			}
			if (CommonUtils.isNotNull(workingCondition)) {
				dailyReport.setWorkingCondition(workingCondition);
			}
			if (CommonUtils.isNotNull(settlement)) {
				dailyReport.setSettlement(settlement);
			}
			if (CommonUtils.isNotNull(settlementImg)) {
				dailyReport.setSettlementImg(settlementImg);
			}
			if (CommonUtils.isNotNull(riskSituation)) {
				dailyReport.setRiskSituation(riskSituation);
			}
			if (CommonUtils.isNotNull(riskSituationImg)) {
				dailyReport.setRiskSituationImg(riskSituationImg);
			}
			if (CommonUtils.isNotNull(geology)) {
				dailyReport.setGeology(geology);
			}
			if (CommonUtils.isNotNull(geologyImg)) {
				dailyReport.setGeologyImg(geologyImg);
			}
			if (CommonUtils.isNotNull(grouting)) {
				dailyReport.setGrouting(grouting);
			}
			if (CommonUtils.isNotNull(groutingImg)) {
				dailyReport.setGroutingImg(groutingImg);
			}
			if (CommonUtils.isNotNull(horizontalAttitude)) {
				dailyReport.setHorizontalAttitude(horizontalAttitude);
			}
			if (CommonUtils.isNotNull(horizontalAttitudeImg)) {
				dailyReport.setHorizontalAttitudeImg(horizontalAttitudeImg);
			}
			if (CommonUtils.isNotNull(verticalAttitude)) {
				dailyReport.setVerticalAttitude(verticalAttitude);
			}
			if (CommonUtils.isNotNull(verticalAttitudeImg)) {
				dailyReport.setVerticalAttitudeImg(verticalAttitudeImg);
			}
			if (CommonUtils.isNotNull(earlyWarning)) {
				dailyReport.setEarlyWarning(earlyWarning);
			}
			if (CommonUtils.isNotNull(earlyWarningImg)) {
				dailyReport.setEarlyWarningImg(earlyWarningImg);
			}
			if (CommonUtils.isNotNull(auditOpinion)) {
				SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				dailyReport.setAuditOpinion(auditOpinion);
				dailyReport.setReviewer(sessionUser.getName());
			}
			if (CommonUtils.isNotNull(reportTime)) {
				dailyReport.setReportTime(reportTime);
			}
			if (!CommonUtils.isNotNull(operator)) {
				SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				dailyReport.setOperator(sessionUser.getName());
			}
			if (CommonUtils.isNotNull(startA0004)) {
				dailyReport.setStartA0004(startA0004);
			}
			if (CommonUtils.isNotNull(endA0004)) {
				dailyReport.setEndA0004(endA0004);
			}
			if (CommonUtils.isNotNull(minA0004)) {
				dailyReport.setMinA0004(minA0004);
			}
			if (CommonUtils.isNotNull(maxA0004)) {
				dailyReport.setMaxA0004(maxA0004);
			}
			if (CommonUtils.isNotNull(analysisA0004)) {
				dailyReport.setAnalysisA0004(analysisA0004);
			}
			if (CommonUtils.isNotNull(startA0013)) {
				dailyReport.setStartA0013(startA0013);
			}
			if (CommonUtils.isNotNull(endA0013)) {
				dailyReport.setEndA0013(endA0013);
			}
			if (CommonUtils.isNotNull(minA0013)) {
				dailyReport.setMinA0013(minA0013);
			}
			if (CommonUtils.isNotNull(maxA0013)) {
				dailyReport.setMaxA0013(maxA0013);
			}
			if (CommonUtils.isNotNull(analysisA0013)) {
				dailyReport.setAnalysisA0013(analysisA0013);
			}
			if (CommonUtils.isNotNull(startB0001)) {
				dailyReport.setStartB0001(startB0001);
			}
			if (CommonUtils.isNotNull(endB0001)) {
				dailyReport.setEndB0001(endB0001);
			}
			if (CommonUtils.isNotNull(minB0001)) {
				dailyReport.setMinB0001(minB0001);
			}
			if (CommonUtils.isNotNull(maxB0001)) {
				dailyReport.setMaxB0001(maxB0001);
			}
			if (CommonUtils.isNotNull(analysisB0001)) {
				dailyReport.setAnalysisB0001(analysisB0001);
			}
			if (CommonUtils.isNotNull(startB0002)) {
				dailyReport.setStartB0002(startB0002);
			}
			if (CommonUtils.isNotNull(endB0002)) {
				dailyReport.setEndB0002(endB0002);
			}
			if (CommonUtils.isNotNull(minB0002)) {
				dailyReport.setMinB0002(minB0002);
			}
			if (CommonUtils.isNotNull(maxB0002)) {
				dailyReport.setMaxB0002(maxB0002);
			}
			if (CommonUtils.isNotNull(analysisB0002)) {
				dailyReport.setAnalysisB0002(analysisB0002);
			}
			if (CommonUtils.isNotNull(startB0004)) {
				dailyReport.setStartB0004(startB0004);
			}
			if (CommonUtils.isNotNull(endB0004)) {
				dailyReport.setEndB0004(endB0004);
			}
			if (CommonUtils.isNotNull(minB0004)) {
				dailyReport.setMinB0004(minB0004);
			}
			if (CommonUtils.isNotNull(maxB0004)) {
				dailyReport.setMaxB0004(maxB0004);
			}
			if (CommonUtils.isNotNull(analysisB0004)) {
				dailyReport.setAnalysisB0004(analysisB0004);
			}
			if (CommonUtils.isNotNull(startB0006)) {
				dailyReport.setStartB0006(startB0006);
			}
			if (CommonUtils.isNotNull(endB0006)) {
				dailyReport.setEndB0006(endB0006);
			}
			if (CommonUtils.isNotNull(minB0006)) {
				dailyReport.setMinB0006(minB0006);
			}
			if (CommonUtils.isNotNull(maxB0006)) {
				dailyReport.setMaxB0006(maxB0006);
			}
			if (CommonUtils.isNotNull(analysisB0006)) {
				dailyReport.setAnalysisB0006(analysisB0006);
			}
			if (CommonUtils.isNotNull(startB0015)) {
				dailyReport.setStartB0015(startB0015);
			}
			if (CommonUtils.isNotNull(endB0015)) {
				dailyReport.setEndB0015(endB0015);
			}
			if (CommonUtils.isNotNull(minB0015)) {
				dailyReport.setMinB0015(minB0015);
			}
			if (CommonUtils.isNotNull(maxB0015)) {
				dailyReport.setMaxB0015(maxB0015);
			}
			if (CommonUtils.isNotNull(analysisB0015)) {
				dailyReport.setAnalysisB0015(analysisB0015);
			}
			if (CommonUtils.isNotNull(startR0026)) {
				dailyReport.setStartR0026(startR0026);
			}
			if (CommonUtils.isNotNull(endR0026)) {
				dailyReport.setEndR0026(endR0026);
			}
			if (CommonUtils.isNotNull(minR0026)) {
				dailyReport.setMinR0026(minR0026);
			}
			if (CommonUtils.isNotNull(maxR0026)) {
				dailyReport.setMaxR0026(maxR0026);
			}
			if (CommonUtils.isNotNull(analysisR0026)) {
				dailyReport.setAnalysisR0026(analysisR0026);
			}
			if (CommonUtils.isNotNull(startR0028)) {
				dailyReport.setStartR0028(startR0028);
			}
			if (CommonUtils.isNotNull(endR0028)) {
				dailyReport.setEndR0028(endR0028);
			}
			if (CommonUtils.isNotNull(minR0028)) {
				dailyReport.setMinR0028(minR0028);
			}
			if (CommonUtils.isNotNull(maxR0028)) {
				dailyReport.setMaxR0028(maxR0028);
			}
			if (CommonUtils.isNotNull(analysisR0028)) {
				dailyReport.setAnalysisR0028(analysisR0028);
			}
			if (CommonUtils.isNotNull(startR0025)) {
				dailyReport.setStartR0025(startR0025);
			}
			if (CommonUtils.isNotNull(endR0025)) {
				dailyReport.setEndR0025(endR0025);
			}
			if (CommonUtils.isNotNull(minR0025)) {
				dailyReport.setMinR0025(minR0025);
			}
			if (CommonUtils.isNotNull(maxR0025)) {
				dailyReport.setMaxR0025(maxR0025);
			}
			if (CommonUtils.isNotNull(analysisR0025)) {
				dailyReport.setAnalysisR0025(analysisR0025);
			}
			if (CommonUtils.isNotNull(startR0004)) {
				dailyReport.setStartR0004(startR0004);
			}
			if (CommonUtils.isNotNull(endR0004)) {
				dailyReport.setEndR0004(endR0004);
			}
			if (CommonUtils.isNotNull(minR0004)) {
				dailyReport.setMinR0004(minR0004);
			}
			if (CommonUtils.isNotNull(maxR0004)) {
				dailyReport.setMaxR0004(maxR0004);
			}
			if (CommonUtils.isNotNull(analysisR0004)) {
				dailyReport.setAnalysisR0004(analysisR0004);
			}

			if ("1".equals(saveOrUpdate)) { // 更新
				dailyReport.setId(Long.parseLong(id));
				boolean result1 = dailyReportService.updateObj(dailyReport);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新日报表信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else if ("2".equals(saveOrUpdate)) { // 保存
				Long result2 = dailyReportService.insertObj(dailyReport);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存日报表信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新日报表信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;

	}

	/**
	 * 分页查找日报表记录信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 *            区间左右线
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/dailyreport/find")
	@ResponseBody
	public PageResultSet<MetroDailyReport> findDailyReportInfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "reportTime", required = false) String reportTime,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroDailyReport> resultSet = dailyReportService.findDailyReportInfo(intervalId, leftOrRight,
				reportTime, pageNum, pageSize);
		return resultSet;
	}
}
