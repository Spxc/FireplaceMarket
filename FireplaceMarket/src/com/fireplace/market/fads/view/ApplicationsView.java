package com.fireplace.market.fads.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fireplace.market.fads.R;
import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.events.Event;
import com.fireplace.market.fads.events.EventListener;
import com.fireplace.market.fads.util.ImageFetcher;
import com.fireplace.market.fads.viewmodel.ApplicationsModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ApplicationsView extends RelativeLayout {

	/**
	 * The interface to send events from the view to the controller
	 */
	public static interface ViewListener {
		public void onAppClick(App app);

		public void onListViewRefresh();
	}

	/**
	 * The listener reference for sending events
	 */
	private ViewListener viewListener;
	private final ApplicationsModel model;
	private TextView mEmptyView, searchView;
	private PullToRefreshListView listView;

	public void setViewListener(ViewListener viewListener) {
		this.viewListener = viewListener;
	}

	public ApplicationsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		model = ApplicationsModel.getInstance();
	}

	/**
	 * Find our references to the objects in the XML layout
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				viewListener.onListViewRefresh();
			}
		});
		listView.setMode(Mode.BOTH);
		model.addListener(ApplicationsModel.ChangeEvent.LIST_REFRESH_COMPLETE,
				refreshListener);
		model.addListener(ApplicationsModel.ChangeEvent.SEARCH_DIALOG_CHANGE,
				searchViewChangeListener);
		LinearLayout emptyLayout = (LinearLayout) View.inflate(getContext(),
				R.layout.empty, null);
		mEmptyView = (TextView) emptyLayout.findViewById(android.R.id.empty);
		listView.setEmptyView(emptyLayout);

		searchView = (TextView) findViewById(R.id.inputSearchApps);
	}

	public PullToRefreshListView getPullToRefreshListView() {
		return listView;
	}

	public void destroy() {
		model.removeListener(
				ApplicationsModel.ChangeEvent.LIST_REFRESH_COMPLETE,
				refreshListener);
		model.removeListener(
				ApplicationsModel.ChangeEvent.SEARCH_DIALOG_CHANGE,
				searchViewChangeListener);
	}

	private final EventListener refreshListener = new EventListener() {
		@Override
		public void onEvent(Event event) {
			listView.onRefreshComplete();
			if (listView.getRefreshableView().getAdapter().isEmpty())
				mEmptyView.setText(getResources().getString(
						R.string.no_apps_label));
		}
	};

	private final EventListener searchViewChangeListener = new EventListener() {
		@Override
		public void onEvent(Event event) {
			if (model.getIsSearchShowing()) {
				searchView.setVisibility(View.VISIBLE);
			} else {
				searchView.setVisibility(View.GONE);
			}
		}
	};

	public class AppAdapter extends ArrayAdapter<App> {

		private final ImageFetcher mFetcher;

		public AppAdapter(Context context, ImageFetcher fetcher) {
			super(context, 0);
			mFetcher = fetcher;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.app_item, null);
			}

			final App app = getItem(position);

			ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
			if (app.getIcon() != null && !("").equals(app.getIcon())) {
				mFetcher.loadImage(app.getIcon(), icon, false);
			} else {
				icon.setImageResource(R.drawable.ic_launcher);
			}

			TextView title = (TextView) convertView.findViewById(R.id.label);
			title.setText(app.getLabel());

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					viewListener.onAppClick(app);
				}
			});

			return convertView;
		}
	}
}
