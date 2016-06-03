package com.xavient.soapui.bean;

public class UploadResponse {
	
	private String uploadStatus;

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return uploadStatus;
	}
}
