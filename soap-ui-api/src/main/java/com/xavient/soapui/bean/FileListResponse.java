package com.xavient.soapui.bean;

import java.util.List;
import java.util.Map;

public class FileListResponse{
	
	private int id;
	private String fileName;
	private String env;
	private List<Map<String,String>> methodEndpointList;
	private String endpoint1;
	private String endpoint2;
	private String lastReportName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
	public List<Map<String, String>> getMethodEndpointList() {
		return methodEndpointList;
	}
	public void setMethodEndpointList(List<Map<String, String>> methodEndpointList) {
		this.methodEndpointList = methodEndpointList;
	}
	
	public String getEndpoint1() {
		return endpoint1;
	}
	public void setEndpoint1(String endpoint1) {
		this.endpoint1 = endpoint1;
	}
	public String getEndpoint2() {
		return endpoint2;
	}
	public void setEndpoint2(String endpoint2) {
		this.endpoint2 = endpoint2;
	}
	
	public String getLastReportName() {
		return lastReportName;
	}
	public void setLastReportName(String lastReportName) {
		this.lastReportName = lastReportName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id:"+id +",\n"+"fileName:"+fileName +",\n"+"env:"+env +",\n"+"methodEndpointList:"+methodEndpointList +",\n"+"endpoint1:"+endpoint1 +",\n"+"endpoint2:"+endpoint2 +",\n"+"lastReportName:"+lastReportName;
	}
}
