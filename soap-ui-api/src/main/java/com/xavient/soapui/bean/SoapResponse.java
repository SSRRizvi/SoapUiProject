package com.xavient.soapui.bean;

import java.util.List;

public class SoapResponse {
	
	private List<ExecuteSoapResponse> executeSoapResponse;
	private String exportResultPath;
	
	public List<ExecuteSoapResponse> getExecuteSoapResponse() {
		return executeSoapResponse;
	}
	public void setExecuteSoapResponse(List<ExecuteSoapResponse> executeSoapResponse) {
		this.executeSoapResponse = executeSoapResponse;
	}
	
	public String getEportResultPath() {
		return exportResultPath;
	}
	public void setEportResultPath(String eportResultPath) {
		this.exportResultPath = eportResultPath;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return executeSoapResponse +","+exportResultPath;
	}
}
