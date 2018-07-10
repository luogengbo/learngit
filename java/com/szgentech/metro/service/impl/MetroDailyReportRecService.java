package com.szgentech.metro.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroDailyReportRecDao;
import com.szgentech.metro.model.MetroDailyreportRec;
import com.szgentech.metro.service.IMetroDailyReportRecService;


/**
 * 盾构日报表上传记录业务接口实现
 * @author luohao
 *
 */
@Service("dailyReportRecService")
public class MetroDailyReportRecService extends BaseService<MetroDailyreportRec> implements IMetroDailyReportRecService {

	@Autowired
	private IMetroDailyReportRecDao dailyReportRecDao;

	/**
	 * 查找日报表上传记录
	 */
	@Override
	public MetroDailyreportRec findDailyReportRec(Long intervalId, String leftOrRight, Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		return dailyReportRecDao.findDailyReportRec(params);
	}

	/**
	 * 插入日报表上传记录
	 */
	@Override
	public int addDailyReportRec(MetroDailyreportRec dailyreportRec) {
		return dailyReportRecDao.insertObj(dailyreportRec);
	}

	/**
	 * 删除日报表记录信息
	 * @param dailyReprotId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long dailyReprotId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dailyReprotId", dailyReprotId);
		int r = dailyReportRecDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 分页查询
	 * 线路日报表记录信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroDailyreportRec> findDailyReportInfo(
			Long intervalId, String leftOrRight, String reportTime, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		PageResultSet<MetroDailyreportRec> resultSet = getPageResultSet(params, pageNum, pageSize, dailyReportRecDao);
		return resultSet;
	}

}
