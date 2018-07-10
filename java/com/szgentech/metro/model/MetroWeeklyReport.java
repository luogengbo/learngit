package com.szgentech.metro.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 风险分析周报表管理数据
 * @author luohao
 *
 */
public class MetroWeeklyReport implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859422776135918624L;

	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String leftOrRight;//左/右线标记 l左r右
	private String summary; //当日掘进情况总结
	private String shieldTunneling; //盾构掘进
	private String shieldTunnelingImg; //盾构掘进图片
	private String riskSituation; //周边建构筑物及风险情况描述
	private String riskSituationImg; //周边建构筑物及风险情况描述图片
	private String geology; //水文地质条件情况分析
	private String geologyImg; //水文地质条件情况图片
	private String effect; //施工功效情况分析
	private String effectImg; //施工功效情况分析图片
	private String settlement; //地面沉降监测情况分析
	private String settlementImg; //地面沉降监测情况图片
	private String paramAnalysis; //参数分析
	private String paramAnalysisImg; //参数分析图片
	private String slagSamplesImg;//地质渣样图片
	private String horizontalAttitude; //水平姿态情况分析
	private String horizontalAttitudeImg; //水平姿态情况图片
	private String verticalAttitude; //垂直姿态情况分析
	private String verticalAttitudeImg; //垂直姿态情况图片
	private String grouting; //注浆统计情况分析
	private String groutingImg; //注浆统计图片
	private String oiltemperature; //液压油温/齿轮油温情况分析（设备情况）
	private String oiltemperatureImg; //液压油温/齿轮油温图片（设备情况）
	private String earlyWarning; //预警情况
	private String earlyWarningImg; //预警情况图片
	private String auditOpinion; //审核意见
	@JsonProperty(value="reportTime")
	private Date reportTime; //掘进日期
	private String operator; //操作员，即报表制表人
	private String reviewer; //报表审核人
	private Float startRingNum; //拟设定盾构掘进参数开始环号
	private Float endRingNum; //拟设定盾构掘进参数结束环号
	private Float startPressure; //拟设定盾构掘进参数开始上部土压力
	private Float endPressure; //拟设定盾构掘进参数结束上部土压力
	private Float startCutterTorque; //拟设定盾构掘进参数开始刀盘扭矩
	private Float endCutterTorque; //拟设定盾构掘进参数结束刀盘扭矩
	private Float startTotalThrust; //拟设定盾构掘进参数开始总推力
	private Float endTotalThrust; //拟设定盾构掘进参数结束总推力
	private Float startCutterSpeed; //拟设定盾构掘进参数开始刀盘转速
	private Float endCutterSpeed; //拟设定盾构掘进参数结束刀盘转速
	private Float startSpeed; //拟设定盾构掘进参数开始推进速度
	private Float endSpeed; //拟设定盾构掘进参数结束推进速度
	private Float startGroutingAmount; //拟设定盾构掘进参数开始注浆量
	private Float endGroutingAmount; //拟设定盾构掘进参数结束注浆量
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
	public String getShieldTunneling() {
		return shieldTunneling;
	}
	public String getShieldTunnelingImg() {
		return shieldTunnelingImg;
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
	public String getEffect() {
		return effect;
	}
	public String getEffectImg() {
		return effectImg;
	}
	public String getSettlement() {
		return settlement;
	}
	public String getSettlementImg() {
		return settlementImg;
	}
	public String getParamAnalysis() {
		return paramAnalysis;
	}
	public String getParamAnalysisImg() {
		return paramAnalysisImg;
	}
	public String getSlagSamplesImg() {
		return slagSamplesImg;
	}
	public String getHorizontalAttitude() {
		return horizontalAttitude;
	}
	public String getHorizontalAttitudeImg() {
		return horizontalAttitudeImg;
	}
	public String getVerticalAttitude() {
		return verticalAttitude;
	}
	public String getVerticalAttitudeImg() {
		return verticalAttitudeImg;
	}
	public String getGrouting() {
		return grouting;
	}
	public String getGroutingImg() {
		return groutingImg;
	}
	public String getOiltemperature() {
		return oiltemperature;
	}
	public String getOiltemperatureImg() {
		return oiltemperatureImg;
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
	public Float getStartRingNum() {
		return startRingNum;
	}
	public Float getEndRingNum() {
		return endRingNum;
	}
	public Float getStartPressure() {
		return startPressure;
	}
	public Float getEndPressure() {
		return endPressure;
	}
	public Float getStartCutterTorque() {
		return startCutterTorque;
	}
	public Float getEndCutterTorque() {
		return endCutterTorque;
	}
	public Float getStartTotalThrust() {
		return startTotalThrust;
	}
	public Float getEndTotalThrust() {
		return endTotalThrust;
	}
	public Float getStartCutterSpeed() {
		return startCutterSpeed;
	}
	public Float getEndCutterSpeed() {
		return endCutterSpeed;
	}
	public Float getStartSpeed() {
		return startSpeed;
	}
	public Float getEndSpeed() {
		return endSpeed;
	}
	public Float getStartGroutingAmount() {
		return startGroutingAmount;
	}
	public Float getEndGroutingAmount() {
		return endGroutingAmount;
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
	public void setShieldTunneling(String shieldTunneling) {
		this.shieldTunneling = shieldTunneling;
	}
	public void setShieldTunnelingImg(String shieldTunnelingImg) {
		this.shieldTunnelingImg = shieldTunnelingImg;
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
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public void setEffectImg(String effectImg) {
		this.effectImg = effectImg;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public void setSettlementImg(String settlementImg) {
		this.settlementImg = settlementImg;
	}
	public void setParamAnalysis(String paramAnalysis) {
		this.paramAnalysis = paramAnalysis;
	}
	public void setParamAnalysisImg(String paramAnalysisImg) {
		this.paramAnalysisImg = paramAnalysisImg;
	}
	public void setSlagSamplesImg(String slagSamplesImg) {
		this.slagSamplesImg = slagSamplesImg;
	}
	public void setHorizontalAttitude(String horizontalAttitude) {
		this.horizontalAttitude = horizontalAttitude;
	}
	public void setHorizontalAttitudeImg(String horizontalAttitudeImg) {
		this.horizontalAttitudeImg = horizontalAttitudeImg;
	}
	public void setVerticalAttitude(String verticalAttitude) {
		this.verticalAttitude = verticalAttitude;
	}
	public void setVerticalAttitudeImg(String verticalAttitudeImg) {
		this.verticalAttitudeImg = verticalAttitudeImg;
	}
	public void setGrouting(String grouting) {
		this.grouting = grouting;
	}
	public void setGroutingImg(String groutingImg) {
		this.groutingImg = groutingImg;
	}
	public void setOiltemperature(String oiltemperature) {
		this.oiltemperature = oiltemperature;
	}
	public void setOiltemperatureImg(String oiltemperatureImg) {
		this.oiltemperatureImg = oiltemperatureImg;
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
	public void setStartRingNum(Float startRingNum) {
		this.startRingNum = startRingNum;
	}
	public void setEndRingNum(Float endRingNum) {
		this.endRingNum = endRingNum;
	}
	public void setStartPressure(Float startPressure) {
		this.startPressure = startPressure;
	}
	public void setEndPressure(Float endPressure) {
		this.endPressure = endPressure;
	}
	public void setStartCutterTorque(Float startCutterTorque) {
		this.startCutterTorque = startCutterTorque;
	}
	public void setEndCutterTorque(Float endCutterTorque) {
		this.endCutterTorque = endCutterTorque;
	}
	public void setStartTotalThrust(Float startTotalThrust) {
		this.startTotalThrust = startTotalThrust;
	}
	public void setEndTotalThrust(Float endTotalThrust) {
		this.endTotalThrust = endTotalThrust;
	}
	public void setStartCutterSpeed(Float startCutterSpeed) {
		this.startCutterSpeed = startCutterSpeed;
	}
	public void setEndCutterSpeed(Float endCutterSpeed) {
		this.endCutterSpeed = endCutterSpeed;
	}
	public void setStartSpeed(Float startSpeed) {
		this.startSpeed = startSpeed;
	}
	public void setEndSpeed(Float endSpeed) {
		this.endSpeed = endSpeed;
	}
	public void setStartGroutingAmount(Float startGroutingAmount) {
		this.startGroutingAmount = startGroutingAmount;
	}
	public void setEndGroutingAmount(Float endGroutingAmount) {
		this.endGroutingAmount = endGroutingAmount;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
