package com.chirag.cloud.azure.cognitivesearch;

import com.azure.search.documents.SearchClient;
import com.azure.search.documents.models.SuggestOptions;
import com.azure.search.documents.util.SuggestPagedIterable;
import com.chirag.cloud.azure.cognitivesearch.config.Configuration;

public class SuggesterQuery {

	public static void main(String[] args) {
		SearchClient searchClientInstance = Configuration.getSearchClientInstance();
		SuggestOptions suggestOptions = new SuggestOptions()
				.setSearchFields("name_cm")
				.setFilter("start_date le 01/11/2020 and end_date ge 01/11/2020");
		
		SuggestPagedIterable suggest = searchClientInstance.suggest("cong", "index-suggester", suggestOptions, null);
		suggest.forEach(result -> {
			System.out.println("------------------------------------------------------");
			System.out.println("text : "+result.getText());
			System.out.println("doc : "+result.getDocument(SearchModelDto.class));
			
		});
		System.out.println("Done");
	}
}
