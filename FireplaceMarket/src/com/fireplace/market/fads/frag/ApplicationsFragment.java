package com.fireplace.market.fads.frag;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.fireplace.market.fads.Fireplace;
import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.controller.DetailedAppsController;
import com.fireplace.market.fads.rest.RestErrorWrapper;
import com.fireplace.market.fads.task.AppTask;
import com.fireplace.market.fads.task.AppTask.NewListListener;
import com.fireplace.market.fads.task.AppTask.OnErrorListener;
import com.fireplace.market.fads.task.AppTask.TaskCompletionListener;
import com.fireplace.market.fads.util.ImageCache.ImageCacheParams;
import com.fireplace.market.fads.util.ImageFetcher;
import com.fireplace.market.fads.view.ApplicationsView;
import com.fireplace.market.fads.view.ApplicationsView.AppAdapter;
import com.fireplace.market.fads.view.ApplicationsView.ViewListener;
import com.fireplace.market.fads.viewmodel.ApplicationsModel;

public class ApplicationsFragment extends SherlockListFragment {

	private int mImageThumbSize;
	private static final String IMAGE_CACHE_DIR = "thumbs";
	private ImageFetcher mImageFetcher;
	private SherlockFragmentActivity mActivity;
	private FireplaceApplication mApplication;
	private ApplicationsView view;
	private ApplicationsModel model;
	private AppAdapter adapter;
	private Boolean fromOnAttach;

	@Override
	public void onAttach(Activity activity) {
		fromOnAttach = true;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = (ApplicationsView) View.inflate(getActivity(),
				R.layout.applications_view, null);
		view.getPullToRefreshListView().getRefreshableView()
				.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		view.setViewListener(viewListener);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.AppImageSize);

		mActivity = getSherlockActivity();
		mApplication = (FireplaceApplication) mActivity.getApplication();

		ImageCacheParams cacheParams = new ImageCacheParams(mActivity,
				IMAGE_CACHE_DIR);

		// Set memory cache to 25% of mem class
		cacheParams.setMemCacheSizePercent(mActivity, 0.25f);

		// The ImageFetcher takes care of loading images into our ImageView
		// children asynchronously
		mImageFetcher = new ImageFetcher(mActivity, mImageThumbSize);
		mImageFetcher.setLoadingImage(R.drawable.ic_launcher);
		mImageFetcher.addImageCache(mActivity.getSupportFragmentManager(),
				cacheParams);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (fromOnAttach) {
			view.getPullToRefreshListView().setRefreshing();
			fromOnAttach = false;
		}

		mActivity = (SherlockFragmentActivity) getActivity();
		adapter = view.new AppAdapter(mActivity, mImageFetcher);
		view.getPullToRefreshListView().setAdapter(adapter);

		if (savedInstanceState == null) {
			model = ApplicationsModel.getInstance();
			setModelDefaults();
		} else {
			if (savedInstanceState.get(FireplaceApplication.MODEL_KEY) != null) {
				model = (ApplicationsModel) savedInstanceState
						.get(FireplaceApplication.MODEL_KEY);
			} else {
				model = ApplicationsModel.getInstance();
				setModelDefaults();
			}
		}
	}

	private void setModelDefaults() {
		model.setIsSearchShowing(false);
		model.setAppList(App.getAll());
		if (Fireplace.IS_HONEYCOMB) {
			adapter.addAll(model.getAppList());
		} else {
			for (App app : model.getAppList()) {
				adapter.add(app);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setExitTasksEarly(true);
		mImageFetcher.flushCache();
	}

	@Override
	public void onResume() {
		refreshAppsFromServer();
		mImageFetcher.setExitTasksEarly(false);
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
		view.destroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(FireplaceApplication.MODEL_KEY, model);
		super.onSaveInstanceState(outState);
	}

	private void refreshAppsFromServer() {
		AppTask aTask = new AppTask(mApplication, mActivity, "");
		aTask.setNewListListener(new NewListListener() {
			@Override
			public void onAppsReturned(final List<App> appList) {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						model.mergeAppList(appList);
						adapter.clear();
						if (Fireplace.IS_HONEYCOMB) {
							adapter.addAll(appList);
						} else {
							for (App app : appList) {
								adapter.add(app);
							}
						}
					}
				});

			}
		});
		aTask.setTaskCompletionListener(new TaskCompletionListener() {
			@Override
			public void onTaskFinished() {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						model.setRefreshComplete();
					}
				});
			}
		});
		aTask.setOnErrorListener(new OnErrorListener() {
			@Override
			public void remoteError(final RestErrorWrapper errorWrapper) {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						FireplaceApplication.makeToast(mActivity,
								errorWrapper.getErrorMessage());
					}
				});
			}
		});
		aTask.execute();
	}

	public ViewListener viewListener = new ViewListener() {

		@Override
		public void onListViewRefresh() {
			// model.setRefreshStarted();
			refreshAppsFromServer();
		}

		@Override
		public void onAppClick(App app) {
			
			/**Intent requestLink = new Intent(mActivity, DetailedAppsController.class);

			Bundle bun = new Bundle();
			bun.putString("Link", "Test");
			bun.putString("Search", "STest");

			requestLink.putExtras(bun);
			startActivity(requestLink); **/
			
			Intent intent = new Intent(mActivity, DetailedAppsController.class);
			Bundle extras = new Bundle();
			extras.putString("appIcon", app.getIcon());
			extras.putString("appName", app.getLabel());
			extras.putString("appUrl", app.getPath());
			extras.putString("appDeveloper", app.getDevel());
			extras.putString("appDescription", app.getDescription());
			intent.putExtras(extras);
			startActivity(intent);
			
			/**
			Intent intent = new Intent(mActivity, DetailedAppsController.class);
			Bundle appInfoBundle = new Bundle();
			//extras.putString("appIcon", app.getIcon());
			appInfoBundle.putString("appName", app.getLabel());
			//extras.putString("appUrl", app.getPath());
			//extras.putString("appDeveloper", app.getDevel());
			//extras.putString("appDescription", app.getDescription());
			//extras.putLong("appId", app.getId()); // Maybe this wont work
			//extras.putLong("appCategory", app.getPtype());// Maybe this wont work
			intent.putExtras(appInfoBundle);
			startActivity(intent); **/ 
			
			/**
			Intent intent = new Intent(mActivity, DetailedAppsController.class);
			intent.putExtra(FireplaceApplication.APP_KEY, app.getId());
			startActivity(intent); 
			**/
		}
	};

	public void toggleSearchDialog() {
		model.setIsSearchShowing(!model.getIsSearchShowing());
	}
}
