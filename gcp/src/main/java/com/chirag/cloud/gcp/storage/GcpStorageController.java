package com.chirag.cloud.gcp.storage;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcpStorageController {

	@Autowired
	GcpStorageService gcpStorageService;
	
	@GetMapping("/uploadFileForOcr")
	public String uploadFileFOrOcr() throws IOException {
		String bucketName = "test-bucket";
		String cloudPath = "temp-test";
		
		File folder = new File("/home/chiragj/Desktop/temp/ocr-test");
		File[] files = folder.listFiles();
		if(files != null) {
			for(File file : files) {
				System.out.println("Start processing of : "+file.getName());
				gcpStorageService.uploadFile(bucketName, cloudPath, file.getAbsolutePath());
				
			}
		}
		return "Done";
	}
}
