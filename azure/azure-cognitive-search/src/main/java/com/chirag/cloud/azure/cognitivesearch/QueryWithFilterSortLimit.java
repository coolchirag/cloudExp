package com.chirag.cloud.azure.cognitivesearch;

import com.azure.core.util.Context;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.models.QueryType;
import com.azure.search.documents.models.SearchOptions;
import com.azure.search.documents.util.SearchPagedIterable;
import com.chirag.cloud.azure.cognitivesearch.config.Configuration;

public class QueryWithFilterSortLimit {

	public static void main(String[] args) {
		SearchClient searchClient = Configuration.getSearchClientInstance();
		String query = "fields/name_cm:'Aberrant'";
		SearchOptions options = new SearchOptions();
		//options.setSearchFields("fields/parent_id");
		//options.setFilter("booktype:ICD10D");
		options.setFilter("fields/parent_id eq 2");
		options.setQueryType(QueryType.SIMPLE);
		options.setSelect("id","fields/id","fields/label","fields/parent_id","fields/name_cm", "fields/booktype");
		
		
		SearchPagedIterable searchPagedIterable = searchClient.search(query, options, Context.NONE);
		searchPagedIterable.forEach(result -> {
			SearchModelDto document = result.getDocument(SearchModelDto.class);
			
			System.out.println(document);
		});
	}
}
