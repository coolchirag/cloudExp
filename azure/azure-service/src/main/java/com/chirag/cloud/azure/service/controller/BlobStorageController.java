package com.chirag.cloud.azure.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.cloud.azure.service.storage.BlobStorageService;

@RestController
@RequestMapping("blobStorage")
public class BlobStorageController {

	@Autowired
	private BlobStorageService blobStorageService;
	
	@GetMapping("/fileStr")
	public ResponseEntity<String> downloadFIleDataInString() {
		String data = blobStorageService.downloadData();
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}
	
	@GetMapping("/fileStrU")
	public ResponseEntity<String> uploadFIleDataInString() {
		String data = blobStorageService.uploadData();
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}
	
	@GetMapping("/fileTransfer")
	public ResponseEntity<String> TransferFile() throws Exception {
		blobStorageService.copyFile("test-container", "file_2021_09_02_0_5.pdf", "test-container", "cfile_2021_09_02_0_5.pdf");
		//blobStorageService.deleteFile("test-container", "file_2021_09_02_0_5.pdf");
		return new ResponseEntity<String>("DOne", HttpStatus.OK);
	}
}
