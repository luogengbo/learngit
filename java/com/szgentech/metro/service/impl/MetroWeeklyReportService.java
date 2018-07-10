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
import com.szgentech.metro.dao.IMetroMonitorCityDao;
import com.szgentech.metro.dao.IMetroWeeklyReportDao;
import com.szgentech.metro.model.MetroWeeklyReport;
import com.szgentech.metro.model.MonitorInfoCity;
import com.szgentech.metro.service.IMetroMonitorInfoCityService;
import com.szgentech.metro.service.IMetroWeeklyReportService;
import com.szgentech.metro.vo.MonitorIntervalLrStaticView;


/**
 * 盾构施工风险分析周报表业务接口实现
 * @author luohao
 *
 */
@Service("weeklyReportService")
public class MetroWeeklyReportService extends BaseService<MetroWeeklyReport> implements IMetroWeeklyReportService {

	private static Logger logger = Logger.getLogger(MetroWeeklyReportService.class);

	private static String DEST_DIR = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH")+"/";
	
	@Autowired
	private IMetroWeeklyReportDao reportDao;
	
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	/**
	 * 根据区间、左右线、日期查询环信息
	 * @param intervalId  区间ID
	 * @param leftOrRight 左右线
	 * @param beginTime   开始时间
	 * @param endTime     结束时间
	 * @return
	 */
	public MonitorIntervalLrStaticView getStaticViewByTime(Long intervalId, String leftOrRight, Date beginTime, Date endTime ){
		MonitorIntervalLrStaticView sv = null;
		PageResultSet<MonitorIntervalLrStaticView> res =  infoCityService.findMonitorStaticTab4(intervalId, leftOrRight, 1, 10, beginTime, endTime, "1");
		List<MonitorIntervalLrStaticView> svList= res.getList();
		if (svList.size() > 0) {
			sv = svList.get(0);
		}
		return sv;
	}
	/**
	 * 查找周报表数据
	 */
	@Override
	public MetroWeeklyReport findWeeklyReport(Long intervalId, String leftOrRight, Date endTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", endTime);
		return reportDao.findWeeklyReport(params);
	}

	/**
	 * 更新周报表数据
	 */
	@Override
	public boolean updateObj(MetroWeeklyReport weeklyReport) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", weeklyReport.getId());
		params.put("intervalId", weeklyReport.getIntervalId());
		params.put("leftOrRight", weeklyReport.getLeftOrRight());
		params.put("summary", weeklyReport.getSummary());
		params.put("shieldTunneling", weeklyReport.getShieldTunneling());
		params.put("shieldTunnelingImg", weeklyReport.getShieldTunnelingImg());
		params.put("riskSituation", weeklyReport.getRiskSituation());
		params.put("riskSituationImg", weeklyReport.getRiskSituationImg());
		params.put("geology", weeklyReport.getGeology());
		params.put("geologyImg", weeklyReport.getGeologyImg());
		params.put("effect", weeklyReport.getEffect());
		params.put("effectImg", weeklyReport.getEffectImg());
		params.put("settlement", weeklyReport.getSettlement());
		params.put("settlementImg", weeklyReport.getSettlementImg());
		params.put("paramAnalysis", weeklyReport.getParamAnalysis());
		params.put("paramAnalysisImg", weeklyReport.getParamAnalysisImg());
		params.put("slagSamplesImg", weeklyReport.getSlagSamplesImg());
		params.put("horizontalAttitude", weeklyReport.getHorizontalAttitude());
		params.put("horizontalAttitudeImg", weeklyReport.getHorizontalAttitudeImg());
		params.put("verticalAttitude", weeklyReport.getVerticalAttitude());
		params.put("verticalAttitudeImg", weeklyReport.getVerticalAttitudeImg());
		params.put("grouting", weeklyReport.getGrouting());
		params.put("groutingImg", weeklyReport.getGroutingImg());
		params.put("oiltemperature", weeklyReport.getOiltemperature());
		params.put("oiltemperatureImg", weeklyReport.getOiltemperatureImg());
		params.put("earlyWarning", weeklyReport.getEarlyWarning());
		params.put("earlyWarningImg", weeklyReport.getEarlyWarningImg());
		params.put("auditOpinion", weeklyReport.getAuditOpinion());
		params.put("reportTime", weeklyReport.getReportTime());
		params.put("operator", weeklyReport.getOperator());
		params.put("reviewer", weeklyReport.getReviewer());
		params.put("startRingNum", weeklyReport.getStartRingNum());
		params.put("endRingNum", weeklyReport.getEndRingNum());
		params.put("startPressure", weeklyReport.getStartPressure());
		params.put("endPressure", weeklyReport.getEndPressure());
		params.put("startCutterTorque", weeklyReport.getStartCutterTorque());
		params.put("endCutterTorque", weeklyReport.getEndCutterTorque());
		params.put("startTotalThrust", weeklyReport.getStartTotalThrust());
		params.put("endTotalThrust", weeklyReport.getEndTotalThrust());
		params.put("startCutterSpeed", weeklyReport.getStartCutterSpeed());
		params.put("endCutterSpeed", weeklyReport.getEndCutterSpeed());
		params.put("startSpeed", weeklyReport.getStartSpeed());
		params.put("endSpeed", weeklyReport.getEndSpeed());
		params.put("startGroutingAmount", weeklyReport.getStartGroutingAmount());
		params.put("endGroutingAmount", weeklyReport.getEndGroutingAmount());
		int r = reportDao.updateObj(params);
		if (r > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 插入周报表数据
	 */
	@Override
	public Long insertObj(MetroWeeklyReport WeeklyReport) {
		int r = reportDao.insertObj(WeeklyReport);
		if (r > 0) {
			return WeeklyReport.getId();
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
	public PageResultSet<MetroWeeklyReport> findReportInfo(
			Long intervalId, String leftOrRight, String reportTime, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);
		PageResultSet<MetroWeeklyReport> resultSet = getPageResultSet(params, pageNum, pageSize, reportDao);
		return resultSet;
	}
	
	/**
	 * 根据区间、左右线、开始日期和结束日期查询每周环信息
	 * @param intervalId
	 * @param leftOrRight
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public MonitorIntervalLrStaticView getWeeklyViewByTime(Long intervalId, String leftOrRight, Date beginTime, Date endTime ){
		MonitorIntervalLrStaticView sv = null;
		PageResultSet<MonitorIntervalLrStaticView> res =  infoCityService.findMonitorStaticTab4(intervalId, leftOrRight, 1, 10, beginTime, endTime, "2");
		List<MonitorIntervalLrStaticView> svList= res.getList();
		if (svList.size() > 0) {
			sv = svList.get(0);
		}
		return sv;
	}

	/**
	 * 生成指定区间左右线、时间的PDF格式周报表
	 * @param intervalId  区间
	 * @param leftOrRight 左右线
	 * @param reportTime  报表时间
	 * @return
	 * @throws Exception
	 */
	public String exportWeeklyReportPdf(Long intervalId, String leftOrRight, Date reportTime) {
    	Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("reportTime", reportTime);	
    	MetroWeeklyReport weeklyReport = reportDao.findWeeklyReport(params);
    	MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
    	if(weeklyReport == null || mic == null){
    		return "";
    	}
    	String fileName = IhistorianUtil.getKey(mic.getLineNo(), mic.getIntervalMark(), mic.getLeftOrRight())+"_"+DateUtil.format(reportTime, "YYYY-MM-dd")+"_周报表.pdf";
    	try {
    		 PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST_DIR+fileName));  
    	        PageSize pagesize = PageSize.A4;
    	    	Document doc = new Document(pdfDoc, pagesize);
    	        PdfFont sysFont = PdfFontFactory.createFont(MetroWeeklyReportService.class.getResource("/").getPath() +"/font/方正楷体_GBK.ttf", PdfEncodings.IDENTITY_H);  
    	        Paragraph par1=new Paragraph(mic.getLineName()
    					+ mic.getIntervalName() + "【" + ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构掘进周分析报表").setFont(sysFont);
    	        par1.setMarginLeft(100);
    	        doc.add(par1);
    	        //工程信息
    	        Paragraph par2 = new Paragraph("工程名称：" + mic.getCityName() + "地铁" + mic.getLineName()
					+ mic.getIntervalName() + "【" + ("l".equals(mic.getLeftOrRight()) ? "左" : "右") + "线】" + "盾构工程"
					+ "    日期：" + DateUtil.format(reportTime, "YYYY年MM月dd日")).setFont(sysFont);
    	        doc.add(par2);
    	        Paragraph par3=new Paragraph("施工单位："+(mic.getMachineContractor() == null ? "" : mic.getMachineContractor())+"							设备型号："+(mic.getMachineNo() == null ? "" : mic.getMachineNo())).setFont(sysFont);
    	        doc.add(par3);
    	        Paragraph par4=new Paragraph("监理单位："+"广州轨道交通建设监理有限公司"+"						填表人:"+(weeklyReport.getOperator() == null ? "" : weeklyReport.getOperator())).setFont(sysFont);
    	        doc.add(par4);
    	        Table table = new Table(new float[] { 70, 130, 630})
    	                .setWidth(530);
    	        Cell cell=new Cell().add(new Paragraph("序号")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("项目")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("具体内容")).setFont(sysFont);
    	        table.addCell(cell);
    	        //本周总结
    	        cell=new Cell(1,3).add(new Paragraph("一、本周总结")).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph(weeklyReport.getSummary() == null ? "" : weeklyReport.getSummary())).setFont(sysFont);
    	        table.addCell(cell);
    	        //盾构掘进
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("盾构掘进")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph(weeklyReport.getShieldTunneling() == null ? "" : weeklyReport.getShieldTunneling())).setFont(sysFont);
    	        Image shieldTunnelingImg = base64StrToImage(weeklyReport.getShieldTunnelingImg());
    	        if(shieldTunnelingImg != null){
    	            cell.add(shieldTunnelingImg);
    	        }
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph("二、工况描述")).setFont(sysFont);
    	        table.addCell(cell);
    	        //地表周边环境
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("地表周边环境")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image riskSituationImg = base64StrToImage(weeklyReport.getRiskSituationImg());
    	        if(riskSituationImg != null){
    	            cell.add(riskSituationImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getRiskSituation() == null ? "" : weeklyReport.getRiskSituation()))
					.setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("2")).setFont(sysFont);  
    	        table.addCell(cell);
    	        //水文地质条件
    	        cell=new Cell().add(new Paragraph("水文地质条件")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image geologyImg = base64StrToImage(weeklyReport.getGeologyImg());
    	        if(geologyImg != null){
    	            cell.add(geologyImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getGeology()==null?"":weeklyReport.getGeology())).setFont(sysFont);
    	        table.addCell(cell);
    	        //功效分析
    	        cell=new Cell().add(new Paragraph("3")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("功效分析")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image effectImg = base64StrToImage(weeklyReport.getEffectImg());
    	        if(effectImg != null){
    	            cell.add(effectImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getEffect() == null?"":weeklyReport.getEffect())).setFont(sysFont);
    	        table.addCell(cell);
    	        //监测情况
    	        cell=new Cell().add(new Paragraph("4")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("监测情况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image settlementImg = base64StrToImage(weeklyReport.getSettlementImg());
    	        if(settlementImg != null){
    	            cell.add(settlementImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getSettlement() == null ? "" : weeklyReport.getSettlement())).setFont(sysFont);
    	        table.addCell(cell);
    	        // table2  拟设定盾构掘进参数
    	        Table table2 = new Table(new float[] { 200, 160, 200,160})
    	                .setWidth(350);
    	        table2.setMarginLeft(35);
    	        Cell cell2=new Cell().add(new Paragraph("环号")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell(1,3).add(new Paragraph((weeklyReport.getStartRingNum()== null ? "" : weeklyReport.getStartRingNum().toString())+
    	        		"-"+(weeklyReport.getEndRingNum()== null ? "" : weeklyReport.getEndRingNum().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("压力（bar）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartPressure()== null ? "" : weeklyReport.getStartPressure().toString())+
    	        		"-"+(weeklyReport.getEndPressure()== null ? "" : weeklyReport.getEndPressure().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("刀盘扭矩(MN.m)")).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartCutterTorque()== null ? "" : weeklyReport.getStartCutterTorque().toString())+
    	        		"-"+(weeklyReport.getEndCutterTorque()== null ? "" : weeklyReport.getEndCutterTorque().toString()))).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("总推力（KN）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartTotalThrust()== null ? "" : weeklyReport.getStartTotalThrust().toString())+
    	        		"-"+(weeklyReport.getEndTotalThrust()== null ? "" : weeklyReport.getEndTotalThrust().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("刀盘转速")).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartCutterSpeed()== null ? "" : weeklyReport.getStartCutterSpeed().toString())+
    	        		"-"+(weeklyReport.getEndCutterSpeed()== null ? "" : weeklyReport.getEndCutterSpeed().toString()))).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("推进速度（mm/min）")).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartSpeed()== null ? "" : weeklyReport.getStartSpeed().toString())+
    	        		"-"+(weeklyReport.getEndSpeed()== null ? "" : weeklyReport.getEndSpeed().toString()))).setFont(sysFont);  
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph("注浆量（m3）")).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell2=new Cell().add(new Paragraph((weeklyReport.getStartGroutingAmount()== null ? "" : weeklyReport.getStartGroutingAmount().toString())+
    	        		"-"+(weeklyReport.getEndGroutingAmount()== null ? "" : weeklyReport.getEndGroutingAmount().toString()))).setFont(sysFont);
    	        table2.addCell(cell2);
    	        cell=new Cell().add(new Paragraph("三、")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("实际掘进波动范围")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(table2).setFont(sysFont);
    	        table.addCell(cell);
    	        //四、掘进情况
    	        cell=new Cell(1,3).add(new Paragraph("四、掘进情况")).setFont(sysFont);
    	        table.addCell(cell);
    	        //参数分析
    	        cell=new Cell().add(new Paragraph("1")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("参数分析")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image paramAnalysisImg = base64StrToImage(weeklyReport.getParamAnalysisImg());
    	        if(paramAnalysisImg != null){
    	            cell.add(paramAnalysisImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getParamAnalysis()== null ? "" : weeklyReport.getParamAnalysis())).setFont(sysFont);
    	        Image slagSamplesImg = base64StrToImage(weeklyReport.getSlagSamplesImg());
    	        if(slagSamplesImg != null){
    	            cell.add(slagSamplesImg);
    	        }
    	        table.addCell(cell);
    	        //盾构姿态
    	        cell=new Cell().add(new Paragraph("2")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("盾构姿态")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph(weeklyReport.getHorizontalAttitude()== null ? "" : weeklyReport.getHorizontalAttitude())).setFont(sysFont);
    	        Image horizontalAttitudeImg = base64StrToImage(weeklyReport.getHorizontalAttitudeImg());
    	        if(horizontalAttitudeImg != null){
    	            cell.add(horizontalAttitudeImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getVerticalAttitude()== null ? "" : weeklyReport.getVerticalAttitude())).setFont(sysFont);
    	        Image verticalAttitudeImg = base64StrToImage(weeklyReport.getVerticalAttitudeImg());
    	        if(verticalAttitudeImg != null){
    	            cell.add(verticalAttitudeImg);
    	        }
    	        table.addCell(cell);
    	        //注浆统计
    	        cell=new Cell().add(new Paragraph("3")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("注浆统计")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image groutingImg = base64StrToImage(weeklyReport.getGroutingImg());
    	        if(groutingImg != null){
    	            cell.add(groutingImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getGrouting()== null ? "" : weeklyReport.getGrouting())).setFont(sysFont);
    	        table.addCell(cell);
    	        //设备情况
    	        cell=new Cell().add(new Paragraph("4")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell().add(new Paragraph("设备情况")).setFont(sysFont);  
    	        table.addCell(cell);
    	        cell=new Cell();
    	        Image oiltemperatureImg = base64StrToImage(weeklyReport.getOiltemperatureImg());
    	        if(oiltemperatureImg != null){
    	            cell.add(oiltemperatureImg);
    	        }
    	        cell.add(new Paragraph(weeklyReport.getOiltemperature()== null ? "" : weeklyReport.getOiltemperature())).setFont(sysFont);
    	        table.addCell(cell);
    	        //四、预警情况
    	        cell=new Cell(1,3).add(new Paragraph("四、预警情况")).setFont(sysFont);
    	        table.addCell(cell);
    	        //预警统计
    	        cell=new Cell(1,3).add(new Paragraph(weeklyReport.getEarlyWarning()== null ? "" : weeklyReport.getEarlyWarning())).setFont(sysFont);
    	        Image earlyWarningImg = base64StrToImage(weeklyReport.getEarlyWarningImg());
    	        if(earlyWarningImg != null){
    	            cell.add(earlyWarningImg);
    	        }
    	        table.addCell(cell);
    	        //五、监理审核意见：
    	        cell=new Cell(1,3).add(new Paragraph("五、监理审核意见")).setFont(sysFont);
    	        table.addCell(cell);
    	        cell=new Cell(1,3).add(new Paragraph(weeklyReport.getAuditOpinion()== null ? "" : weeklyReport.getAuditOpinion())).setFont(sysFont);
    	        table.addCell(cell);
    	        //报表审核人：
    	        cell=new Cell(1,3).add(new Paragraph("报表审核人："+(weeklyReport.getReviewer()== null ? "" : weeklyReport.getReviewer()))).setFont(sysFont);
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


