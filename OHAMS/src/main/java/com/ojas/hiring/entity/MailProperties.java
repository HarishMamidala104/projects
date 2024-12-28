package com.ojas.hiring.entity;

import java.util.List;
import java.util.Map;

public class MailProperties {

	private String fromAddress;
	private List<String> toAddress;
	private List<String> ccAddress;
	private List<String> bccAddress;
	private String emailSubject;
	private String emailContent;
	private Map<String, byte[]> fileAttachments;

//	public MailProperties(String fromAddress, List<String> toAddress, List<String> ccAddress, List<String> bccAddress,
//			String emailSubject, String emailContent, Map<String, byte[]> fileAttachments) {
//		super();
//		this.fromAddress = fromAddress;
//		this.toAddress = toAddress;
//		this.ccAddress = ccAddress;
//		this.bccAddress = bccAddress;
//		this.emailSubject = emailSubject;
//		this.emailContent = emailContent;
//		this.fileAttachments = fileAttachments;
//	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<String> getToAddress() {
		return toAddress;
	}

	public void setToAddress(List<String> toAddress) {
		this.toAddress = toAddress;
	}

	public List<String> getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(List<String> ccAddress) {
		this.ccAddress = ccAddress;
	}

	public List<String> getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(List<String> bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public Map<String, byte[]> getFileAttachments() {
		return fileAttachments;
	}

	public void setFileAttachments(Map<String, byte[]> fileAttachments) {
		this.fileAttachments = fileAttachments;
	}

}
