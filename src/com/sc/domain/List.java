package com.sc.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class List {
	
	public static final String STATUS_ACTIVE = "A";
	private Integer id;
	private Integer parentId;
	private String name;
	private Integer createdUid;
	private String status;
	private Date modified;
	private Integer itemCount;
	private java.util.List<List> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCreatedUid() {
		return createdUid;
	}
	public void setCreatedUid(Integer createUid) {
		this.createdUid = createUid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public java.util.List<List> getItems() {
		return items;
	}
	public void setItems(java.util.List<List> items) {
		this.items = items;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	
	
}
