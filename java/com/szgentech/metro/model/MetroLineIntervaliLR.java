package com.szgentech.metro.model;


/**
 * APP主页选择区间返回的自定义数据（单独查询区间和左右线）
 * @author admin
 *
 */
public class MetroLineIntervaliLR implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3979577866677724755L;
	
	private Long id;
	private String intervalname;//线路区间
	private String leftorright;//左右线
	
	private SettlingVelocity settlingVelocity;//沉降速率
	private SettlingTotal settlingTotal;//累计沉降
	
	public SettlingVelocity getSettlingVelocity() {
		return settlingVelocity;
	}
	public void setSettlingVelocity(SettlingVelocity settlingVelocity) {
		this.settlingVelocity = settlingVelocity;
	}
	public SettlingTotal getSettlingTotal() {
		return settlingTotal;
	}
	public void setSettlingTotal(SettlingTotal settlingTotal) {
		this.settlingTotal = settlingTotal;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIntervalname() {
		return intervalname;
	}
	public void setIntervalname(String intervalname) {
		this.intervalname = intervalname;
	}
	public String getLeftorright() {
		return leftorright;
	}
	public void setLeftorright(String leftorright) {
		this.leftorright = leftorright;
	}
	
	
}
