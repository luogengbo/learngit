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

import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.StringUtil;
import com.szgentech.metro.base.utils.ZookeTransactionException;
import com.szgentech.metro.dao.IMetroLineIntervalSaDao;
import com.szgentech.metro.model.MetroLineIntervalSa;
import com.szgentech.metro.service.IMetroLineIntervalSaService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 地铁线路区间管片姿态业务接口实现
 * 
 * @author hank
 *
 *         2016年8月17日
 */
@Service("lineIntervalSaService")
public class MetroLineIntervalSaService extends BaseService<MetroLineIntervalSa> implements IMetroLineIntervalSaService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalSaService.class);

	@Autowired
	private IMetroLineIntervalSaDao lineIntervalSaDao;

	/**
	 * 分页查询 线路区间管片姿态信息
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
	public PageResultSet<MetroLineIntervalSa> findLineIntervalSaInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroLineIntervalSa> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalSaDao);
		return resultSet;
	}

	/**
	 * 保存区间管片姿态信息
	 * 
	 * @param sa
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalSa sa) {
		int r = lineIntervalSaDao.insertObj(sa);
		if (r > 0) {
			return sa.getId();
		}
		return null;
	}

	/**
	 * 通过id查询管片姿态信息
	 * 
	 * @param intervalSaId
	 * @return
	 */
	@Override
	public MetroLineIntervalSa findObjById(Long intervalSaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", intervalSaId);
		return lineIntervalSaDao.findObjById(params);
	}

	/**
	 * 删除管片姿态信息
	 * 
	 * @param intervalSaId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalSaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", intervalSaId);
		int r = lineIntervalSaDao.deleteObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 删除区间、左右线管片姿态信息
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteSaInfo(Long intervalId, String leftOrRight){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return lineIntervalSaDao.deleteObjall(params);
	}

	/**
	 * 更新管片姿态信息
	 * 
	 * @param sa
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalSa sa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", sa.getId());
		params.put("ringNum", sa.getRingNum());
		params.put("intervalId", sa.getIntervalId());
		params.put("leftOrRight", sa.getLeftOrRight());
		params.put("horizontalDev", sa.getHorizontalDev());
		params.put("verticalDev", sa.getVerticalDev());
		int r = lineIntervalSaDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		return lineIntervalSaDao.findLineIntervalSas(params);
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
				String leftOrRight = "";
				if (value[1].equals("左线")) {
					leftOrRight = "l";
				} else if (value[1].equals("右线")) {
					leftOrRight = "r";
				}
				MetroLineIntervalSa save = new MetroLineIntervalSa();
				save.setIntervalId(Long.parseLong(intervalId));
				save.setRingNum(value[0]);
				save.setLeftOrRight(leftOrRight);
				save.setHorizontalDev(Float.parseFloat(value[2]));
				save.setVerticalDev(Float.parseFloat(value[3]));
				save.setDateTime(StringUtil.stringToDate(value[4]));
				Long i = insertObj(save);
				if (i == null) { // 新增失败
					logger.error("新增数据失败，区间：" + intervalId + " 环号：" + value[0]);
					throw new ZookeTransactionException("新增数据失败");
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
	 * 根据条件查询管片姿态信息，排顺序
	 * @param intervalId   区间ID
	 * @param leftOrRight  左右线，左线："l",右线："r" 为空则不区分
	 * @param startRingNum 开始环号，为0则从第1环开始
	 * @param endRingNum   结束环号    为0则查到最大环号
	 * @return
	 */
	public List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId, String leftOrRight, 
			int startRingNum, int endRingNum) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		if(CommonUtils.isNotNull(leftOrRight)){
			params.put("leftOrRight", leftOrRight);
		}
		if(startRingNum!=0){
			params.put("startRingNum", startRingNum);
		}
		if(endRingNum!=0){
			params.put("endRingNum", endRingNum);
		}
		return lineIntervalSaDao.findLineIntervalSas(params);
	}

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
			int endRingNum, boolean isComplete) {
		List<MetroLineIntervalSa> saList = findLineIntervalSas(intervalId, leftOrRight, startRingNum, endRingNum);
		//需要补齐环号数据
		if(isComplete){
			int preRingNum = 0;
			// 初始化上一环号
			for (MetroLineIntervalSa sa:saList) {
				if(sa.getRingNum() == null){
					continue;
				}
				sa.setRingNum(sa.getRingNum().trim());
				if("".equals(sa.getRingNum())){
					continue;
				}
				//取第一个不为空的环号即可
				preRingNum = Integer.parseInt(sa.getRingNum());
				break;
			}
			boolean nonFlag = false;
			if(preRingNum == 0){
				nonFlag = true;
				preRingNum = startRingNum;
			}
			List<MetroLineIntervalSa> tempSaList = new ArrayList<MetroLineIntervalSa>();
			// 补齐前面环号信息
			for (int j = startRingNum; j < (nonFlag?preRingNum+1:preRingNum); j++) {
				MetroLineIntervalSa tempSa = new MetroLineIntervalSa();
				tempSa.setIntervalId(intervalId);
				tempSa.setLeftOrRight(leftOrRight);
				tempSa.setRingNum(String.valueOf(j));
				tempSaList.add(tempSa);
			}
			for (MetroLineIntervalSa sa:saList) {
				if(sa.getRingNum() == null){
					continue;
				}
				sa.setRingNum(sa.getRingNum().trim());
				if("".equals(sa.getRingNum())){
					continue;
				}
				int curRingNum = Integer.parseInt(sa.getRingNum());
				//第一条时初始化上一个环号
				if(preRingNum == 0){
					preRingNum = curRingNum;
				}
				// 补齐中间缺失的环号对象
				for (int i = preRingNum+1; i < curRingNum; i++) {
					MetroLineIntervalSa tempSa = new MetroLineIntervalSa();
					tempSa.setIntervalId(intervalId);
					tempSa.setLeftOrRight(leftOrRight);
					tempSa.setRingNum(String.valueOf(i));
					tempSaList.add(tempSa);
				}
				preRingNum = curRingNum;
				tempSaList.add(sa);
			}
			// 补齐后面环号信息
			for (int k = preRingNum+1; k <= endRingNum; k++) {
				MetroLineIntervalSa tempSa = new MetroLineIntervalSa();
				tempSa.setIntervalId(intervalId);
				tempSa.setLeftOrRight(leftOrRight);
				tempSa.setRingNum(String.valueOf(k));
				tempSaList.add(tempSa);
			}
			saList = tempSaList;
		}
		return saList;
	}

}
