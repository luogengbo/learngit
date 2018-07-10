package com.szgentech.metro.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szgentech.metro.background.vo.IntervalWarningVO;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.base.utils.ZookeTransactionException;
import com.szgentech.metro.dao.IMetroLineIntervalStDao;
import com.szgentech.metro.model.MetroLineIntervalSt;
import com.szgentech.metro.service.IMetroLineIntervalStService;
import com.szgentech.metro.service.IMetroLineIntervalWarningService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 地铁线路区间盾尾间隙业务接口实现
 * 
 * @author hank
 *
 *         2016年8月17日
 */
@Service("lineIntervalStService")
public class MetroLineIntervalStService extends BaseService<MetroLineIntervalSt> implements IMetroLineIntervalStService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalStService.class);

	@Autowired
	private IMetroLineIntervalStDao lineIntervalStDao;

	@Autowired
	private IMetroLineIntervalWarningService lineIntervalWarningService;

	/**
	 * 分页查询 线路区间盾尾间隙信息
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param leftOrRight
	 * 			 区间左右线
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalSt> findLineIntervalStInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroLineIntervalSt> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalStDao);
		return resultSet;
	}

	/**
	 * 保存区间盾尾间隙信息
	 * 
	 * @param st
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalSt st) {
		int r = lineIntervalStDao.insertObj(st);
		if (r > 0) {
			return st.getId();
		}
		return null;
	}

	/**
	 * 删除区间、左右盾尾间隙全部信息
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteStInfo(Long intervalId, String leftOrRight){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return lineIntervalStDao.deleteObjall(params);
	}
	
	/**
	 * 通过id查询盾尾间隙信息
	 * 
	 * @param intervalStId
	 * @return
	 */
	@Override
	public MetroLineIntervalSt findObjById(Long intervalStId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", intervalStId);
		return lineIntervalStDao.findObjById(params);
	}

	/**
	 * 删除盾尾间隙信息
	 * 
	 * @param intervalStId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalStId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", intervalStId);
		int r = lineIntervalStDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更新盾尾间隙信息
	 * 
	 * @param st
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalSt st) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", st.getId());
		params.put("intervalId", st.getIntervalId());
		params.put("ringNum", st.getRingNum());
		params.put("leftOrRight", st.getLeftOrRight());
		params.put("stUp", st.getStUp());
		params.put("stDown", st.getStDown());
		params.put("stLeft", st.getStLeft());
		params.put("stRight", st.getStRight());
		int r = lineIntervalStDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<MetroLineIntervalSt> findLineIntervalSts(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		return lineIntervalStDao.findLineIntervalSts(params);
	}

	@Override
	public boolean importExcelData(String intervalId, MultipartFile file) throws Exception {
		// 读取excel文件内容
		InputStream instream = file.getInputStream();
		Workbook readwb = Workbook.getWorkbook(instream);
		Sheet sheet = readwb.getSheet(0);
		List<String[]> sheetList = getSheetData(sheet);
		if (CommonUtils.isNotNull(sheetList)) {
			for (String[] value : sheetList) {
				Map<String, Object> params = new HashMap<String, Object>();
				String leftOrRight = "";
				if (value[1].equals("左线")) {
					leftOrRight = "l";
				} else if (value[1].equals("右线")) {
					leftOrRight = "r";
				}
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("ringNum", value[0]);
				params.put("leftOrRight", leftOrRight);
				MetroLineIntervalSt result = lineIntervalStDao.findUniqueData(params);
				if (result == null) { // 新增数据
					MetroLineIntervalSt save = new MetroLineIntervalSt();
					save.setIntervalId(Long.parseLong(intervalId));
					save.setRingNum(value[0]);
					save.setLeftOrRight(leftOrRight);
					save.setStUp(Float.parseFloat(value[2]));
					save.setStDown(Float.parseFloat(value[3]));
					save.setStLeft(Float.parseFloat(value[4]));
					save.setStRight(Float.parseFloat(value[5]));
					save.setDateTime(StringUtil.stringToDate(value[6]));

					Long i = insertObj(save);
					if (i == null) { // 新增失败
						logger.error("新增数据失败，区间：" + intervalId + " 环号：" + value[0]);
						throw new ZookeTransactionException("新增数据失败");
					}
				} else { // 更新数据
					result.setStUp(Float.parseFloat(value[2]));
					result.setStDown(Float.parseFloat(value[3]));
					result.setStLeft(Float.parseFloat(value[4]));
					result.setStRight(Float.parseFloat(value[5]));
					result.setDateTime(StringUtil.stringToDate(value[6]));
					boolean j = updateObj(result);
					if (!j) { // 更新失败
						logger.error("更新数据失败，id：" + result.getId());
						throw new ZookeTransactionException("更新数据失败");
					}
				}
			}
		} else {
			logger.error("解析导入的文件数据为空");
		}
		return true;
	}

	/**
	 * 通过excel的sheet对象获取数据
	 *
	 * @param s
	 * @return
	 */
	private List<String[]> getSheetData(Sheet s) {
		int column = s.getColumns();
		int row = s.getRows();
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 1; i < row; i++) {
			String[] value = new String[column];
			for (int j = 0; j < column; j++) {
				value[j] = s.getCell(j, i).getContents();
			}
			result.add(value);
		}
		if (CommonUtils.isNotNull(result)) {
			return result;
		}
		return null;
	}
	/**
	 * 根据条件查询盾尾间隙数据和预警级别
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 
	 *@param ringNum 环号
	 * @return
	 */
	public MetroLineIntervalSt findLineIntervalSt(Long intervalId, String leftOrRight, int ringNum) {
		if(intervalId ==null || !CommonUtils.isNotNull(leftOrRight)
				|| ringNum < 1){
			return new MetroLineIntervalSt();
		}
		// 1、查询盾尾间隙信息
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		MetroLineIntervalSt st = lineIntervalStDao.findUniqueData(params);
		if(st == null){
			return new MetroLineIntervalSt();
		}
		// 2、查询该环号的盾尾间隙预警信息
		List<IntervalWarningVO> list = lineIntervalWarningService.findQueryVOListForParams(
				intervalId, leftOrRight, ringNum, "Z0001");
		if(list == null || list.size() < 1){
			return st;
		}
		IntervalWarningVO warningVo = list.get(0);
		// 3、判断盾尾间隙上下左右预警级别
		st.setUpLevel(getWarnLevel(st.getStUp(), warningVo));
		st.setDownLevel(getWarnLevel(st.getStDown(), warningVo));
		st.setLeftLevel(getWarnLevel(st.getStLeft(), warningVo));
		st.setRightLevel(getWarnLevel(st.getStRight(), warningVo));
		return st;
	}
	
	/**
	 * 获取预警级别
	 * @param fCurValue
	 * @param warningVo
	 * @return
	 */
	private String getWarnLevel(Float fCurValue, IntervalWarningVO warningVo){
		if(warningVo == null){
			return Constants.WARN_LEVEL_NONE;
		}
		if (fCurValue >= warningVo.getRedWarningMax()) { // 红色上限预警
			return Constants.WARN_LEVEL_RED_MAX;
		} else if (fCurValue >= warningVo.getOrangeWarningMax()
				&& fCurValue < warningVo.getRedWarningMax()) { // 橙色上限预警
			return Constants.WARN_LEVEL_ORANGE_MAX;
		} else if (fCurValue > warningVo.getRedWarningMin()
				&& fCurValue <= warningVo.getOrangeWarningMin()) { // 橙色下限预警
			return Constants.WARN_LEVEL_ORANGE_MIN;
		} else if (fCurValue <= warningVo.getRedWarningMin()) { // 红色下限预警
			return Constants.WARN_LEVEL_RED_MIN;
		} else if (fCurValue <= warningVo.getYellowWarningMin()
				&& fCurValue > warningVo.getOrangeWarningMin()) { // 黄色下限预警
			return Constants.WARN_LEVEL_YELLOW_MIN;
		} else if (fCurValue >= warningVo.getYellowWarningMax()
				&& fCurValue < warningVo.getOrangeWarningMax()) { // 黄色上限预警
			return Constants.WARN_LEVEL_YELLOW_MAX;
		}
		return Constants.WARN_LEVEL_NORMAL;
	}
}
