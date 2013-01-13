package com.fireplace.market.fads;

import android.app.Application;

import com.fireplace.market.fads.dal.helpers.FireplaceDBHelper;

public class FireplaceApplication extends Application {

	public static final Boolean DEBUG = true;

	public static final String DATABASE_NAME = "fireplace";
	public static final Integer DATABASE_VERSION = 1;
	public static final String REPO_KEY = "repoKey";
	public static final String APP_KEY = "appKey";
	public static final String MODEL_KEY = "modelKey";

	public static FireplaceDBHelper mDbHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		if (mDbHelper == null) {
			mDbHelper = FireplaceDBHelper.getInstance(this);
		}
	}

	@Override
	public void onTerminate() {
		if (mDbHelper != null) {
			mDbHelper.close();
			mDbHelper = null;
		}
		super.onTerminate();
	}

}
