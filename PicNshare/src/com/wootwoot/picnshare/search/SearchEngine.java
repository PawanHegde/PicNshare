package com.wootwoot.picnshare.search;

import java.util.ArrayList;

import net.billylieurance.azuresearch.AzureSearchImageQuery;
import net.billylieurance.azuresearch.AzureSearchImageResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

import com.wootwoot.picnshare.Images.Image;
import com.wootwoot.picnshare.Images.ImageStore;

public class SearchEngine implements Runnable{
	private String appId = "V8ZJlzZ55Q+tbE2OtpWRWKXp7mjZDf8FGlrB8wSJCnk=";
	private String query;
	private int skip;

	private SearchEngine() {
	}
	
	public SearchEngine(String query, int skip) {
		this.query = query;
		this.skip = skip;
	}
	
	private void getImages() {
		
		AzureSearchImageQuery sQuery = new AzureSearchImageQuery();
		sQuery.setAppid(appId);
		sQuery.setSkip(skip);
		sQuery.setQuery(query);
		sQuery.setPerPage(10);

		sQuery.doQuery();

		AzureSearchResultSet<AzureSearchImageResult> imageResults = sQuery
				.getQueryResult();

		feedToImageStore(imageResults.getASRs());

	}

	private void feedToImageStore(ArrayList<AzureSearchImageResult> imageResults) {
		int i = 0;
		Image temp = null;
		ArrayList<Image> images = new ArrayList<Image>();
		AzureSearchImageResult result = null;

		while (i < imageResults.size()) {
			temp = new Image();
			result = imageResults.get(i);

			temp.setContentType(result.getContentType());
			temp.setDisplayUrl(result.getDisplayUrl());
			temp.setFileSize(result.getFileSize());
			temp.setHeight(result.getHeight());
			temp.setId(result.getId());
			temp.setMediaUrl(result.getMediaUrl());
			temp.setSourceUrl(result.getSourceUrl());
			temp.setTitle(result.getTitle());
			temp.setWidth(result.getWidth());

			System.out.println (temp.getTitle());
			
			images.add(temp);
			i++;
		}
		
		ImageStore.addImages(images);

	}

	@Override
	public void run() {
		getImages();
	}
}
