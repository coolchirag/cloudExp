package com.chirag.cloud.azure.service.keyvault;

import org.springframework.stereotype.Service;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;

@Service
public class KeyvaultService {
	
	private final String managedIdentityClientId = "<MagedIdentityClientId>";
	private final String keyVaultUri = "https://<keyvaultname>.vault.usgovcloudapi.net/";

	public String getKeyValue(String key) {
		ManagedIdentityCredential credential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
		SecretClient secretClient = new SecretClientBuilder()
				.vaultUrl(keyVaultUri)
				.credential(new DefaultAzureCredentialBuilder().build())
				//.credential(credential)
				.buildClient();
		
		
		KeyVaultSecret secret = secretClient.getSecret(key);
		return secret.getValue();
	}
}
