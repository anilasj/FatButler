package com.sc.util;

public class NameValuePair {
	private String value;
	private String label;
	
	public NameValuePair(String label, String value) {
		super();
		this.value = value;
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
