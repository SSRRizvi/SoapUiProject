package com.xavient.soapui.bean;

import java.util.ArrayList;
import java.util.List;

public class FileList {
	
	private int size=0;
	//private String projectName;
	private List<String> fileNameList;
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<String> getFileNameList() {
		if(fileNameList==null){
			fileNameList= new ArrayList<String>();
		}
		return fileNameList;
	}
	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}
	/*public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}*/
	
}
