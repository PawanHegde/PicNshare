package com.wootwoot.picnshare.search;

import net.billylieurance.azuresearch.AzureSearchImageQuery;
import net.billylieurance.azuresearch.AzureSearchImageResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

public class SearchEngine {
	private static String appId = "V8ZJlzZ55Q+tbE2OtpWRWKXp7mjZDf8FGlrB8wSJCnk="; 
	
	public void getImages(String query, int skip) {
		AzureSearchImageQuery sQuery = new AzureSearchImageQuery();
		sQuery.setAppid(appId);
		sQuery.setSkip(skip);
		sQuery.setQuery(query);
		
		sQuery.doQuery();
		
		AzureSearchResultSet<AzureSearchImageResult> imageResults = sQuery.getQueryResult();
		System.out.println (imageResults);
	}
}
