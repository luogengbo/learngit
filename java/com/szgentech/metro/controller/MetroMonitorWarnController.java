package com.szgentech.metro.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.base.utils.SMSUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroDailyReportParam;
import com.szgentech.metro.model.MetroDept;
import com.szgentech.metro.model.MetroLineIntervalTota;
import com.szgentech.metro.model.MetroLineIntervalWarning;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.MetroSmsRec;
import com.szgentech.metro.model.MetroUser;
import com.szgentech.metro.model.MetroWeeklyReportParam;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.WarnRiskForecastEntry;
import com.szgentech.metro.service.IMetroDeptService;
import com.szgentech.metro.service.IMetroLineIntervalWarningService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroSmsRecService;
import com.szgentech.metro.service.IMetroUserService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.SessionUser;

/**
 * 监测预警控制器
 * 
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/monitor/warn")
public class MetroMonitorWarnController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MetroMonitorWarnController.class);

	@Autowired
	private IMetroWarningRecService warningRecService;

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroLineIntervalWarningService warnService;
	
	@Autowired
	private IMetroDeptService deptService;
	
	@Autowired
	private IMetroUserService userService;
	
	@Autowired
	private IMetroSmsRecService smsRecService;
	
	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	/**
	 * 监测预警主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {
		return "/find-info/monitor_warn";
	}

	@RequestMapping("/to/warn-right")
	public String toWarnRight(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -5);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		MonitorInfoCity mics = infoCityService.findIntervalMonitorInfoDatas(intervalId,leftOrRight);
		modelMap.addAttribute("cityName",mics.getCityName());
		modelMap.addAttribute("lineName",mics.getLineName());
		modelMap.addAttribute("intervalName",mics.getIntervalName());
		PageResultSet<MetroLineIntervalWarning> params = warnService.findLineIntervalWarningInfo(intervalId,
				leftOrRight, 0, 230);
		modelMap.addAttribute("wps", params != null ? params.getList() : null);
		return "/find-info/monitor_warn_right";
	}

	/**
	 * 周报表截图参数POST
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/to/warn/screenshots", method=RequestMethod.POST)
	public  String screenshotstoWeekly(@RequestBody MetroWeeklyReportParam param) {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -5);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		MonitorInfoCity mics = infoCityService.findIntervalMonitorInfoDatas(param.getIntervalId(),param.getLeftOrRight());
		modelMap.addAttribute("cityName",mics.getCityName());
		modelMap.addAttribute("lineName",mics.getLineName());
		modelMap.addAttribute("intervalName",mics.getIntervalName());
		PageResultSet<MetroLineIntervalWarning> params = warnService.findLineIntervalWarningInfo(param.getIntervalId(),
				param.getLeftOrRight(), 0, 230);
		modelMap.addAttribute("wps", params != null ? params.getList() : null);
		return "/project-info/capture_warn";
	}
	
	/**
	 * 日报表截图参数POST
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/to/warn/daily/screenshots", method=RequestMethod.POST)
	public  String screenshotstoDaily(@RequestBody MetroDailyReportParam param) {
		modelMap.addAttribute("intervalId", param.getIntervalId());
		modelMap.addAttribute("leftOrRight", param.getLeftOrRight());
		modelMap.addAttribute("saveOrUpdate", param.getSaveOrUpdate());
		modelMap.addAttribute("id", param.getId());
		modelMap.addAttribute("summary", param.getSummary());
		modelMap.addAttribute("progressStatistics", param.getProgressStatistics());
		modelMap.addAttribute("workingCondition", param.getWorkingCondition());
		modelMap.addAttribute("settlement", param.getSettlement());
		modelMap.addAttribute("settlementImg", param.getSettlementImg());
		modelMap.addAttribute("riskSituation", param.getRiskSituation());
		modelMap.addAttribute("riskSituationImg", param.getRiskSituationImg());
		modelMap.addAttribute("geology", param.getGeology());
		modelMap.addAttribute("geologyImg", param.getGeologyImg());
		modelMap.addAttribute("grouting", param.getGrouting());
		modelMap.addAttribute("groutingImg", param.getGroutingImg());
		modelMap.addAttribute("horizontalAttitude", param.getHorizontalAttitude());
		modelMap.addAttribute("horizontalAttitudeImg", param.getHorizontalAttitudeImg());
		modelMap.addAttribute("verticalAttitude", param.getVerticalAttitude());
		modelMap.addAttribute("verticalAttitudeImg", param.getVerticalAttitudeImg());
		modelMap.addAttribute("earlyWarning", param.getEarlyWarning());
		modelMap.addAttribute("earlyWarningImg", param.getEarlyWarningImg());
		modelMap.addAttribute("auditOpinion", param.getAuditOpinion());
		modelMap.addAttribute("reportTime", param.getReportTime());
		modelMap.addAttribute("operator", param.getOperator());
		modelMap.addAttribute("reviewer", param.getReviewer());
		modelMap.addAttribute("startA0004", param.getStartA0004());
		modelMap.addAttribute("endA0004", param.getEndA0004());
		modelMap.addAttribute("minA0004", param.getMinA0004());
		modelMap.addAttribute("maxA0004", param.getMaxA0004());
		modelMap.addAttribute("analysisA0004", param.getAnalysisA0004());
		modelMap.addAttribute("startA0013", param.getStartA0013());
		modelMap.addAttribute("endA0013", param.getEndA0013());
		modelMap.addAttribute("minA0013", param.getMinA0013());
		modelMap.addAttribute("maxA0013", param.getMaxA0013());
		modelMap.addAttribute("analysisA0013", param.getAnalysisA0013());
		modelMap.addAttribute("startB0001", param.getStartB0001());
		modelMap.addAttribute("endB0001", param.getEndB0001());
		modelMap.addAttribute("minB0001", param.getMinB0001());
		modelMap.addAttribute("maxB0001", param.getMaxB0001());
		modelMap.addAttribute("analysisB0001", param.getAnalysisB0001());
		modelMap.addAttribute("startB0002", param.getStartB0002());
		modelMap.addAttribute("endB0002", param.getEndB0002());
		modelMap.addAttribute("minB0002", param.getMinB0002());
		modelMap.addAttribute("maxB0002", param.getMaxB0002());
		modelMap.addAttribute("analysisB0002", param.getAnalysisB0002());
		modelMap.addAttribute("startB0004", param.getStartB0004());
		modelMap.addAttribute("endB0004", param.getEndB0004());
		modelMap.addAttribute("minB0004", param.getMinB0004());
		modelMap.addAttribute("maxB0004", param.getMaxB0004());
		modelMap.addAttribute("analysisB0004", param.getAnalysisB0004());
		modelMap.addAttribute("startB0006", param.getStartB0006());
		modelMap.addAttribute("endB0006", param.getEndB0006());
		modelMap.addAttribute("minB0006", param.getMinB0006());
		modelMap.addAttribute("maxB0006", param.getMaxB0006());
		modelMap.addAttribute("analysisB0006", param.getAnalysisB0006());
		modelMap.addAttribute("startB0015", param.getStartB0015());
		modelMap.addAttribute("endB0015", param.getEndB0015());
		modelMap.addAttribute("minB0015", param.getMinB0015());
		modelMap.addAttribute("maxB0015", param.getMaxB0015());
		modelMap.addAttribute("analysisB0015", param.getAnalysisB0015());
		modelMap.addAttribute("startR0026", param.getStartR0026());
		modelMap.addAttribute("endR0026", param.getEndR0026());
		modelMap.addAttribute("minR0026", param.getMinR0026());
		modelMap.addAttribute("maxR0026", param.getMaxR0026());
		modelMap.addAttribute("analysisR0026", param.getAnalysisR0026());
		modelMap.addAttribute("startR0028", param.getStartR0028());
		modelMap.addAttribute("endR0028", param.getEndR0028());
		modelMap.addAttribute("minR0028", param.getMinR0028());
		modelMap.addAttribute("maxR0028", param.getMaxR0028());
		modelMap.addAttribute("analysisR0028", param.getAnalysisR0028());
		modelMap.addAttribute("startR0025", param.getStartR0025());
		modelMap.addAttribute("endR0025", param.getEndR0025());
		modelMap.addAttribute("minR0025", param.getMinR0025());
		modelMap.addAttribute("maxR0025", param.getMaxR0025());
		modelMap.addAttribute("analysisR0025", param.getAnalysisR0025());
		modelMap.addAttribute("startR0004", param.getStartR0004());
		modelMap.addAttribute("endR0004", param.getEndR0004());
		modelMap.addAttribute("minR0004", param.getMinR0004());
		modelMap.addAttribute("maxR0004", param.getMaxR0004());
		modelMap.addAttribute("analysisR0004", param.getAnalysisR0004());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -5);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		MonitorInfoCity mics = infoCityService.findIntervalMonitorInfoDatas(param.getIntervalId(),param.getLeftOrRight());
		modelMap.addAttribute("cityName",mics.getCityName());
		modelMap.addAttribute("lineName",mics.getLineName());
		modelMap.addAttribute("intervalName",mics.getIntervalName());
		PageResultSet<MetroLineIntervalWarning> params = warnService.findLineIntervalWarningInfo(param.getIntervalId(),
				param.getLeftOrRight(), 0, 230);
		modelMap.addAttribute("wps", params != null ? params.getList() : null);
		return "/project-info/capture_warn2";
	}
	/**
	 * 监测预警记录分页查询
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "监测预警", operate = "查询预警记录")
	@RequestMapping("/find/warns")
	@ResponseBody
	public PageResultSet<MetroLineIntervalWarningRec> findWarnAll(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("warnParam") String warnParam,
			@RequestParam(value = "confirmLevel", required = false, defaultValue = "-1") Integer confirmLevel,
			@RequestParam(value = "isPush", required = false) Integer isPush){
		
		String beginTime = StringUtil.timeCnToEn(request.getParameter("beginTime"));
		String endTime = StringUtil.timeCnToEnAddOne(request.getParameter("endTime"));

		PageResultSet<MetroLineIntervalWarningRec> res = warningRecService.findWarningRecs(getCurrentUser().getId(),
				intervalId, leftOrRight, pageNum, pageSize, beginTime, endTime, warnParam, confirmLevel, isPush);
		if (res == null) {
			res = new PageResultSet<MetroLineIntervalWarningRec>();
			res.setPage(new Page(0, pageSize, pageNum));
			res.setList(new ArrayList<MetroLineIntervalWarningRec>());
		}
		return res;
	}

	/**
	 * 主页铃铛定时获取预警记录
	 * 
	 * @return
	 */
	@RequestMapping("/find/warns/all")
	@ResponseBody
	public ModelMap findWarnsAll() {
		ModelMap modelMap = new ModelMap();
		if(getCurrentUser().getId()!=null || !getCurrentUser().getId().equals("")){
			List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecs(getCurrentUser().getId(), null);
			modelMap.addAttribute("total", res != null && res.size() > 0 ? res.size() : 0);
			modelMap.addAttribute("res", res);
		}
		return modelMap;
	}

	@RequestMapping("/get/data/tree")
	@ResponseBody
	public List<Jstree> getDataRight() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		String[] urls = new String[4];
		urls[3] = "/monitor/warn/to/warn-right";
		Boolean[] diss = new Boolean[4];
		diss[0] = true;
		diss[1] = true;
		diss[2] = true;
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}
	
	@RequestMapping("/find/linkmanlist")
	@ResponseBody
	public List<MetroDept> getLinkmanList() {
		List<MetroDept> deptList = deptService.findAllDeptInfo();
		if(deptList == null || deptList.size() < 1){
			return new ArrayList<MetroDept>();
		}
		for (int i = 0; i < deptList.size(); i++) {
			MetroDept dept = deptList.get(i);
			List<MetroUser> userList = userService.findAllUserByDeptId(dept.getId());
			// 没有用户，移除掉该部门，不在前台展示
			if(userList == null || userList.size() < 1){
				deptList.remove(i);
				i--;
				continue;
			}
			//过滤掉所有没有手机号码的用户，添加到页面也无法发送短信，没有意义
			for (int j = 0; j < userList.size(); j++) {
				MetroUser user = userList.get(j);
				String phoneNo = user.getPhoneNo();
				if(phoneNo == null || phoneNo.length() == 0){
					userList.remove(j);
					j--;
				}
			}
			if(userList == null || userList.size() < 1 ){
				deptList.remove(i);
				i--;
			}
			dept.setUserList(userList);
		}
		return deptList;
	}
	
	/**
	 * 发送预警短信
	 * to 发送给谁（手机号码），多个用英文逗号分隔开，如: 13810001000,13810001001templateId 
	 * 短信模板ID 如：189065或189066
	 * params 短信内容需要的参数
	 * @return
	 */
	@SysControllorLog(menu = "监测预警", operate = "预警短信发送")
	@RequestMapping(value="/sendsms" , method = RequestMethod.POST)
	@ResponseBody
	public  CommonResponse sendWarnSMS(@RequestParam("to") String to, 
			@RequestParam("templateId") String templateId,
			@RequestParam("params[]") String[] params) {
		CommonResponse res = new CommonResponse();
		if(to == null || to.length() == 0){
			res.setCode(Constants.CODE_FAIL);
			res.setResult("没有收信人！");
		}
		if(templateId == null || templateId.length() == 0){
			res.setCode(Constants.CODE_FAIL);
			res.setResult("没有短信模板！");
		}
		if(params == null){
			params = new String[]{};
		}
		//发送短信请求到短信平台
		res = SMSUtil.sendTemlateSMS(to, templateId, params);
		//记录短信发送记录
		MetroSmsRec smsRec = new MetroSmsRec();
		smsRec.setSendTo(to);
		smsRec.setTemplateId(templateId);
		//smsRec.setParams(JSONObject.toJSON(params).toString());
		try {
			smsRec.setParams(new ObjectMapper().writeValueAsString(params));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("发送预警短信:"+e.getMessage());
		}
		smsRec.setStatusCode(String.valueOf(res.getCode()));
		smsRec.setStatusMsg(String.valueOf(res.getResult()==null?"":res.getResult()));
		SessionUser sessionUser = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		smsRec.setOperator(sessionUser.getId());
		smsRecService.addMetroSmsRec(smsRec);
		return res;
	}
	
	
	/**
	 * APP监测预警记录分页查询
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "监测预警", operate = "查询预警记录")
	@RequestMapping("/app/find/warns")
	@ResponseBody
	public CommonResponse findWarnAll(
			@RequestParam("userId") Long userId,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("warnParam") String warnParam,
			@RequestParam("beginTimeL") String beginTimel,
			@RequestParam("endTimeL") String endTimel,
			@RequestParam(value = "confirmLevel", required = false) Integer confirmLevel,
			@RequestParam(value = "isPush", required = false) Integer isPush) {
		CommonResponse commonResponse = new CommonResponse();
		String beginTime;//开始的时间
		String endTime;//结束 的时间
		// 判断是否传入的有时间参数，如果为空就自己构造一个时间（时间范围是前三天）
		if (beginTimel == null || endTimel == null || beginTimel == "" || endTimel == "" || beginTimel.isEmpty()
				|| endTimel.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			Date date = new Date();
			Date date2 = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -3);
			date = calendar.getTime();
			String aa = dateFormat.format(date);
			String bb = dateFormat.format(date2);
			beginTime = StringUtil.timeCnToEn(aa);// 开始的时间
			endTime = StringUtil.timeCnToEnAddOne(bb);// 结束 的时间
		} else {
			beginTime = StringUtil.timeCnToEn(beginTimel);
			endTime = StringUtil.timeCnToEnAddOne(endTimel);
		}

		PageResultSet<MetroLineIntervalWarningRec> res = warningRecService.findWarningRecs(userId,
				intervalId, leftOrRight, pageNum, pageSize, beginTime, endTime, warnParam, confirmLevel, isPush);
		if (res == null) {
			res = new PageResultSet<MetroLineIntervalWarningRec>();
			res.setPage(new Page(0, pageSize, pageNum));
			res.setList(new ArrayList<MetroLineIntervalWarningRec>());
			commonResponse.setResult(res);
			commonResponse.setCode(500);
		}
		
		if(res!=null) {
			commonResponse.setResult(res);
			commonResponse.setCode(200);
		}
		
		return commonResponse;
	}
	
	
	@RequestMapping("/app/find/riskForecast")
	@ResponseBody
	public WarnRiskForecastEntry findWarnRiskForecast(@RequestParam("userId")Integer userId,@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		WarnRiskForecastEntry entry = new WarnRiskForecastEntry();
		entry.setCode(500);
		//entry = warningRecService.findWarnRiskForecast(userId, pageNum, pageSize);
		return entry;
	}
	
	/**
	 * 更新是否已人工确认预警等级或是否推送
	 */
	@SysControllorLog(menu = "监测预警记录管理", operate = "更新是否已人工确认预警等级或是否推送")
	@RequestMapping(value = "/editConfirmInfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse editConfirmInfo(
			@RequestParam("id") Long id,
			@RequestParam(value = "confirmLevel", required = false) Integer confirmLevel,
			@RequestParam(value = "isPush", required = false) Integer isPush,
			@RequestParam(value = "warningLevel", required = false) Integer warningLevel) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = warningRecService.editConfirmInfo(id, confirmLevel, isPush, warningLevel);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新是否已人工确认预警等级或是否推送出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新是否已人工确认预警等级或是否推送异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}
	
	/**
	 * 删除预警记录信息
	 */
	@SysControllorLog(menu = "监测预警记录管理", operate = "删除预警记录")
	@RequestMapping(value = "/warnrecinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSainfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = warningRecService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除预警记录信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除预警记录信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	
	/**
	 * 监测预警记录分析统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/warns/total")
	@ResponseBody
	public MetroLineIntervalTota findWarningTotal(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftorright") String leftorright, String starttime, String endtime) {
		MetroLineIntervalTota res = new MetroLineIntervalTota();
		if (intervalId != null || leftorright != null || intervalId.equals("") || leftorright.equals("")) {
			res = warningRecService.findWarningTotal(intervalId, leftorright, starttime, endtime);
		}

		return res;
	}
}
