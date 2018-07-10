package com.szgentech.metro.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.ZookeTransactionException;
import com.szgentech.metro.dao.IMetroLineIntervalMdDao;
import com.szgentech.metro.dao.IMetroLineIntervalMdReDao;
import com.szgentech.metro.model.MetroLineIntervalMd;
import com.szgentech.metro.model.MetroLineIntervalMdRe;
import com.szgentech.metro.service.IMetroLineIntervalMdReService;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 地铁线路区间上传监测数据记录业务接口实现
 * 
 * @author hank
 *
 *         2016年8月17日
 */
@Service("lineIntervalMdReService")
public class MetroLineIntervalMdReService extends BaseService<MetroLineIntervalMdRe>
		implements IMetroLineIntervalMdReService {

	private static Logger logger = Logger.getLogger(MetroLineIntervalMdReService.class);

	@Autowired
	private IMetroLineIntervalMdReDao lineIntervalMdReDao;
	@Autowired
	private IMetroLineIntervalMdDao lineIntervalMdDao;

	/**
	 * 分页查询 上传监测数据记录信息
	 * 
	 * @param intervalId
	 *            线路区间id
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalMdRe> findLineIntervalMdReInfo(Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalMdRe> resultSet = getPageResultSet(params, pageNum, pageSize,
				lineIntervalMdReDao);
		return resultSet;
	}

	/**
	 * 沉降点监测数据上传
	 * 
	 * @return
	 */
	@Override
	public CommonResponse uploadLineIntervalMdReData(Long intervalId, String uploadFileUrl) {
		CommonResponse commonResponse = new CommonResponse();
		String msg = null;
		// 上传文件，入库上传记录
		MetroLineIntervalMdRe mdRe = new MetroLineIntervalMdRe();
		String fileName = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/") + 1);
		String originFileName = fileName.substring(fileName.indexOf("_") + 1);
		mdRe.setFileName(fileName);
		mdRe.setOriginFileName(originFileName);
		mdRe.setFileUrl(uploadFileUrl);
		mdRe.setIntervalId(intervalId);
		mdRe.setIsDel(0);
		// 获取该文件服务器地址
		String filepath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH") + "/" + fileName;
		// 读取excel文件内容到map
		InputStream instream = null;
		Workbook readwb = null;
		int result1 = -1;
		try {
			result1 = lineIntervalMdReDao.insertObj(mdRe); // 插入数据到数据库
			if (result1 > 0) {// 插入上传记录成功
				instream = new FileInputStream(filepath);
				readwb = Workbook.getWorkbook(instream);
				Sheet[] sheets = readwb.getSheets();
				// 获取第一个可见的sheet
				Sheet firstVisiableSheet = getFirstVisiableSheet(sheets);
				// 判断格式
				int resultCode = getTemplateFormat(firstVisiableSheet);
				if (resultCode == 0) {
					// 与当前使用的模板都不匹配
					msg = "与当前使用的模板都不匹配";
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult(msg);
					logger.error(msg);
					return commonResponse;
				}
				List<MetroLineIntervalMd> mdList = null;
				switch (resultCode) {
				// case 0: // 与当前使用的模板都不匹配
				// break;

				case 1: // 区间左线地面沉降监测(待开发格式 小坪石井).xls
					mdList = getSheetData1(firstVisiableSheet, mdRe.getId());

					break;

				case 2: // 沉降日报2017.12.19上午(待开发格式 小坪平沙).xls
					mdList = getSheetData2(firstVisiableSheet, mdRe.getId());
					break;

				case 3: // 当前使用
					// 开始读取数据
					// Sheet sheet = readwb.getSheet(0);
					Map<String, String> map = getSheetMap(firstVisiableSheet);
					mdList = getMdReInfoFromSheetMap(map, mdRe.getId());// 从文件中读取上传的沉降点监测数据列表
					break;
					
					case 4: // 出入段线
						mdList = getSheetData4(firstVisiableSheet, mdRe.getId());
						break;
					
					
				}

				if (CommonUtils.isNotNull(mdList)) {
					int result2 = lineIntervalMdDao.insertObjs(mdList); // 需要查重么
					if (result2 > 0) { // 批量入库记录数据成功
						commonResponse.setCode(Constants.CODE_SUCCESS);

						return commonResponse;
					} else {
						msg = "批量入库记录数据出错";
						logger.error(msg);
						throw new ZookeTransactionException(msg);
					}
				} else {
					msg = "文件内记录为空";
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult(msg);
					logger.error(msg);
					throw new ZookeTransactionException(msg);
				}
			} else {
				msg = "插入上传记录出错";
				logger.error(msg);
				throw new ZookeTransactionException(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			if (result1 > 0) {
				Map<String, Object> params = new HashMap<>();
				params.put("intervalMdReId", mdRe.getId());
				lineIntervalMdReDao.deleteObj(params);
			}
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult(e.getMessage());
			return commonResponse;
		}

	}
	
	
	/**
	 * 出入段线
	 * @param long1 
	 * 
	 * @param firstVisiableSheet
	 *            第一个可见的sheet
	 * @return
	 */
	private static List<MetroLineIntervalMd> getSheetData4(Sheet sheet, Long mdReId)  throws Exception{
		boolean result = false;
		int currentRows = 0;
		for (int i = 0; i < sheet.getRows(); i++) {
			System.out.println(sheet.getCell(0, i).getContents().trim());
			if (sheet.getCell(0, i).getContents().trim().contains("测号编号")) {
				currentRows = i;
				result = true;
				break; // 跳出循环，提高效率
			}
		}

		System.out.println("currentRows=" + currentRows);
		if (!result) {
			return null;
		}

		List<MetroLineIntervalMd> settlementPoinList = new ArrayList<>();
		int column = sheet.getColumns();// 总列数
		int row = sheet.getRows();// 总行数
//		System.out.println("=================================================");

		for (int i = 2; i < column; i = i + 4) {
			if (sheet.getCell(i, currentRows).getContents().trim().contains("备注")) {
				//System.out.println("备注的列数=" + i);
				break;
			}

			String monitorDateStr = sheet.getCell(i, currentRows).getContents().trim(); // 监测时间

			Calendar calendar = new GregorianCalendar(1900, 0, -1);
			if (monitorDateStr != null) {
				calendar.add(Calendar.DATE, Integer.parseInt(monitorDateStr));
			}
			Date date = calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			monitorDateStr = sdf.format(date);
			String monitorTimeInterval = "09:30:00";
//			System.out.println("监测时间: " + monitorDateStr);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date monitorDate = sdf2.parse(monitorDateStr + " " + monitorTimeInterval);

			for (int j = currentRows + 2; j < row; j++) {
				String mdNo = sheet.getCell(0, j).getContents().trim(); // 编号也可称作沉降点名称
				if (mdNo.equals("")) {
					break;
				}

				int errorColumn = 0;
				int errorRow = j+1;
				try {
					MetroLineIntervalMd md = new MetroLineIntervalMd();
					errorColumn = 1+1;
					Float startElevation = Float.valueOf(sheet.getCell(1, j).getContents().trim());// 初始高程
					errorColumn = i+1;
					Float thisElevation = Float.valueOf(sheet.getCell(i, j).getContents().trim());// 高程
					errorColumn = i + 2;
					Float thisVar = Float.valueOf(sheet.getCell(i + 1, j).getContents().trim());// 本次变化量
					errorColumn = i + 3;
					Float sumVar = Float.valueOf(sheet.getCell(i + 2, j).getContents().trim());// 累计变化量
					errorColumn = i + 4;
					Float spSpeed = Float.valueOf(sheet.getCell(i + 3, j).getContents().trim());// 沉降速率

					md.setStartElevation(startElevation);
					md.setThisElevation(thisElevation);
					md.setThisVar(thisVar);
					md.setSumVar(sumVar);
					md.setSpSpeed(spSpeed);
					md.setMonitorDate(monitorDate);
					md.setMdNo(mdNo);
					md.setMdReId(mdReId);
//					 System.out.println( "沉降点名称: " + mdNo + " 初始高程: " + startElevation + " 高程: " +thisElevation
//					 + " 本次变化量: " + thisVar + " 累计变化量: " + sumVar + " 沉降速率: "+ spSpeed);

					settlementPoinList.add(md);

				} catch (NumberFormatException e) {
					 throw new NumberFormatException("第" + errorRow + "行,第" +errorColumn + "列数据为空或错误");
				}
			}
		}

		return settlementPoinList;

	}

	
	

	/**
	 * 通过excel的sheet对象获取数据( 沉降日报2017.12.19上午(待开发格式 小坪平沙).xls)
	 * 
	 * @param sheet
	 * @return
	 */
	private static List<MetroLineIntervalMd> getSheetData2(Sheet sheet, Long mdReId) throws Exception {
		// int column = sheet.getColumns();// 总列数
		int row = sheet.getRows();// 总行数
		String strKey = "点号";
		List<MetroLineIntervalMd> settlementPoinList = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			String contents = sheet.getCell(0, i).getContents().trim();
			contents = contents.replaceAll(" ", "");
			if (contents.equals(strKey)) {
				// System.out.println("===============点号所在行======================" + i);

				String monitorTimeInterval = sheet.getCell(10, i - 1).getContents().trim();// 监测时段
				monitorTimeInterval = monitorTimeInterval.replaceAll(" ", "");
				if (monitorTimeInterval.equals("上午")) {
					monitorTimeInterval = "09:30:00";
				} else if (monitorTimeInterval.equals("下午")) {
					monitorTimeInterval = "16:30:00";
				} else {
					monitorTimeInterval = "09:30:00";
				}

				// 监测日期
				String monitorDateStr = "";
				Cell cell = sheet.getCell(8, i);
				// System.out.println("getType->" + cell.getType());
				DateCell dc = (DateCell) cell;
				Date date = dc.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				monitorDateStr = sdf.format(date);
				Date monitorDate = null;
				try {
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					monitorDate = sdf2.parse(monitorDateStr + " " + monitorTimeInterval);
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					throw new ParseException("第" + i + "行,第8列的监测日期错误,请参考模板文件", 0);
				}

				for (int j = i + 3; j < row; j++) { // 读取沉降点数据
					MetroLineIntervalMd intervalMd = new MetroLineIntervalMd();
					// 沉降点名称
					String mdNo = sheet.getCell(0, j).getContents().trim();
					if (mdNo.equals("") || mdNo.contains("对应点号")) {
						break;
					}
					
					
					

					int errorColumn = -1;
					int errorRow = j + 1;
					try {
						errorColumn = 2;
						Float startElevation = Float.parseFloat(sheet.getCell(1, j).getContents().trim());//初始高程
						
						errorColumn = 7;
						Float thisElevation = Float.parseFloat(sheet.getCell(6, j).getContents().trim()); // 高程值
						errorColumn = 8;
						Float thisVar = Float.parseFloat(sheet.getCell(7, j).getContents().trim()); // 沉降量
						errorColumn = 9;
						Float sumVar = Float.parseFloat(sheet.getCell(8, j).getContents().trim()); // 累计沉降量
						errorColumn = 10;
//						Float thisVar = Float.parseFloat(sheet.getCell(9, j).getContents().trim()); // 沉降速率

						intervalMd.setMdNo(mdNo);
						intervalMd.setStartElevation(startElevation);
						
						intervalMd.setThisElevation(thisElevation);
						intervalMd.setSumVar(sumVar);
						intervalMd.setThisVar(thisVar);
						intervalMd.setMonitorDate(monitorDate);
						intervalMd.setMdReId(mdReId);

						settlementPoinList.add(intervalMd);
					} catch (NumberFormatException e) {
						logger.error(e.getMessage());
						throw new NumberFormatException("第" + errorRow + "行,第" + errorColumn + "列数据错误或格式不正确");
					}
				}
			}
		}

		return settlementPoinList;
	}

	/**
	 * 通过excel的sheet对象获取数据（模板：区间左线地面沉降监测(待开发格式 小坪石井).xls）
	 * 
	 * @param sheet
	 *            第一个可见的sheet
	 * @return
	 */
	private static List<MetroLineIntervalMd> getSheetData1(Sheet sheet, Long mdReId) throws Exception {
		int column = sheet.getColumns();// 总列数
		int row = sheet.getRows();// 总行数

		String dateKey = "观测日期";
		List<MetroLineIntervalMd> settlementPoinList = new ArrayList<>();
		for (int i = 0; i < row; i++) {
			if (sheet.getCell(0, i).getContents().contains(dateKey)) {
				// List<MetroLineIntervalMd> spNameList = new ArrayList<>();
				for (int j = 3; j < column; j = j + 3) {

					String spName = sheet.getCell(j, i).getContents().trim(); // 沉降点名称
					if (!spName.contains("测点编号")) {
						break;
					}
					// System.out.println("spName-->"+spName +" ======= " + j+" --> "+ i);
					if (spName.contains(":")) {
						String[] spNames = spName.split(":");
						spName = spNames[1];
					}

					for (int x = i + 3; i < row; x++) {
						// if (sheet.getCell(0, x).getContents().contains("沉降值负值表示下沉，正值表示上升")) {
						// break;
						// }
						MetroLineIntervalMd intervalMd = new MetroLineIntervalMd();
						intervalMd.setMdNo(spName);
						// 监测时间
						String year = sheet.getCell(0, x).getContents().trim(); // 年
						if (year.equals("")) { // 观测日期的年为空，结束遍历
							break;
						}

						String month = sheet.getCell(1, x).getContents().trim();// 月
						String day = sheet.getCell(2, x).getContents().trim();// 日
						String monitorTimeInterval = "09:30:00";
						if (day.contains("a")) {
							day = day.replace("a", "").trim();
						} else if (day.contains("p")) {
							day = day.replace("p", "").trim();
							monitorTimeInterval = "16:30:00";
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						try {
							Date date = sdf.parse(year + "-" + month + "-" + day + " " + monitorTimeInterval);
							// String format = sdf.format(date);
							// System.out.println("format="+format);
							intervalMd.setMonitorDate(date);
						} catch (ParseException e) {
							logger.error(e.getMessage());
							int errorRow = x + 1;
							throw new ParseException("第" + errorRow + "行的监测日期错误,请参考模板文件", 0);
							// break;
						}
						int errorRow = x + 1;
						int errorColumn = j + 1;
						try {
							Float thisElevation = Float.valueOf(sheet.getCell(j, x).getContents().trim()); // 本次高程
							errorColumn = j + 2;
							Float thisVar = Float.valueOf(sheet.getCell(j + 1, x).getContents().trim()); // 本次变化量
							errorColumn = j + 2;
							Float sumVar = Float.valueOf(sheet.getCell(j + 2, x).getContents().trim()); // 累计变化量
							// System.out.println(thisVar + " ----> " + sumVar + " ----> " + year+ " ----> "
							// +j+ " ----> " +x);
							intervalMd.setThisElevation(thisElevation);
							intervalMd.setSumVar(sumVar);
							intervalMd.setThisVar(thisVar);
							intervalMd.setMdReId(mdReId);
							settlementPoinList.add(intervalMd);
						} catch (NumberFormatException e) {
							logger.error(e.getMessage());
							throw new NumberFormatException("第" + errorRow + "行,第" + errorColumn + "列数据为空或错误");
						}

					}

				}

			}
		}
		return settlementPoinList;
	}

	/**
	 * 判断模板文件格式
	 * 
	 * @param sheet
	 */
	public int getTemplateFormat(Sheet sheet) {
		int resultCode = 0;
		int columns = sheet.getColumns(); // 总列数
		System.out.println("总列数：" + columns);

		if (sheet.getCell(0, 4).getContents().contains("观测日期")) {
			// System.out.println("区间左线地面沉降监测(待开发格式).xls");
			resultCode = 1;

		} else if (sheet.getCell(0, 5).getContents().contains("点号")) {
			resultCode = 2;
			// System.out.println("沉降日报2017.12.19上午(待开发格式)");
		} else if (sheet.getCell(0, 6).getContents().contains("监测时间")) {
			// System.out.println("Excel/171219_161109_（当前使用）.xls");
			resultCode = 3;
		} else if (isHasTestInstrumentStr(sheet)) {
			resultCode = 4;
		}
		return resultCode;
	}

	
	/**
	 * 判断是否有 "测试仪器字" 符串
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean isHasTestInstrumentStr(Sheet sheet) {
		// System.out.println("总行数=" + sheet.getRows());
		// System.out.println("总列数：" + sheet.getColumns());
		boolean result = false;
		for (int i = 0; i < sheet.getRows(); i++) {
			System.out.println(sheet.getCell(0, i).getContents().trim());
			if (sheet.getCell(0, i).getContents().trim().contains("测试仪器")) {
				result = true;
				break;
			}
		}

		return result;
	}
	
	
	
	/**
	 * 获取第一个可见的的Sheet
	 * @param sheets
	 * @return 
	 */
	private static Sheet getFirstVisiableSheet(Sheet[] sheets) {
		Sheet firstVisiableSheet = null;
		for (Sheet sheet : sheets) {
			if (!sheet.isHidden()) {
				// String name = sheet.getName();
				// System.out.println("sheet名称：" + name);
				firstVisiableSheet = sheet;
				break;
			}
		}
		return firstVisiableSheet;
	}

	/**
	 * 从文件中读取上传的沉降点监测数据列表
	 */
	private List<MetroLineIntervalMd> getMdReInfoFromSheetMap(Map<String, String> map, Long mdReId) throws Exception {

		// 获取检测日期
		String dateKey = "监测时间";
		Date monitorDate = null;
		if (map.keySet().contains(dateKey)) {
			try {
				String dataValue = map.get(dateKey);
				String[] dateSplit = dataValue.split("#");
				String timeStr = dateSplit[0].trim();
				String dateStr = dateSplit[3].trim();
				SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm");
				monitorDate = f.parse(dateStr + " " + timeStr);
				// mdRe.setMonitorDate(monitorDate);

			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ParseException("监测日期错误,请参考模板文件", 0);
			}
		}

		List<MetroLineIntervalMd> mdList = new ArrayList<MetroLineIntervalMd>();
		// 根据文件来查重还是根据沉降点来查重
		for (String key : map.keySet()) {
			if (!key.trim().equals("监测时间")) { // 为沉降点详细信息
				String value = map.get(key);
				String[] split = value.split("#");
				String startElevation = split[0];
				String upnoceElevation = split[1];
				String thisElevation = split[2];
				String thisVar = split[3];
				String sumVar = split[4];
				String errorRow = split[split.length - 1];
				// String column = split[5];
				String mdNo = key.trim();
				MetroLineIntervalMd md = new MetroLineIntervalMd();
				int errorColumn = -1;
				try {
					md.setMdReId(mdReId);
					errorColumn = 2;
					md.setStartElevation(Float.valueOf(startElevation.trim()));
					errorColumn = 3;
					md.setUpnoceElevation(Float.valueOf(upnoceElevation.trim()));
					errorColumn = 4;
					md.setThisElevation(Float.valueOf(thisElevation.trim()));
					errorColumn = 5;
					md.setThisVar(Float.valueOf(thisVar.trim()));
					errorColumn = 6;
					md.setSumVar(Float.valueOf(sumVar.trim()));
					md.setMdNo(mdNo);
					md.setMonitorDate(monitorDate);
					mdList.add(md);
				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					throw new NumberFormatException("第" + errorRow + "行,第" + errorColumn + "列数据错误或格式不正确");
				}
			}
		}

		if (CommonUtils.isNotNull(mdList)) {
			return mdList;
		} else {
			return null;
		}
	}

	/**
	 * 通过excel的sheet对象获取数据，key为第一列，value为后面所有列（用#隔开） 直接读取有数据的
	 * 
	 * @param s
	 * @return
	 */
	private Map<String, String> getSheetMap(Sheet s) {
		int column = s.getColumns();
		int row = s.getRows();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < row; i++) {
			String key = s.getCell(0, i).getContents();
			if (i < 9 && !key.contains("监测时间")) {
				continue;
			}
			if (key.startsWith("说明")) {
				break;
			}
			String value = "";
			for (int j = 1; j < column; j++) {
				if (key.contains("监测时间") && j == 1) { // 处理excel的24小时时间格式
					Cell cell = s.getCell(j, i);
					value += "#" + formateExcelTime(cell);
				} else {
					Cell cell = s.getCell(j, i);
					value += "#" + cell.getContents();
				}

				// System.out.println(cell.getContents());
			}
			value += (i + 1);
			map.put(key, value.substring(1));
		}
		map.remove("");
		return map;
	}

	/**
	 * <概要描述> excel文件中时间类型数据格式转换 默认读取出来回变成12小时制，这里转换成24小时制
	 * @param formatecell
	 * @return
	 */
	public static String formateExcelTime(Cell formatecell) {
		try {
			java.util.Date mydate = null;
			DateCell datecll = (DateCell) formatecell;
			mydate = datecll.getDate();
			long time = (mydate.getTime() / 1000) - 60 * 60 * 8;// 格林乔治 东八区
			mydate.setTime(time * 1000);
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
			return formatter.format(mydate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 沉降点监测数据记录删除
	 * 
	 * @param intervalMdReId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean deleteLineIntervalMdReInfo(Long intervalMdReId) throws Exception {
		String msg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalMdReId", intervalMdReId);
		int r = lineIntervalMdReDao.deleteObj(params);
		if (r > 0) {
			int r2 = lineIntervalMdDao.deleteByIntervalMdReId(intervalMdReId);
			if (r2 > 0) {
				return true;
			} else {
				msg = "删除记录成功，删除记录数据为0";
				logger.error(msg);
				return true;
				// throw new ZookeTransactionException(msg);
			}
		} else {
			msg = "删除记录出错";
			logger.error(msg);
			throw new ZookeTransactionException(msg);
		}
	}
}
