package com.fireplace.market.fads.task;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import android.content.Context;
import android.os.AsyncTask;

import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.rest.ErrorMessage;
import com.fireplace.market.fads.rest.FireplaceRestTemplate;
import com.fireplace.market.fads.rest.RestErrorWrapper;
import com.fireplace.market.fads.rest.RestResponseException;

public class AppTask extends AsyncTask<Void, Void, Void> {

	public AppTask(FireplaceApplication application, Context context,
			String progressBarMessage) {
		this.mApplication = application;
		this.mContext = context;
		this.mMessage = progressBarMessage;
	}

	private final FireplaceApplication mApplication;
	private final Context mContext;
	private final String mMessage;
	private FireplaceRestTemplate mTemplate;
	private NewListListener newListListener;
	private OnErrorListener onErrorListener;
	private TaskCompletionListener taskCompletionListener;

	public void setNewListListener(NewListListener newListListener) {
		this.newListListener = newListListener;
	}

	public void setOnErrorListener(OnErrorListener onErrorListener) {
		this.onErrorListener = onErrorListener;
	}

	public void setTaskCompletionListener(
			TaskCompletionListener taskCompletionListener) {
		this.taskCompletionListener = taskCompletionListener;
	}

	public static interface NewListListener {
		void onAppsReturned(List<App> appList);
	}

	public static interface OnErrorListener {
		void remoteError(RestErrorWrapper errorWrapper);
	}

	public static interface TaskCompletionListener {
		void onTaskFinished();
	}

	private ReachabilityListener reachabilityListener;

	public void setReacabilityListener(ReachabilityListener reachabilityListener) {
		this.reachabilityListener = reachabilityListener;
	}

	public static interface ReachabilityListener {
		void onUnknownHostException();
	}

	@Override
	protected void onPreExecute() {
		if (!("").equalsIgnoreCase(mMessage))
			// mApplication.showProgressLoader(mContext, "", mMessage, true);
			super.onPreExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Void... unused) {
		if (isCancelled())
			return null;
		mTemplate = new FireplaceRestTemplate(FireplaceApplication.API_ROOT,
				mContext);

		List<App> appList = null;
		if (isCancelled())
			return null;
		try {

			App[] appArray = mTemplate.getRESTful(App.toResourceName(),
					App[].class);
			if (appArray != null && appArray.length > 0) {
				appList = new ArrayList<App>(Arrays.asList(appArray));
				if (appList != null && newListListener != null)
					newListListener.onAppsReturned(appList);
				if (isCancelled())
					return null;
				App.saveOrUpdateAll(appList);
			}

		} catch (ResourceAccessException rae) {
			if (rae.getRootCause() instanceof UnknownHostException) {
				if (reachabilityListener != null)
					reachabilityListener.onUnknownHostException();
			}
		} catch (RestResponseException e) {
			ResponseEntity<ErrorMessage> errorEntity = (ResponseEntity<ErrorMessage>) e
					.getResponseEntity();
			if (onErrorListener != null)
				onErrorListener.remoteError(new RestErrorWrapper(errorEntity));
		} catch (RestClientException rce) {
			rce.printStackTrace();
		} finally {
			if (taskCompletionListener != null)
				taskCompletionListener.onTaskFinished();
		}

		if (isCancelled())
			return null;

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// mApplication.dismissProgressLoader();
		super.onPostExecute(result);
	}
}
