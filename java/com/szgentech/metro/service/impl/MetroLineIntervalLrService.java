package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroLineIntervalLrDao;
import com.szgentech.metro.model.MetroLineIntervalLr;
import com.szgentech.metro.service.IMetroLineIntervalLrService;

/**
 * 地铁线路区间左右线信息数据业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalLrService")
public class MetroLineIntervalLrService extends BaseService<MetroLineIntervalLr> implements IMetroLineIntervalLrService {

	@Autowired
	private IMetroLineIntervalLrDao lineIntervalLrDao;

	/**
	 * 分页查询
	 * 地铁线路区间左右线信息数据
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalLr> findLineIntervalLrInfo(
			Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroLineIntervalLr> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalLrDao);
		return resultSet;
	}
	/**
	 * 更新线路区间左右线信息
	 * @param intervalLr
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalLr intervalLr) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalLrId", intervalLr.getId());
		params.put("leftOrRight", intervalLr.getLeftOrRight());
		params.put("ringNum", intervalLr.getRingNum());
		params.put("buildStatus", intervalLr.getBuildStatus());
		params.put("status", intervalLr.getStatus());
		params.put("buildDate", intervalLr.getBuildDate());
		params.put("throughDate", intervalLr.getThroughDate());
		params.put("intervalColor", intervalLr.getIntervalColor());
		params.put("mapXy", intervalLr.getMapXy());
		params.put("cutterPhotoId", intervalLr.getCutterPhotoId());
		params.put("slurryPhotoId", intervalLr.getSlurryPhotoId());
		params.put("screwPhotoId", intervalLr.getScrewPhotoId());
		params.put("machineNo", intervalLr.getMachineNo());
		params.put("machineCompany", intervalLr.getMachineCompany());
		params.put("machineType", intervalLr.getMachineType());
		params.put("machineProductDate", intervalLr.getMachineProductDate());
		params.put("machineContractor", intervalLr.getMachineContractor());
		params.put("machineReviewDate", intervalLr.getMachineReviewDate());
		params.put("remark", intervalLr.getRemark());
		params.put("speedQualifiedRange", intervalLr.getSpeedQualifiedRange());
		params.put("direction", intervalLr.getDirection());
		params.put("isDel", intervalLr.getIsDel());
		int r = lineIntervalLrDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 保存线路区间左右线信息
	 * @param intervalLr
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalLr intervalLr) {
		int r = lineIntervalLrDao.insertObj(intervalLr);
		if (r > 0) {
			return intervalLr.getId();
		}
		return null;
	}

	@Override
	public MetroLineIntervalLr findIntervalLr(Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);

		return lineIntervalLrDao.findIntervalLr(params);
	}
}
