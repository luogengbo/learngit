package com.szgentech.metro.model;

import java.util.Date;
/**
 * 区间左右线信息
 * @author hank
 *
 * 2016年8月24日
 */
public class MetroLineIntervalLr implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6615467204511260060L;
	private Long id;//系统ID
	private Long intervalId; //区间id
	private String leftOrRight;//左右线标记
	private Integer ringNum;//总环数
	private Integer buildStatus;//施工状态0未启动1已启动2已贯通
	private Integer status; //全网图是否可见，0：停用 1：启用
	private Date buildDate;//施工日期
	private Date throughDate;//贯通日期
	private String intervalColor;//线路颜色 颜色编码
	private String mapXy;//左或右线坐标集合[{"x":1,"y":1},{"x":2,"y":2}]},{"R":[{"x":1,"y":1},{"x":2,"y":2}]
	private String machineNo;//盾构机编号
	private String machineCompany;//盾构机厂家
	private String machineType;//盾构机类型
	private Date machineProductDate;//盾构机出厂日期
	private String machineContractor;//盾构机承包商
	private Date machineReviewDate;//盾构机审查日期
	private String remark; //备注
	private Long cutterPhotoId; //刀盘背景图id
	private Long slurryPhotoId; //刀盘背景图id
	private Long screwPhotoId; //螺旋背景图id
	private Integer isDel; //是否删除
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private Integer speedQualifiedRange;//日掘进速度合格范围
	private String direction;//推进方向，0是大里程从小往大的推进，1是小里程从大往小的推进

	public MetroLineIntervalLr(){}

	/**
	 * 获取cutterPhotoId
	 * @return cutterPhotoId cutterPhotoId
	 */
	public Long getCutterPhotoId() {
		return cutterPhotoId;
	}

	/**
	 * 设置cutterPhotoId
	 * @param cutterPhotoId cutterPhotoId 
	 */
	public void setCutterPhotoId(Long cutterPhotoId) {
		this.cutterPhotoId = cutterPhotoId;
	}

	/**
	 * 获取screwPhotoId
	 * @return screwPhotoId screwPhotoId
	 */
	public Long getScrewPhotoId() {
		return screwPhotoId;
	}

	/**
	 * 设置screwPhotoId
	 * @param screwPhotoId screwPhotoId 
	 */
	public void setScrewPhotoId(Long screwPhotoId) {
		this.screwPhotoId = screwPhotoId;
	}

	/**
	 * 获取slurryPhotoId
	 * @return slurryPhotoId slurryPhotoId
	 */
	public Long getSlurryPhotoId() {
		return slurryPhotoId;
	}

	/**
	 * 设置slurryPhotoId
	 * @param slurryPhotoId
	 */
	public void setSlurryPhotoId(Long slurryPhotoId) {
		this.slurryPhotoId = slurryPhotoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeftOrRight() {
		return leftOrRight;
	}

	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}

	public Integer getRingNum() {
		return ringNum;
	}

	public void setRingNum(Integer ringNum) {
		this.ringNum = ringNum;
	}

	public Integer getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Date getThroughDate() {
		return throughDate;
	}

	public void setThroughDate(Date throughDate) {
		this.throughDate = throughDate;
	}

	public String getIntervalColor() {
		return intervalColor;
	}

	public void setIntervalColor(String intervalColor) {
		this.intervalColor = intervalColor;
	}

	/**
	 * 获取mapXy
	 * @return mapXy mapXy
	 */
	public String getMapXy() {
		return mapXy;
	}

	/**
	 * 设置mapXy
	 * @param mapXy mapXy 
	 */
	public void setMapXy(String mapXy) {
		this.mapXy = mapXy;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getMachineCompany() {
		return machineCompany;
	}

	public void setMachineCompany(String machineCompany) {
		this.machineCompany = machineCompany;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public Date getMachineProductDate() {
		return machineProductDate;
	}

	public void setMachineProductDate(Date machineProductDate) {
		this.machineProductDate = machineProductDate;
	}

	public String getMachineContractor() {
		return machineContractor;
	}

	public void setMachineContractor(String machineContractor) {
		this.machineContractor = machineContractor;
	}

	public Date getMachineReviewDate() {
		return machineReviewDate;
	}

	public void setMachineReviewDate(Date machineReviewDate) {
		this.machineReviewDate = machineReviewDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取isDel
	 * @return isDel isDel
	 */
	public Integer getIsDel() {
		return isDel;
	}

	/**
	 * 设置isDel
	 * @param isDel isDel
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	/**
	 * 获取status
	 * @return status status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置status
	 * @param status status 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取remark
	 * @return remark remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置remark
	 * @param remark remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取intervalId
	 * @return intervalId intervalId
	 */
	public Long getIntervalId() {
		return intervalId;
	}

	/**
	 * 设置intervalId
	 * @param intervalId intervalId 
	 */
	public void setIntervalId(Long intervalId) {
		this.intervalId = intervalId;
	}

	public Integer getSpeedQualifiedRange() {
		return speedQualifiedRange;
	}

	public void setSpeedQualifiedRange(Integer speedQualifiedRange) {
		this.speedQualifiedRange = speedQualifiedRange;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
