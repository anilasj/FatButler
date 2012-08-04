package com.sc.domain;

import java.util.Date;

public class Notification {
	public static final String NOTIFY_ASSIGNMENT = "ASS";
	public static final String NOTIFY_DONE = "DON";
	
	private Integer uid;
	private Integer sourceId;
	private Integer taskId;
	private boolean processedFlg;
	private String type;
	private Date modified;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public boolean isProcessedFlg() {
		return processedFlg;
	}
	public void setProcessedFlg(boolean processedFlg) {
		this.processedFlg = processedFlg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	
}
