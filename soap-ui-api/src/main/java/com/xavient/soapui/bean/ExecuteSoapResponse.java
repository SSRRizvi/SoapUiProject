package com.xavient.soapui.bean;

public class ExecuteSoapResponse {
	private String status;
	private String totalTestCases;
	private String successCount;
	private String failureCount;
	private String soapFaultCount;
	private String operation;
	private String exception;
	
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTotalTestCases() {
		return totalTestCases;
	}
	public void setTotalTestCases(String totalTestCases) {
		this.totalTestCases = totalTestCases;
	}
	
	public String getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(String successCount) {
		this.successCount = successCount;
	}
	
	public String getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(String failureCount) {
		this.failureCount = failureCount;
	}
	
	public String getSoapFaultCount() {
		return soapFaultCount;
	}
	public void setSoapFaultCount(String soapFaultCount) {
		this.soapFaultCount = soapFaultCount;
	}
	
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return status +","+ totalTestCases +"," +successCount + "," +failureCount +"," +soapFaultCount+"," +operation +"," +exception;
	}
}
