package com.szgentech.metro.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroWarningRecDao;
import com.szgentech.metro.model.DaiyWarnStatistics;
import com.szgentech.metro.model.MetroLineIntervalTota;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.model.WarnRiskForecastEntry;
import com.szgentech.metro.service.IMetroWarningRecService;

@Service("warningRecService")
public class MetroWarningRecService extends BaseService<MetroLineIntervalWarningRec>
		implements IMetroWarningRecService {

	@Autowired
	private IMetroWarningRecDao recDao;

	@Override
	public List<MetroLineIntervalWarningRec> findLastWarningRecs(Long userId, Date p_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return recDao.findLastWarningRecs(params);
	}

	@Override
	public List<MetroLineIntervalWarningRec> findLastWarningRecsIntervalId(Long intervalId, String leftOrRight,
			Date p_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return recDao.findLastWarningRecsByIntervalId(params);
	}

	@Override
	public PageResultSet<MetroLineIntervalWarningRec> findWarningRecs(Long userId, String intervalId,
			String leftOrRight, int pageNum, int pageSize, String beginTime, String endTime, String warnParam, Integer confirmLevel, Integer isPush) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		if (confirmLevel >= 0) {
			params.put("confirmLevel", confirmLevel);
		}
		params.put("isPush", isPush);
		if (beginTime != null && !"".equals(beginTime) && endTime != null && !"".equals(endTime)) {
			String[] bs = beginTime.split("/");
			String[] ns = endTime.split("/");
			params.put("beginTime", bs[2] + "-" + bs[0] + "-" + bs[1]);
			params.put("endTime", ns[2] + "-" + ns[0] + "-" + ns[1]);
		}
		if (warnParam != null && !"-1".equals(warnParam)) {
			params.put("warnParam", warnParam);
		}
		return this.getPageResultSet(params, pageNum, pageSize, recDao);
	}

	/**
	 * 批量插入监测预警数据记录
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public boolean insertObjs(List<MetroLineIntervalWarningRec> list) throws Exception {
		int i = recDao.insertObjs(list);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public WarnRiskForecastEntry findWarnRiskForecast(Integer userId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		WarnRiskForecastEntry entry = recDao.findWarnRiskForecast(params);
		return entry;
	}
	/**
	 * 是否已人工确认预警等级或是否推送
	 * 
	 * @param id
	 *            id
	 * @param confirmLevel 
	 * 					是否已人工确认预警等级
	 * @param isPush pageSize 
	 *                  是否已推送
	 * @return
	 */
	@Override
	public boolean editConfirmInfo(Long id, Integer confirmLevel, Integer isPush, Integer warningLevel) {
		Map<String, Object> params = new HashMap<>();
		params.put("warnRecId", id);
		params.put("confirmLevel", confirmLevel);
		params.put("isPush", isPush);
		params.put("warningLevel", warningLevel);
		return recDao.editConfirmInfo(params) > 0 ? true : false;
	}
	
	/**
	 * 删除预警记录信息
	 * 
	 * @param warnRecId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long warnRecId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalWariningRecId", warnRecId);
		int r = recDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/***
	 * （单个参数总计、已确认等级数、已推送预警短信数(响应)）
	 * @return
	 */
	@Override
	public List<DaiyWarnStatistics> findDaiyWarnStatistics(Long intervalId, String leftOrRight, Date beginTime, Date endTime){
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		return recDao.findDaiyWarnStatistics(params);
	}

	@Override
	public MetroLineIntervalTota findWarningTotal(String intervalId, String leftorright,
			String starttime, String endtime) {
		
		String starttime2 = null;
		String endtime2 = null;
		if (starttime == null || endtime == null) {
			String qq = "00:00:00";
			String ww = "23:59:59";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = new Date();
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			date = calendar.getTime();
			String aa = dateFormat.format(date2);
			String bb = dateFormat.format(date);
			
			starttime2 = aa + " " + ww;
			endtime2 = bb + " " + qq;
		}else{
			starttime2 = starttime;
			endtime2 = endtime;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftorright", leftorright);
		params.put("starttime", starttime2);
		params.put("endtime", endtime2);
		List<MetroLineIntervalWarningRec> list = recDao.findWarningTotal(params);
//		MetroLineIntervalTota interval = new MetroLineIntervalTota();
		MetroLineIntervalTota intervalTota = new MetroLineIntervalTota();
		int a = 0;// 红色上限次数
		int b = 0;// 橙色上限次数
		int c = 0;// 橙色下限次数
		int d = 0;// 红色下限次数
		int e = 0;// 黄色上限次数
		int f = 0;// 黄色下限次数
		int g = 0;// 人工确认次数
		int h = 0;// 推送次数
		if (!list.isEmpty()) {
			for (MetroLineIntervalWarningRec mWc : list) {
				if (mWc.getWarningLevel() == 1) {
					a++;
				}
				if (mWc.getWarningLevel() == 2) {
					b++;
				}
				if (mWc.getWarningLevel() == 3) {
					c++;
				}
				if (mWc.getWarningLevel() == 4) {
					d++;
				}
				if (mWc.getWarningLevel() == 5) {
					e++;
				}
				if (mWc.getWarningLevel() == 6) {
					f++;
				}
				if (mWc.getConfirmLevel() == 1) {
					g++;
				}
				if (mWc.getIsPush() == 1) {
					h++;
				}
			}
			intervalTota.setRednessUpper(a);
			intervalTota.setRednessMinimum(d);
			intervalTota.setOrangeUpper(b);
			intervalTota.setOrangeMinimuml(c);
			intervalTota.setYellowWarningMinNum(e);
			intervalTota.setYellowWarningMaxNum(f);
			intervalTota.setConfirmLevelTota(g);
			intervalTota.setIsPushTota(h);
		}
//		interval.
		return intervalTota;
	}
}
