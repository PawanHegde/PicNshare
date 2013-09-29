package com.wootwoot.picnshare.Images;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.wootwoot.picnshare.search.SearchEngine;

public class ImageStore {
	private static ArrayList<Image> imageList = null;
	private static String curTerm = null;
	private static IImageStoreClient client = null;

	public interface IImageStoreClient {
		void onNewImages();
	}

	public static int getImageCount() {
		if (imageList != null)
			return imageList.size();
		return 0;
	}

	public static void addImages(List<Image> images) {
		imageList.addAll(images);
		
		Runnable runnable = new Runnable () {

			@Override
			public void run() {
				client.onNewImages();
			}
			
		};
		
		((Activity)client).runOnUiThread(runnable);
	}

	public static List<Image> getThumbList(int start) {
		if (start < imageList.size()) {
			return imageList.subList(start, imageList.size() - 1);
		}
		return null;
	}

	public static void getNewImages(String term, IImageStoreClient client) {
		if (curTerm == null) {
			curTerm = term;
			imageList = new ArrayList<Image>();
			ImageStore.client = client;
		}
		SearchEngine bing = new SearchEngine(term, imageList.size());
		new Thread(bing).start();
	}
	
	public static List<String> getTitles() {
		int i = 0;
		ArrayList<String> titles = new ArrayList<String>();
		
		while (i < imageList.size()) {
			titles.add(imageList.get(i).title);
		}
		
		return titles;
	}
	
	public static List<Image> getImages() {
		return imageList;
	}

	public static String[] getImagesTitles() {
		String [] imageTitles = new String[imageList.size()];
		
		int i = 0;
		
		while (i < imageList.size()) {
			imageTitles[i] = imageList.get(i).toString();
			i++;
		}
				
		return imageTitles;
	}
}
