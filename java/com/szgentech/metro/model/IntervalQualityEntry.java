package com.szgentech.metro.model;

import java.util.List;

public class IntervalQualityEntry {
	private String msg;
	private int total;
	private List<DuctPiece> rows;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<DuctPiece> getRows() {
		return rows;
	}

	public void setRows(List<DuctPiece> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "IntervalQualityEntry [msg=" + msg + ", total=" + total + ", rows=" + rows + "]";
	}

}
