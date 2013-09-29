package com.wootwoot.picnshare.Images;

import java.util.ArrayList;

public class ImageStore {
	private static ArrayList<Image> imageList = new ArrayList<Image>();
	
	public static int getImageCount() {
		return imageList.size();
	}
	
	public void addImages (Image image) {
		imageList.add(image);
	}
}
