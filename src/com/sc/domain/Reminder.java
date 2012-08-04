package com.sc.domain;

import java.util.Date;

public class Reminder {
	public static final String TYPE_REMINDER="RE";
	public static final String TYPE_NOTIFICATION="NO";
	
	private Integer taskId;
	private boolean processed;
	private String type;
	private Date runDate;
	private Date modified;
	
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
