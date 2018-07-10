package com.szgentech.metro.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.mv.JModelAndView;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.base.utils.JsTreeUtil;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroDictionary;
import com.szgentech.metro.model.MetroLineIntervalWarning;
import com.szgentech.metro.service.IMetroDictionaryService;
import com.szgentech.metro.service.IMetroLineIntervalWarningService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 监测预警设置管理控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/project-info/monitor")
public class ProjectInfoMonitorController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoMonitorController.class);

	@Autowired
	private IMetroLineIntervalWarningService lineIntervalWarningService;
	@Autowired
	private IMetroDictionaryService dictionaryService;
	@Autowired
	private ISysRightService rightService;

	/**
	 * 监测预警设置首页
	 */
	@RequestMapping("/index")
	public String monitorSet() {

		return "/project-info/item_monitor";
	}
	
	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public List<Jstree> getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(getCurrentUser().getId());
		String[] urls = new String[4];
		urls[3] = "/project-info/monitor/lrinfo";
		Boolean[] diss = new Boolean[4];
		diss[0] = true;
		diss[1] = true;
		diss[2] = true;
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}
	/**
	 * 加载左右线的监测预警信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/lrinfo")
	public String lrinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		request.setAttribute("dics", dicSet.getList());
		request.setAttribute("intervalId", intervalId);
		request.setAttribute("leftOrRight", leftOrRight);
		return "/project-info/item_monitor_right";
	}

	/**
	 * 监测预警阈值信息保存
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "监测预警设置", operate = "新增监测预警阈值信息")
	@RequestMapping(value = "/lrinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse lrinfoSave(MetroLineIntervalWarning warning) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalWarningService.insertObj(warning);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存监测预警阈值信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存监测预警阈值信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新监测预警阈值信息
	 */
	@SysControllorLog(menu = "监测预警设置", operate = "编辑监测预警阈值信息")
	@RequestMapping(value = "/lrinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateLrinfo(MetroLineIntervalWarning warning) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalWarningService.updateObj(warning);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新城市线路信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新城市线路信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 删除监测预警阈值信息
	 */
	@SysControllorLog(menu = "监测预警设置", operate = "删除监测预警阈值信息")
	@RequestMapping(value = "/lrinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLrinfo(@RequestParam("id") Long id) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalWarningService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除监测预警阈值信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除监测预警阈值信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 将监测预警阈值信息写入到excel
	 * 
	 * @param warns
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeExcel(List<MetroLineIntervalWarning> warns) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "监测预警数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("监测预警阈值", 0);
			String[] title = { "参数代号", "参数含义", "推进预警", "拼装预警", "停机预警", "开始环号", "结束环号", "红色预警上限", "红色预警下限", "橙色预警上限",
					"橙色预警下限", "黄色预警上限", "黄色预警下限", "预警扫描频率", "红色预警持续时间", "橙色预警持续时间", "黄色预警持续时间" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			// NumberFormat nf = new jxl.write.NumberFormat("0.######");
			// WritableCellFormat wcfN = new WritableCellFormat(nf);
			// jxl.write.Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			if (CommonUtils.isNotNull(warns)) {
				for (int i = 0; i < warns.size(); i++) {
					MetroLineIntervalWarning warn = warns.get(i);
					sheet.addCell(new Label(0, i + 1, warn.getParam()));
					sheet.addCell(new Label(1, i + 1, warn.getParamDic().getDicMean()));
					int category = warn.getCategory() == null ? 0 : warn.getCategory();
					if (category == 1 || category == 3 || category == 5 || category == 7) {
						sheet.addCell(new Label(2, i + 1, "1"));
					} else {
						sheet.addCell(new Label(2, i + 1, "0"));
					}
					if (category == 2 || category == 3 || category == 6 || category == 7) {
						sheet.addCell(new Label(3, i + 1, "1"));
					} else {
						sheet.addCell(new Label(3, i + 1, "0"));
					}
					if (category == 4 || category == 5 || category == 6 || category == 7) {
						sheet.addCell(new Label(4, i + 1, "1"));
					} else {
						sheet.addCell(new Label(4, i + 1, "0"));
					}
					sheet.addCell(new Label(5, i + 1, String.valueOf(warn.getStartRingNum())));
					sheet.addCell(new Label(6, i + 1, String.valueOf(warn.getEndRingNum())));
					sheet.addCell(new Label(7, i + 1, String.valueOf(warn.getRedWarningMax())));
					sheet.addCell(new Label(8, i + 1, String.valueOf(warn.getRedWarningMin())));
					sheet.addCell(new Label(9, i + 1, String.valueOf(warn.getOrangeWarningMax())));
					sheet.addCell(new Label(10, i + 1, String.valueOf(warn.getOrangeWarningMin())));
					sheet.addCell(new Label(11, i + 1, String.valueOf(warn.getYellowWarningMax())));
					sheet.addCell(new Label(12, i + 1, String.valueOf(warn.getYellowWarningMin())));
					sheet.addCell(new Label(13, i + 1, String.valueOf(warn.getScanningFrequency())));
					sheet.addCell(new Label(14, i + 1, String.valueOf(warn.getRedDuration())));
					sheet.addCell(new Label(15, i + 1, String.valueOf(warn.getOrangeDuration())));
					sheet.addCell(new Label(16, i + 1, String.valueOf(warn.getYellowDuration())));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("监测预警阈值信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("监测预警阈值信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 导出监测预警阈值信息
	 */
	@SysControllorLog(menu = "监测预警设置", operate = "导出监测预警阈值信息")
	@RequestMapping(value = "/lrinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportLrinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroLineIntervalWarning> warns = lineIntervalWarningService
					.findAllByIntervalIdAndLr(Long.parseLong(intervalId), leftOrRight);
			String excelFileName = writeExcel(warns);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出监测预警阈值信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导入监测预警阈值信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "监测预警设置", operate = "导入监测预警阈值信息")
	@RequestMapping(value = "/lrinfo/import", method = RequestMethod.POST)
	public String uploadCityProjectPdf(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalWarningService.importExcelData(intervalId, leftOrRight, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				request.setAttribute("msg", "导入失败");
				return "/common/error";
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			request.setAttribute("msg", "导入异常");
			return "/common/error";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/monitor/lrinfo?intervalId=" + intervalId + "&leftOrRight=" + leftOrRight;
	}

	/**
	 * 监测预警信息编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/lrinfo/to-edit")
	public ModelAndView lrinfoToEdit(@RequestParam("id") Long id) {
		MetroLineIntervalWarning warn = lineIntervalWarningService.findObjById(id);
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		JModelAndView mv = new JModelAndView("/project-info/item_monitor_right_edit", request, response);
		mv.addObject("model", warn);
		mv.addObject("dics", dicSet.getList());
		return mv;
	}

	/**
	 * 分页查找左右线的监测预警信息
	 * 
	 * @return
	 */
	@RequestMapping("/lrinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalWarning> findLrinfo(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroLineIntervalWarning> resultSet = lineIntervalWarningService
				.findLineIntervalWarningInfo(intervalId, leftOrRight, pageNum, pageSize);
		return resultSet;
	}

}
