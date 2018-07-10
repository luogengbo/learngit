package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroIntervalRiskSegmentationRecDao;
import com.szgentech.metro.model.MetroIntervalRiskSegmentationRec;
import com.szgentech.metro.service.IMetroIntervalRiskSegmentationRecService;


/**
 * 盾构地质风险划分阻段划分预警记录接口实现
 * @author luohao
 *
 */
@Service("riskSegmentationRecService")
public class MetroIntervalRiskSegmentationRecService extends BaseService<MetroIntervalRiskSegmentationRec> implements IMetroIntervalRiskSegmentationRecService {
	private static Logger logger = Logger.getLogger(MetroIntervalRiskSegmentationRecService.class);
	
	@Autowired
	private IMetroIntervalRiskSegmentationRecDao riskSegmentationRecDao;
	
	/**
	 * 查找地质风险划分阻段划分预警记录
	 */
	@Override
	public List<MetroIntervalRiskSegmentationRec> findRiskSegmentationRec(Long riskId, int start, int end){
		Map<String, Object> params = new HashMap<>();
		params.put("riskId", riskId);
		params.put("start", start);
		params.put("end", end);
		return riskSegmentationRecDao.findRiskRecByRiskId(params);
	}
	/**
	 * 分页查询
	 * 地质风险划分阻段划分预警记录
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroIntervalRiskSegmentationRec> findriskSegmentationRecInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);		
		params.put("leftOrRight", leftOrRight);		
		PageResultSet<MetroIntervalRiskSegmentationRec> resultSet = getPageResultSet(params, pageNum, pageSize, riskSegmentationRecDao);
	    return resultSet;
	}
    /**
     * 更新地质风险划分阻段划分数据
     */
	@Override
	public boolean updateObj(MetroIntervalRiskSegmentationRec riskSegmentationRec) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskRecId", riskSegmentationRec.getId());
		params.put("intervalId", riskSegmentationRec.getIntervalId());
		params.put("leftOrRight", riskSegmentationRec.getLeftOrRight());
		params.put("riskSegmentationId", riskSegmentationRec.getRiskSegmentationId());
		params.put("ringNUm", riskSegmentationRec.getRingNum());
		int r = riskSegmentationRecDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除地质风险划分阻段划分预警记录
	 * 
	 * @param riskRecId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long riskRecId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskId", riskRecId);
		int r = riskSegmentationRecDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 插入地质风险划分阻段划分预警记录
	 */
	@Override
	public int insertSegmentationRec(Long intervalId, String leftOrRight, Long riskId, Integer ringNum) {
		MetroIntervalRiskSegmentationRec rec = new MetroIntervalRiskSegmentationRec();
		rec.setIntervalId(intervalId);
		rec.setLeftOrRight(leftOrRight);
		rec.setRiskSegmentationId(riskId);
		rec.setRingNum(ringNum);
		return riskSegmentationRecDao.insertObj(rec);
	}
	
	@Override
	public MetroIntervalRiskSegmentationRec findObjById(Long riskRecId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("riskRecId", riskRecId);
		return riskSegmentationRecDao.findObjById(params);
	}
	

}
