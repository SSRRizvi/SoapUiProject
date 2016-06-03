package com.xavient.soapui.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.xavient.soapui.bean.FileList;
import com.xavient.soapui.bean.FileListResponse;
import com.xavient.soapui.bean.SoapResponse;
import com.xavient.soapui.bean.UploadResponse;
import com.xavient.soapui.process.SoapUiServiceResult;
import com.xavient.soapui.utils.GetProperties;


@Controller
public class SoapuiApi {
	
	@RequestMapping(value = "/rest/getListOfFile", method = RequestMethod.GET)
	public ResponseEntity getFilesList() {
		//Response response = null;
		ResponseEntity response = null;
		
		GetProperties getProperties = new GetProperties();
		String fileLocation = getProperties.getProperty("uploadFileLocation");
		File uploadFolder = new File(fileLocation);
		java.util.List<FileListResponse> fileListResponseList = null;

		if (uploadFolder.list().length > 0) {
			fileListResponseList = new ArrayList<FileListResponse>();
			int index = 1;
			for (String fileName : uploadFolder.list()) {
				// String extension =
				// fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				if (fileName.toUpperCase().contains("XLS")) {
					/*
					 * if( !fileName.endsWith("_Report.xls")){ FileListResponse
					 * fileListResponse = new FileListResponse();
					 * fileListResponse.setFileName(fileName);
					 * fileListResponse.setId(index++);
					 * fileListResponseList.add(fileListResponse); }
					 */
					FileListResponse fileListResponse = new FileListResponse();
					if( !fileName.endsWith("_Report.xls") )
						fileListResponse.setFileName(fileName);
					fileListResponse.setId(index++);
					for(String reportName : uploadFolder.list()){
						if (reportName.toUpperCase().endsWith("_REPORT.XLS") 
								&& reportName.toUpperCase().contains(fileName.toUpperCase().substring(0, fileName.toUpperCase().indexOf(".XLS")))) {
							fileListResponse.setLastReportName(reportName);
							break;
						}
					}
					if(fileListResponse.getFileName() != null){
						fileListResponseList.add(fileListResponse);
					}
				}
			}

			//response = Response.status(200).entity(fileListResponseList).build();
			response = new ResponseEntity<List<FileListResponse>>(fileListResponseList,HttpStatus.OK);
					
		} else {
			//response = Response.status(200).entity("No data found").build();
			response = new ResponseEntity<String>("No data found",HttpStatus.OK);
		}
    	 
	 return response;  
    
    }
	
	@RequestMapping(value = "/rest/getListOfFile/{webService}", method = RequestMethod.GET)
	public ResponseEntity getFilesList(@PathVariable("webService") String webService) {
		ResponseEntity response = null;
		GetProperties getProperties = new GetProperties();
		String fileLocation = getProperties.getProperty("uploadFileLocation");
		fileLocation+=webService.toUpperCase()+"/";
		
		File uploadFolder = new File(fileLocation);
		File reportFolder = new File(fileLocation+"reports/");
		java.util.List<FileListResponse> fileListResponseList = null;
		
		if (uploadFolder.list()!=null && uploadFolder.list().length > 0) {
			fileListResponseList = new ArrayList<FileListResponse>();
			int index = 1;
			for (String fileName : uploadFolder.list()) {
				// String extension =
				// fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
				if (fileName.toUpperCase().contains("XLS")) {
					/*
					 * if( !fileName.endsWith("_Report.xls")){ FileListResponse
					 * fileListResponse = new FileListResponse();
					 * fileListResponse.setFileName(fileName);
					 * fileListResponse.setId(index++);
					 * fileListResponseList.add(fileListResponse); }
					 */
					FileListResponse fileListResponse = new FileListResponse();
					if( !fileName.endsWith("_Report.xls") )
						fileListResponse.setFileName(fileName);
					fileListResponse.setId(index++);
					
					if(reportFolder.list()!=null){
						for(String reportName : reportFolder.list()){
							if (reportName.toUpperCase().endsWith(".XLS") 
									&& reportName.toUpperCase().contains(fileName.toUpperCase().substring(0, fileName.toUpperCase().indexOf(".XLS"))+"_REPORT") ) {
								fileListResponse.setLastReportName(reportName);
								break;
							}
						}
					}
					
					if(fileListResponse.getFileName() != null){
						fileListResponseList.add(fileListResponse);
					}
				}
			}

			//response = Response.status(200).entity(fileListResponseList).build();
			response = new ResponseEntity<List<FileListResponse>>(fileListResponseList,HttpStatus.OK);
		} else {
			//response = Response.status(200).entity("No data found").build();
			response = new ResponseEntity<String>("No data found",HttpStatus.OK);
		}
    	 
	 return response;  
    
    }
	
	@RequestMapping(value = "/rest/getArchiveFilesList/{webService}", method = RequestMethod.GET)
	public ResponseEntity getArchiveFilesList(@PathVariable("webService") String webService) {
		ResponseEntity response = null;
		GetProperties getProperties = new GetProperties();
		String fileLocation = getProperties.getProperty("uploadFileLocation");
		fileLocation+=webService.toUpperCase()+"/archive/";
		System.out.println("fileLocation: "+fileLocation);
		File archiveFolder = new File(fileLocation);
		HashMap<String,FileList> fileListMap = null;
		
		if (archiveFolder.list()!=null && archiveFolder.list().length > 0) {
			fileListMap = new HashMap<String,FileList>();
			for (String fileName : archiveFolder.list()) {
				if (fileName.toUpperCase().contains("XLS")) {
					//FileList fileList = new FileList();
					String projectName=fileName.substring(0, fileName.indexOf("_Report"));
					FileList fileList = fileListMap.get(projectName);
					if(fileList==null){
						fileList = new FileList();
					}
					
					fileList.getFileNameList().add(fileName);
					fileList.setSize(fileList.getSize()+1);
					
					fileListMap.put(projectName, fileList);
				}
			}
			response = new ResponseEntity<HashMap<String,FileList>>(fileListMap,HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("No data found",HttpStatus.OK);
		} 
	 return response;  
    
    }
	
	@RequestMapping(value = "/rest/deleteArchiveFilesList/{webService}", method = RequestMethod.POST)
	public ResponseEntity deleteArchiveFilesList(@PathVariable("webService") String webService, 
			@RequestBody FileList fileList){
		GetProperties getProperties = new GetProperties();
		String fileLocation = getProperties.getProperty("uploadFileLocation");
		fileLocation+=webService.toUpperCase()+"/archive/";
		System.out.println("fileLocation: "+fileLocation);
		
		String notRemove="";
		String notExists="";
		for(String fileName : fileList.getFileNameList()){
			File file = new File(fileLocation+fileName);
			if(file.exists()){
				if(!file.delete()){
					notRemove=notRemove+fileName+";";
				}
			}else{
				notExists=notExists+fileName+";";
			}
		}
		String message="";
		if(!"".equals(notRemove)){
			message="UnableToRemoveTheFiles::"+notRemove;
		}
		if(!"".equals(notExists)){
			message=message+"::::FilesNotExist::"+notExists;
		}
		if("".equals(message)){
			message="files successfully deleted.";
		}
		System.out.println("message::"+message);
    	return new ResponseEntity<String>(message,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/executeSoapRequest", method = RequestMethod.POST)
	public ResponseEntity executeSoapRequest(@RequestBody FileListResponse  fileDetail){
		ResponseEntity response	=	null;
    	SoapResponse soapResponse = null;
    	SoapUiServiceResult soapUiServiceResult	=	new SoapUiServiceResult();
    	System.out.println("env::"+fileDetail.getEnv());
    	System.out.println("env::"+fileDetail.toString());
    	if(fileDetail.getEnv() != null){
    		soapResponse = soapUiServiceResult.execute(fileDetail.getFileName(), fileDetail.getEnv(), fileDetail);
    	}else{
    		soapResponse = soapUiServiceResult.execute(fileDetail.getFileName(), fileDetail);
    	}
    	//response = Response.status(200).entity(soapResponse).build();
    	response = new ResponseEntity<SoapResponse>(soapResponse,HttpStatus.OK);
    	return response;
    }
	
	@RequestMapping(value = "/rest/executeRequest/{webService}", method = RequestMethod.POST)
	public ResponseEntity executeRequest(@PathVariable("webService") String webService, 
			@RequestBody FileListResponse  fileDetail){
		ResponseEntity response	=	null;
    	SoapResponse soapResponse = null;
    	SoapUiServiceResult soapUiServiceResult	=	new SoapUiServiceResult();
    	System.out.println("webService::"+webService+"  env::"+fileDetail.getEnv());
    	System.out.println("toString::"+fileDetail.toString());
    	if("REST".equalsIgnoreCase(webService)){
    		if(fileDetail.getEnv() != null){
        		soapResponse = soapUiServiceResult.executeRest(fileDetail.getFileName(), fileDetail.getEnv(), fileDetail);
        	}else{
        		soapResponse = soapUiServiceResult.executeRest(fileDetail.getFileName(), fileDetail);
        	}
    	}else if("SOAP".equalsIgnoreCase(webService)){
    		if(fileDetail.getEnv() != null){
        		soapResponse = soapUiServiceResult.executeSoap(fileDetail.getFileName(), fileDetail.getEnv(), fileDetail);
        	}else{
        		soapResponse = soapUiServiceResult.executeSoap(fileDetail.getFileName(), fileDetail);
        	}
    	}
    	System.out.println("soapResponse::"+soapResponse.toString());
    	//response = Response.status(200).entity(soapResponse).build();
    	response = new ResponseEntity<SoapResponse>(soapResponse,HttpStatus.OK);
    	return response;
    }
	
	/*@RequestMapping(value = "/rest/executeRestRequest", method = RequestMethod.POST)
	public ResponseEntity executeRestRequest(FileListResponse  fileDetail){
		ResponseEntity response	=	null;
    	SoapResponse soapResponse = null;
    	System.out.println("inside execute Rest request......");
    	SoapUiServiceResult soapUiServiceResult	=	new SoapUiServiceResult();
    	if(fileDetail.getEnv() != null){
    		soapResponse = soapUiServiceResult.executeRest(fileDetail.getFileName(), fileDetail.getEnv(), fileDetail);
    	}else{
    		soapResponse = soapUiServiceResult.executeRest(fileDetail.getFileName(), fileDetail);
    	}
    	
    	//response = Response.status(200).entity(soapResponse).build();
    	response = new ResponseEntity<SoapResponse>(soapResponse,HttpStatus.OK);
    	return response;
    }*/
	
	@RequestMapping(value = "/rest/download/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadXlsFile(@PathVariable("fileName") String fileName) throws IOException {
    	GetProperties getProperties	=	new GetProperties();
    	String fileLocation	=	getProperties.getProperty("uploadFileLocation");
    	System.out.println("Source File::"+fileLocation+fileName);
        File file = new File(fileLocation+fileName);
        //ResponseBuilder response = Response.ok((Object) file);
        //response.header("Content-Disposition", "attachment;filename="+fileName);
        //return response.build();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM/*new MediaType("application", "pdf")*/);
        //header.setContentLength(12345678);
        header.setContentLength((int)file.length());
        header.set("Content-Disposition", "attachment; filename=" + fileName);
                
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr,header,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/download/{webService}/{fileType}/{fileName:.+}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadXlsFile(@PathVariable("webService") String webService,
			@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName) throws IOException {
    	GetProperties getProperties	=	new GetProperties();
    	String fileLocation	=	getProperties.getProperty("uploadFileLocation");
    	System.out.println("download:::webService:"+webService+",\n fileType:"+fileType+",\n fileName"+fileName+"\n fileLocation:"+fileLocation);
    	fileLocation+=webService.toUpperCase()+"/";
    	if("REPORT".equalsIgnoreCase(fileType)){
    		fileLocation+="reports/";
    	}else if("archive".equalsIgnoreCase(fileType)){
    		fileLocation+="archive/";
    	}
    	System.out.println("Source File::"+fileLocation+fileName);
        File file = new File(fileLocation+fileName);
        /*ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename="+fileName);
        return response.build();*/
        
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //header.setContentLength(12345678);
        header.setContentLength((int)file.length());
        header.set("Content-Disposition", "attachment; filename=" + fileName);
        
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr,header,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/rest/upload", method = RequestMethod.POST , consumes="multipart/form-data")
    public ResponseEntity<UploadResponse> uploadFileHandler( @RequestParam("uploadedFile") MultipartFile file) {
		System.out.println("upload file......................");
        /*// Root Directory.
        String uploadRootPath = request.getServletContext().getRealPath(
                "upload");
        System.out.println("uploadRootPath=" + uploadRootPath);*/
		GetProperties getProperties	=	new GetProperties();
    	String uploadRootPath	=	getProperties.getProperty("uploadFileLocation");
    	//uploadRootPath+=webService.toUpperCase()+"/";
    	
        File uploadRootDir = new File(uploadRootPath);
        //
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        
        UploadResponse uploadResponse	=	new UploadResponse();
        
        //
        //List<File> uploadedFiles = new ArrayList<File>();
        /*for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];*/
 
            // Client File Name
            String name = file.getOriginalFilename();
            System.out.println("Client File Name = " + name);
 
            if (name != null && name.length() > 0) {
                try {
                    byte[] bytes = file.getBytes();
 
                    // Create the file on server
                    File serverFile = new File(uploadRootDir.getAbsolutePath()
                            + File.separator + name);
 
                    // Stream to write data to file in server.
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    //
                    //uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                    uploadResponse.setUploadStatus("Upload Successfully"+ serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    uploadResponse.setUploadStatus("File can not be Upload"+"--Error Write file: " + name);
                }
            }
        //}
        //model.addAttribute("uploadedFiles", uploadedFiles);
        return new ResponseEntity<UploadResponse>(uploadResponse,HttpStatus.OK);
    }

	@RequestMapping(value = "/rest/upload/{webService}", method = RequestMethod.POST , consumes="multipart/form-data")
    public ResponseEntity<UploadResponse> uploadFileHandler( @RequestParam("uploadedFile") MultipartFile file, 
    		@PathVariable("webService") String webService) {
		System.out.println("upload file......................");
        GetProperties getProperties	=	new GetProperties();
    	String uploadRootPath	=	getProperties.getProperty("uploadFileLocation");
    	uploadRootPath+=webService.toUpperCase()+"/";
    	
        File uploadRootDir = new File(uploadRootPath);
        
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        
        UploadResponse uploadResponse	=	new UploadResponse();
        
        //List<File> uploadedFiles = new ArrayList<File>();
        /*for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];*/
 
            // Client File Name
            String name = file.getOriginalFilename();
            System.out.println("Client File Name = " + name);
 
            if (name != null && name.length() > 0) {
                try {
                    byte[] bytes = file.getBytes();
 
                    // Create the file on server
                    File serverFile = new File(uploadRootDir.getAbsolutePath()
                            + File.separator + name);
 
                    // Stream to write data to file in server.
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    
                    //uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                    uploadResponse.setUploadStatus("Upload Successfully"+ serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    uploadResponse.setUploadStatus("File can not be Upload"+"--Error Write file: " + name);
                }
            }
        //}
        //model.addAttribute("uploadedFiles", uploadedFiles);
        return new ResponseEntity<UploadResponse>(uploadResponse,HttpStatus.OK);
    }
	
	
	
	
	@RequestMapping(value = "/rest/getEndpointsName", method = RequestMethod.GET)
	public ResponseEntity getEndpoints(){
    	GetProperties getProperties	=	new GetProperties();
    	String endpoints	=	getProperties.getProperty("endpoints");
    	//ResponseBuilder response = null;
    	//ResponseEntity<String[]> response =null;
    	if(endpoints == null){
    		//response = Response.ok("endpoints not defined, check properties");
    		//return response.build();
    		return new ResponseEntity<String>("endpoints not defined, check properties",HttpStatus.OK);
    	}
    	String[] endpointArr = endpoints.split(",");
    	//response = Response.ok(endpointArr);
		return new ResponseEntity<String[]>(endpointArr,HttpStatus.OK);
    }

	public static void main(String[] args){
		SoapuiApi soapuiApi = new SoapuiApi();
		FileList fileList= new FileList();
		fileList.getFileNameList().add("accountEquipment_Report20160427_03-25-45-052_449 - Copy.xls");
		soapuiApi.deleteArchiveFilesList("soap",fileList);
	}
}
