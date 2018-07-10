/**
 * 
 */
package com.szgentech.metro.base.model;

/**
 * @className:CommonResponse.java
 * @author: luowq
 * @createTime: 2015年10月28日
 */
public class CommonResponse {
	/**
	 * 返回信息
	 */
	private Object result;
	/**
	 * 返回结果，成功：1，失败：0
	 */
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
