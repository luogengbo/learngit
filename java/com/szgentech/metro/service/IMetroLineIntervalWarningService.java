package com.szgentech.metro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.background.vo.IntervalWarningVO;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalWarning;

/**
 * 地铁线路区间监测预警阈值业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalWarningService {
	/**
	 * 保存监测预警阈值信息
	 * @param warning
	 * @return
	 */
	Long insertObj(MetroLineIntervalWarning warning);
	/**
	 * 删除监测预警阈值信息
	 * @param intervalWariningId id
	 * @return
	 */
	boolean deleteObj(Long intervalWariningId);
	/**
	 * 更新监测预警阈值信息
	 * @param warning
	 * @return
	 */
	boolean updateObj(MetroLineIntervalWarning warning);
	/**
	 * 通过id查询监测预警阈值信息
	 * @param intervalWariningId id
	 * @return
	 */
	MetroLineIntervalWarning findObjById(Long intervalWariningId);
	/**
	 * 分页查询
	 * 线路区间左右线的监测预警阀值设置信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalWarning> findLineIntervalWarningInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	
	/**
	 * 获取某线路区间左或右线的监测预警阀值设置信息列表
	 * @param intervalId 区间id
	 * @param leftOrRight 左右线
	 * @return
	 */
	List<MetroLineIntervalWarning> findAllByIntervalIdAndLr(Long intervalId, String leftOrRight);
	/**
	 * 导入excel数据
	 * @param intervalId 区间id
	 * @param leftOrRight 左右线
	 * @param file 文件
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, String leftOrRight,
			MultipartFile file) throws Exception;
	
	/**
	 * 获取所有监测预警阀值设置信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * @return
	 */
	List<IntervalWarningVO> findQueryVOListForAll();
	
	/**
	 * 获取某线路左右线、参数的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param intervalId 区间id
	 * @param leftOrRight 左右线
	 * @param ringNum   环号
	 * @param param
	 * @return
	 */
	
	List<IntervalWarningVO> findQueryVOListForParams(Long intervalId,String leftOrRight,Integer ringNum, String param);


	/**
	 * 获取某线路左右线的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param intervalId 区间id
	 * @param leftOrRight 左右线
	 * @param ringNum   环号
	 * @return
	 */
	List<IntervalWarningVO> findQueryVOList(Long intervalId,String leftOrRight,Integer ringNum);
}
