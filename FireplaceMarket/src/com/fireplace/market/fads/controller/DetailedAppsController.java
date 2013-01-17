package com.fireplace.market.fads.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.R.layout;

public class DetailedAppsController extends SherlockActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Apps");
		
		Bundle extras = getIntent().getExtras();
		
		String appName = extras.getString("appName");
		String appDeveloper = extras.getString("appDeveloper");
		String appDescription = extras.getString("appDescription");
		
		//Log.d("Getting: ", appName); //This works so appName isnt null
		
		TextView txtAppName = (TextView) findViewById(R.id.txtAppName);
		TextView txtDeveloper = (TextView) findViewById(R.id.txtAppDeveloper);
		TextView txtDesc = (TextView) findViewById(R.id.txtDescription);
		
		txtAppName.setText(appName);
		txtDeveloper.setText(appDeveloper);
		txtDesc.setText("");

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

		
		
		