package com.szgentech.metro.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.Page;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.SerializeUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroLineIntervalPp;
import com.szgentech.metro.model.MetroLineIntervalSp;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.service.IMetroLineIntervalPpService;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.service.IMetroLineIntervalSpService;
import com.szgentech.metro.service.IMetroLineService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroTubeMapService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.vo.IhistorianResponse;
import com.szgentech.metro.vo.MonitorViewData;
import com.szgentech.metro.vo.SessionUser;
import com.szgentech.metro.vo.TubeMapIntervalLr;

@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {
	private static Logger logger = Logger.getLogger(SerializeUtil.class);
	@Autowired
	private IMetroLineService lineService;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@Autowired
	private IMetroWarningRecService warningRecService;
	
	@Autowired
	private IMetroTubeMapService tmService;
	
	@Autowired
	private IMetroLineIntervalSpService spService;
	
	@Autowired
	private IMetroLineIntervalService intervalService;
	
	@Autowired
	private IMetroLineIntervalPpService lineIntervalPpService;

	
	/**
	 * 刀盘螺旋导向基础数据
	 * 
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线,"l":左线，"r":右线
	 * @return
	 */
	@RequestMapping(value="/data/now",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMonitorDataNow(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight, null);
		map.put("res", res);
		return map;
	}
	
	/**
	 * 查找线路数据
	 * 
	 * @param intervalId 区间ID
	 * @return 线路信息
	 */
	@RequestMapping(value="/interval/datas",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, TubeMapIntervalLr> findUserDatasByIntervalId(
			@RequestParam("intervalId") Long intervalId) {
		TubeMapIntervalLr tm = tmService.findLrInfo(intervalId);
		Map<String, TubeMapIntervalLr> map = new HashMap<String, TubeMapIntervalLr>();
		map.put("data", tm);

		return map;
	}
	
	/**
	 * 刀盘SVG图形数据
	 * 
	 * @param intervalId  区间ID
	 * @param leftOrRight 左右线,"l":左线，"r":右线
	 * @return
	 */
	@RequestMapping(value="/knife/data", method=RequestMethod.GET)
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
	 * 泥水SVG图形数据
	 * 
	 * @param intervalId  区间ID
	 * @param leftOrRight 左右线,"l":左线，"r":右线
	 * @return
	 */
	@RequestMapping(value="/slurry/data", method=RequestMethod.GET)
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
	 * 导向当前盾位置
	 * 
	 * @param intervalId  区间ID
	 * @param leftOrRight 左右线,"l":左线，"r":右线
	 * @return
	 */
	@RequestMapping(value="/guide/data", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getLrMonitorDatas(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		Map<String, Object> map = infoCityService.findIntervalLrDaoxDatas(intervalId, leftOrRight);
		return map;
	}
	
	/**
	 * 线路剖面环数里程信息
	 * @param line  如：GZ8_9L GZ   广州缩写  8：线路编号  9：工程标号  L左R右
	 * @return
	 */
	@RequestMapping(value = "/line/monitor/datas", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findMonitorLineDatas(String line) {
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
	 * 区间信息主页（平面图）剖面图左右线数据
	 * 
	 * @param intervalId  区间ID
	 * @param type  类型，1：平面图,2:左线剖面图，3右线剖面图
	 * @param goNumAll
	 * @param pingLorR
	 * @return
	 */
	@RequestMapping(value="/to/area/project", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> projectProccse(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("type") int type,
			@RequestParam(value = "goNumA", required = false, defaultValue = "0") int goNumAll,
			@RequestParam(value = "pingLorR", required = false, defaultValue = "") String pingLorR) {
		if (goNumAll < 0) {
			goNumAll = 0;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MetroLineInterval interval = intervalService.findObjById(intervalId);
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(intervalId);
		resultMap.put("pp", pp);
		List<MetroLineIntervalLr> lrList = interval.getIntervalLrList();
		String PARAM_RING_NUM = "A0001"; // 当前环号
		MetroLine line = lineService.findObjById(interval.getLineId());
		List<String> keys = new ArrayList<String>();
		String lRingNumkey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l", PARAM_RING_NUM);
		String rRingNumkey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r", PARAM_RING_NUM);
		keys.add(lRingNumkey);
		keys.add(rRingNumkey);
		// 获取该路线左右线具体的环数
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil
				.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			resultMap.put("lRingNum", lRingNum);
			resultMap.put("lCountRing",
					result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("count")));
			resultMap.put("rRingNum", rRingNum);
			resultMap.put("rCountRing",
					result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("count")));
		}

		if (CommonUtils.isNotNull(lrList)) {
			resultMap.put("lr_l", lrList.get(0));
			if (lrList.get(0).getRingNum() != null && lrList.get(0).getRingNum() > 0) {
				resultMap.put("lvl", StringUtil.nullToDouble(lRingNum) / lrList.get(0).getRingNum());
				resultMap.put("lvlz", StringUtil.nullToDouble(lRingNum) / lrList.get(0).getRingNum() * 100);
			} else {
				resultMap.put("lvl", 0);
				resultMap.put("lvlz", 0);
			}
			if (lrList.size() > 1) {
				resultMap.put("lr_r", lrList.get(1));
				if (lrList.get(1).getRingNum() != null && lrList.get(1).getRingNum() > 0) {
					resultMap.put("rvr", StringUtil.nullToDouble(rRingNum) / lrList.get(1).getRingNum());
					resultMap.put("rvrz", StringUtil.nullToDouble(rRingNum) / lrList.get(1).getRingNum() * 100);
				} else {
					resultMap.put("rvr", 0);
					resultMap.put("rvrz", 0);
				}
			}
		}
		String lLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l");
		String rLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r");
		resultMap.put("lLinekey", lLinekey);
		resultMap.put("rLinekey", rLinekey);
		resultMap.put("intervalId", intervalId);
		Date date = new Date();
		resultMap.put("lSearchTime", date);
		resultMap.put("rSearchTime", date);
		if (type == 1) {
			// 获取沉降点数据
			List<MetroLineIntervalSp> metroLineIntervalSpList = spService.findLineIntervalSps(intervalId);
			resultMap.put("intervalSpList", metroLineIntervalSpList);
			if (pingLorR.equals("l")) {
				resultMap.put("pingLorR", pingLorR);
			} else if (pingLorR.equals("r")) {
				resultMap.put("pingLorR", pingLorR);
			}
			if (goNumAll != 0) {
				resultMap.put("goNumAll", goNumAll);
			}
		} else if (type == 2) {
			if (goNumAll != 0) {
				resultMap.put("goNumAll", goNumAll);
			}
		} else if (type == 3) {
			if (goNumAll != 0) {
				resultMap.put("goNumAll", goNumAll);
			}
		}
		return resultMap;
	}
	
	/**
	 * 城市数据监测， 根据传入施工状态查找城市数据监测施工信息
	 * @param cityId       城市编号
	 * @param buildStatus  工程状态 -1全部, 0未施工, 1正施工， 2已贯通
	 * @param intervalName 区间名字
	 * @param pageNum      当前页码
	 * @param pageSize     每页记录数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/city/datas", method=RequestMethod.GET)
	@ResponseBody
	public CommonResponse findMonitorCityAll(
			@RequestParam("cityId") Long cityId,
			@RequestParam("buildStatus") Integer buildStatus,
			@RequestParam(value="intervalName",required=false) String intervalName,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		CommonResponse commonResponse = new CommonResponse();
		SessionUser user = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(cityId, null, buildStatus, intervalName,
				pageNum, pageSize,user.getId());
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
	 * 线路数据监测，根据传入施工状态查找城市线路数据监测施工信息
	 * @param lineId      线路ID
	 * @param buildStatus 工程状态 -1全部, 0未施工, 1正施工， 2已贯通
	 * @param pageNum     当前页码
	 * @param pageSize    每页记录数
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/line/datas", method=RequestMethod.GET)
	@ResponseBody
	public CommonResponse findMonitorLineAll
			(@RequestParam("lineId") Long lineId,
			@RequestParam("buildStatus") int buildStatus, 
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		CommonResponse commonResponse = new CommonResponse();
		SessionUser user = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(null, lineId, buildStatus, null,
				pageNum, pageSize,user.getId());
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
	 * 城市信息主页盾构机数量统计接口
	 * @param cityId 城市编号
	 * @return
	 */
	@RequestMapping(value="/city/machine", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> cityMain(
			@RequestParam("cityId") String cityId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("cityId", cityId);
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), null);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			resultMap.put("total0", total0);
			resultMap.put("total1", total1);
			resultMap.put("total2", total2);
			resultMap.put("totalSuccess", totalSuccess);
			resultMap.put("totalFail", totalFail);
			resultMap.put("totalm", total1 + total2);
		}
		return resultMap;
	}
	/**
	 * 线路信息主页,线路盾构机数量统计接口
	 * @param lineId  线路ID
	 * @return
	 */
	@RequestMapping(value="/line/machine", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> lineMain(
			@RequestParam("lineId") Long lineId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lineId", lineId);
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), lineId);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			resultMap.put("total0", total0);
			resultMap.put("total1", total1);
			resultMap.put("total2", total2);
			resultMap.put("totalSuccess", totalSuccess);
			resultMap.put("totalFail", totalFail);
			resultMap.put("totalm", total1 + total2);
		}
		return resultMap;
	}
	
	/**
	 * 监测预警记录分页查询
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线,"l":左线，"r":右线
	 * @param pageSize     每页返回记录数
	 * @param pageNum      当前页
	 * @param warnParam    预警参数，如B0001
	 * @param confirmLevel 确认等级
	 * @param isPush       是否推送短信
	 * @return
	 */
	@RequestMapping(value="/find/warns" ,method=RequestMethod.GET)
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

}


