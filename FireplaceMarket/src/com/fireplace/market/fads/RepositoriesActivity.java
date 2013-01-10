package com.fireplace.market.fads;

import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.controller.FireplaceController;

public class RepositoriesActivity extends FireplaceController{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSupportActionBar.setDisplayHomeAsUpEnabled(true);
		mSupportActionBar.setTitle("Repositories");
		
	}

	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	super.finish();
	        break;
	       
        default:
            return super.onOptionsItemSelected(item);
	    }
		return true;
	}
	
}
