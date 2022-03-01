package com.chirag.cloud.azure.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.cloud.azure.service.keyvault.KeyvaultService;

@RestController
@RequestMapping("keyvault")
public class KeyvaultController {

	@Autowired
	private KeyvaultService keyvaultService;
	
	@GetMapping("/key")
	public ResponseEntity<String> getKeyvaultValue(@RequestParam("key") String key) {
		String keyValue = keyvaultService.getKeyValue(key);
		return new ResponseEntity<String>(keyValue, HttpStatus.OK);
	}
}
