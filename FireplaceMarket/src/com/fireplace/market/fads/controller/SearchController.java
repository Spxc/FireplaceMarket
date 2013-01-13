package com.fireplace.market.fads.controller;

import android.os.Bundle;

import com.fireplace.market.fads.R;
import com.fireplace.market.fads.R.layout;

public class SearchController extends FireplaceController {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSupportActionBar.setDisplayHomeAsUpEnabled(true);
		mSupportActionBar.setTitle("Search");

	}

}
