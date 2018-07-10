package com.szgentech.metro.service;

import java.util.List;
import java.util.Map;

import com.szgentech.metro.model.MetroLineIntervalRingReport;
import com.szgentech.metro.model.RingStatistics;

/**
 * 环报表业务接口
 * @author luohao
 *
 */
public interface IMetroLineIntervalRingReportService {
	
	/**
	 * 查询制定区间、左右线、环号和指定参数的环报表数据， 不指定参数取默认需要所有参数
	 * @param intervalId    区间
	 * @param leftOrRight   左右线
	 * @param ringNum       环号
	 * @param paramNameList 参数列表
	 * @return
	 */
	public Map<String, Object> findRingReport(Long intervalId, String leftOrRight, String ringNum, List<String> paramNameList);
	
	/**
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param ringNum    环号
	 * @param paramName  参数名称
	 * @return
	 */
	public List<MetroLineIntervalRingReport> findRingReport(Long intervalId,String leftOrRight,String ringNum,String paramName);
	
	/**
	 * 查询环号对应参数的统计值（平均、最大、最小、总和）
	 * @param intervalId
	 * @param leftOrRight
	 * @param ringNum
	 * @return
	 */
	public List<RingStatistics> findRingStatistics(Long intervalId, String leftOrRight, String ringNum);
	
	/**
	 * 获取paramName1-paramName2对应行程的值
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param ringNum     环号
	 * @param paramName1   
	 * @param paramName2
	 * @return
	 */
	public List<MetroLineIntervalRingReport> findRingTowParamReduce(Long intervalId, String leftOrRight, String ringNum,
			String paramName1, String paramName2);
	
	/**
	 * 批量删除
	 * @param ids ID列表
	 * @return
	 */
	public int deleteRingReportBathByIds(String[] ids);
	
	/**
	 * 
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param ringNum     环号
	 * @return
	 */
	public Map<String, Object> findRingReportBase(String intervalId, String leftOrRight, String ringNum);

}
 