package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.dao.IMetroDictionaryDao;
import com.szgentech.metro.dao.IMetroLineIntervalRingReportDao;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.model.MetroLineIntervalRingReport;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.model.RingStatistics;
import com.szgentech.metro.service.IMetroLineIntervalRingReportService;
import com.szgentech.metro.vo.IhistorianResponse;

/**
 * 环报表业务接口实现
 * 
 * @author luohao
 *
 */
@Service("lineIntervalRingReportService")
public class MetroLineIntervalRingReportService extends BaseService<MetroLineIntervalRingReport>
		implements IMetroLineIntervalRingReportService {

private static String[] defaultParamNames = {
		"A0004", "A0005", "A0007", "A0009", "A0011", "A0013",
		"B0001", "B0002", "B0003", "B0004", "B0006", "B0011", "B0012", 
		"C0009", "C0013", "C0011", "C0015", "C0009-C0013", "C0015-C0011",
		"C0001", "C0005", "C0007", "C0003", "C0001-C0005", "C0007-C0003",
		"D0001", "D0002", "D0003", "D0004","D0016", "D0018", "D0020", "D0022","D0023",
		"E0002", "E0004", "E0006", "E0008","E0010", "E0012", "E0014", "E0016",
		"F0002", "F0001", "E0003",
		"G0002", "G0003", "G0004", "G0006", "G0007", "G0008", "G0010", "G0011", "G0012", "G0014", "G0015", "G0016",
		"J0020", "J0021", "J0024", "J0025", 
		"H0002", "H0006", "H0040", "H0041", "H0043",
	};

	private static final String A0003 = "A0003";

	@Autowired
	private IMetroMonitorCityDao monitorCityDao;
	
	@Autowired
	private IMetroLineIntervalRingReportDao lineIntervalRingReportDao;
	
	@Autowired
	private IMetroDictionaryDao dicDao;
	
	/**
	 * 查询制定区间、左右线、环号和指定参数的环报表数据， 不指定参数取默认需要所有参数
	 * @param intervalId    区间
	 * @param leftOrRight   左右线
	 * @param ringNum       环号
	 * @param paramNameList 参数列表
	 * @return
	 */
	public Map<String, Object> findRingReport(Long intervalId, String leftOrRight, String ringNum,
			List<String> paramNameList) {
		if (paramNameList == null || paramNameList.size() < 1) {
			paramNameList = Arrays.asList(defaultParamNames);
		}
		// 1.1 将单参数与相减参数分开
		List<String> singleList = new ArrayList<String>();
		List<String> reduceList = new ArrayList<String>();
		spiltParams(paramNameList, singleList, reduceList);
		//1.2  固定加A0003参数进去
		if(!singleList.contains(A0003)){
			singleList.add(A0003);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		// 2.1 查询单数环报表基础数据
		params.put("paramNames", singleList);
		List<MetroLineIntervalRingReport> singleBaseList = lineIntervalRingReportDao.findRingReport(params);
		// 2.2 根据参数对结果分组
		Map<String, List<MetroLineIntervalRingReport>> singleBaseMapper = splitReportGrouByParam(singleBaseList);
		// 2.3  查询单参数环报表统计数据
		List<RingStatistics> singleStatisticsList = lineIntervalRingReportDao.findRingStatistics(params);
		Map<String, RingStatistics> singleStatisticsMapper = splitStatisticsGrouByParam(singleStatisticsList);
		// 3.1 查询相减参数环报表基础数据
		Map<String, List<MetroLineIntervalRingReport>> reduceBaseMapper = getReduceReportData(intervalId, leftOrRight,
				ringNum, reduceList);
		// 3.2  获取查询相减参数环报表统计数据
		Map<String, RingStatistics> reduceStatisticsMapper = getReduceStatisticsMapper(reduceBaseMapper);
		//4 构建需要的结果数据
		Map<String, Object> result = new HashMap<String, Object>();
		//4.1 查询所有参数标签信息
		List<String> totalParamList = getTotalParamList(singleList, reduceList);
		List<MetroDictionary> dictList = dicDao.findDictionaryList(totalParamList);
		Map<String, MetroDictionary> dictMapper = new HashMap<String, MetroDictionary>();
		for (MetroDictionary dict:dictList) {
			dictMapper.put(dict.getDicName(), dict);
		}
		//4.2 合并单参数、相减参数的基础数据和统计数据
		Map<String, List<MetroLineIntervalRingReport>> baseReportMapper = singleBaseMapper;
		baseReportMapper.putAll(reduceBaseMapper);
		Map<String, RingStatistics> statisticsMapper = singleStatisticsMapper;
		statisticsMapper.putAll(reduceStatisticsMapper);
		//4.3 取A0003列表值
		result.put("keyItem",
				buildParamItem(baseReportMapper.get(A0003), statisticsMapper.get(A0003), A0003, dictMapper));
		//4.4  构建环报表输出每个参数数据
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (String paramName : paramNameList) {
			items.add(buildParamItem(baseReportMapper.get(paramName), statisticsMapper.get(paramName), paramName,
					dictMapper));
		}
		result.put("items", items);
		return result;
	}
	
	/**
	 * 构建某参数结果对象
	 * @param itemReportList  参数对应环报表基础数据
	 * @param statistics      参数对应统计汇总数据
	 * @param paramName       参数
	 * @param dictMapper      字典映射器
	 * @return
	 */
	public Map<String, Object> buildParamItem(List<MetroLineIntervalRingReport> itemReportList,
			RingStatistics statistics, String paramName, Map<String, MetroDictionary> dictMapper) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		if(!CommonUtils.isNotNull(paramName)){
			return itemMap;
		}
		if(itemReportList == null){
			itemReportList = new ArrayList<MetroLineIntervalRingReport>();
		}
		if(statistics == null){
			statistics = new RingStatistics();
		}
		//1. 封装参数标签、参数名字、单位
		itemMap.put("param", paramName);
		if(paramName.indexOf("-") < 0){
			MetroDictionary dict = dictMapper.get(paramName);
			itemMap.put("name", dict.getDicMean());
			itemMap.put("unit", dict.getDicUnit());
		}else{
			itemMap.put("name", "");
			itemMap.put("unit", "");
			String[] paramArr = paramName.split("-");
			if(paramArr.length == 2){
				MetroDictionary dict1 = dictMapper.get(paramArr[0]);
				MetroDictionary dict2 = dictMapper.get(paramArr[1]);
				if(dict1 != null && dict2 != null){
					itemMap.put("name", dict1.getDicMean() + "-" + dict2.getDicMean());
					itemMap.put("unit", dict1.getDicUnit());
				}
			}
		}
		//2.  封装汇总信息
		itemMap.put("sumValue", statistics.getSumValue());
		itemMap.put("minValue", statistics.getMinValue());
		itemMap.put("maxValue", statistics.getMaxValue());
		itemMap.put("avgValue", statistics.getAvgValue());
		Object startValue = null;
		Object endValue = null;
		//3. 封装参数值列表
		List<Object> dataList = new ArrayList<Object>();
		for (int i = 0; i < itemReportList.size(); i++) {
			MetroLineIntervalRingReport itemReport = itemReportList.get(i);
			dataList.add(itemReport.getNumValue());
			if(i == 0){
				startValue = itemReport.getNumValue();
			}
			if(i == (itemReportList.size()-1)){
				endValue = itemReport.getNumValue();
			}
		}
		itemMap.put("startValue", startValue);
		itemMap.put("endValue", endValue);
		itemMap.put("data", dataList);
		return itemMap;
	}
	
	/**
	 * 将单参数与相减参数分开
	 * @param oriList        原参数列表
	 * @param singleList     单参数列表
	 * @param reduceList 相减参数列表
	 */
	private void spiltParams(List<String> oriList, List<String> singleList, List<String> reduceList){
		if(singleList == null){
			singleList = new ArrayList<String>();
		}
		if(reduceList == null){
			reduceList = new ArrayList<String>();
		}
		if(oriList == null || oriList.size() < 1){
			return;
		}
		for (String ori:oriList) {
			if(!CommonUtils.isNotNull(ori)){
				continue;
			}
			if(ori.indexOf("-") > -1&&!reduceList.contains(ori)){
				reduceList.add(ori);
			}
			if(ori.indexOf("-") < 0 &&!singleList.contains(ori)){
				singleList.add(ori);
			}
		}
	}
	
	/**
	 * 根据参数标签对环报表统计数据分组
	 * @param statisticsList
	 * @return
	 */
	private Map<String, RingStatistics> splitStatisticsGrouByParam(List<RingStatistics> statisticsList){
		Map<String, RingStatistics> mapper = new HashMap<String, RingStatistics>();
		if(statisticsList == null || statisticsList.size() < 1){
			return mapper;
		}
		for (RingStatistics statistics : statisticsList) {
			mapper.put(statistics.getParamName(), statistics);
		}
		return mapper;
	}
	
	/**
	 * 根据参数标签对环报表基础数据分组
	 * @param reportList
	 * @return
	 */
	private Map<String, List<MetroLineIntervalRingReport>> splitReportGrouByParam(List<MetroLineIntervalRingReport> reportList){
		Map<String, List<MetroLineIntervalRingReport>> mapper = new HashMap<String, List<MetroLineIntervalRingReport>>();
		if(reportList == null || reportList.size() < 1){
			return mapper;
		}
		for (MetroLineIntervalRingReport report : reportList) {
			if(mapper.containsKey(report.getParamName())){
				List<MetroLineIntervalRingReport> tempList = mapper.get(report.getParamName());
				tempList.add(report);
				mapper.put(report.getParamName(), tempList);
			}else{
				List<MetroLineIntervalRingReport> tempList = new ArrayList<MetroLineIntervalRingReport>();
				tempList.add(report);
				mapper.put(report.getParamName(), tempList);
			}
		}
		return mapper;
	}
	
	/**
	 * 获取查询相减参数环报表统计数据
	 * @param reduceReportMapper
	 * @return
	 */
	private Map<String, RingStatistics> getReduceStatisticsMapper(
			Map<String, List<MetroLineIntervalRingReport>> reduceReportMapper) {
		Map<String, RingStatistics> statisticsMapper = new HashMap<String, RingStatistics>();
		if (reduceReportMapper == null || reduceReportMapper.isEmpty()) {
			return statisticsMapper;
		}
		for (Entry<String, List<MetroLineIntervalRingReport>> entry : reduceReportMapper.entrySet()) {
			statisticsMapper.put(entry.getKey(), getReduceRingStatistics(entry.getKey(), entry.getValue()));
		}
		return statisticsMapper;
	}
	
	/**
	 * 汇总统计某相减参数的汇总数据
	 * @param paramName  参数名称 A0001-A0002
	 * @param reportList 参数相减基础数据列表
	 * @return
	 */
	private RingStatistics getReduceRingStatistics(String paramName, List<MetroLineIntervalRingReport> reportList){
		RingStatistics ringStatistics = new RingStatistics();
		if(reportList == null || reportList.size() < 1){
			return ringStatistics;
		}
		Float sumValue = 0f;
		Float maxValue = null;
		Float minValue = null;
		Float avgValue = 0f;
		for (MetroLineIntervalRingReport report : reportList) {
			Float numValue = report.getNumValue();
			if(numValue == null){
				continue;
			}
			sumValue += numValue;
			if(maxValue == null){
				maxValue = numValue;
			}
			if(maxValue < numValue){
				maxValue = numValue;
			}
			if(minValue == null){
				minValue = numValue;
			}
			if(minValue > numValue){
				minValue = numValue;
			}
		}
		avgValue = sumValue/reportList.size();
		ringStatistics.setSumValue(sumValue);
		ringStatistics.setMaxValue(maxValue);
		ringStatistics.setMinValue(minValue);
		ringStatistics.setAvgValue(avgValue);
		ringStatistics.setParamName(paramName);
		return ringStatistics;
	}
	
	/**
	 * 获取两两相减的环报表基础数据
	 * @param reduceList
	 * @return
	 */
	private Map<String, List<MetroLineIntervalRingReport>> getReduceReportData(Long intervalId, String leftOrRight, String ringNum, List<String> reduceList){
		Map<String, List<MetroLineIntervalRingReport>> reduceMapper = new HashMap<String, List<MetroLineIntervalRingReport>>();
		if(reduceList == null || reduceList.size() < 1){
			return reduceMapper;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		for (String reduceParam : reduceList) {
			if(reduceParam == null || "".equals(reduceParam)){
				continue;
			}
			String[] paramPair = reduceParam.split("-");
			if(paramPair.length != 2){
				continue;
			}
			params.put("paramName1", paramPair[0]);
			params.put("paramName2", paramPair[1]);
			reduceMapper.put(reduceParam, lineIntervalRingReportDao.findRingTowParamReduce(params));
		}
		return reduceMapper;
	}
	/**
	 * 取所有参数列表
	 * @param singleList
	 * @param reduceList
	 * @return
	 */
	private List<String> getTotalParamList(List<String> singleList, List<String> reduceList){
		List<String> totalList = singleList;
		if(totalList == null){
			totalList = new ArrayList<String>();
		}
		if(reduceList == null || totalList.size() < 1){
			return totalList;
		}
		for (String reduceParam:reduceList) {
			String[] paramPair = reduceParam.split("-");
			if(paramPair.length != 2){
				continue;
			}
			if(!totalList.contains(paramPair[0])){
				totalList.add(paramPair[0]);
			}
			if(!totalList.contains(paramPair[1])){
				totalList.add(paramPair[1]);
			}
		}
		return totalList;
	}

	/**
	 * 查找
	 */
	@Override
	public List<MetroLineIntervalRingReport> findRingReport(Long intervalId,String leftOrRight,String ringNum,String paramName) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		if(paramName != null && !"".equals(paramName)){
			List<String> paramNames = new ArrayList<String>();
			paramNames.add(paramName);
			params.put("paramNames", paramNames);
		}
		return lineIntervalRingReportDao.findRingReport(params);
	}
	
	/**
	 * 查询环号对应参数的统计值（平均、最大、最小、总和）
	 * @param intervalId  区间ID
	 * @param leftOrRight 左右线
	 * @param ringNum   环号
	 * @return
	 */
	@Override
	public List<RingStatistics> findRingStatistics(Long intervalId, String leftOrRight, String ringNum){
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		return lineIntervalRingReportDao.findRingStatistics(params);
	}
	
	/**
	 * 获取paramName1-paramName2对应行程的值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param ringNum     环号
	 * @param paramName1   
	 * @param paramName2
	 * @return
	 */
	@Override
	public List<MetroLineIntervalRingReport> findRingTowParamReduce(Long intervalId, String leftOrRight, String ringNum,
			String paramName1, String paramName2) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		params.put("paramName1", paramName1);
		params.put("paramName2", paramName2);
		return lineIntervalRingReportDao.findRingTowParamReduce(params);
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            ID列表
	 * @return
	 */
	@Override
	public int deleteRingReportBathByIds(String[] ids) {
		List<String> idList = Arrays.asList(ids);
		return lineIntervalRingReportDao.deleteRingReportBatch(idList);
	}
	/**
	 * 
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param ringNum     环号
	 * @return
	 */
	@Override
	public Map<String, Object> findRingReportBase(String intervalId, String leftOrRight, String ringNum) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, Object>> timeList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic == null) {
			return res;
		}
		String key = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), "A0002");
		res.put("machineNo", mic.getMachineNo());
		List<String> rings = new ArrayList<String>();
		rings.add(ringNum);
		IhistorianResponse ir = IhistorianUtil.getDataByKeyAndRings(key, rings);
		if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
			List<HashMap<String, HashMap>> result = (List<HashMap<String, HashMap>>) ir.getResult().get("list");
			Integer k0001Time = null;
			Integer k0002Time = null;
			Integer k0003Time = null;
			if (result !=null&&result.size()>0) {
				Map<String, Object> m =  result.get(0).get(ringNum);
				k0001Time = (Integer)m.get("K0001");// 停止时间
				k0002Time = (Integer)m.get("K0002");// 推进时间
				k0003Time = (Integer)m.get("K0003");// 拼装时间
			}
			Integer totalTime = null;
			if(k0001Time != null || k0002Time != null || k0003Time != null){
				totalTime = (k0001Time == null ? 0 : k0001Time) + (k0002Time == null ? 0 : k0002Time)
						+ (k0003Time == null ? 0 : k0003Time);
			}
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("value", k0002Time);
			map1.put("name", "推进时间");
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("value", k0003Time);
			map2.put("name", "拼装时间");
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("value", k0001Time);
			map3.put("name", "停止时间");
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("value", totalTime);
			map4.put("name", "总时间");
			timeList.add(map1);
			timeList.add(map2);
			timeList.add(map3);
			timeList.add(map4);
		}
		res.put("timeList", timeList);
		res.put("head", IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight()));
		return res;
	}
	
}
