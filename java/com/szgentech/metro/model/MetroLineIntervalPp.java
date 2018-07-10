package com.szgentech.metro.model;

import java.util.Date;

/**
 * 工程进度
 * @author MAJL
 *
 */
public class MetroLineIntervalPp implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5978494097545497563L;
	private Long id;//系统ID
	private Long intervalId; //线路区间id
	private String mapPointa;//大地坐标1
	private String mapPointb;//大地坐标2
	private String svgPointa;//SVG坐标1
	private String svgPointb;//SVG坐标2
	private String sectionPointa;//剖面图坐标1
	private String sectionPointb;//剖面图坐标2
	private String sectionSvgPointa;//剖面SVG坐标1
	private String sectionSvgPointb;//剖面SVG坐标2
	private String ppSvgUrl;//工程进度SVG
	private String sectionSvgUrl;//剖面图SVG
	private Date updateTime;//更新时间
	private Date createTime;//创建时间
	private String section_svg_url_r;//剖面图右线
	
	public MetroLineIntervalPp() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMapPointa() {
		return mapPointa;
	}

	public void setMapPointa(String mapPointa) {
		this.mapPointa = mapPointa;
	}

	public String getMapPointb() {
		return mapPointb;
	}

	public void setMapPointb(String mapPointb) {
		this.mapPointb = mapPointb;
	}

	public String getSvgPointa() {
		return svgPointa;
	}

	public void setSvgPointa(String svgPointa) {
		this.svgPointa = svgPointa;
	}

	public String getSvgPointb() {
		return svgPointb;
	}

	public void setSvgPointb(String svgPointb) {
		this.svgPointb = svgPointb;
	}

	public String getSectionPointa() {
		return sectionPointa;
	}

	public void setSectionPointa(String sectionPointa) {
		this.sectionPointa = sectionPointa;
	}

	public String getSectionPointb() {
		return sectionPointb;
	}

	public void setSectionPointb(String sectionPointb) {
		this.sectionPointb = sectionPointb;
	}

	public String getSectionSvgPointa() {
		return sectionSvgPointa;
	}

	public void setSectionSvgPointa(String sectionSvgPointa) {
		this.sectionSvgPointa = sectionSvgPointa;
	}

	public String getSectionSvgPointb() {
		return sectionSvgPointb;
	}

	public void setSectionSvgPointb(String sectionSvgPointb) {
		this.sectionSvgPointb = sectionSvgPointb;
	}

	public String getPpSvgUrl() {
		return ppSvgUrl;
	}

	public void setPpSvgUrl(String ppSvgUrl) {
		this.ppSvgUrl = ppSvgUrl;
	}


	/**
	 * 获取sectionSvgUrl
	 * @return sectionSvgUrl sectionSvgUrl
	 */
	public String getSectionSvgUrl() {
		return sectionSvgUrl;
	}

	/**
	 * 设置sectionSvgUrl
	 * @param sectionSvgUrl sectionSvgUrl 
	 */
	public void setSectionSvgUrl(String sectionSvgUrl) {
		this.sectionSvgUrl = sectionSvgUrl;
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

	public String getSection_svg_url_r() {
		return section_svg_url_r;
	}

	public void setSection_svg_url_r(String section_svg_url_r) {
		this.section_svg_url_r = section_svg_url_r;
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
	
	
}