package com.fireplace.market.fads;

import android.os.Build;
import android.util.Log;

public class Fireplace {

	/*
	 * Android Version Constants
	 */

	/**
	 * Whether or not the Application is running on API Level 8 (FROYO) or
	 * higher.
	 */
	public static final Boolean IS_FROYO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 9 (GINGERBREAD) or
	 * higher.
	 */
	public static final Boolean IS_GINGERBREAD = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 11 (HONEYCOMB) or
	 * higher.
	 */
	public static final Boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 12 (HONEYCOMB_MR1)
	 * or higher.
	 */
	public static final Boolean IS_HONEYCOMB_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 14
	 * (ICE_CREAM_SANDWICH) or higher.
	 */
	public static final Boolean IS_ICS = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 16 (JELLY_BEAN) or
	 * higher.
	 */
	public static final Boolean IS_JELLY_BEAN = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? true
			: false;

	/**
	 * Whether or not the Application is running on API Level 17
	 * (JELLY_BEAN_MR1) or higher.
	 */
	public static final Boolean IS_JELLY_BEAN_MR1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ? true
			: false;

	/**
	 * Custom Log Function - DEBUG
	 */
	public static void debugLog(String tag, String msg) {
		if (FireplaceApplication.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * Custom Log Function - ERROR
	 */
	public static void errorLog(String tag, String msg) {
		if (FireplaceApplication.DEBUG) {
			Log.e(tag, msg);
		}
	}

	/**
	 * Custom Log Function - INFO
	 */
	public static void infoLog(String tag, String msg) {
		if (FireplaceApplication.DEBUG) {
			Log.i(tag, msg);
		}
	}
}
