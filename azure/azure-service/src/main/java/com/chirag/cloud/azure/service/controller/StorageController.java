package com.chirag.cloud.azure.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.cloud.azure.service.storage.StorageService;

@RestController
@RequestMapping("storage")
public class StorageController {
	/*
	 * 
	 * @Autowired private StorageService storageService;
	 * 
	 * @GetMapping("/fileStr") public String downloadFileStr(@RequestParam("name")
	 * String fileName) throws Exception { //fileName =
	 * "document300109_A00000051_1_RO1.txt"; return
	 * storageService.downloadData("test-container", "test_file.txt"); }
	 * 
	 * @GetMapping("/fileBytes") public String
	 * downloadFileData(@RequestParam("name") String fileName) throws Exception {
	 * //fileName = "document300109_A00000051_1_RO1.txt"; byte[] downloadByteData =
	 * storageService.downloadByteData("capc-apigateway",
	 * "document300109_A00000051_1_RO1.txt"); return "DOne"; }
	 */}
