package com.sc.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {
	
	public static final Integer MAX_CNT = 100;
	
	private Integer taskId;
	private String comment;
	private Date created;
	private User createUser;
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
