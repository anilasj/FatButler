package com.sc.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.sc.util.Formatter;

@XmlRootElement
public class Task {
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_INACTIVE = "I";
	public static final String STATUS_DELETED = "D";
	public static final String STATUS_RECURRING = "R";
	public static final String ACTION_CALL = "C";
	public static final String ACTION_SMS = "S";
	public static final String ACTION_EMAIL = "E";
	public static final String ACTION_LOCATION = "L";
	public static final String SHARE_PUBLIC = "P";
	public static final String SHARE_CUSTOM ="C";
	public static final String PRIORITY_NORMAL = "5";
	public static final String PRIORITY_HIGH = "10";
	public static final String REMINDER_OFF = "OFF";
	public static final String RECUR_ONCE = "ONE";
	public static final String RECUR_DAILY = "DLY";
	public static final String RECUR_WEEKLY = "WLY";
	public static final String RECUR_MONTHLY = "MLY";
	public static final String RECUR_YEARLY = "YLY";
	
	private Integer id;
	private String name;
	private Date dueDate;
	private Integer snoozeMins;
	private Date snoozeDate;
	private String reminder;
	private String priority;
	private String shareType;
	private String status;
	private String action;
	private Integer createdUid;
	private Date created;
	private Date modified;
	private boolean hasNotes;
	private boolean hasComments;
	private boolean done;
	private Date doneDate;
	private String recurrence;
	private Integer listId;
	
	public Task() {
		super();
	}

	public Task(String name) {
		super();
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Integer getSnoozeMins() {
		return snoozeMins;
	}
	public void setSnoozeMins(Integer snoozeMins) {
		this.snoozeMins = snoozeMins;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Integer getCreatedUid() {
		return createdUid;
	}
	public void setCreatedUid(Integer createdUid) {
		this.createdUid = createdUid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public boolean isHasNotes() {
		return hasNotes;
	}
	public void setHasNotes(boolean hasNotes) {
		this.hasNotes = hasNotes;
	}
	public boolean isHasComments() {
		return hasComments;
	}
	public void setHasComments(boolean hasComments) {
		this.hasComments = hasComments;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	
	public boolean isValidRecurrence(){
		if (recurrence == null) return false;
		if (recurrence.equals(Task.RECUR_DAILY) ||
				recurrence.equals(Task.RECUR_MONTHLY) ||
				recurrence.equals(Task.RECUR_WEEKLY) ||
				recurrence.equals(Task.RECUR_YEARLY) ||
				recurrence.equals(Task.RECUR_ONCE))
			return true;
		return false;
			
	}

	public Date getSnoozeDate() {
		return snoozeDate;
	}

	public void setSnoozeDate(Date snoozeDate) {
		this.snoozeDate = snoozeDate;
	}


	public String getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(String recurrence) {
		this.recurrence = recurrence;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public String getDueDay(){
		if (dueDate == null) return "";
		String dateStr = Formatter.getDateAsString(Formatter.PATTERN_DATE_9, dueDate);
		if (dateStr != null)
			return dateStr.substring(0, dateStr.indexOf(","));
		return "";
	}
	
	public String getDueDateStr(){
		if (dueDate == null) return "";
		String dateStr = Formatter.getDateAsString(Formatter.PATTERN_DATE_9, dueDate);
		if (dateStr != null)
			return dateStr.substring(dateStr.indexOf(",") + 2);
		return "";
	}
	
}
