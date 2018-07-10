package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.dao.IMetroDataRightDao;
import com.szgentech.metro.dao.IMetroLineIntervalDao;
import com.szgentech.metro.dao.IMetroLineIntervalLrDao;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.model.MetroUserDataRel;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroLineIntervalService;
import com.szgentech.metro.vo.IhistorianResponse;

/**
 * 地铁线路区间业务接口实现
 * 
 * @author hank
 *
 *         2016年8月17日
 */
@Service("lineIntervalService")
public class MetroLineIntervalService extends BaseService<MetroLineInterval> implements IMetroLineIntervalService {

	@Autowired
	private IMetroLineIntervalDao lineIntervalDao;
	@Autowired
	private IMetroDataRightDao drDao;
	@Autowired
	private IMetroLineIntervalLrDao lrDao;
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	/**
	 * 保存线路区间信息
	 * 
	 * @param interval
	 * @param userId
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineInterval interval, Long userId) {

		int result = lineIntervalDao.insertObj(interval);
		if (result > 0) {
			// 新增数据权限关系
			MetroUserDataRel l_rel = new MetroUserDataRel();
			l_rel.setCityId(1l);
			l_rel.setLineId(interval.getLineId());
			l_rel.setIntervalId(interval.getId());
			l_rel.setLeftOrRight("l");
			l_rel.setUserId(userId);
			MetroUserDataRel r_rel = new MetroUserDataRel();
			r_rel.setCityId(1l);
			r_rel.setLineId(interval.getLineId());
			r_rel.setIntervalId(interval.getId());
			r_rel.setLeftOrRight("r");
			r_rel.setUserId(userId);
			List<MetroUserDataRel> udrlist = new ArrayList<MetroUserDataRel>();
			udrlist.add(l_rel);
			udrlist.add(r_rel);
			Map<String, Object> params = new HashMap<>();
			params.put("udrlist", udrlist);
			drDao.insertObjs(params);
			// 新增区间左右线信息
			MetroLineIntervalLr l = new MetroLineIntervalLr();
			l.setIntervalId(interval.getId());
			l.setLeftOrRight("l");
			l.setBuildStatus(0);
			l.setStatus(1);
			MetroLineIntervalLr r = new MetroLineIntervalLr();
			r.setIntervalId(interval.getId());
			r.setLeftOrRight("r");
			r.setBuildStatus(0);
			r.setStatus(1);
			lrDao.insertObj(l);
			lrDao.insertObj(r);

			return interval.getId();
		}
		return null;
	}

	/**
	 * 通过id查询地铁城市线路区间信息
	 * 
	 * @param intervalId
	 * @return
	 */
	@Override
	public MetroLineInterval findObjById(Long intervalId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		return lineIntervalDao.findObjById(params);
	}

	@Override
	public boolean deleteObj(Long intervalId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		int r = lineIntervalDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateObj(MetroLineInterval interval) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", interval.getId());
		params.put("intervalMark", interval.getIntervalMark());
		params.put("lineId", interval.getLineId());
		params.put("intervalName", interval.getIntervalName());
		params.put("status", interval.getStatus());
		params.put("projectPdfUrl", interval.getProjectPdfUrl());
		params.put("imgMapXyUrl", interval.getImgMapXyUrl());
		params.put("remark", interval.getRemark());
		params.put("parameterPdfUrl", interval.getParameterPdfUrl());
		int r = lineIntervalDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改风险组段划分文档url
	 * 
	 * @param id
	 *            区间id用于地图
	 * @param riskPdfUrl
	 *            风险组段划分文档url
	 * @return
	 */
	@Override
	public boolean editRiskPdfUrl(Long id, String riskPdfUrl) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", id);
		params.put("riskPdfUrl", riskPdfUrl);
		return lineIntervalDao.editRiskPdfUrl(params) > 0 ? true : false;
	}
	/**
	 * 修改地质详勘文档url
	 * 
	 * @param id
	 *            区间id
	 * @param surveyPdfUrl
	 *            地质详勘文档url
	 * @return
	 */
	@Override
	public boolean editSurveyPdfUrl(Long id, String surveyPdfUrl) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", id);
		params.put("surveyPdfUrl", surveyPdfUrl);
		return lineIntervalDao.editSurveyPdfUrl(params) > 0 ? true : false;
	}
	@Override
	public Map<String, Object> getShieldData(Long intervalId, String leftOrRight, Date date, String ring, String key, String type) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if (mic != null) {
			IhistorianResponse ir = null;
			Map<String, Object> list = new HashMap<>();
			String key1 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), key);
			if (type.equals("ring")) {
				ir = IhistorianUtil.getExportdatabyringmin(key1, ring);
			} else if (type.equals("date")) {
				ir = IhistorianUtil.getExportdatabydatemin(key1, date);
			}
			if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
				list = ir.getResult();
				return list;
			}
		}
		return null;
	}
	
	/**
	 * 查询多参数不同条件的盾构数据
	 * @param intervalId   区间
	 * @param leftOrRight  左右线
	 * @param date         日期
	 * @param iStartRing   开始环
	 * @param iEndRing     结束环
	 * @param dicNameList  参数列表
	 * @param type         查询方式，“ring”按环号差，“date”按日期查询
	 * @return
	 */
	public List<Map<String, Object>> getShieldData1(Long intervalId, String leftOrRight, 
			Date date, int iStartRing, int iEndRing, List<String> dicNameList, String type) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 处理加工参数
		// 查询区间信息
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if(mic == null){
			return dataList;
		}
		// 取每个参数数据
		Map<String, Map<String, Object>> dataMapper = new HashMap<String, Map<String, Object>>();
		IhistorianResponse ir = null;
		if (type.equals("ring")) {
			// 根据环号查询
			for (String dicName:dicNameList) {
				String key1 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), dicName);
				for (int i = iStartRing; i <= iEndRing; i++) {
					Map<String, Object> map = new HashMap<>();
					ir = IhistorianUtil.getExportdatabyringmin(key1, String.valueOf(i));
					if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
						map = ir.getResult();
					}
					//同一时间已经存在取原来存在的，否则新建一条数据
					for (Map.Entry<String,Object> entry:map.entrySet()) {
						if(!dataMapper.containsKey(entry.getKey())){
							Map<String, Object> dataMap = new HashMap<String, Object>();
							dataMap.put("time", StringUtil.timeToString(entry.getKey()));
							if("A0002".equals(dicName)&&entry.getValue() != null){
								dataMap.put(dicName, "3".equals(entry.getValue().toString()) ? "推进"
										: ("4".equals(entry.getValue().toString()) ? "拼装" : "停机"));
							}else{
								dataMap.put(dicName, entry.getValue()==null?"":entry.getValue());
							}
							dataMapper.put(entry.getKey(), dataMap);
						}else{
							Map<String, Object> dataMap = dataMapper.get(entry.getKey());
							if("A0002".equals(dicName) &&  entry.getValue()!= null){
								dataMap.put(dicName, "3".equals(entry.getValue().toString()) ? "推进"
										: ("4".equals(entry.getValue().toString()) ? "拼装" : "停机"));
							}else{
								dataMap.put(dicName, entry.getValue()==null?"":entry.getValue());
							}
						}
					}
				}
			}
			
		} else if (type.equals("date")) {
			// 根据时间查询
			for (String dicName:dicNameList) {
				Map<String, Object> map = new HashMap<>();
				String key1 = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight(), dicName);
				ir = IhistorianUtil.getExportdatabydatemin(key1, date);
				if (ir != null && ir.getCode() == 200 && ir.getResult() != null) {
					map = ir.getResult();
				}
				//同一时间已经存在取原来存在的，否则新建一条数据
				for (Map.Entry<String,Object> entry:map.entrySet()) {
					if(!dataMapper.containsKey(entry.getKey())){
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("time", StringUtil.timeToString(entry.getKey()));
						if("A0002".equals(dicName) &&  entry.getValue()!= null){
							dataMap.put(dicName, "3".equals(entry.getValue().toString()) ? "推进"
									: ("4".equals(entry.getValue().toString()) ? "拼装" : "停机"));
						}else{
							dataMap.put(dicName, entry.getValue()==null?"":entry.getValue());
						}
						dataMapper.put(entry.getKey(), dataMap);
					}else{
						Map<String, Object> dataMap = dataMapper.get(entry.getKey());
						if("A0002".equals(dicName) &&  entry.getValue()!= null){
							dataMap.put(dicName, "3".equals(entry.getValue().toString()) ? "推进"
									: ("4".equals(entry.getValue().toString()) ? "拼装" : "停机"));
						}else{
							dataMap.put(dicName, entry.getValue()==null?"":entry.getValue());
						}
					}
				}
			}
		}
		// 转成数组类型
		for (Map.Entry<String, Map<String, Object>> entry:dataMapper.entrySet()) {
			dataList.add(entry.getValue());
		}
		return dataList;
	}
	

}
