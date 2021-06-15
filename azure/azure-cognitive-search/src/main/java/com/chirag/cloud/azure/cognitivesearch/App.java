package com.chirag.cloud.azure.cognitivesearch;

import java.util.Arrays;

import com.azure.core.util.Context;
import com.azure.search.documents.SearchClient;
import com.azure.search.documents.models.SearchOptions;
import com.azure.search.documents.util.SearchPagedIterable;
import com.chirag.cloud.azure.cognitivesearch.config.Configuration;

public class App {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SearchClient searchClient = Configuration.getSearchClientInstance();

		System.out.println("-------Start searching");
		SearchPagedIterable searchPagedIterable = searchClient.search("58740");
		searchPagedIterable.forEach(result -> {
			SearchModelDto document = result.getDocument(SearchModelDto.class);
			System.out.println(document);
		});

		System.out.println("-----------Done1");
		SearchOptions options = new SearchOptions();
		options.setSearchFields("fields/booktype");
		SearchPagedIterable searchPagedIterable2 = searchClient.search("CPT", options, Context.NONE);
		// System.out.println(searchPagedIterable2.getTotalCount());

		searchPagedIterable2.forEach(result -> {
			SearchModelDto document = result.getDocument(SearchModelDto.class);
			System.out.println(document);
		});

		System.out.println("-------------------Done2");
	}

}
