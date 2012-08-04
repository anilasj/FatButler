package com.sc.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Error {
	public static final Integer AUTH_ERR = new Integer("100");
	public static final Integer MISSING_API_KEY = new Integer("101");
	public static final Integer ACC_ERR = new Integer("102");
	public static final Integer INVALID_API_KEY = new Integer("103");
	public static final Integer SUCCESS = new Integer("500");
	
	public static final Integer MISSING_FIELD = new Integer("201");
	public static final Integer INVALID_FIELD = new Integer("202");
	public static final Integer BAD_FORMAT = new Integer("203");
	public static final Integer INVALID_MISSING_FIELD = new Integer("204");
	
	private Integer id;
	private String description;
	
	
	public Error() {
		super();
	}
	public Error(Integer id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
