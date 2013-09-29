package com.wootwoot.picnshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wootwoot.picnshare.Images.ImageStore;
import com.wootwoot.picnshare.Images.ImageStore.IImageStoreClient;

public class SearchActivity extends Activity implements IImageStoreClient {

	private boolean searching;
	private int numOfResults;
	private boolean loadMore;
	private ListAdapter listAdapter = new ImageAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		if (savedInstanceState == null) {
			searching = false;
			numOfResults = 0;
			loadMore = false;
		} else {
			searching = savedInstanceState.getBoolean("SEARCHING");
			numOfResults = savedInstanceState.getInt("RESULTS_AVAILABLE");
			loadMore = savedInstanceState.getBoolean("LOAD_MORE");
		}
		updateUI();
	}

	private void updateUI() {
		if (searching) {
			ProgressBar prbarSearch = (ProgressBar) findViewById(R.id.progbar_search);
			prbarSearch.setVisibility(View.VISIBLE);
		} else {
			ProgressBar prbarSearch = (ProgressBar) findViewById(R.id.progbar_search);
			prbarSearch.setVisibility(View.INVISIBLE);
		}

		if (loadMore) {
			ProgressBar prbarLoad = (ProgressBar) findViewById(R.id.progbar_load_more);
			prbarLoad.setVisibility(View.VISIBLE);
		} else {
			ProgressBar prbarLoad = (ProgressBar) findViewById(R.id.progbar_load_more);
			prbarLoad.setVisibility(View.INVISIBLE);
		}
		
		if (ImageStore.getImageCount() > 0) {
			ImageStore.getThumbList(numOfResults);
			
			GridView grid = (GridView) findViewById(R.id.grid_search_results);
			listAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, ImageStore.getImagesTitles());
			grid.setAdapter(listAdapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putBoolean("SEARCHING", searching);
		bundle.putInt("RESULTS_AVAILABLE", numOfResults);
		bundle.putBoolean("LOAD_MORE", loadMore);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		searching = savedInstanceState.getBoolean("SEARCHING");
		numOfResults = savedInstanceState.getInt("RESULTS_AVAILABLE");
		loadMore = savedInstanceState.getBoolean("LOAD_MORE");
	}

	public void loadMore(View v) {
		if (loadMore == true) {
			Toast.makeText(SearchActivity.this, R.string.already_searching,
					Toast.LENGTH_SHORT).show();
			return;
		}

		getMoreImages();

		updateUI();
	}

	public void startSearch(View v) {
		if (searching == true) {
			Toast.makeText(SearchActivity.this, R.string.already_searching,
					Toast.LENGTH_LONG).show();
			return;
		}

		searching = true;
		numOfResults = 0;
		loadMore = false;

		getMoreImages();

		updateUI();
	}

	private void getMoreImages() {
		EditText searchBar = (EditText) findViewById(R.id.txt_search_term);
		String term = searchBar.getText().toString();

		System.out.println("Searching for " + term);
		ImageStore.getNewImages(term, this);
	}

	@Override
	public void onNewImages() {
		searching = false;
		loadMore = false;
		updateUI();
	}

	class ImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}