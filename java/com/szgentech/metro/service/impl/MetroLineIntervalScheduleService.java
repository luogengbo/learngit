package com.szgentech.metro.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.DateUtil;
import com.szgentech.metro.dao.IMetroLineIntervalScheduleDao;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.MetroLineIntervalSchedule;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroLineIntervalScheduleService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;

/**
 * 线路区间进度计划业务接口实现
 * 
 * @author luohao
 *
 */
@Service("lineIntervalScheduleService")
public class MetroLineIntervalScheduleService extends BaseService<MetroLineIntervalSchedule>
		implements IMetroLineIntervalScheduleService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalScheduleService.class);
	@Autowired
	private IMetroLineIntervalScheduleDao lineIntervalScheduleDao;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@Autowired
	private IMetroMonitorCityDao monitorCityDao;
	
	/**
	 * 分页查询 区间计划进度
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param leftOrRight
	 *            左右线
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalSchedule> findPageScheduleInfo(Long intervalId, String leftOrRight,
			String startDateStr, String endDateStr, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("startDateStr", startDateStr);
		params.put("endDateStr", endDateStr);
		PageResultSet<MetroLineIntervalSchedule> resultSet = getPageResultSet(params, pageNum, pageSize,
				lineIntervalScheduleDao);
		return resultSet;
	}


	/**
	 * 按区间左右线查找左右线计划进度数据
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param leftOrRight
	 *            左右线
	 * @param startDateStr
	 *            开始日期
	 * @param endDateStr
	 *            结束日期
	 * @param cycleType
	 *            周期类型
	 * @isFill 未设置计划进度是否填充空计划（计划进度0环）对象
	 * @return
	 */
	@Override
	public List<MetroLineIntervalSchedule> findIntervalLrSchedule(Long intervalId, String leftOrRight,
			String startDateStr, String endDateStr, String cycleType, boolean isFill) {
		leftOrRight = "".equals(leftOrRight) ? null : leftOrRight;
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("startDateStr", startDateStr);
		params.put("endDateStr", endDateStr);
		List<MetroLineIntervalSchedule> resultList = new ArrayList<MetroLineIntervalSchedule>();
		if (Constants.CYCLE_TYPE_MONTHLY.equals(cycleType)) {
			resultList = lineIntervalScheduleDao.findIntervalLrMonthlySchedule(params);
		} else if (Constants.CYCLE_TYPE_WEEKLY.equals(cycleType)) {
			resultList = getWeeklySchedule(intervalId, leftOrRight, startDateStr, endDateStr, isFill);
		} else {
			resultList = lineIntervalScheduleDao.findIntervalLrDailySchedule(params);
		}
		if (isFill
				&& (Constants.CYCLE_TYPE_MONTHLY.equals(cycleType) || Constants.CYCLE_TYPE_DAILY.equals(cycleType))) {
			resultList = fillNullSchedule(resultList, intervalId, leftOrRight, startDateStr, endDateStr, cycleType);
		}
		return resultList;
	}

	/**
	 * 删除区间线路计划进度
	 * 
	 * @param scheduleId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long scheduleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("scheduleId", scheduleId);
		int r = lineIntervalScheduleDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 保存计划进度 将用户输入计划拆分成每一天的进度
	 */
	@Override
	public CommonResponse saveSchedule(Long intervalId, String leftOrRight, String startDateStr, String endDateStr,
			int scheduleRingNum) {
		CommonResponse res = new CommonResponse();
		if (!CommonUtils.isNotNull(intervalId) || !CommonUtils.isNotNull(leftOrRight)
				|| !CommonUtils.isNotNull(startDateStr) || !CommonUtils.isNotNull(endDateStr)) {
			res.setCode(Constants.CODE_FAIL);
			res.setResult("参数intervalId、leftOrRight、startDateStr、endDateStr不能为空！");
			return res;
		}
		try {
			List<Map<String, Object>> cycleList = CommonUtils.getCycleDateList(startDateStr, endDateStr,
					Constants.CYCLE_TYPE_DAILY);
			if (cycleList == null || cycleList.size() < 1) {
				res.setCode(Constants.CODE_FAIL);
				res.setResult("保存出错！拆分周期出现错误。");
				return res;
			}
			// 判断哪些需要更新，哪些需要新增
			// 查找原来所有计划进度并做对比
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("intervalId", intervalId);
			params.put("leftOrRight", leftOrRight);
			List<MetroLineIntervalSchedule> oriList = lineIntervalScheduleDao.findSchedule(params);
			Map<String, MetroLineIntervalSchedule> scheduleMapper = getScheduleMapper(oriList);
			List<MetroLineIntervalSchedule> addScheduleList = new ArrayList<MetroLineIntervalSchedule>();
			List<MetroLineIntervalSchedule> updateScheduleList = new ArrayList<MetroLineIntervalSchedule>();
			for (Map<String, Object> map : cycleList) {
				MetroLineIntervalSchedule schedule = new MetroLineIntervalSchedule();
				schedule.setIntervalId(intervalId);
				schedule.setLeftOrRight(leftOrRight);
				Date scheduleDate = (Date) map.get("cycleDate");
				String cycleType = (String) map.get("cycleType");
				schedule.setScheduleDate(scheduleDate);
				schedule.setScheduleRingNum(scheduleRingNum);
				// 判断是新增还是修改
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String scheduleDateStr = format.format(scheduleDate);
				String key = "" + intervalId + "#" + leftOrRight + "#" + scheduleDateStr + "#" + cycleType;
				MetroLineIntervalSchedule oriSchedule = scheduleMapper.get(key);
				if (oriSchedule == null) {
					addScheduleList.add(schedule);
				} else {
					oriSchedule.setScheduleRingNum(scheduleRingNum);
					updateScheduleList.add(oriSchedule);
				}
			}
			if (addScheduleList != null && addScheduleList.size() > 0) {
				lineIntervalScheduleDao.addScheduleBatch(addScheduleList);
			}
			if (updateScheduleList != null && updateScheduleList.size() > 0) {
				for (MetroLineIntervalSchedule updateSchedule : updateScheduleList) {
					Map<String, Object> updateParam = new HashMap<String, Object>();
					updateParam.put("id", updateSchedule.getId());
					updateParam.put("scheduleRingNum", updateSchedule.getScheduleRingNum());
					lineIntervalScheduleDao.updateObj(updateParam);
				}
			}
			res.setCode(Constants.CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存或更新异常:"+e.getMessage());
			res.setCode(Constants.CODE_FAIL);
			res.setResult("保存或更新异常");
		}
		return res;
	}

	/**
	 * 将计划列表转成Map映射器
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, MetroLineIntervalSchedule> getScheduleMapper(List<MetroLineIntervalSchedule> list) {
		Map<String, MetroLineIntervalSchedule> mapper = new HashMap<String, MetroLineIntervalSchedule>();
		if (list == null || list.size() < 1) {
			return mapper;
		}
		for (MetroLineIntervalSchedule schedule : list) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String scheduleDateStr = format.format(schedule.getScheduleDate());
			String key = "" + schedule.getIntervalId() + "#" + schedule.getLeftOrRight() + "#" + scheduleDateStr + "#"
					+ schedule.getCycleType();
			mapper.put(key, schedule);
		}
		return mapper;
	}

	/**
	 * 获取每周计划进度
	 * 
	 * @param intervalId
	 *            区间
	 * @param leftOrRight
	 *            左右线
	 * @param startDateStr
	 *            开始日期
	 * @param endDateStr
	 *            结束日期
	 * @param isFill
	 *            未设置计划进度是否填充空对象
	 * @return
	 */
	private List<MetroLineIntervalSchedule> getWeeklySchedule(Long intervalId, String leftOrRight, String startDateStr,
			String endDateStr, boolean isFill) {
		List<MetroLineIntervalSchedule> scheduleList = new ArrayList<MetroLineIntervalSchedule>();
		// 根据区间、左右线、开始时间、结束时间获取天每天的计划
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("startDateStr", startDateStr);
		params.put("endDateStr", endDateStr);
		List<MetroLineIntervalSchedule> dailyScheduleList = new ArrayList<MetroLineIntervalSchedule>();
		if (CommonUtils.isNotNull(leftOrRight)) {
			params.put("leftOrRight", leftOrRight);
			dailyScheduleList = lineIntervalScheduleDao.findIntervalLrDailySchedule(params);
		} 
		if (dailyScheduleList == null || dailyScheduleList.size() < 1) {
			return scheduleList;
		}
		// 将日期拆分成每周的时间列表
		List<Map<String, Object>> cycleList = CommonUtils.getCycleDateList(startDateStr, endDateStr,
				Constants.CYCLE_TYPE_WEEKLY);
		// 匹配将每一周的计划累积
		for (Map<String, Object> cycle : cycleList) {
			MetroLineIntervalSchedule weeklySchedule = new MetroLineIntervalSchedule();
			weeklySchedule.setIntervalId(intervalId);
			weeklySchedule.setLeftOrRight(leftOrRight);
			weeklySchedule.setCycleType(Constants.CYCLE_TYPE_WEEKLY);
			Date beginDate = (Date) cycle.get("beginDate");
			Date endDate = (Date) cycle.get("endDate");
			weeklySchedule.setScheduleDate(beginDate);
			weeklySchedule.setCycleTitle(cycle.get("cycleTitle").toString());
			int totalNum = 0;
			boolean haveFlag = false;
			for (MetroLineIntervalSchedule dailySchedule : dailyScheduleList) {
				Date date = dailySchedule.getScheduleDate();
				// 日期不落入本周开始时间和结束时间的跳过
				if (date.before(beginDate) || date.after(endDate)) {
					continue;
				}
				// 没有计划值的跳过
				if (dailySchedule.getScheduleRingNum() == null) {
					continue;
				}
				haveFlag = true;
				totalNum += dailySchedule.getScheduleRingNum();
			}
			// 本周有计划
			if (haveFlag || isFill) {
				weeklySchedule.setScheduleRingNum(totalNum);
				scheduleList.add(weeklySchedule);
			}
		}
		return scheduleList;
	}

	/**
	 * 未设置进度的周期填充进度为0的进度对象
	 * 
	 * @param scheduleList
	 * @param intervalId
	 * @param leftOrRight
	 * @param startDateStr
	 * @param endDateStr
	 * @param cycleType
	 * @return
	 */
	private List<MetroLineIntervalSchedule> fillNullSchedule(List<MetroLineIntervalSchedule> scheduleList,
			Long intervalId, String leftOrRight, String startDateStr, String endDateStr, String cycleType) {
		List<MetroLineIntervalSchedule> resultList = new ArrayList<MetroLineIntervalSchedule>();
		List<Map<String, Object>> cycleList = CommonUtils.getCycleDateList(startDateStr, endDateStr, cycleType);
		Map<String, MetroLineIntervalSchedule> scheduleMapper = getScheduleMapper(scheduleList);
		for (Map<String, Object> cycleMap : cycleList) {
 			Date scheduleDate = (Date) cycleMap.get("beginDate");
			Calendar c = Calendar.getInstance();
			c.setTime(scheduleDate);
			if(Constants.CYCLE_TYPE_MONTHLY.equals(cycleType)){
				c.set(Calendar.DAY_OF_MONTH, 1);
			}
			Date firstDate = c.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String scheduleDateStr = format.format(firstDate);
			String key = "" + intervalId + "#" + leftOrRight + "#" + scheduleDateStr + "#" + cycleType;
			if (scheduleMapper.get(key) == null) {
				MetroLineIntervalSchedule schedule = new MetroLineIntervalSchedule();
				resultList.add(schedule);
				schedule.setIntervalId(intervalId);
				schedule.setLeftOrRight(leftOrRight);
				schedule.setScheduleDate(scheduleDate);
				schedule.setScheduleRingNum(0);
				schedule.setCycleTitle(String.valueOf(cycleMap.get("cycleTitle")));
			} else {
				MetroLineIntervalSchedule schedule1 = scheduleMapper.get(key);
				schedule1.setCycleTitle(String.valueOf(cycleMap.get("cycleTitle")));
				resultList.add(schedule1);
			}
		}
		return resultList;
	}

	/**
	 * 取区间左右线某时间段，某周期类型的进度（计划进度、实际进度）
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginDate
	 * @param endDate
	 * @param cycleType
	 * @return
	 */
	public List<List<Object>> getIntervalLrSchedule(Long intervalId, String leftOrRight, Date beginDate, Date endDate,
			String cycleType) {
		List<List<Object>> result = new ArrayList<List<Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = sdf.format(beginDate);
		String endDateStr = sdf.format(endDate);
		// 拆分周期列表
		List<Map<String, Object>> cycleList = CommonUtils.getCycleDateList(startDateStr, endDateStr, cycleType);
		List<Object> timeList = new ArrayList<Object>();
		result.add(timeList);
		for (Map<String, Object> cycleMap : cycleList) {
			timeList.add(String.valueOf(cycleMap.get("cycleTitle")));
		}
		// 查询计划进度
		List<Object> schRingNumList = new ArrayList<Object>();
		result.add(schRingNumList);
		List<MetroLineIntervalSchedule> scheduleList = findIntervalLrSchedule(intervalId, leftOrRight, startDateStr, endDateStr, cycleType, true);
		for (MetroLineIntervalSchedule schedule : scheduleList) {
			schRingNumList.add(schedule.getScheduleRingNum() == null ? 0 : schedule.getScheduleRingNum());
		}
		// 获取实际进度(进度是每天的实际进度需要转化为不同周期类型的统计数据)
		List<Object> actualList = infoCityService.getActualProgress(intervalId, leftOrRight, beginDate, endDate);
		if (Constants.CYCLE_TYPE_DAILY.equals(cycleType)) {
			result.add(actualList);
		} else {
			// 统计周期内的实际进度，周期内开始时间到结束时间的实际进度累加
			result.add(statisticsActualRingNumList(cycleList, actualList, startDateStr, endDateStr));
		}
		return result;
	}

	/**
	 * 统计周期内的实际进度，周期内开始时间到结束时间的实际进度累加
	 * 
	 * @param cycleList
	 *            周期列表
	 * @param actualList
	 *            每天的实际进度
	 * @param startDateStr
	 *            开始时间
	 * @param endDateStr
	 *            结束时间
	 * @return
	 */
	public List<Object> statisticsActualRingNumList(List<Map<String, Object>> cycleList, List<Object> actualList,
			String startDateStr, String endDateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> actualRingNumList = new ArrayList<Object>();
		// 获取按天的周期列表
		List<Map<String, Object>> dailyList = CommonUtils.getCycleDateList(startDateStr, endDateStr,
				Constants.CYCLE_TYPE_DAILY);
		// 将实际进度按天转成Mapper映射器并将每天的实际进度放入按天的周期对象内
		Map<String, Map<String, Object>> mapper = new HashMap<String, Map<String, Object>>();
		for (int i = 0; i < dailyList.size(); i++) {
			Map<String, Object> dailyMap = dailyList.get(i);
			if (i < actualList.size()) {
				dailyMap.put("actualRingNum", actualList.get(i));
			} else {
				dailyMap.put("actualRingNum", 0);
			}
			mapper.put(sdf.format((Date) dailyMap.get("cycleDate")), dailyMap);
		}
		for (Map<String, Object> cycleMap : cycleList) {
			int totalRingNum = 0;
			Date cycleBeginDate = (Date) cycleMap.get("beginDate");
			Date cycleEndDate = (Date) cycleMap.get("endDate");
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(cycleBeginDate);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(cycleEndDate);
			long now = 0l;
			// 统计本周期内实际环数（从周期内开始时间到结束时间，每天的实际进度环数累加）
			while (now <= endCal.getTimeInMillis()) {
				String key = sdf.format(beginCal.getTime());
				Map<String, Object> curDayMap = mapper.get(key);
				if (curDayMap != null) {
					totalRingNum += (int) curDayMap.get("actualRingNum");
				}
				now = beginCal.getTimeInMillis();
				beginCal.add(Calendar.DATE, 1);
			}
			actualRingNumList.add(totalRingNum);
		}
		return actualRingNumList;
	}


	/**
	 * 获取区间、左右线进度的基础信息
	 * 
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public Map<String, Object> getIntervalLrBase(Long intervalId, String leftOrRight) {
		Map<String, Object> baseMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		// 查询项目总长
		MonitorInfoCity moi = monitorCityDao.findMonitorInfoCity(params);
		if(moi == null){
			return baseMap;
		}
		int totalRingNum = moi.getRingNum()==null?0:moi.getRingNum().intValue();
		baseMap.put("totalRingNum", totalRingNum);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//始发日期
		Date buildDate = moi.getBuildDate();
		baseMap.put("buildDate", buildDate);
		//日掘进速度合格范围
		int speedQualifiedRange = moi.getSpeedQualifiedRange()==null?0:moi.getSpeedQualifiedRange().intValue();
		baseMap.put("speedQualifiedRange", speedQualifiedRange);
		// 获取已推进环数即当前环号
		int currentRingNum= infoCityService.findCurrRingNum(intervalId, leftOrRight);
		baseMap.put("currentRingNum", currentRingNum);
		// 取当前时间作为统计截止时间
		baseMap.put("currentTime", format.format(new Date()));
		// 取7天内平均掘进速度
		Float fAvgWeekRingNum = getWeekAvgRingNum(intervalId, leftOrRight);
		baseMap.put("weekAvgNum", fAvgWeekRingNum);
		// 计算预计完工时间及日期（剩余未推进环数/七日内平均掘进速度并向上取整）
		Float fFinishDays= 0f;
		if(fAvgWeekRingNum != 0f){
			fFinishDays = (totalRingNum-currentRingNum)/fAvgWeekRingNum;
		}
		int finishDay = fFinishDays == 0f? 0:fFinishDays.intValue()+1;
		baseMap.put("finishDays", finishDay);
		Calendar finishCal = Calendar.getInstance();
		finishCal.add(Calendar.DATE, finishDay);
		baseMap.put("finishDate", format.format(finishCal.getTime()));
		// 获取计划日掘进速度（用已设置计划进度平均）
		int totalSchedule = 0;
		List<MetroLineIntervalSchedule> scheduleList = lineIntervalScheduleDao.findSchedule(params);
		for (MetroLineIntervalSchedule schedule : scheduleList) {
			totalSchedule +=  schedule.getScheduleRingNum()== null?0:schedule.getScheduleRingNum();
		}
		Float scheduleSpeed = 0f;
		if(scheduleList.size() != 0){
			scheduleSpeed = (float) (totalSchedule/scheduleList.size());
		}
		baseMap.put("scheduleSpeed", scheduleSpeed);
		return baseMap;
	}
	
	/**
	 * 取7天内平均掘进速度
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	private Float getWeekAvgRingNum(Long intervalId, String leftOrRight){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beginCal = Calendar.getInstance();
		beginCal.setTime(DateUtil.formatDate(format.format(new Date()), "yyyy-MM-dd"));//当天去掉时分秒
		beginCal.add(Calendar.DATE, -7);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(DateUtil.formatDate(format.format(new Date()), "yyyy-MM-dd"));//当天去掉时分秒
		endCal.add(Calendar.DATE, -1);
		List<Object> weeklyProgress = infoCityService.getActualProgress(intervalId, leftOrRight, beginCal.getTime(), endCal.getTime());
		// 7天实际进度总和
		Float fSumNum = 0f;
		for (int i = 0; i < weeklyProgress.size(); i++) {
			fSumNum += (int) weeklyProgress.get(i);
		}
		// 七天平均值
		Float fAvgWeekRingNum = weeklyProgress.size() == 0 ? 0f:(fSumNum/weeklyProgress.size());
		return fAvgWeekRingNum;
	}
}
