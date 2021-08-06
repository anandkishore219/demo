package com.example.demo.model;

import java.util.List;


public class Message {
	private String companyName;
	private String message;
	private String fromNumber;
	private List<String> toNumbers;
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public List<String> getToNumbers() {
		return toNumbers;
	}
	public void setToNumbers(List<String> toNumbers) {
		this.toNumbers = toNumbers;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	
	@Override
	public String toString() {
		return "Message [companyName=" + companyName + ", message=" + message + ", fromNumber=" + fromNumber
				+ ", toNumbers=" + toNumbers + "]";
	}
	
}
