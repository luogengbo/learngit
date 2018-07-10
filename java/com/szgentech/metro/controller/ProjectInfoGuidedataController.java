package com.szgentech.metro.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.szgentech.metro.base.aop.SysControllorLog;
import com.szgentech.metro.base.controller.BaseController;
import com.szgentech.metro.base.model.CommonResponse;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.utils.CommonUtils;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.Constants;
import com.szgentech.metro.model.MetroCity;
import com.szgentech.metro.model.MetroLine;
import com.szgentech.metro.model.MetroLineInterval;
import com.szgentech.metro.model.MetroLineIntervalData;
import com.szgentech.metro.service.IMetroLineIntervalDataService;
import com.szgentech.metro.service.ISysRightService;
import com.szgentech.metro.vo.Jstree;
import com.szgentech.metro.vo.Jstree.State;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 导向数据管理控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/project-info/guidedata")
public class ProjectInfoGuidedataController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoGuidedataController.class);

	@Autowired
	private IMetroLineIntervalDataService lineIntervalDataService;
	@Autowired
	private ISysRightService rightService;

	/**
	 * 导向数据管理首页
	 */
	@RequestMapping("/index")
	public String guidedataIndex() {
		return "/project-info/item_guide";
	}
	
	
	/**
	 * 导出导向数据信息
	 */
	@SysControllorLog(menu = "导向数据管理", operate = "导出导向数据")
	@RequestMapping(value = "/lrinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportLrinfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("desc") String desc) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			List<MetroLineIntervalData> datas = lineIntervalDataService.findAllByIntervalIdAndLr(intervalId,
					leftOrRight);
			String excelFileName = writeExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出导向数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 将导向数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeExcel(List<MetroLineIntervalData> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "导向数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("导向数据", 0);
			String[] title = { "里程", "盾首X坐标", "盾首Y坐标", "盾首Z坐标", "线路" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalData data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, String.valueOf(data.getMileage())));
					sheet.addCell(new Label(1, i + 1, String.valueOf(data.getMapX().floatValue())));
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getMapY().floatValue())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getMapZ().floatValue())));
					sheet.addCell(new Label(4, i + 1, desc));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("导向数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("导向数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 分页查找左右线的导向数据信息
	 * 
	 * @return
	 */
	@RequestMapping("/lrinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalData> findLrinfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("leftOrRight") String leftOrRight) {

		PageResultSet<MetroLineIntervalData> resultSet =
				lineIntervalDataService.findLineIntervalDataInfo(intervalId, leftOrRight, pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 删除区间左右线导向数据信息
	 */
	@SysControllorLog(menu = "导向数据管理", operate = "删除导向数据")
	@RequestMapping(value = "/lrinfoall/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLrinfo(
			@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalDataService.deleteDataInfo(intervalId,leftOrRight);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除导向数据信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除导向数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	
	/**
	 * 加载左右线的导向数据信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/lrinfo")
	public String lrinfo(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("desc") String desc) {
		/*
		 * PageResultSet<MetroDictionary> dicSet =
		 * dictionaryService.findMetroDictionaryInfo(0, 1000);
		 * request.setAttribute("dics", dicSet.getList());
		 */
		request.setAttribute("intervalId", intervalId);
		request.setAttribute("leftOrRight", leftOrRight);
		request.setAttribute("desc", desc);

		return "/project-info/item_guide_right";
	}


	/**
	 * 删除导向数据信息
	 */
	@SysControllorLog(menu = "导向数据管理", operate = "删除导向数据")
	@RequestMapping(value = "/lrinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteLrinfo(@RequestParam("id") Long id) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalDataService.deleteObj(id);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除导向数据信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除导向数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}
	/**
	 * 导入导向数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "导向数据管理", operate = "上传导向数据")
	@RequestMapping(value = "/lrinfo/import", method = RequestMethod.POST)
	public String importLrinfo(
			@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight,
			@RequestParam("desc") String desc,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalDataService.importExcelData(intervalId, leftOrRight, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				/*
				 * commonResponse.setCode(Constants.CODE_FAIL);
				 * commonResponse.setResult("文件上传成功，入库失败");
				 */
				request.setAttribute("msg", "导入失败");
				return "/common/error";
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			request.setAttribute("msg", "导入异常");
			return "/common/error";
			/*
			 * commonResponse.setCode(Constants.CODE_FAIL);
			 * commonResponse.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/guidedata/lrinfo?intervalId=" + intervalId + "&leftOrRight=" + leftOrRight
				+ "&desc=" + desc;
	}
	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public Jstree getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		Jstree tree = new Jstree();
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			// tree.setUrl(request.getContextPath()+"/project-info/basic/cityinfo"+"?cityId="+city.getId());
			State cityState = tree.new State();
			cityState.setOpened(true);
			cityState.setDisabled(true);
			tree.setState(cityState);
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree cityChild = new Jstree();
					cityChild.setId("line_" + line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					// cityChild.setUrl(request.getContextPath()+"/project-info/basic/lineinfo"+"?lineId="+line.getId());
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					lineState.setDisabled(true);
					cityChild.setState(lineState);
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_" + interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							// lineChild.setUrl(request.getContextPath()+"/project-info/basic/intervalinfo"+"?intervalId="+interval.getId());
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							intervalState.setDisabled(true);
							lineChild.setState(intervalState);

							List<Jstree> intervalChilds = new ArrayList<Jstree>();
							Jstree l_intervalChild = new Jstree();
							l_intervalChild.setId("l_" + interval.getId());
							l_intervalChild.setIcon("left");
							l_intervalChild.setText("左线");
							l_intervalChild.setType("left");
							l_intervalChild.setUrl(request.getContextPath()
									+ "/project-info/guidedata/lrinfo?leftOrRight=l&intervalId=" + interval.getId()
									+ "&desc=" + line.getLineName() + "-" + interval.getIntervalName() + "-左线");
							State lState = l_intervalChild.new State();
							lState.setOpened(true);
							if (cityChilds.size() == 0 && lineChilds.size() == 0) { // 默认选中第一个
								lState.setSelected(true);
							}
							l_intervalChild.setState(lState);
							Jstree r_intervalChild = new Jstree();
							r_intervalChild.setId("r_" + interval.getId());
							r_intervalChild.setIcon("right");
							r_intervalChild.setText("右线");
							r_intervalChild.setType("right");
							r_intervalChild.setUrl(request.getContextPath()
									+ "/project-info/guidedata/lrinfo?leftOrRight=r&intervalId=" + interval.getId()
									+ "&desc=" + line.getLineName() + "-" + interval.getIntervalName() + "-右线");
							State rState = r_intervalChild.new State();
							rState.setOpened(true);
							r_intervalChild.setState(rState);
							intervalChilds.add(l_intervalChild);
							intervalChilds.add(r_intervalChild);
							lineChild.setChildren(intervalChilds);

							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		return tree;
	}
}
