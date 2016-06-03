package com.xavient.soapui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
	
	private static Properties prop = new Properties();
	
	static{
		InputStream input = null;
		String filename = "soapuiapi.properties";
		input = GetProperties.class.getClassLoader().getResourceAsStream(filename);
		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Error in loading properties file soapuiapi.properties");
			e.printStackTrace();
		}
	}
	
public String getProperty(String key){
	String value=null;
	if("uploadFileLocation".equals(key) && ( prop.getProperty(key)==null || "".equals(prop.getProperty(key).trim()) ) ){
		value=System.getProperty("catalina.base")+"/soapuiapiUploadFiles/";
	}else{
		value=prop.getProperty(key);
	}
	/*InputStream input = null;
	String value = "";
	try {*/
		
		/*String filename = "resources/soapuiapi.properties";
		Class c	=	getClass();
		File	file 	=	new File(filename);
		System.out.println(file.exists());
		input =new  FileInputStream(file.getAbsolutePath());
		
		if (input == null) {
			System.out.println("Sorry, unable to find " + filename);
			return null;
		}

		prop.load(input);*/
		return value;
	
	/*} catch (IOException ex) {
		ex.printStackTrace();
	} 
	return value;*/
	
}

public static void main(String[] args){
	GetProperties properties	=	new GetProperties();
	System.out.println(properties.getProperty("uploadFileLocation"));
}

}
