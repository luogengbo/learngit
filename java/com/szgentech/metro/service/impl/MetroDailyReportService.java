package com.szgentech.metro.service.impl;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.szgentech.metro.base.page.PageResultSet;
import com.szgentech.metro.base.service.BaseService;
import com.szgentech.metro.base.utils.ConfigProperties;
import com.szgentech.metro.base.utils.DateUtil;
import com.szgentech.metro.base.utils.IhistorianUtil;
import com.szgentech.metro.dao.IMetroDailyReportDao;
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.model.MetroDailyReport;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroDailyReportService;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;


/**
 * 盾构施工风险分析日报表业务接口实现
 * @author luohao
 *
 */
@Service("dailyReportService")
public class MetroDailyReportService extends BaseService<MetroDailyReport> implements IMetroDailyReportService {

	private static Logger logger = Logger.getLogger(MetroDailyReportService.class);

	private static String DEST_DIR = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH")+"/";
	
	@Autowired
	private IMetroDailyReportDao dailyReportDao;
	
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	/**
	 * 根据区间、左右线、日期查询环信息
	 * @param intervalId
	 * @param leftOrRight
	 * @param time
	 * @return
	 */
	public MonitorIntervalLrStaticView getStaticViewByTime(Long intervalId, String leftOrRight, Date time ){
		MonitorIntervalLrStaticView sv = null;
		PageResultSet<MonitorIntervalLrStaticView> res =  infoCityService.findMonitorStaticTab4(intervalId, leftOrRight, 1, 10, time, time, "1");
		List<MonitorIntervalLrStaticView> svList= res.getList();
		if (svList.size() > 0) {
			sv = svList.get(0);
		}
		return sv;
	}
	/**
	 * 查找日报表数据
	 */
	@Override
	public MetroDailyReport findDailyReport(Long intervalId, String leftOrRight, Date reportTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		return dailyReportDao.findDailyReport(params);
	}

	/**
	 * 更新日报表数据
	 */
	@Override
	public boolean updateObj(MetroDailyReport dailyReport) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", dailyReport.getId());
		params.put("intervalId", dailyReport.getIntervalId());
		params.put("leftOrRight", dailyReport.getLeftOrRight());
		params.put("summary", dailyReport.getSummary());
		params.put("progressStatistics", dailyReport.getProgressStatistics());
		params.put("workingCondition", dailyReport.getWorkingCondition());
		params.put("settlement", dailyReport.getSettlement());
		params.put("settlementImg", dailyReport.getSettlementImg());
		params.put("riskSituation", dailyReport.getRiskSituation());
		params.put("riskSituationImg", dailyReport.getRiskSituationImg());
		params.put("geology", dailyReport.getGeology());
		params.put("geologyImg", dailyReport.getGeologyImg());
		params.put("grouting", dailyReport.getGrouting());
		params.put("groutingImg", dailyReport.getGroutingImg());
		params.put("horizontalAttitude", dailyReport.getHorizontalAttitude());
		params.put("horizontalAttitudeImg", dailyReport.getHorizontalAttitudeImg());
		params.put("verticalAttitude", dailyReport.getVerticalAttitude());
		params.put("verticalAttitudeImg", dailyReport.getVerticalAttitudeImg());
		params.put("earlyWarning", dailyReport.getEarlyWarning());
		params.put("earlyWarningImg", dailyReport.getEarlyWarningImg());
		params.put("auditOpinion", dailyReport.getAuditOpinion());
		params.put("reportTime", dailyReport.getReportTime());
		params.put("operator", dailyReport.getOperator());
		params.put("reviewer", dailyReport.getReviewer());
		params.put("startA0004", dailyReport.getStartA0004());
		params.put("endA0004", dailyReport.getEndA0004());
		params.put("minA0004", dailyReport.getMinA0004());
		params.put("maxA0004", dailyReport.getMaxA0004());
		params.put("analysisA0004", dailyReport.getAnalysisA0004());
		params.put("startA0013", dailyReport.getStartA0013());
		params.put("endA0013", dailyReport.getEndA0013());
		params.put("minA0013", dailyReport.getMinA0013());
		params.put("maxA0013", dailyReport.getMaxA0013());
		params.put("analysisA0013", dailyReport.getAnalysisA0013());
		params.put("startB0001", dailyReport.getStartB0001());
		params.put("endB0001", dailyReport.getEndB0001());
		params.put("minB0001", dailyReport.getMinB0001());
		params.put("maxB0001", dailyReport.getMaxB0001());
		params.put("analysisB0001", dailyReport.getAnalysisB0001());
		params.put("startB0002", dailyReport.getStartB0002());
		params.put("endB0002", dailyReport.getEndB0002());
		params.put("minB0002", dailyReport.getMinB0002());
		params.put("maxB0002", dailyReport.getMaxB0002());
		params.put("analysisB0002", dailyReport.getAnalysisB0002());
		params.put("startB0004", dailyReport.getStartB0004());
		params.put("endB0004", dailyReport.getEndB0004());
		params.put("minB0004", dailyReport.getMinB0004());
		params.put("maxB0004", dailyReport.getMaxB0004());
		params.put("analysisB0004", dailyReport.getAnalysisB0004());
		params.put("startB0006", dailyReport.getStartB0006());
		params.put("endB0006", dailyReport.getEndB0006());
		params.put("minB0006", dailyReport.getMinB0006());
		params.put("maxB0006", dailyReport.getMaxB0006());
		params.put("analysisB0006", dailyReport.getAnalysisB0006());
		params.put("startB0015", dailyReport.getStartB0015());
		params.put("endB0015", dailyReport.getEndB0015());
		params.put("minB0015", dailyReport.getMinB0015());
		params.put("maxB0015", dailyReport.getMaxB0015());
		params.put("analysisB0015", dailyReport.getAnalysisB0015());
		params.put("startR0026", dailyReport.getStartR0026());
		params.put("endR0026", dailyReport.getEndR0026());
		params.put("minR0026", dailyReport.getMinR0026());
		params.put("maxR0026", dailyReport.getMaxR0026());
		params.put("analysisR0026", dailyReport.getAnalysisR0026());
		params.put("startR0028", dailyReport.getStartR0028());
		params.put("endR0028", dailyReport.getEndR0028());
		params.put("minR0028", dailyReport.getMinR0028());
		params.put("maxR0028", dailyReport.getMaxR0028());
		params.put("analysisR0028", dailyReport.getAnalysisR0028());
		params.put("startR0025", dailyReport.getStartR0025());
		params.put("endR0025", dailyReport.getEndR0025());
		params.put("minR0025", dailyReport.getMinR0025());
		params.put("maxR0025", dailyReport.getMaxR0025());
		params.put("analysisR0025", dailyReport.getAnalysisR0025());
		params.put("startR0004", dailyReport.getStartR0004());
		params.put("endR0004", dailyReport.getEndR0004());
		params.put("minR0004", dailyReport.getMinR0004());
		params.put("maxR0004", dailyReport.getMaxR0004());
		params.put("analysisR0004", dailyReport.getAnalysisR0004());
		int r = dailyReportDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 插入日报表数据
	 */
	@Override
	public Long insertObj(MetroDailyReport dailyReport) {
		int r = dailyReportDao.insertObj(dailyReport);
		if (r > 0) {
			return dailyReport.getId();
		}
		return null;
	}
	
	/**
	 * 分页查询
	 * 线路周报表信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 区间左右线
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroDailyReport> findDailyReportInfo(
			Long intervalId, String leftOrRight, String reportTime, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		PageResultSet<MetroDailyReport> resultSet = getPageResultSet(params, pageNum, pageSize, dailyReportDao);
		return resultSet;
	}

	/**
	 * 生成指定区间左右线、时间的PDF格式日报表
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param reportTime  报表时间
	 * @return
	 * @throws Exception
	 */
	public String exportDailyReportPdf(Long intervalId, String leftOrRight, Date reportTime) {
    	Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);	
		MetroDailyReport dailyReport = dailyReportDao.findDailyReport(params);
    	MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
    	if(dailyReport == null || mic == null){
    		return "";
    	}
    	String fileName = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight())+"_"+DateUtil.format(reportTime, "YYYY-MM-dd")+"_日报表.pdf";
    	try {
    		 PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST_DIR+fileName));  
    	        PageSize pagesize = PageSize.A4;
    	    	Document doc = new Document(pdfDoc, pagesize);
    	        PdfFont sysFont = PdfFontFactory.createFont(MetroDailyReportService.class.getResource("/").getPath() +"/font/方正楷体_GBK.ttf", PdfEncodings.IDENTITY_H);  
    	        Paragraph par1=new Paragraph(mic.getLineName()+ mic.getIntervalName() + "【" + ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进日分析报表").setFont(sysFont);
    	        par1.setMarginLeft(100);
    	        doc.add(par1);
    	        //工程信息
    	       
    	        Paragraph par2 = new Paragraph("工程名称：" + mic.getCityName() + "地铁" + mic.getLineName()
					+ mic.getIntervalName() + "【" + ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构工程"
					+ "    日期：" + DateUtil.format(reportTime, "YYYY年MM月dd日")).setFont(sysFont);
    	        doc.add(par2);
    	        Paragraph par3=new Paragraph("施工单位："+(mic.getMachineContractor() == null ? "" : mic.getMachineContractor())+"							设备型号："+(mic.getMachineNo() == null ? "" : mic.getMachineNo())).setFont(sysFont);
    	        doc.add(par3);
    	        Paragraph par4=new Paragraph("监理单位："+"广州轨道交通建设监理有限公司"+"						填表人:"+(dailyReport.getOperator() == null ? "" : dailyReport.getOperator())).setFont(sysFont);
    	        doc.add(par4);
    	        Table table = new Table(new float[] { 70, 130, 630})
    	                .setWidth(530);
    	        Cell cell=new Cell().add(new Paragraph("序号")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("项目")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("具体内容")).setFont(sysFont);
    	        table.addCell(cell);
    	        //当日情况总结
    	        cell=new Cell(1,3).add(new Paragraph("一、当日情况总结")).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph(dailyReport.getSummary() == null ? "" : dailyReport.getSummary())).setFont(sysFont);
    	        table.addCell(cell);
    	        //进度统计
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("进度统计")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph(dailyReport.getProgressStatistics() == null ? "" : dailyReport.getProgressStatistics())).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph("二、工况描述")).setFont(sysFont);
    	        table.addCell(cell);
    	        //当日工况
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("当日工况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph(dailyReport.getWorkingCondition() == null ? "" : dailyReport.getWorkingCondition())).setFont(sysFont);
    	        table.addCell(cell);
    	        //沉降情况
    	        cell=new Cell().add(new Paragraph("2")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("沉降情况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image settlementImg = base64StrToImage(dailyReport.getSettlementImg());
    	        if(settlementImg != null){
    	            cell.add(settlementImg);
    	        }
    	        cell.add(new Paragraph(dailyReport.getSettlement() == null ? "" : dailyReport.getSettlement())).setFont(sysFont);
    	        table.addCell(cell);
    	        //地表周边环境
    	        cell=new Cell().add(new Paragraph("3")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("地表周边环境")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image riskSituationImg = base64StrToImage(dailyReport.getRiskSituationImg());
    	        if(riskSituationImg != null){
    	            cell.add(riskSituationImg);
    	        }
    	        cell.add(new Paragraph(dailyReport.getRiskSituation() == null ? "" : dailyReport.getRiskSituation()))
					.setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("4")).setFont(sysFont);  
    	        table.addCell(cell);
    	        //地质情况
    	        cell=new Cell().add(new Paragraph("地质情况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image geologyImg = base64StrToImage(dailyReport.getGeologyImg());
    	        if(geologyImg != null){
    	            cell.add(geologyImg);
    	        }
    	        cell.add(new Paragraph(dailyReport.getGeology()==null?"":dailyReport.getGeology())).setFont(sysFont);
    	        table.addCell(cell);
    	        //三、掘进情况
    	        cell=new Cell(1,3).add(new Paragraph("三、掘进情况")).setFont(sysFont);
    	        table.addCell(cell);
    	       //同步注浆情况
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("同步注浆情况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image groutingImg = base64StrToImage(dailyReport.getGroutingImg());
    	        if(groutingImg != null){
    	            cell.add(groutingImg);
    	        }
    	        cell.add(new Paragraph(dailyReport.getGrouting()== null ? "" : dailyReport.getGrouting())).setFont(sysFont);
    	        table.addCell(cell);
    	      //盾构姿态
    	        cell=new Cell().add(new Paragraph("2")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("盾构姿态")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph(dailyReport.getHorizontalAttitude()== null ? "" : dailyReport.getHorizontalAttitude())).setFont(sysFont);
    	        Image horizontalAttitudeImg = base64StrToImage(dailyReport.getHorizontalAttitudeImg());
    	        if(horizontalAttitudeImg != null){
    	            cell.add(horizontalAttitudeImg);
    	        }
    	        cell.add(new Paragraph(dailyReport.getVerticalAttitude()== null ? "" : dailyReport.getVerticalAttitude())).setFont(sysFont);
    	        Image verticalAttitudeImg = base64StrToImage(dailyReport.getVerticalAttitudeImg());
    	        if(verticalAttitudeImg != null){
    	            cell.add(verticalAttitudeImg);
    	        }
    	        table.addCell(cell);
    	      //四、预警情况
    	        cell=new Cell(1,3).add(new Paragraph("四、预警情况")).setFont(sysFont);
    	        table.addCell(cell);
    	        //预警统计
    	        cell=new Cell(1,3).add(new Paragraph(dailyReport.getEarlyWarning()== null ? "" : dailyReport.getEarlyWarning())).setFont(sysFont);
    	        Image earlyWarningImg = base64StrToImage(dailyReport.getEarlyWarningImg());
    	        if(earlyWarningImg != null){
    	            cell.add(earlyWarningImg);
    	        }
    	        table.addCell(cell);
    	        // table2  盾构掘进参数情况
    	        
    	        Table table2 = new Table(new float[] { 150, 130, 130,240})
    	                .setWidth(530);
    	        Cell cell2=new Cell().add(new Paragraph("参数")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("掘进参数预设范围")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("实际掘进波动范围")).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("分析（简要）")).setFont(sysFont);
    	        table2.addCell(cell2);
    	        if(mic.getMachineType()==1) {
    	        	//土压力A0004
        	        cell2=new Cell().add(new Paragraph("土压力（bar）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartA0004()== null ? "" : dailyReport.getStartA0004().toString())+
        	        		"-"+(dailyReport.getEndA0004()== null ? "" : dailyReport.getEndA0004().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinA0004()== null ? "" : dailyReport.getMinA0004().toString())+
        	        		"-"+(dailyReport.getMaxA0004()== null ? "" : dailyReport.getMaxA0004().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisA0004()== null ? "" : dailyReport.getAnalysisA0004())).setFont(sysFont);
        	        table2.addCell(cell2);
    	        }else {
    	        	//切口水压A0013
        	        cell2=new Cell().add(new Paragraph("切口水压（bar）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartA0013()== null ? "" : dailyReport.getStartA0013().toString())+
        	        		"-"+(dailyReport.getEndA0013()== null ? "" : dailyReport.getEndA0013().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinA0013()== null ? "" : dailyReport.getMinA0013().toString())+
        	        		"-"+(dailyReport.getMaxA0013()== null ? "" : dailyReport.getMaxA0013().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisA0013()== null ? "" : dailyReport.getAnalysisA0013())).setFont(sysFont);
        	        table2.addCell(cell2);
    	        }
    	        //掘进速度
    	        cell2=new Cell().add(new Paragraph("掘进速度（mm/min）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getStartB0001()== null ? "" : dailyReport.getStartB0001().toString())+
    	        		"-"+(dailyReport.getEndB0001()== null ? "" : dailyReport.getEndB0001().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getMinB0001()== null ? "" : dailyReport.getMinB0001().toString())+
    	        		"-"+(dailyReport.getMaxB0001()== null ? "" : dailyReport.getMaxB0001().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisB0001()== null ? "" : dailyReport.getAnalysisB0001())).setFont(sysFont);
    	        table2.addCell(cell2);
    	        //刀盘转速
    	        cell2=new Cell().add(new Paragraph("刀盘转速（rpm）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getStartB0002()== null ? "" : dailyReport.getStartB0002().toString())+
    	        		"-"+(dailyReport.getEndB0002()== null ? "" : dailyReport.getEndB0002().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getMinB0002()== null ? "" : dailyReport.getMinB0002().toString())+
    	        		"-"+(dailyReport.getMaxB0002()== null ? "" : dailyReport.getMaxB0002().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisB0002()== null ? "" : dailyReport.getAnalysisB0002())).setFont(sysFont);
    	        table2.addCell(cell2);
    	        //刀盘扭矩
    	        cell2=new Cell().add(new Paragraph("刀盘扭矩（kNm）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getStartB0004()== null ? "" : dailyReport.getStartB0004().toString())+
    	        		"-"+(dailyReport.getEndB0004()== null ? "" : dailyReport.getEndB0004().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getMinB0004()== null ? "" : dailyReport.getMinB0004().toString())+
    	        		"-"+(dailyReport.getMaxB0002()== null ? "" : dailyReport.getMaxB0002().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisB0004()== null ? "" : dailyReport.getAnalysisB0004())).setFont(sysFont);
    	        table2.addCell(cell2);
    	        //总推力
    	        cell2=new Cell().add(new Paragraph("总推力（KN）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getStartB0006()== null ? "" : dailyReport.getStartB0006().toString())+
    	        		"-"+(dailyReport.getEndB0006()== null ? "" : dailyReport.getEndB0006().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((dailyReport.getMinB0006()== null ? "" : dailyReport.getMinB0006().toString())+
    	        		"-"+(dailyReport.getMaxB0006()== null ? "" : dailyReport.getMaxB0006().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisB0006()== null ? "" : dailyReport.getAnalysisB0006())).setFont(sysFont);
    	        table2.addCell(cell2);
    	        if(mic.getMachineType()==1) {
    	        	 //出土量（土压盾构）
        	        cell2=new Cell().add(new Paragraph("出土量")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartB0015()== null ? "" : dailyReport.getStartB0015().toString())+
        	        		"-"+(dailyReport.getEndB0015()== null ? "" : dailyReport.getEndB0015().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinB0015()== null ? "" : dailyReport.getMinB0015().toString())+
        	        		"-"+(dailyReport.getMaxB0015()== null ? "" : dailyReport.getMaxB0015().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisB0015()== null ? "" : dailyReport.getAnalysisB0015())).setFont(sysFont);
        	        table2.addCell(cell2);
    	        }else {
    	        	//进浆管流量
        	        cell2=new Cell().add(new Paragraph("进浆管流量（m3/h）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartR0026()== null ? "" : dailyReport.getStartR0026().toString())+
        	        		"-"+(dailyReport.getEndR0026()== null ? "" : dailyReport.getEndR0026().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinR0026()== null ? "" : dailyReport.getMinR0026().toString())+
        	        		"-"+(dailyReport.getMaxR0026()== null ? "" : dailyReport.getMaxR0026().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisR0026()== null ? "" : dailyReport.getAnalysisR0026())).setFont(sysFont);
        	        table2.addCell(cell2);
        	      //排浆管流量
        	        cell2=new Cell().add(new Paragraph("排浆管流量（m3/h）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartR0028()== null ? "" : dailyReport.getStartR0028().toString())+
        	        		"-"+(dailyReport.getEndR0028()== null ? "" : dailyReport.getEndR0028().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinR0028()== null ? "" : dailyReport.getMinR0028().toString())+
        	        		"-"+(dailyReport.getMaxR0028()== null ? "" : dailyReport.getMaxR0028().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisR0028()== null ? "" : dailyReport.getAnalysisR0028())).setFont(sysFont);
        	        table2.addCell(cell2);
        	   	    //进浆管压力
        	        cell2=new Cell().add(new Paragraph("进浆管压力（bar）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartR0025()== null ? "" : dailyReport.getStartR0025().toString())+
        	        		"-"+(dailyReport.getEndR0025()== null ? "" : dailyReport.getEndR0025().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinR0025()== null ? "" : dailyReport.getMinR0025().toString())+
        	        		"-"+(dailyReport.getMaxR0025()== null ? "" : dailyReport.getMaxR0025().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisR0025()== null ? "" : dailyReport.getAnalysisR0025())).setFont(sysFont);
        	        table2.addCell(cell2);
        	        //排浆泵出口压力
        	        cell2=new Cell().add(new Paragraph("排浆泵出口压力（bar）")).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getStartR0004()== null ? "" : dailyReport.getStartR0004().toString())+
        	        		"-"+(dailyReport.getEndR0004()== null ? "" : dailyReport.getEndR0004().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph((dailyReport.getMinR0004()== null ? "" : dailyReport.getMinR0004().toString())+
        	        		"-"+(dailyReport.getMaxR0004()== null ? "" : dailyReport.getMaxR0004().toString()))).setFont(sysFont);  
        	        table2.addCell(cell2);
        	        cell2=new Cell().add(new Paragraph(dailyReport.getAnalysisR0004()== null ? "" : dailyReport.getAnalysisR0004())).setFont(sysFont);
        	        table2.addCell(cell2);
				}
    	       
    	        
    	      //五、盾构掘进参数情况
    	        cell=new Cell(1,3).add(new Paragraph("五、盾构掘进参数情况")).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(table2).setFont(sysFont);
    	        table.addCell(cell);
    	        //六、监理审核意见：
    	        cell=new Cell(1,3).add(new Paragraph("六、监理审核意见")).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph(dailyReport.getAuditOpinion()== null ? "" : dailyReport.getAuditOpinion())).setFont(sysFont);
    	        table.addCell(cell);
    	        //报表审核人：
    	        cell=new Cell(1,3).add(new Paragraph("报表审核人："+(dailyReport.getReviewer()== null ? "" : dailyReport.getReviewer()))).setFont(sysFont);
    	        table.addCell(cell);
    	        
    	        doc.add(table);  
    	        doc.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Create a pdf Exception! intervalId:"+intervalId+" leftOrRight:"+leftOrRight+" reportTime:"+DateUtil.format(reportTime, "YYYY-MM-dd"));
			fileName = "";
		}
        return fileName;
    }
	
	public static byte[] base64StrChangeByte(String imgStr) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) { // 图像数据为空
			return null;
		}
		Decoder decoder = Base64.getDecoder();
		try {
			// Base64解码
			return decoder.decode(imgStr);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * base64字符串转图片对象
	 * @param base64Str
	 * @return
	 */
	public Image base64StrToImage(String base64Str){
		Image image = null;
		if(base64Str == null){
			return image;
		}
		try {
			base64Str = base64Str.replace("data:image/png;base64,", "");
	        byte[] bt = base64StrChangeByte(base64Str);
	        if(bt != null){
	        	image = new Image(ImageDataFactory.create(bt));
	            image.setWidth(300);
	            image.setHeight(200);
	            image.setMarginLeft(50);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
        return image;
	}

}


