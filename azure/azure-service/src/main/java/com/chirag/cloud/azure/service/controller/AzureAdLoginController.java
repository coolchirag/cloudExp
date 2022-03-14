package com.chirag.cloud.azure.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.cloud.azure.service.azuread.AzureAdSecurityService;

@RestController
public class AzureAdLoginController {

	@Autowired
	private AzureAdSecurityService adSecurityService;
	
	@GetMapping("/azurelogin")
	public ResponseEntity<String> azureLogin(/*
												 * @RequestParam("token_type") String tokenType, @RequestParam(value =
												 * "access_token", required = false) String
												 * accessToken, @RequestParam("id_token") String idToken
												 */
			HttpServletRequest request) {
		String data = "test";//tokenType+" : "+idToken+" : "+accessToken;
		return new ResponseEntity<String>(data, HttpStatus.OK);
	}
	
	@GetMapping("/azlog")
	public void azLog(HttpServletResponse response) {
		String azureAdLoginUrl = adSecurityService.getAzureAdLoginUrl();
		response.setHeader("Location", azureAdLoginUrl);
		response.setStatus(302);
	}
}
