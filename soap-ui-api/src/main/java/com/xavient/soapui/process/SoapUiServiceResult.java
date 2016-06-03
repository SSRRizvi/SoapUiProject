package com.xavient.soapui.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xavient.soapui.bean.ExecuteSoapResponse;
import com.xavient.soapui.bean.FileListResponse;
import com.xavient.soapui.bean.SoapResponse;
import com.xavient.soapui.utils.GetProperties;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestSuiteRunner;

public class SoapUiServiceResult {

	int FailureCount 	= 	3;
	int Operation		=	0;
	int SoapFaultCount	=	4;
	int SuccessCount	=	2;
	int Status			=	5;
	int TotalTestCases	=	1;
	int exceptionMessage=	5;
	int errorMsg		=	4;
	
	
	
	
	
	public SoapResponse execute(String xlsFile, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		System.out.println("filePath >> "+projectLoc);
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			List<String> endpoints = new ArrayList<String>();
			for(Map<String, String> methodEndpoint : fileDetail.getMethodEndpointList()){
				endpoints.add(new ArrayList<String>(methodEndpoint.values()).get(0));
			}
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				String wsdlFile = xlsFile
						.substring(0, xlsFile.lastIndexOf("."));
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				project.setPropertyValue("operationName", wsdlFile);
				project.setPropertyValue("dataSheetName", xlsFile);
				project.setPropertyValue("wsdlName", wsdlFile + ".wsdl");
				project.setPropertyValue("outputFilePath", uploadFileLocation);
				//set all endpoints values
				int count = 1;
				String endpoint = "endpoint";
				for(String val : endpoints){
					project.setPropertyValue(endpoint+count, val);
					count++;
				}
				
				WsdlTestSuite testSuite = project
						.getTestSuiteByName("Comparative Analysis Engine");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				//System.out.println("finished :: " + runner.getStatus());
				//String str = testSuite.getPropertyValue("wsdlName");
				//System.out.println("Property :: " + str);
				
			//	executeSoapResponse.setStatus(runner.getStatus().toString());
				String soapUIResponse 	=	testSuite
						.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite
						.getPropertyValue("resultFileName");
								
				executeSoapResponseList = getResolvedDelimetedData(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
	public SoapResponse execute(String xlsFile, String environment, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();;
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			List<String> endpoints = new ArrayList<String>();
			for(Map<String, String> methodEndpoint : fileDetail.getMethodEndpointList()){
				endpoints.add(new ArrayList<String>(methodEndpoint.values()).get(0));
			}
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				String wsdlFile = xlsFile
						.substring(0, xlsFile.lastIndexOf("."));
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				project.setPropertyValue("operationName", wsdlFile);
			//	project.setPropertyValue("dataSheetName", uploadFileLocation + xlsFile);
			//	project.setPropertyValue("wsdlName", uploadFileLocation + wsdlFile + ".wsdl");
				
				project.setPropertyValue("dataSheetName",  xlsFile);
				project.setPropertyValue("wsdlName",  wsdlFile + ".wsdl");
				project.setPropertyValue("env", environment );
				project.setPropertyValue("outputFilePath", uploadFileLocation);
				//set all endpoints values
				int count = 1;
				String endpoint = "endpoint";
				for(String val : endpoints){
					project.setPropertyValue(endpoint+count, val);
					count++;
				}
				
				WsdlTestSuite testSuite = project
						.getTestSuiteByName("Run Engine");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				//System.out.println("finished :: " + runner.getStatus());
				//String str = testSuite.getPropertyValue("wsdlName");
				//System.out.println("Property :: " + str);
				
			//	executeSoapResponse.setStatus(runner.getStatus().toString());
				String soapUIResponse 	=	testSuite
						.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite
						.getPropertyValue("resultFileName");
								
				executeSoapResponseList = getResolvedDelimetedData1(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
	public SoapResponse executeSoap(String xlsFile, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation")+"SOAP/";
		String responseType = getProperties.getProperty("endpoints");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		System.out.println("filePath >> "+projectLoc);
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				//String wsdlFile = xlsFile.substring(0, xlsFile.lastIndexOf("."));
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				//project.setPropertyValue("operationName", wsdlFile);
				project.setPropertyValue("dataSheetName", xlsFile);
				//project.setPropertyValue("wsdlName", wsdlFile + ".wsdl");
				project.setPropertyValue("env", "" );
				project.setPropertyValue("uploadFileLocation", uploadFileLocation);
				project.setPropertyValue("outputFilePath", uploadFileLocation+"reports/");
				project.setPropertyValue("responseType", responseType);
				//set all endpoints values
				project.setPropertyValue("endpoint1", fileDetail.getEndpoint1()==null? "":fileDetail.getEndpoint1());
				project.setPropertyValue("endpoint2", fileDetail.getEndpoint2()==null? "":fileDetail.getEndpoint2());
				
				WsdlTestSuite testSuite = project.getTestSuiteByName("Comparative Analysis Engine SOAP");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				//System.out.println("finished :: " + runner.getStatus());
				//String str = testSuite.getPropertyValue("wsdlName");
				//System.out.println("Property :: " + str);
				
			//	executeSoapResponse.setStatus(runner.getStatus().toString());
				String soapUIResponse 	=	testSuite.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite.getPropertyValue("resultFileName");
				System.out.println("exportedFile: "+exportedFile+"\n soapUIResponse: "+soapUIResponse);
				executeSoapResponseList = getResolvedDelimetedData(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
	public SoapResponse executeSoap(String xlsFile, String environment, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation")+"SOAP/";
		String responseType = getProperties.getProperty("endpoints");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		System.out.println("filePath >> "+projectLoc);
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				//String wsdlFile = xlsFile.substring(0, xlsFile.lastIndexOf("."));
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				//project.setPropertyValue("operationName", wsdlFile);
				project.setPropertyValue("dataSheetName", xlsFile);
				//project.setPropertyValue("wsdlName", wsdlFile + ".wsdl");
				project.setPropertyValue("env", environment );
				project.setPropertyValue("uploadFileLocation", uploadFileLocation);
				project.setPropertyValue("outputFilePath", uploadFileLocation+"reports/");
				project.setPropertyValue("responseType", responseType);
				//set all endpoints values
				project.setPropertyValue("endpoint1", fileDetail.getEndpoint1()==null? "":fileDetail.getEndpoint1());
				project.setPropertyValue("endpoint2", fileDetail.getEndpoint2()==null? "":fileDetail.getEndpoint2());
				
				WsdlTestSuite testSuite = project.getTestSuiteByName("Run Engine SOAP");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				//System.out.println("finished :: " + runner.getStatus());
				//String str = testSuite.getPropertyValue("wsdlName");
				//System.out.println("Property :: " + str);
				
			//	executeSoapResponse.setStatus(runner.getStatus().toString());
				String soapUIResponse 	=	testSuite.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite.getPropertyValue("resultFileName");
				System.out.println("exportedFile: "+exportedFile+"\n soapUIResponse: "+soapUIResponse);
				executeSoapResponseList = getResolvedDelimetedData(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
	public SoapResponse executeRest(String xlsFile, String environment, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();;
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation")+"REST/";
		String responseType = getProperties.getProperty("endpoints");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				
				project.setPropertyValue("dataSheetName",  xlsFile);
				//project.setPropertyValue("wsdlName",  wsdlFile + ".wsdl");
				project.setPropertyValue("env", environment );
				project.setPropertyValue("uploadFileLocation", uploadFileLocation);
				project.setPropertyValue("outputFilePath", uploadFileLocation+"reports/");
				project.setPropertyValue("responseType", responseType);
				//set all endpoints values
				project.setPropertyValue("endpoint1", fileDetail.getEndpoint1()==null? "":fileDetail.getEndpoint1());
				project.setPropertyValue("endpoint2", fileDetail.getEndpoint2()==null? "":fileDetail.getEndpoint2());
				
				WsdlTestSuite testSuite = project.getTestSuiteByName("Run Engine Rest");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				
				String soapUIResponse 	=	testSuite.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite.getPropertyValue("resultFileName");
								
				executeSoapResponseList = getResolvedDelimetedData1(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
	public SoapResponse executeRest(String xlsFile, FileListResponse fileDetail) {
		SoapResponse soapResponse = new SoapResponse();;
		GetProperties getProperties = new GetProperties();
		String uploadFileLocation = getProperties.getProperty("uploadFileLocation")+"REST/";
		String responseType = getProperties.getProperty("endpoints");
		// System.out.println("Project Location is"+projectLocation);
		String projectLoc = "CAE.xml";
		File file = new File(this.getClass().getClassLoader().getResource(projectLoc).getFile());
		projectLoc = file.getAbsolutePath();
		List<ExecuteSoapResponse> executeSoapResponseList	=	new ArrayList<ExecuteSoapResponse>();

		try {
			if (projectLoc != null) {
				ExecuteSoapResponse executeSoapResponse = new ExecuteSoapResponse();
				
				WsdlProject project = null;
				try{
					project = new WsdlProject(projectLoc);
				
				}catch(Exception ee){
					ee.printStackTrace();
				}
				
				project.setPropertyValue("dataSheetName",  xlsFile);
				//project.setPropertyValue("wsdlName",  wsdlFile + ".wsdl");
				project.setPropertyValue("env", "" );
				project.setPropertyValue("uploadFileLocation", uploadFileLocation);
				project.setPropertyValue("outputFilePath", uploadFileLocation+"reports/");
				project.setPropertyValue("responseType", responseType);
				//set all endpoints values
				project.setPropertyValue("endpoint1", fileDetail.getEndpoint1()==null? "":fileDetail.getEndpoint1());
				project.setPropertyValue("endpoint2", fileDetail.getEndpoint2()==null? "":fileDetail.getEndpoint2());
				
				WsdlTestSuite testSuite = project.getTestSuiteByName("Comparative Analysis Engine Rest");
				WsdlTestSuiteRunner runner = testSuite.run(null, false);
				
				String soapUIResponse 	=	testSuite.getPropertyValue("soapUIResponse");
				String exportedFile	=	testSuite.getPropertyValue("resultFileName");
								
				executeSoapResponseList = getResolvedDelimetedData1(soapUIResponse);
			
				soapResponse.setEportResultPath(exportedFile);
				System.out.println("executeSoapResponseList"+executeSoapResponseList);
				
				soapResponse.setExecuteSoapResponse(executeSoapResponseList);
				
			}
		} catch (Exception e) {
			System.out.println((new StringBuilder("Exception - ")).append(
					e.toString()).toString());
		}
		return soapResponse ;
	}
	
		
	
	public List<ExecuteSoapResponse> getResolvedDelimetedData(String result){
		List<ExecuteSoapResponse> executeSoapResponses	=	new ArrayList<ExecuteSoapResponse>();
		
		//System.out.println(result);
		String[] records	=	result.split("::");
		for(String record:records){
			String[] singleRecord	=	record.split(",");
			ExecuteSoapResponse executeSoapRespons	=	new ExecuteSoapResponse();
			executeSoapRespons.setFailureCount(singleRecord[FailureCount]);
			executeSoapRespons.setOperation(singleRecord[Operation]);
			executeSoapRespons.setSoapFaultCount(singleRecord[SoapFaultCount]);
			executeSoapRespons.setSuccessCount(singleRecord[SuccessCount]);
			if(!singleRecord[exceptionMessage].equals("0")){
				executeSoapRespons.setException(singleRecord[exceptionMessage]);
				//System.out.println("exceptionMessage----------------"+executeSoapRespons.getException());
				executeSoapRespons.setStatus("Error");
			}
			else if(Integer.parseInt(singleRecord[FailureCount]) > 0){
				executeSoapRespons.setStatus("Fail");
			}else{
				executeSoapRespons.setStatus("Pass");
			}
			executeSoapRespons.setTotalTestCases(singleRecord[1]);
			executeSoapResponses.add(executeSoapRespons);
		}
		return executeSoapResponses;
	}
	
	public List<ExecuteSoapResponse> getResolvedDelimetedData1(String result){
		List<ExecuteSoapResponse> executeSoapResponses	=	new ArrayList<ExecuteSoapResponse>();
		
		//System.out.println(result);
		String[] records	=	result.split("::");
		for(String record:records){
			String[] singleRecord	=	record.split(",");
			ExecuteSoapResponse executeSoapRespons	=	new ExecuteSoapResponse();
//			executeSoapRespons.setFailureCount(singleRecord[FailureCount]);
			executeSoapRespons.setOperation(singleRecord[0]);
			executeSoapRespons.setSoapFaultCount(singleRecord[3]);
			executeSoapRespons.setSuccessCount(singleRecord[2]);
			if(!singleRecord[errorMsg].equals("0")){
				executeSoapRespons.setException(singleRecord[errorMsg]);
				//System.out.println("exceptionMessage----------------"+executeSoapRespons.getException());
				executeSoapRespons.setStatus("Error");
			}
			/*else if(Integer.parseInt(singleRecord[FailureCount]) > 0){
				executeSoapRespons.setStatus("Fail");
			}*/else{
				executeSoapRespons.setStatus("Pass");
			}
			executeSoapRespons.setTotalTestCases(singleRecord[1]);
			executeSoapResponses.add(executeSoapRespons);
		}
		return executeSoapResponses;
	}
	
	public static void main(String[] args) {
		SoapUiServiceResult soapUiServiceResult = new SoapUiServiceResult();
		//soapUiServiceResult.execute("cpni.xls",null);
		//soapUiServiceResult.getResolvedDelimetedData("getAccountEquipment,2,1,0,1,fds::");
		FileListResponse fileDetail = new FileListResponse();
		fileDetail.setId(1);
		fileDetail.setEnv("CAP");
		fileDetail.setFileName("addition.xls");
		SoapResponse soapResponse = soapUiServiceResult.executeSoap("addition.xls", "CAP", fileDetail );
	}

}
