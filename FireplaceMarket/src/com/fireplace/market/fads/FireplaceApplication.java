package com.fireplace.market.fads;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fireplace.market.fads.dal.helpers.FireplaceDBHelper;

public class FireplaceApplication extends Application {

	public static final Boolean DEBUG = true;

	public static final String DATABASE_NAME = "fireplace";
	public static final Integer DATABASE_VERSION = 1;
	public static final String REPO_KEY = "repoKey";
	public static final String APP_KEY = "appKey";
	public static final String MODEL_KEY = "modelKey";

	public static final String API_ROOT = "http://www.fireplace-market.com/";

	public static FireplaceDBHelper mDbHelper;
	protected ProgressDialog mLoadingDialog;

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

	public static void makeToast(Context context, String toastText) {
		makeToast(context, toastText, R.drawable.toast, R.color.StartupDarkGrey);
	}

	public static void makeToast(Context context, String toastText,
			int backgroundResource) {
		makeToast(context, toastText, backgroundResource,
				R.color.StartupDarkGrey);
	}

	public static void makeToast(Context context, String toastText,
			int backgroundResource, int textColorResource) {
		LinearLayout toastLayout = new LinearLayout(context);
		toastLayout.setBackgroundResource(backgroundResource);
		TextView text = new TextView(context);
		text.setTextColor(context.getResources().getColor(textColorResource));
		text.setTypeface(null, Typeface.BOLD);
		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		text.setText(toastText);
		toastLayout.addView(text);
		Toast toast = new Toast(context);
		toast.setView(toastLayout);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

	public ProgressDialog getLoadingDialog() {
		return mLoadingDialog;
	}

	public void setLoadingDialog(ProgressDialog loadingDialog) {
		this.mLoadingDialog = loadingDialog;
	}

	public void showProgressLoader(Context context, CharSequence title,
			CharSequence message, boolean indeterminate) {
		mLoadingDialog = ProgressDialog.show(context, title, message,
				indeterminate);
	}

	public void showProgressLoader(Context context, CharSequence title,
			CharSequence message, boolean indeterminate, boolean cancelable) {
		mLoadingDialog = ProgressDialog.show(context, title, message,
				indeterminate, cancelable);
	}

	public void dismissProgressLoader() {
		if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
			this.mLoadingDialog.dismiss();
		}
	}

}
