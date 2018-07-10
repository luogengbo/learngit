package com.szgentech.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.dao.IMetroLineDao;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.service.IMetroLineService;

/**
 * 地铁线路信息业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineService")
public class MetroLineService extends BaseService<MetroLine> implements IMetroLineService {
	@Autowired
	private IMetroLineDao lineDao;

	/**
	 * 保存线路信息
	 * @param line
	 * @return
	 */
	@Override
	public Long insertObj(MetroLine line) {
		int r = lineDao.insertObj(line);
		if(r > 0){
			return line.getId();
		}
		return null;
	}
	/**
	 * 通过id查询地铁城市线路信息
	 * @param lineId
	 * @return
	 */
	@Override
	public MetroLine findObjById(Long lineId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lineId", lineId);
		return lineDao.findObjById(params);
	}
	/**
	 * 删除线路信息
	 * @param lineId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long lineId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lineId", lineId);
		int r = lineDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 更新线路信息
	 * @param line
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLine line) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lineId", line.getId());
		params.put("cityId", line.getCityId());
		params.put("lineName", line.getLineName());
		params.put("lineNo", line.getLineNo());
		params.put("lineColor", line.getLineColor());
		params.put("lineStatus", line.getLineStatus());
		params.put("mapX", line.getMapX());
		params.put("mapY", line.getMapY());
		params.put("projectPdfUrl", line.getProjectPdfUrl());
		params.put("remark", line.getRemark());
		int r = lineDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
}
