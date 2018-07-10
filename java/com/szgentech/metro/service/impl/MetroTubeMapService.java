package com.szgentech.metro.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.dao.IMetroTubeMapDao;
import com.szgentech.metro.model.TubeMapInfo;
import com.szgentech.metro.service.IMetroTubeMapService;
import com.szgentech.metro.vo.DataChild;
import com.szgentech.metro.vo.IhistorianResponse;
import com.szgentech.metro.vo.TubeMapIntervalLr;

@Service("tmService")
public class MetroTubeMapService implements IMetroTubeMapService {
	
	private static Logger logger = Logger.getLogger(MetroTubeMapService.class);
	@Autowired
	private IMetroTubeMapDao tubeDao;

	@SuppressWarnings("unchecked")
	@Override
	public TubeMapIntervalLr findLrInfo(Long intervalId) {
		TubeMapIntervalLr lr = new TubeMapIntervalLr();
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		List<TubeMapInfo> lrs = tubeDao.findTubeMapInfo(params);
		List<String> keys = new ArrayList<String>();
		for (TubeMapInfo tm : lrs) {
			DataChild dc = new DataChild();
			String key = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0001");
			keys.add(key);
			key = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0003");
			keys.add(key);
			String warn = tm.getWarningLevel() != null ? (tm.getWarningLevel() == 1 ? "[红色上限预警]"
					: (tm.getWarningLevel() == 2 ? "[橙色上限预警]" : (tm.getWarningLevel() == 3 ? "橙色下限预警" : "红色下限预警")))
					: null;
			dc.setAlarm(StringUtil.nullToString(warn) + " " + StringUtil.nullToString(tm.getDicMean()) + " "
					+ StringUtil.nullToString(tm.getNumValue() != null ? tm.getNumValue().toString() : "") + " "
					+ StringUtil.nullToString(tm.getDicUnit()));
			dc.setBuildStatus(tm.getBuildStatus());
			dc.setDataTime(Calendar.getInstance().getTime());
			if ("l".equals(tm.getLeftOrRight())) {
				lr.setLeft(dc);
			} else {
				lr.setRight(dc);
			}
		}
		IhistorianResponse ir = IhistorianUtil.getDataByKeysRing(keys);
		for (TubeMapInfo tm : lrs) {
			String key1 = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0001");
			String key2 = IhistorianUtil.getKey(tm.getLineNo(), tm.getIntervalMark(), tm.getLeftOrRight(), "A0002");
			if (ir != null) {
				try {
					if ("l".equals(tm.getLeftOrRight())) {
						if (ir.getResult() != null) {
							String result = new ObjectMapper().writeValueAsString(ir.getResult().get(key1));
							ObjectMapper mapper = new ObjectMapper();
							Map<String, Object> resultMap = (Map<String, Object>) mapper.readValue(result, HashMap.class);
							if(resultMap == null || resultMap.isEmpty()){
								continue;
							}
							lr.getLeft().setCurrentRing(StringUtil.nullToInt(resultMap.get("ring").toString()));
							int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
							lr.getLeft().setCommuniStatus(quality == 100 ? 1 : 0);
						} else {
							lr.getLeft().setCurrentRing(0);
							lr.getLeft().setAdvanceStatus(0);
						}
						lr.getLeft().setAdvanceStatus(StringUtil
								.nullToInt(ir.getResult() != null ? ir.getResult().get(key2).toString() : null));
					} else {
						if (ir.getResult() != null) {
							String result = new ObjectMapper().writeValueAsString(ir.getResult().get(key1));
							ObjectMapper mapper = new ObjectMapper();
							Map<String, Object> resultMap = (Map<String, Object>) mapper.readValue(result, HashMap.class);
							if(resultMap == null || resultMap.isEmpty()){
								continue;
							}
							lr.getRight().setCurrentRing(StringUtil.nullToInt(resultMap.get("ring").toString()));
							int quality = StringUtil.nullToInt(resultMap.get("quality").toString());
							lr.getRight().setCommuniStatus(quality == 100 ? 1 : 0);
						} else {
							lr.getRight().setCurrentRing(0);
							lr.getRight().setAdvanceStatus(0);
						}
						lr.getRight().setAdvanceStatus(StringUtil
								.nullToInt(ir.getResult() != null ? ir.getResult().get(key2).toString() : null));
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		return lr;
	}

}
