package com.fireplace.market.fads.viewmodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.events.BaseEvent;
import com.fireplace.market.fads.events.EventDispatcher;

public class ApplicationsModel extends EventDispatcher implements Serializable {

	private static final long serialVersionUID = 7379959697033321138L;

	public static class ChangeEvent extends BaseEvent {
		public static final String APP_LIST_UPDATE = "appListUpdate";
		public static final String LIST_REFRESH_COMPLETE = "listRefreshComplete";
		public static final String LIST_REFRESH_STARTED = "listRefreshStarted";

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

	public List<App> getAppList() {
		return appList;
	}

	public void setAppList(List<App> appList) {
		this.appList = appList;
		Collections.sort(this.appList);
		notifyChange(ChangeEvent.APP_LIST_UPDATE);
	}

	public void setRefreshComplete() {
		notifyChange(ChangeEvent.LIST_REFRESH_COMPLETE);
	}

	public void setRefreshStarted() {
		notifyChange(ChangeEvent.LIST_REFRESH_STARTED);
	}

}
