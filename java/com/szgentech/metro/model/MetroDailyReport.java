package com.szgentech.metro.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 风险分析日报表管理数据
 * @author luohao
 *
 */
public class MetroDailyReport implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859422776135918624L;

	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左/右线标记 l左r右
	private String summary; //当日掘进情况总结
	private String progressStatistics; //进度统计
	private String workingCondition; //工况
	private String settlement; //地面沉降监测情况分析
	private String settlementImg; //地面沉降监测情况图片
	private String riskSituation; //周边建构筑物及风险情况描述
	private String riskSituationImg; //周边建构筑物及风险情况描述图片
	private String geology; //水文地质条件情况分析
	private String geologyImg; //水文地质条件情况图片
	private String grouting; //注浆统计情况分析
	private String groutingImg; //注浆统计图片
	private String verticalAttitude; //垂直姿态情况分析/前点垂直/中点垂直/后点垂直
	private String verticalAttitudeImg; //垂直姿态情况图片/前点垂直/中点垂直/后点垂直
	private String horizontalAttitude; //盾构机水平姿态情况分析/前点水平/中点水平/后点水平水平
	private String horizontalAttitudeImg; //盾构机水平姿态情况分析/前点水平/中点水平/后点水平水平图片
	private String earlyWarning; //预警情况
	private String earlyWarningImg; //预警情况图片
	private String auditOpinion; //审核意见
	@JsonProperty(value="reportTime")
	private Date reportTime; //掘进日期
	private String operator; //操作员，即报表制表人
	private String reviewer; //报表审核人
	private Float startA0004; //预设开始上部土压力A0004
	private Float endA0004; //预设结束上部土压力A0004
	private Float minA0004; //实际最小上部土压力A0004
	private Float maxA0004; //实际最大上部土压力A0004
	private String analysisA0004; //分析上部土压力A0004
	private Float startA0013; //预设开始切口水压A0013
	private Float endA0013; //预设结束切口水压A0013
	private Float minA0013; //实际最小切口水压A0013
	private Float maxA0013; //实际最大切口水压A0013
	private String analysisA0013; //分析切口水压A0013
	private Float startB0001; //预设开始掘进速度B0001
	private Float endB0001; //预设结束掘进速度B0001
	private Float minB0001; //实际最小掘进速度B0001
	private Float maxB0001; //实际最大掘进速度B0001
	private String analysisB0001; //分析掘进速度B0001
	private Float startB0002; //预设开始刀盘转速B0002
	private Float endB0002; //预设结束刀盘转速B0002
	private Float minB0002; //实际最小刀盘转速B0002
	private Float maxB0002; //实际最大刀盘转速B0002
	private String analysisB0002; //分析刀盘转速B0002
	private Float startB0004; //预设开始刀盘扭矩B0004
	private Float endB0004; //预设结束刀盘扭矩B0004
	private Float minB0004; //实际最小刀盘扭矩B0004
	private Float maxB0004; //实际最大刀盘扭矩B0004
	private String analysisB0004; //分析刀盘扭矩B0004
	private Float startB0006; //预设开始总推力B0006
	private Float endB0006; //预设结束总推力B0006
	private Float minB0006; //实际最小总推力B0006
	private Float maxB0006; //实际最大总推力B0006
	private String analysisB0006; //分析总推力B0006
	private Float startB0015; //预设开始出土量（土压盾构）B0015
	private Float endB0015; //预设结束出土量（土压盾构）B0015
	private Float minB0015; //实际最小出土量（土压盾构）B0015
	private Float maxB0015; //实际最大出土量（土压盾构）B0015
	private String analysisB0015; //分析出土量（土压盾构）B0015
	private Float startR0026; //预设开始进浆管流量R0026
	private Float endR0026; //预设结束进浆管流量R0026
	private Float minR0026; //实际最小进浆管流量R0026
	private Float maxR0026; //实际最大进浆管流量R0026
	private String analysisR0026; //分析进浆管流量R0026
	private Float startR0028; //预设开始排浆管流量R0028
	private Float endR0028; //预设结束排浆管流量R0028
	private Float minR0028; //实际最小排浆管流量R0028
	private Float maxR0028; //实际最大排浆管流量R0028
	private String analysisR0028; //分析排浆管流量R0028
	private Float startR0025; //预设开始进浆管压力R0025
	private Float endR0025; //预设结束进浆管压力R0025
	private Float minR0025; //实际最小进浆管压力R0025
	private Float maxR0025; //实际最大进浆管压力R0025
	private String analysisR0025; //分析进浆管压力R0025
	private Float startR0004; //预设开始排浆泵出口压力R0004
	private Float endR0004; //预设结束排浆泵出口压力R0004
	private Float minR0004; //实际最小排浆泵出口压力R0004
	private Float maxR0004; //实际最大排浆泵出口压力R0004
	private String  analysisR0004; //分析排浆泵出口压力R0004
	@JsonProperty(value="updateTime")
	private Date updateTime;//更新时间
	@JsonProperty(value="createTime")
	private Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public Long getIntervalId() {
		return intervalId;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public String getSummary() {
		return summary;
	}
	public String getProgressStatistics() {
		return progressStatistics;
	}
	public String getWorkingCondition() {
		return workingCondition;
	}
	public String getSettlement() {
		return settlement;
	}
	public String getSettlementImg() {
		return settlementImg;
	}
	public String getRiskSituation() {
		return riskSituation;
	}
	public String getRiskSituationImg() {
		return riskSituationImg;
	}
	public String getGeology() {
		return geology;
	}
	public String getGeologyImg() {
		return geologyImg;
	}
	public String getGrouting() {
		return grouting;
	}
	public String getGroutingImg() {
		return groutingImg;
	}
	public String getVerticalAttitude() {
		return verticalAttitude;
	}
	public String getVerticalAttitudeImg() {
		return verticalAttitudeImg;
	}
	public String getHorizontalAttitude() {
		return horizontalAttitude;
	}
	public String getHorizontalAttitudeImg() {
		return horizontalAttitudeImg;
	}
	public String getEarlyWarning() {
		return earlyWarning;
	}
	public String getEarlyWarningImg() {
		return earlyWarningImg;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getReportTime() {
		return reportTime;
	}
	public String getOperator() {
		return operator;
	}
	public String getReviewer() {
		return reviewer;
	}
	public Float getStartA0004() {
		return startA0004;
	}
	public Float getEndA0004() {
		return endA0004;
	}
	public Float getMinA0004() {
		return minA0004;
	}
	public Float getMaxA0004() {
		return maxA0004;
	}
	public String getAnalysisA0004() {
		return analysisA0004;
	}
	public Float getStartA0013() {
		return startA0013;
	}
	public Float getEndA0013() {
		return endA0013;
	}
	public Float getMinA0013() {
		return minA0013;
	}
	public Float getMaxA0013() {
		return maxA0013;
	}
	public String getAnalysisA0013() {
		return analysisA0013;
	}
	public Float getStartB0001() {
		return startB0001;
	}
	public Float getEndB0001() {
		return endB0001;
	}
	public Float getMinB0001() {
		return minB0001;
	}
	public Float getMaxB0001() {
		return maxB0001;
	}
	public String getAnalysisB0001() {
		return analysisB0001;
	}
	public Float getStartB0002() {
		return startB0002;
	}
	public Float getEndB0002() {
		return endB0002;
	}
	public Float getMinB0002() {
		return minB0002;
	}
	public Float getMaxB0002() {
		return maxB0002;
	}
	public String getAnalysisB0002() {
		return analysisB0002;
	}
	public Float getStartB0004() {
		return startB0004;
	}
	public Float getEndB0004() {
		return endB0004;
	}
	public Float getMinB0004() {
		return minB0004;
	}
	public Float getMaxB0004() {
		return maxB0004;
	}
	public String getAnalysisB0004() {
		return analysisB0004;
	}
	public Float getStartB0006() {
		return startB0006;
	}
	public Float getEndB0006() {
		return endB0006;
	}
	public Float getMinB0006() {
		return minB0006;
	}
	public Float getMaxB0006() {
		return maxB0006;
	}
	public String getAnalysisB0006() {
		return analysisB0006;
	}
	public Float getStartB0015() {
		return startB0015;
	}
	public Float getEndB0015() {
		return endB0015;
	}
	public Float getMinB0015() {
		return minB0015;
	}
	public Float getMaxB0015() {
		return maxB0015;
	}
	public String getAnalysisB0015() {
		return analysisB0015;
	}
	public Float getStartR0026() {
		return startR0026;
	}
	public Float getEndR0026() {
		return endR0026;
	}
	public Float getMinR0026() {
		return minR0026;
	}
	public Float getMaxR0026() {
		return maxR0026;
	}
	public String getAnalysisR0026() {
		return analysisR0026;
	}
	public Float getStartR0028() {
		return startR0028;
	}
	public Float getEndR0028() {
		return endR0028;
	}
	public Float getMinR0028() {
		return minR0028;
	}
	public Float getMaxR0028() {
		return maxR0028;
	}
	public String getAnalysisR0028() {
		return analysisR0028;
	}
	public Float getStartR0025() {
		return startR0025;
	}
	public Float getEndR0025() {
		return endR0025;
	}
	public Float getMinR0025() {
		return minR0025;
	}
	public Float getMaxR0025() {
		return maxR0025;
	}
	public String getAnalysisR0025() {
		return analysisR0025;
	}
	public Float getStartR0004() {
		return startR0004;
	}
	public Float getEndR0004() {
		return endR0004;
	}
	public Float getMinR0004() {
		return minR0004;
	}
	public Float getMaxR0004() {
		return maxR0004;
	}
	public String getAnalysisR0004() {
		return analysisR0004;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setProgressStatistics(String progressStatistics) {
		this.progressStatistics = progressStatistics;
	}
	public void setWorkingCondition(String workingCondition) {
		this.workingCondition = workingCondition;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public void setSettlementImg(String settlementImg) {
		this.settlementImg = settlementImg;
	}
	public void setRiskSituation(String riskSituation) {
		this.riskSituation = riskSituation;
	}
	public void setRiskSituationImg(String riskSituationImg) {
		this.riskSituationImg = riskSituationImg;
	}
	public void setGeology(String geology) {
		this.geology = geology;
	}
	public void setGeologyImg(String geologyImg) {
		this.geologyImg = geologyImg;
	}
	public void setGrouting(String grouting) {
		this.grouting = grouting;
	}
	public void setGroutingImg(String groutingImg) {
		this.groutingImg = groutingImg;
	}
	public void setVerticalAttitude(String verticalAttitude) {
		this.verticalAttitude = verticalAttitude;
	}
	public void setVerticalAttitudeImg(String verticalAttitudeImg) {
		this.verticalAttitudeImg = verticalAttitudeImg;
	}
	public void setHorizontalAttitude(String horizontalAttitude) {
		this.horizontalAttitude = horizontalAttitude;
	}
	public void setHorizontalAttitudeImg(String horizontalAttitudeImg) {
		this.horizontalAttitudeImg = horizontalAttitudeImg;
	}
	public void setEarlyWarning(String earlyWarning) {
		this.earlyWarning = earlyWarning;
	}
	public void setEarlyWarningImg(String earlyWarningImg) {
		this.earlyWarningImg = earlyWarningImg;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public void setStartA0004(Float startA0004) {
		this.startA0004 = startA0004;
	}
	public void setEndA0004(Float endA0004) {
		this.endA0004 = endA0004;
	}
	public void setMinA0004(Float minA0004) {
		this.minA0004 = minA0004;
	}
	public void setMaxA0004(Float maxA0004) {
		this.maxA0004 = maxA0004;
	}
	public void setAnalysisA0004(String analysisA0004) {
		this.analysisA0004 = analysisA0004;
	}
	public void setStartA0013(Float startA0013) {
		this.startA0013 = startA0013;
	}
	public void setEndA0013(Float endA0013) {
		this.endA0013 = endA0013;
	}
	public void setMinA0013(Float minA0013) {
		this.minA0013 = minA0013;
	}
	public void setMaxA0013(Float maxA0013) {
		this.maxA0013 = maxA0013;
	}
	public void setAnalysisA0013(String analysisA0013) {
		this.analysisA0013 = analysisA0013;
	}
	public void setStartB0001(Float startB0001) {
		this.startB0001 = startB0001;
	}
	public void setEndB0001(Float endB0001) {
		this.endB0001 = endB0001;
	}
	public void setMinB0001(Float minB0001) {
		this.minB0001 = minB0001;
	}
	public void setMaxB0001(Float maxB0001) {
		this.maxB0001 = maxB0001;
	}
	public void setAnalysisB0001(String analysisB0001) {
		this.analysisB0001 = analysisB0001;
	}
	public void setStartB0002(Float startB0002) {
		this.startB0002 = startB0002;
	}
	public void setEndB0002(Float endB0002) {
		this.endB0002 = endB0002;
	}
	public void setMinB0002(Float minB0002) {
		this.minB0002 = minB0002;
	}
	public void setMaxB0002(Float maxB0002) {
		this.maxB0002 = maxB0002;
	}
	public void setAnalysisB0002(String analysisB0002) {
		this.analysisB0002 = analysisB0002;
	}
	public void setStartB0004(Float startB0004) {
		this.startB0004 = startB0004;
	}
	public void setEndB0004(Float endB0004) {
		this.endB0004 = endB0004;
	}
	public void setMinB0004(Float minB0004) {
		this.minB0004 = minB0004;
	}
	public void setMaxB0004(Float maxB0004) {
		this.maxB0004 = maxB0004;
	}
	public void setAnalysisB0004(String analysisB0004) {
		this.analysisB0004 = analysisB0004;
	}
	public void setStartB0006(Float startB0006) {
		this.startB0006 = startB0006;
	}
	public void setEndB0006(Float endB0006) {
		this.endB0006 = endB0006;
	}
	public void setMinB0006(Float minB0006) {
		this.minB0006 = minB0006;
	}
	public void setMaxB0006(Float maxB0006) {
		this.maxB0006 = maxB0006;
	}
	public void setAnalysisB0006(String analysisB0006) {
		this.analysisB0006 = analysisB0006;
	}
	public void setStartB0015(Float startB0015) {
		this.startB0015 = startB0015;
	}
	public void setEndB0015(Float endB0015) {
		this.endB0015 = endB0015;
	}
	public void setMinB0015(Float minB0015) {
		this.minB0015 = minB0015;
	}
	public void setMaxB0015(Float maxB0015) {
		this.maxB0015 = maxB0015;
	}
	public void setAnalysisB0015(String analysisB0015) {
		this.analysisB0015 = analysisB0015;
	}
	public void setStartR0026(Float startR0026) {
		this.startR0026 = startR0026;
	}
	public void setEndR0026(Float endR0026) {
		this.endR0026 = endR0026;
	}
	public void setMinR0026(Float minR0026) {
		this.minR0026 = minR0026;
	}
	public void setMaxR0026(Float maxR0026) {
		this.maxR0026 = maxR0026;
	}
	public void setAnalysisR0026(String analysisR0026) {
		this.analysisR0026 = analysisR0026;
	}
	public void setStartR0028(Float startR0028) {
		this.startR0028 = startR0028;
	}
	public void setEndR0028(Float endR0028) {
		this.endR0028 = endR0028;
	}
	public void setMinR0028(Float minR0028) {
		this.minR0028 = minR0028;
	}
	public void setMaxR0028(Float maxR0028) {
		this.maxR0028 = maxR0028;
	}
	public void setAnalysisR0028(String analysisR0028) {
		this.analysisR0028 = analysisR0028;
	}
	public void setStartR0025(Float startR0025) {
		this.startR0025 = startR0025;
	}
	public void setEndR0025(Float endR0025) {
		this.endR0025 = endR0025;
	}
	public void setMinR0025(Float minR0025) {
		this.minR0025 = minR0025;
	}
	public void setMaxR0025(Float maxR0025) {
		this.maxR0025 = maxR0025;
	}
	public void setAnalysisR0025(String analysisR0025) {
		this.analysisR0025 = analysisR0025;
	}
	public void setStartR0004(Float startR0004) {
		this.startR0004 = startR0004;
	}
	public void setEndR0004(Float endR0004) {
		this.endR0004 = endR0004;
	}
	public void setMinR0004(Float minR0004) {
		this.minR0004 = minR0004;
	}
	public void setMaxR0004(Float maxR0004) {
		this.maxR0004 = maxR0004;
	}
	public void setAnalysisR0004(String analysisR0004) {
		this.analysisR0004 = analysisR0004;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
