package com.fireplace.market.fads;

import android.os.Bundle;

import com.fireplace.market.fads.controller.FireplaceController;

public class DetailedAppsActivity extends FireplaceController{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSupportActionBar.setDisplayHomeAsUpEnabled(true);
		mSupportActionBar.setTitle("Will be replaced with app name");
		
	}

}
