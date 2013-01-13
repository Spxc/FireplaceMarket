package com.fireplace.market.fads.controller;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;

import com.fireplace.market.fads.R;
import com.fireplace.market.fads.R.layout;
import com.fireplace.market.fads.bll.ChangeLog;

public class MainController extends FireplaceController {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSupportActionBar.setDisplayHomeAsUpEnabled(true);
		mSupportActionBar.setTitle("Fireplace Market");
		
		File dirApps = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/" + getPackageName() + "/");
		if (dirApps.exists() && dirApps.isDirectory()) {

		} else {
			dirApps.mkdirs();
		}

		ChangeLog cl = new ChangeLog(this);
		if (cl.firstRun())
			cl.getLogDialog().show();

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSlidingMenuFragment.setSelection(1);
	}

}
