package com.szgentech.metro.controller;

import java.util.ArrayList;
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
import com.szgentech.metro.base.utils.DateUtil;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.DaiyWarnStatistics;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervalRingReport;
import com.szgentech.metro.model.MetroWeeklyReport;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroLineIntervalLrService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.service.IMetroWeeklyReportService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.service.impl.MetroLineIntervalRingReportService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;
import com.szgentech.metro.vo.SessionUser;

/**
 * 周报表管理控制器
 * 
 * @author luohao
 *
 */
@Controller
@RequestMapping("/project-info/report")
public class ProjectInfoReportController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoReportController.class);

	@Autowired
	private ISysRightService rightService;
	@Autowired
	private IMetroWeeklyReportService weeklyReportService;
	@Autowired
	private IMetroLineIntervalLrService intervalLrService;
	@Autowired
	private MetroLineIntervalRingReportService lineIntervalRingReportService;
	@Autowired
	private IMetroWarningRecService warningRecService;
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;
	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@RequestMapping("/index")
	public String list() {
		return "/project-info/item_report";
	}

	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public List<Jstree> getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(getCurrentUser().getId());
		String[] urls = new String[4];
		urls[3] = "/project-info/report/item_report";
		Boolean[] diss = new Boolean[4];
		diss[0] = true;
		diss[1] = true;
		diss[2] = true;
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}

	@RequestMapping("/item_report")
	public String toReport(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		MetroLineIntervalLr lr = intervalLrService.findIntervalLr(intervalId, leftOrRight);
		modelMap.addAttribute("machineType", lr.getMachineType());
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		return "/project-info/item_report_right";
	}

	/**
	 * 查询风险分析周报表数据
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            掘进时间
	 * @return
	 */
	@RequestMapping("/findReport")
	@ResponseBody
	public MetroWeeklyReport getReport(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Date endTime = DateUtil.getWeekEndTime(reportTime);
		MetroWeeklyReport rp = (MetroWeeklyReport) weeklyReportService.findWeeklyReport(Long.parseLong(intervalId),
				leftOrRight, endTime);
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
	@RequestMapping("/findStatistics")
	@ResponseBody
	public Map<String, Object> getStatistics(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		Date beginTime = DateUtil.getWeekStartTime(reportTime);
		Date endTime = DateUtil.getWeekEndTime(reportTime);
		Date currentTime = new Date();
		if (endTime.after(currentTime)) {
			endTime = currentTime;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DaiyWarnStatistics> statisticsList = warningRecService.findDaiyWarnStatistics(intervalId, leftOrRight,
				endTime, endTime);
		int sum = 0;
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			sum += statistics.getSingleSum();
		}
		String warnList = "";
		String warnSum = "本周系统一共预警提示" + sum + "次、主要为：";
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			warnList += statistics.getDicMean() + "预警提示" + String.valueOf(statistics.getSingleSum()) + "次，预警确认"
					+ String.valueOf(statistics.getConfirmSum()) + "次，响应"
					+ String.valueOf(statistics.getPushSum() + "次;");
		}
		String warnStatisticsList = warnSum + warnList;
		MonitorIntervalLrStaticView ringNum = weeklyReportService.getWeeklyViewByTime(intervalId, leftOrRight,
				beginTime, endTime);
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
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进周分析报表";
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
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param reportTime
	 *            掘进时间
	 * @return
	 */
	@RequestMapping("/findStatistics2")
	@ResponseBody
	public Map<String, Object> getStatistics2(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		Date beginTime = DateUtil.getWeekStartTime(reportTime);
		Date endTime = DateUtil.getWeekEndTime(reportTime);
		Date currentTime = new Date();
		if (endTime.after(currentTime)) {
			endTime = currentTime;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<DaiyWarnStatistics> statisticsList = warningRecService.findDaiyWarnStatistics(intervalId, leftOrRight,
				endTime, endTime);
		int sum = 0;
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			sum += statistics.getSingleSum();
		}
		String warnList = "";
		String warnSum = "本周系统一共预警提示" + sum + "次、主要为：";
		for (int i = 0; i < statisticsList.size(); i++) {
			DaiyWarnStatistics statistics = statisticsList.get(i);// 获取每一个对象
			warnList += statistics.getDicMean() + "预警提示" + String.valueOf(statistics.getSingleSum()) + "次，预警确认"
					+ String.valueOf(statistics.getConfirmSum()) + "次，响应"
					+ String.valueOf(statistics.getPushSum() + "次;");
		}
		String warnStatisticsList = warnSum + warnList;
		Map<String, Object> ringNumMap = infoCityService.findRingNumInfo(intervalId, leftOrRight, beginTime, endTime);
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
				+ ("l".equals(moi.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进周分析报表";
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
	 *            掘进时间
	 * @return
	 */
	@RequestMapping("/exportPdf")
	@ResponseBody
	public String exportWeeklyReportPdf(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("reportTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime) {
		Date endTime = DateUtil.getWeekEndTime(reportTime);
		String fileName = weeklyReportService.exportWeeklyReportPdf(Long.parseLong(intervalId), leftOrRight, endTime);
		return fileName;
	}

	/**
	 * 更新保存风险分析周报表信息信息
	 */
	@SysControllorLog(menu = "周报表管理", operate = "区间周报表编辑")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLineIntervalLrReport(
			@RequestParam(value = "saveOrUpdate", required = false) String saveOrUpdate,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "intervalId", required = false) Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "summary", required = false) String summary,
			@RequestParam(value = "shieldTunneling", required = false) String shieldTunneling,
			@RequestParam(value = "shieldTunnelingImg", required = false) String shieldTunnelingImg,
			@RequestParam(value = "riskSituation", required = false) String riskSituation,
			@RequestParam(value = "riskSituationImg", required = false) String riskSituationImg,
			@RequestParam(value = "geology", required = false) String geology,
			@RequestParam(value = "geologyImg", required = false) String geologyImg,
			@RequestParam(value = "effect", required = false) String effect,
			@RequestParam(value = "effectImg", required = false) String effectImg,
			@RequestParam(value = "settlement", required = false) String settlement,
			@RequestParam(value = "settlementImg", required = false) String settlementImg,
			@RequestParam(value = "paramAnalysis", required = false) String paramAnalysis,
			@RequestParam(value = "paramAnalysisImg", required = false) String paramAnalysisImg,
			@RequestParam(value = "slagSamplesImg", required = false) String slagSamplesImg,
			@RequestParam(value = "horizontalAttitude", required = false) String horizontalAttitude,
			@RequestParam(value = "horizontalAttitudeImg", required = false) String horizontalAttitudeImg,
			@RequestParam(value = "verticalAttitude", required = false) String verticalAttitude,
			@RequestParam(value = "verticalAttitudeImg", required = false) String verticalAttitudeImg,
			@RequestParam(value = "grouting", required = false) String grouting,
			@RequestParam(value = "groutingImg", required = false) String groutingImg,
			@RequestParam(value = "oiltemperature", required = false) String oiltemperature,
			@RequestParam(value = "oiltemperatureImg", required = false) String oiltemperatureImg,
			@RequestParam(value = "earlyWarning", required = false) String earlyWarning,
			@RequestParam(value = "earlyWarningImg", required = false) String earlyWarningImg,
			@RequestParam(value = "auditOpinion", required = false) String auditOpinion,
			@RequestParam(value = "reportTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date reportTime,
			@RequestParam(value = "operator", required = false) String operator,
			@RequestParam(value = "reviewer", required = false) String reviewer,
			@RequestParam(value = "startRingNum", required = false) Float startRingNum,
			@RequestParam(value = "endRingNum", required = false) Float endRingNum,
			@RequestParam(value = "startPressure", required = false) Float startPressure,
			@RequestParam(value = "endPressure", required = false) Float endPressure,
			@RequestParam(value = "startCutterTorque", required = false) Float startCutterTorque,
			@RequestParam(value = "endCutterTorque", required = false) Float endCutterTorque,
			@RequestParam(value = "startTotalThrust", required = false) Float startTotalThrust,
			@RequestParam(value = "endTotalThrust", required = false) Float endTotalThrust,
			@RequestParam(value = "startCutterSpeed", required = false) Float startCutterSpeed,
			@RequestParam(value = "endCutterSpeed", required = false) Float endCutterSpeed,
			@RequestParam(value = "startSpeed", required = false) Float startSpeed,
			@RequestParam(value = "endSpeed", required = false) Float endSpeed,
			@RequestParam(value = "startGroutingAmount", required = false) Float startGroutingAmount,
			@RequestParam(value = "endGroutingAmount", required = false) Float endGroutingAmount) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			MetroWeeklyReport weeklyReport = new MetroWeeklyReport();
			if (CommonUtils.isNotNull(intervalId)) {
				weeklyReport.setIntervalId(intervalId);
			}
			if (CommonUtils.isNotNull(leftOrRight)) {
				weeklyReport.setLeftOrRight(leftOrRight);
			}
			if (CommonUtils.isNotNull(summary)) {
				weeklyReport.setSummary(summary);
			}
			if (CommonUtils.isNotNull(shieldTunneling)) {
				weeklyReport.setShieldTunneling(shieldTunneling);
			}
			if (CommonUtils.isNotNull(shieldTunnelingImg)) {
				weeklyReport.setShieldTunnelingImg(shieldTunnelingImg);
			}
			if (CommonUtils.isNotNull(riskSituation)) {
				weeklyReport.setRiskSituation(riskSituation);
			}
			if (CommonUtils.isNotNull(riskSituationImg)) {
				weeklyReport.setRiskSituationImg(riskSituationImg);
			}
			if (CommonUtils.isNotNull(geology)) {
				weeklyReport.setGeology(geology);
			}
			if (CommonUtils.isNotNull(geologyImg)) {
				weeklyReport.setGeologyImg(geologyImg);
			}
			if (CommonUtils.isNotNull(effect)) {
				weeklyReport.setEffect(effect);
			}
			if (CommonUtils.isNotNull(effectImg)) {
				weeklyReport.setEffectImg(effectImg);
			}
			if (CommonUtils.isNotNull(settlement)) {
				weeklyReport.setSettlement(settlement);
			}
			if (CommonUtils.isNotNull(settlementImg)) {
				weeklyReport.setSettlementImg(settlementImg);
			}
			if (CommonUtils.isNotNull(paramAnalysis)) {
				weeklyReport.setParamAnalysis(paramAnalysis);
			}
			if (CommonUtils.isNotNull(paramAnalysisImg)) {
				weeklyReport.setParamAnalysisImg(paramAnalysisImg);
			}
			if (CommonUtils.isNotNull(slagSamplesImg)) {
				weeklyReport.setSlagSamplesImg(slagSamplesImg);
			}
			if (CommonUtils.isNotNull(horizontalAttitude)) {
				weeklyReport.setHorizontalAttitude(horizontalAttitude);
			}
			if (CommonUtils.isNotNull(horizontalAttitudeImg)) {
				weeklyReport.setHorizontalAttitudeImg(horizontalAttitudeImg);
			}
			if (CommonUtils.isNotNull(verticalAttitude)) {
				weeklyReport.setVerticalAttitude(verticalAttitude);
			}
			if (CommonUtils.isNotNull(verticalAttitudeImg)) {
				weeklyReport.setVerticalAttitudeImg(verticalAttitudeImg);
			}
			if (CommonUtils.isNotNull(grouting)) {
				weeklyReport.setGrouting(grouting);
			}
			if (CommonUtils.isNotNull(groutingImg)) {
				weeklyReport.setGroutingImg(groutingImg);
			}
			if (CommonUtils.isNotNull(oiltemperature)) {
				weeklyReport.setOiltemperature(oiltemperature);
			}
			if (CommonUtils.isNotNull(oiltemperatureImg)) {
				weeklyReport.setOiltemperatureImg(oiltemperatureImg);
			}
			if (CommonUtils.isNotNull(earlyWarning)) {
				weeklyReport.setEarlyWarning(earlyWarning);
			}
			if (CommonUtils.isNotNull(earlyWarningImg)) {
				weeklyReport.setEarlyWarningImg(earlyWarningImg);
			}
			if (CommonUtils.isNotNull(auditOpinion)) {
				SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				weeklyReport.setAuditOpinion(auditOpinion);
				weeklyReport.setReviewer(sessionUser.getName());
			}
			if (CommonUtils.isNotNull(reportTime)) {
				Date endTime = DateUtil.getWeekEndTime(reportTime);
				weeklyReport.setReportTime(endTime);
			}
			if (!CommonUtils.isNotNull(operator)) {
				SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
				weeklyReport.setOperator(sessionUser.getName());
			}
			if (CommonUtils.isNotNull(startRingNum)) {
				weeklyReport.setStartRingNum(startRingNum);
			}
			if (CommonUtils.isNotNull(endRingNum)) {
				weeklyReport.setEndRingNum(endRingNum);
			}
			if (CommonUtils.isNotNull(startPressure)) {
				weeklyReport.setStartPressure(startPressure);
			}
			if (CommonUtils.isNotNull(endPressure)) {
				weeklyReport.setEndPressure(endPressure);
			}
			if (CommonUtils.isNotNull(startCutterTorque)) {
				weeklyReport.setStartCutterTorque(startCutterTorque);
			}
			if (CommonUtils.isNotNull(endCutterTorque)) {
				weeklyReport.setEndCutterTorque(endCutterTorque);
			}
			if (CommonUtils.isNotNull(startTotalThrust)) {
				weeklyReport.setStartTotalThrust(startTotalThrust);
			}
			if (CommonUtils.isNotNull(endTotalThrust)) {
				weeklyReport.setEndTotalThrust(endTotalThrust);
			}
			if (CommonUtils.isNotNull(startCutterSpeed)) {
				weeklyReport.setStartCutterSpeed(startCutterSpeed);
			}
			if (CommonUtils.isNotNull(endCutterSpeed)) {
				weeklyReport.setEndCutterSpeed(endCutterSpeed);
			}
			if (CommonUtils.isNotNull(startSpeed)) {
				weeklyReport.setStartSpeed(startSpeed);
			}
			if (CommonUtils.isNotNull(endSpeed)) {
				weeklyReport.setEndSpeed(endSpeed);
			}
			if (CommonUtils.isNotNull(startGroutingAmount)) {
				weeklyReport.setStartGroutingAmount(startGroutingAmount);
			}
			if (CommonUtils.isNotNull(endGroutingAmount)) {
				weeklyReport.setEndGroutingAmount(endGroutingAmount);
			}
			if ("1".equals(saveOrUpdate)) { // 更新
				weeklyReport.setId(Long.parseLong(id));
				boolean result1 = weeklyReportService.updateObj(weeklyReport);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新周报表信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else if ("2".equals(saveOrUpdate)) { // 保存
				Long result2 = weeklyReportService.insertObj(weeklyReport);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存周报表信息出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新周报表信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;

	}

	/**
	 * 分页查找周报表记录信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 *            区间左右线
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/reportinfo/find")
	@ResponseBody
	public PageResultSet<MetroWeeklyReport> findDailyReportInfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam(value = "leftOrRight", required = false) String leftOrRight,
			@RequestParam(value = "reportTime", required = false) String reportTime,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroWeeklyReport> resultSet = weeklyReportService.findReportInfo(intervalId, leftOrRight,
				reportTime, pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 查找环报表基础数据
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param ringNum
	 *            环号
	 * @param params
	 *            参数
	 * @return
	 */
	@RequestMapping("/getRingReport")
	@ResponseBody
	public Map<String, Object> findComplicatedRingReport(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ringNum") String ringNum,
			@RequestParam(value = "params", required = false) String params) {
		List<String> paramNameList = new ArrayList<String>();
		if (params != null) {
			String[] paramArr = params.split(",");
			for (String param : paramArr) {
				paramNameList.add(param);
			}
		}
		return lineIntervalRingReportService.findRingReport(intervalId, leftOrRight, ringNum, paramNameList);
	}

	/**
	 * 查找环报表基础数据
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param ringNum
	 *            环号
	 * @param paramName
	 *            参数
	 * @return
	 */
	@RequestMapping("/getRingReport/Basic")
	@ResponseBody
	public List<List<Object>> findRingReport(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ringNum") String ringNum,
			@RequestParam(value = "paramName", required = false) String paramName) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		List<Object> ringReportList = new ArrayList<Object>();
		result.add(ringReportList);
		List<MetroLineIntervalRingReport> ringReportBasic = lineIntervalRingReportService.findRingReport(intervalId,
				leftOrRight, ringNum, paramName);
		for (MetroLineIntervalRingReport ringReport : ringReportBasic) {
			ringReportList.add(ringReport.getNumValue());
		}
		return result;
	}

	/**
	 * 查询环的时间统计和盾构机编号
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param ringNum
	 *            环号
	 * @return
	 */
	@RequestMapping("/find/ringreport/base")
	@ResponseBody
	public Map<String, Object> getStaticTime(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ringNum") String ringNum) {
		return lineIntervalRingReportService.findRingReportBase(intervalId, leftOrRight, ringNum);
	}

}
