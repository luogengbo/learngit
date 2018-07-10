package com.szgentech.metro.model;

import java.util.Date;
/**
 * 环状态
 * @author luohao
 *
 */
public class RingStatus implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859422776135918666L;

	private Long id;//系统ID
	private Long lrId; //线路区间左右线id
	private Integer ringNo; //环号
	private Date startDt; //开始时间
	private Date endDt; //结束时间
	private Double mileage; //里程
	private Double gcX; //大地坐标X(南北方向)
	private Double gcY; //大地坐标Y(东西方向)
	private Double gcZ; //大地坐标Z
	private Double deviationHorz; //水平偏差
	private Double deviationVert; //垂直偏差
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLrId() {
		return lrId;
	}
	public void setLrId(Long lrId) {
		this.lrId = lrId;
	}
	public Integer getRingNo() {
		return ringNo;
	}
	public void setRingNo(Integer ringNo) {
		this.ringNo = ringNo;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getGcX() {
		return gcX;
	}
	public void setGcX(Double gcX) {
		this.gcX = gcX;
	}
	public Double getGcY() {
		return gcY;
	}
	public void setGcY(Double gcY) {
		this.gcY = gcY;
	}
	public Double getGcZ() {
		return gcZ;
	}
	public void setGcZ(Double gcZ) {
		this.gcZ = gcZ;
	}
	public Double getDeviationHorz() {
		return deviationHorz;
	}
	public void setDeviationHorz(Double deviationHorz) {
		this.deviationHorz = deviationHorz;
	}
	public Double getDeviationVert() {
		return deviationVert;
	}
	public void setDeviationVert(Double deviationVert) {
		this.deviationVert = deviationVert;
	}
	
}
