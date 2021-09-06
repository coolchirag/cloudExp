package com.chirag.cloud.azure.cognitivesearch;

import java.util.List;
import java.util.Map;

import com.azure.search.documents.SearchClient;
import com.azure.search.documents.models.FacetResult;
import com.azure.search.documents.models.SearchOptions;
import com.azure.search.documents.util.SearchPagedIterable;
import com.chirag.cloud.azure.cognitivesearch.config.Configuration;

public class FacetSearch {

	public static void main(String[] args) {
		SearchClient searchClient = Configuration.getSearchClientInstance();
		SearchOptions options = new SearchOptions()
				.setFilter("code_type eq 'ICD10Diagnosis' and hospital_code eq 'rumc' and user_code eq 'rumc.admin'")
				//.setFacets("f1,count:5")
				.setFacets("code,sort:count,count:7")
				.setSearchFields("code_list")
				.setIncludeTotalCount(true);
		SearchPagedIterable iterable = searchClient.search("i10", options, null);
		Long totalCount = iterable.getTotalCount();
		System.out.println(totalCount);
		Map<String, List<FacetResult>> facets = iterable.getFacets();
		System.out.println(facets);
		
	}
}
