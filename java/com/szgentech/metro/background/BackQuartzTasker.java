package com.szgentech.metro.background;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.background.vo.IntervalWarningVO;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.base.utils.MathUtil;
import com.szgentech.metro.model.MetroLineIntervalWarningRec;
import com.szgentech.metro.service.IMetroIntervalRiskSegmentationService;
import com.szgentech.metro.service.IMetroLineIntervalWarningService;
import com.szgentech.metro.service.IMetroWarningRecService;
import com.szgentech.metro.vo.IhistorianResponse;

/**
 * @className:QuartzOrderTasker.java
 * @classDescription:定时任务
 * @author: luowq
 * @createTime: 2016年9月6日
 */
@Service("backQuartzTasker")
public class BackQuartzTasker {
	private static Logger logger = Logger.getLogger(BackQuartzTasker.class);

	@Autowired
	private IMetroLineIntervalWarningService lineIntervalWarningService;
	@Autowired
	private IMetroWarningRecService warningRecService;

	@Autowired
	private IMetroIntervalRiskSegmentationService riskSegmentationService;
	/**
	 * 获取区间监测预警记录，对比入库warning warning_rec
	 */
	@SuppressWarnings("unchecked")
	public void executeGetAndSaveIntervalWarningRec() {
		try {
			logger.info("执行定时任务：获取和保存区间监测预警记录");
			String PARAM_RING_NUM = "A0001"; // 当前环号
			String PARAM_STATUS = "A0002"; // 工作状态
			// 获取所有监测预警阀值设置信息的路线左右线列表
			List<IntervalWarningVO> list1 = lineIntervalWarningService.findQueryVOListForAll();
			for (IntervalWarningVO vo1 : list1) {
				List<String> keys = new ArrayList<String>();
				String ringNumkey = IhistorianUtil.getKey(vo1.getLineNo(), vo1.getIntervalMark(), vo1.getLeftOrRight(),
						PARAM_RING_NUM);
				String ringStatuskey = IhistorianUtil.getKey(vo1.getLineNo(), vo1.getIntervalMark(),
						vo1.getLeftOrRight(), PARAM_STATUS);
				keys.add(ringNumkey);
				keys.add(ringStatuskey);
				// 获取该路线左右线具体的环数
				IhistorianResponse ir = IhistorianUtil.getDataByKeys(keys);
				if (ir != null && ir.getCode() == 200) {
					Map<String, Object> result = ir.getResult();
					Integer ringNum = (Integer) result.get(ringNumkey);
					Integer ringStatus = (Integer) result.get(ringStatuskey);
					// 获取符合该环数要求的所有预警阈值信息
					List<IntervalWarningVO> list2 = lineIntervalWarningService.findQueryVOList(
							vo1.getIntervalId(), vo1.getLeftOrRight(), ringNum);
					Map<String, Object> map = new HashMap<String, Object>();
					// 用map组装预警阈值信息，key:查询historian的key
					// value:List<IntervalWarningVO>
					for (IntervalWarningVO vo2 : list2) {
						if (MathUtil.checkStatus(vo2.getCategory(), ringStatus)) {
							String key = IhistorianUtil.getKey(vo2.getLineNo(), vo2.getIntervalMark(),
									vo2.getLeftOrRight(), vo2.getParam());
							if (map.containsKey(key)) {
								List<IntervalWarningVO> l = (List<IntervalWarningVO>) map.get(key);
								l.add(vo2);
							} else {
								List<IntervalWarningVO> l = new ArrayList<IntervalWarningVO>();
								l.add(vo2);
								map.put(key, l);
							}
						}
					}
					List<String> keys2 = new ArrayList<String>();
					keys2.addAll(map.keySet());
					// 查出所有的key对应的值
					if (keys2.size() > 0) {
						IhistorianResponse ir2 = IhistorianUtil.getDataByKeys(keys2);
						if (ir2 != null && ir2.getCode() == 200) {
							Map<String, Object> result2 = ir2.getResult();
							// 需要保存的预警记录
							List<MetroLineIntervalWarningRec> recs = new ArrayList<MetroLineIntervalWarningRec>();
							for (String key : result2.keySet()) {
								Float value = Float.valueOf(String.valueOf(result2.get(key)));
								List<IntervalWarningVO> list3 = (List<IntervalWarningVO>) map.get(key);
								for (IntervalWarningVO vo3 : list3) {
									try {
										if(vo3.getOrangeWarningMax()==null||vo3.getOrangeWarningMin()==null||vo3.getRedWarningMax()==null
												||vo3.getRedWarningMin()==null||vo3.getYellowWarningMax()==null||vo3.getYellowWarningMin()==null){
												continue;
											}
											if (!(value >= vo3.getYellowWarningMin() && value <= vo3.getYellowWarningMax())) {
												MetroLineIntervalWarningRec rec = new MetroLineIntervalWarningRec();
												rec.setIntervalId(vo3.getIntervalId());
												rec.setLeftOrRight(vo3.getLeftOrRight());
												rec.setNumValue(value);
												rec.setOrangeWarningMax(vo3.getOrangeWarningMax());
												rec.setOrangeWarningMin(vo3.getOrangeWarningMin());
												rec.setRedWarningMax(vo3.getRedWarningMax());
												rec.setRedWarningMin(vo3.getRedWarningMin());
												rec.setYellowWarningMax(vo3.getYellowWarningMax());
												rec.setYellowWarningMin(vo3.getYellowWarningMin());
												rec.setParamName(vo3.getParam());
												rec.setRingNum(ringNum);
												rec.setWarningTime(new Date());
												Integer warningLevel = null;
												if (value >= vo3.getRedWarningMax()) { // 红色上限预警
													warningLevel = 1;
												} else if (value >= vo3.getOrangeWarningMax()
														&& value < vo3.getRedWarningMax()) { // 橙色上限预警
													warningLevel = 2;
												} else if (value > vo3.getRedWarningMin()
														&& value <= vo3.getOrangeWarningMin()) { // 橙色下限预警
													warningLevel = 3;
												} else if (value <= vo3.getRedWarningMin()) { // 红色下限预警
													warningLevel = 4;
												} else if (value >= vo3.getYellowWarningMax()
														&& value < vo3.getOrangeWarningMax()){//黄色上限预警
													warningLevel = 5;
												} else if (value <= vo3.getYellowWarningMin()
														&& value > vo3.getOrangeWarningMin()){//黄色下限预警
													warningLevel = 6;
												}
												rec.setWarningLevel(warningLevel);
												recs.add(rec);
											}
									} catch (Exception e) {
										logger.error("处理"+vo3.getIntervalId()+"区间"+vo3.getLeftOrRight()+"线"+vo3.getParam()+"参数预警出错！" + e.getMessage());
									}
								}
							}

							if (recs.size() == 0) {
								logger.info(vo1.getLineNo() + "号线" + vo1.getIntervalMark() + "标"
										+ vo1.getLeftOrRight().toUpperCase() + "线，" + "没有报警!!!");
								continue;
							}
							// 批量插入预警记录
							boolean r = warningRecService.insertObjs(recs);
							if (r) {
								logger.info(vo1.getLineNo() + "号线" + vo1.getIntervalMark() + "标"
										+ vo1.getLeftOrRight().toUpperCase() + "线，" + "保存预警记录成功");
							} else {
								logger.warn(vo1.getLineNo() + "号线" + vo1.getIntervalMark() + "标"
										+ vo1.getLeftOrRight().toUpperCase() + "线，" + "没有符合条件的预警记录或保存预警记录失败");
							}
						}
					}
				}
			}
			logger.info("执行定时任务完成！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时获取和保存区间监测预警记录失败：" + e.getMessage());
		}
	}
	
	public void executePositionRiskWarning(){
		
		try {
			logger.info("执行定时任务：获取和保存区间地质风险划分组段划预警记录");
			riskSegmentationService.riskSegmentationTimerService();
			logger.info("执行定时任务完成！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("定时获取和保存区间地质风险划分组段划预警记录失败：" + e.getMessage());
		}
	  
	}
}