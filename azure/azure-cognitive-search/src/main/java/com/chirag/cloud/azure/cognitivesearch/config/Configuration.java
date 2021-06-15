package com.chirag.cloud.azure.cognitivesearch.config;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.SearchClientBuilder;

public class Configuration {

	private static SearchClient searchClient = null;
	
	public static SearchClient getSearchClientInstance() {
		if(searchClient == null) {
			synchronized (Configuration.class) {
				if(searchClient == null) {
					searchClient = new SearchClientBuilder()
							.endpoint("https://int-codebook-index.search.azure.us")
							.credential(new AzureKeyCredential("83800877726D4E44B98D65027882F7C4"))
							.indexName("intcodebookindex")
							.buildClient();
				}
			}
		}
		searchClient.search("i10");
		return searchClient;
	}
}
