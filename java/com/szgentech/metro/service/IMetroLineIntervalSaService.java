package com.szgentech.metro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.model.MetroLineIntervalSa;

/**
 * 地铁线路区间管片姿态业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalSaService {
	/**
	 * 保存区间管片姿态信息
	 * @param sa
	 * @return
	 */
	Long insertObj(MetroLineIntervalSa sa);
	/**
	 * 通过id查询管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	MetroLineIntervalSa findObjById(Long intervalSaId);
	/**
	 * 删除管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	boolean deleteObj(Long intervalSaId);
	/**
	 * 删除区间、左右线管片姿态全部信息
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteSaInfo(Long intervalId, String leftOrRight);
	/**
	 * 更新管片姿态信息
	 * @param sa
	 * @return
	 */
	boolean updateObj(MetroLineIntervalSa sa);
	/**
	 * 分页查询
	 * 线路区间管片姿态信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight  区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSa> findLineIntervalSaInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	/**
	 * 查询某个区间的管片姿态信息
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId);

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, MultipartFile file) throws Exception;
	
	/**
	 * 根据条件查询管片姿态信息，排顺序
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 为空则不区分
	 * @param startRingNum 开始环号，为0则从第1环开始
	 * @param endRingNum   结束环号    为0则查到最大环号
	 * @return
	 */
	public List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId, String leftOrRight, 
			int startRingNum, int endRingNum);
	
	/**
	 * 根据条件查询管片姿态信息，排顺序
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 为空则不区分
	 * @param startRingNum 开始环号，为0则从第1环开始
	 * @param endRingNum   结束环号    为0则查到最大环号
	 * @param isComplete   是否补齐未上传的环号信息，true：补齐，false：不用补齐
	 * @return
	 */
	public List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId, String leftOrRight, int startRingNum,
			int endRingNum, boolean isComplete);

}
