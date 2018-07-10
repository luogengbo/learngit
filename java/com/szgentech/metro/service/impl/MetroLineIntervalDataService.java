package com.szgentech.metro.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
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
import com.szgentech.metro.base.utils.ZookeTransactionException;
import com.szgentech.metro.dao.IMetroLineIntervalDataDao;
import com.szgentech.metro.model.MetroLineIntervalData;
import com.szgentech.metro.service.IMetroLineIntervalDataService;

import jxl.Sheet;
import jxl.Workbook;

/**
 * 地铁线路区间导向数据业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalDataService")
public class MetroLineIntervalDataService extends BaseService<MetroLineIntervalData> implements IMetroLineIntervalDataService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalDataService.class);
	@Autowired
	private IMetroLineIntervalDataDao lineIntervalDataDao;

	/**
	 * 分页查询
	 * 区间导向数据信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalData> findLineIntervalDataInfo(
			Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		PageResultSet<MetroLineIntervalData> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalDataDao);
		return resultSet;
	}
	/**
	 * 删除导向数据信息
	 * @param intervalDataId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalDataId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalDataId", intervalDataId);
		int r = lineIntervalDataDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除区间、左右线导向数据
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	public boolean deleteDataInfo(Long intervalId, String leftOrRight){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return lineIntervalDataDao.deleteObjall(params);
	}
	/**
	 * 获取某线路区间左或右线的导向数据信息列表
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	@Override
	public List<MetroLineIntervalData> findAllByIntervalIdAndLr(
			Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return lineIntervalDataDao.findAllByIntervalIdAndLr(params);
	}
	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param leftOrRight
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean importExcelData(String intervalId, String leftOrRight,
			MultipartFile file) throws Exception{
		//读取excel文件内容
		InputStream instream = file.getInputStream();
		Workbook readwb = Workbook.getWorkbook(instream);
		Sheet sheet = readwb.getSheet(0);
		List<String[]> sheetList = getSheetData(sheet);
		if(CommonUtils.isNotNull(sheetList)){
			for(String[] value : sheetList){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("leftOrRight", leftOrRight);
				params.put("mileage", value[0]);
				MetroLineIntervalData result = lineIntervalDataDao.findUniqueData(params);
			    if(result == null){ //新增数据
			    	MetroLineIntervalData save = new MetroLineIntervalData();
			    	save.setIntervalId(Long.parseLong(intervalId));
			    	save.setLeftOrRight(leftOrRight);
			    	save.setMileage(Float.valueOf(value[0]));
			    	save.setMapX(new BigDecimal(value[1]));
			    	save.setMapY(new BigDecimal(value[2]));
			    	save.setMapZ(new BigDecimal(value[3]));

			    	Long i = insertObj(save);
			    	if(i == null){ //新增失败
			    		logger.error("新增数据失败，区间："+intervalId+" 左右线："+leftOrRight+" 里程："+value[0]);
			    	    throw new ZookeTransactionException("新增数据失败");
			    	}
			    }else{ //更新数据
			    	result.setMapX(new BigDecimal(value[1]));
			    	result.setMapY(new BigDecimal(value[2]));
			    	result.setMapZ(new BigDecimal(value[3]));
			    	boolean j = updateObj(result);
			    	if(!j){ //更新失败
			    		logger.error("更新数据失败，id："+result.getId());
			    	    throw new ZookeTransactionException("更新数据失败");
			    	}
			    }
			}
		}else{
			logger.error("解析导入的文件数据为空");
		}
		return true;
	}
	/**
	 * 保存导向数据信息
	 * @param data
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalData data) {
		int r = lineIntervalDataDao.insertObj(data);
		if(r > 0){
			return data.getId();
		}
		return null;
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
		if(CommonUtils.isNotNull(result)){
			return result;
		}
		return null;
	}
	/**
	 * 更新导向数据信息
	 * @param data
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalData data) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalDataId", data.getId());
		params.put("intervalId", data.getIntervalId());
		params.put("mapX", data.getMapX());
		params.put("mapY", data.getMapY());
		params.put("mapZ", data.getMapZ());
		params.put("mileage", data.getMileage());
		params.put("leftOrRight", data.getLeftOrRight());
		params.put("isDel", data.getIsDel());
		int r = lineIntervalDataDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
}
