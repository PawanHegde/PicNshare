package com.wootwoot.picnshare;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wootwoot.picnshare.search.SearchEngine;

public class SearchActivity extends Activity {

	private boolean searching;
	private int numOfResults;
	private boolean loadMore;

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
			Toast.makeText(SearchActivity.this, R.string.already_searching, Toast.LENGTH_SHORT).show();
			return;
		}
		
		getImages(numOfResults);
		
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
		
		getImages(0);
		
		updateUI();
	}
	
	private void getImages(int skip) {
		SearchEngine bing = new SearchEngine();
		
		bing.getImages("Hunters", skip);
	}	

}
