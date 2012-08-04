package com.sc.mail;

import java.util.HashMap;

public class EmailMessage {
	private String[] toAddressList;
	private String fromAddress;
	private String[] ccList;
	private String messageBody;
	private String subject;
	private String[] attachFiles;
	private HashMap attachImgs;
	
	public void setToAddress(String toAddress) {
		toAddressList = new String[1];
		toAddressList[0] = toAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setCc(String cc) {
		ccList = new String[1];
		ccList[0] = cc;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String[] getToAddressList() {
		return toAddressList;
	}
	public void setToAddressList(String[] toAddressList) {
		this.toAddressList = toAddressList;
	}
	public String[] getCcList() {
		return ccList;
	}
	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}
	public String[] getAttachFiles() {
		return attachFiles;
	}
	public void setAttachFiles(String[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	public HashMap getAttachImgs() {
		return attachImgs;
	}
	public void setAttachImgs(HashMap attachImgs) {
		this.attachImgs = attachImgs;
	}
	
	
}
