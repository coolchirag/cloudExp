package com.chirag.cloud.azure.service.azuread;

import org.springframework.stereotype.Service;

@Service
public class AzureAdSecurityService {

	private String clientId = "a694f15e-6ace-4e68-880f-e2f3cdfed6e1";
	private String redirectUrl = "http://localhost:8080/azurelogin";
	
	public String getAzureAdLoginUrl() {
		String loginUrl = "https://login.microsoftonline.us/organizations/oauth2/v2.0/authorize?"
				+ "client_id="+clientId
				+"&response_type=token"
				+"&redirect_url="+redirectUrl
				+"&scope=access_token"
				+"&state=12345"
				+"&nonce=589";
				
		
		return loginUrl;
	}
}
