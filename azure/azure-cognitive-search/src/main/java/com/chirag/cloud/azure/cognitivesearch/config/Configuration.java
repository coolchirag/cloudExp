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
							.endpoint("https://int-coding-platform-search-service.search.azure.us")
							//.endpoint("https://localhost:8085")
							.credential(new AzureKeyCredential("6B83509A84C1A0941F3AE3008BD0BEA9"))
							.indexName("new-codebook-index")
							.buildClient();
				}
			}
		}
		return searchClient;
	}
}
