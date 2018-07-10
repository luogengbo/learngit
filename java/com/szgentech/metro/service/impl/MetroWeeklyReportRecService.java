package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroWeeklyReportRecDao;
import com.szgentech.metro.model.MetroWeeklyReportRec;
import com.szgentech.metro.service.IMetroWeeklyReportRecService;


/**
 * 盾构周报表上传记录业务接口实现
 * @author luohao
 *
 */
@Service("weeklyReportRecService")
public class MetroWeeklyReportRecService extends BaseService<MetroWeeklyReportRec> implements IMetroWeeklyReportRecService {

	@Autowired
	private IMetroWeeklyReportRecDao weeklyReportRecDao;

	/**
	 * 查找周报表上传记录
	 */
	public MetroWeeklyReportRec findWeeklyReportRec(Long intervalId, String leftOrRight, String reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		return weeklyReportRecDao.findWeeklyReportRec(params);
	}

	/**
	 * 插入周报表上传记录
	 */
	@Override
	public int addWeeklyReportRec(MetroWeeklyReportRec weeklyreportRec) {
		return weeklyReportRecDao.insertObj(weeklyreportRec);
	}

	/**
	 * 删除周报表记录信息
	 * @param weeklyReprotId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long weeklyReprotId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("weeklyReprotId", weeklyReprotId);
		int r = weeklyReportRecDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 分页查询
	 * 线路周报表记录信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroWeeklyReportRec> findWeeklyReportInfo(
			Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroWeeklyReportRec> resultSet = getPageResultSet(params, pageNum, pageSize, weeklyReportRecDao);
		return resultSet;
	}

}
