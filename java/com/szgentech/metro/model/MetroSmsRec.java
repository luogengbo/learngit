package com.szgentech.metro.model;

import java.util.Date;

public class MetroSmsRec implements java.io.Serializable{
	
	private static final long serialVersionUID = -6777957478732848603L;
	
	private Long id;//系统ID
	private String templateId;//短信模板ID
	private String sendTo;//收信人手机号码，多个用英文逗号分隔
	private String params;//短信参数，JSON数组
	private Long operator;//操作员，即发送人
	private	String statusCode;//短信平台返回码，000000为成功，其他为失败
	private	String statusMsg;//短信平台返回信息
	private Date createTime;//创建时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
