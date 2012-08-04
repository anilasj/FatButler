package com.sc.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response {
	
	public static final String STATUS_ERR = "Error";
	public static final String STATUS_SUCCESS = "Success";
	
	private Integer recordKey;
	private String status = STATUS_SUCCESS;
	private List<Error> errors;
	public Integer getRecordKey() {
		return recordKey;
	}
	public void setRecordKey(Integer recordKey) {
		this.recordKey = recordKey;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void setErrors(List<Error> errors) {
		this.errors = errors;
		
		if (errors != null && !errors.isEmpty())
			status = STATUS_ERR;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
