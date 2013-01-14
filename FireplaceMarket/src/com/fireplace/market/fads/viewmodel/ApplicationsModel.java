package com.fireplace.market.fads.viewmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.events.BaseEvent;
import com.fireplace.market.fads.events.EventDispatcher;

public class ApplicationsModel extends EventDispatcher implements Serializable {

	private static final long serialVersionUID = 7379959697033321138L;

	public static class ChangeEvent extends BaseEvent {
		public static final String APP_LIST_UPDATE = "appListUpdate";
		public static final String LIST_REFRESH_COMPLETE = "listRefreshComplete";
		public static final String LIST_REFRESH_STARTED = "listRefreshStarted";
		public static final String SEARCH_DIALOG_CHANGE = "searchDialogChange";

		public ChangeEvent(String type) {
			super(type);
		}
	}

	private static ApplicationsModel instance;

	private ApplicationsModel() {
		super();
	}

	public static ApplicationsModel getInstance() {
		if (instance == null)
			instance = new ApplicationsModel();
		return instance;
	}

	private void notifyChange(String type) {
		dispatchEvent(new ChangeEvent(type));
	}

	private List<App> appList;
	private Boolean isSearchShowing;

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
		if (this.appList != null) {
			Collections.sort(this.appList);
		}
		notifyChange(ChangeEvent.APP_LIST_UPDATE);
	}

	public void mergeAppList(List<App> appList) {
		if (this.appList != null) {
			Set<App> appSet = new TreeSet<App>(new Comparator<App>() {
				@Override
				public int compare(App lha, App rha) {
					return lha.getId().compareTo(rha.getId());
				}
			});
			appSet.addAll(this.appList);
			appSet.addAll(new LinkedHashSet<App>(appList));
			this.appList = new ArrayList<App>(appSet);
			Collections.sort(this.appList);
			notifyChange(ChangeEvent.APP_LIST_UPDATE);
		} else {
			setAppList(appList);
		}
	}

	public Boolean getIsSearchShowing() {
		return isSearchShowing;
	}

	public void setIsSearchShowing(Boolean isSearchShowing) {
		this.isSearchShowing = isSearchShowing;
		notifyChange(ChangeEvent.SEARCH_DIALOG_CHANGE);
	}

	public void setRefreshComplete() {
		notifyChange(ChangeEvent.LIST_REFRESH_COMPLETE);
	}

	public void setRefreshStarted() {
		notifyChange(ChangeEvent.LIST_REFRESH_STARTED);
	}

}
