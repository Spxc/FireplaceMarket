package com.fireplace.market.fads.dal;

import java.sql.SQLException;
import java.util.List;

import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.bll.App;
import com.j256.ormlite.dao.Dao;

public class AppRepository extends BaseRepo {

	public static final String ID_FIELD_NAME = "id";
	public static final String PTYPE_FIELD_NAME = "ptype";
	public static final String DEVELOPER_FIELD_NAME = "devel";
	public static final String STATUS_FIELD_NAME = "status";

	@Override
	protected <T> Dao<T, Integer> getDao(Class<T> objectClass) {
		try {
			return FireplaceApplication.mDbHelper.getDao(App.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean applicationExists(App application) {
		return existsById(application.getId(), App.class);
	}

	public App saveApp(App application) {
		return save(application, App.class);
	}

	public Boolean saveApps(List<App> appList) {
		int count = 0;
		for (App application : appList) {
			save(application, App.class);
			count++;
		}
		return count == appList.size();
	}

	public App saveOrUpdate(App application) {
		if (!applicationExists(application)) {
			return saveApp(application);
		} else {
			return updateApp(application);
		}
	}

	public Boolean saveOrUpdateApps(List<App> appList) {
		int count = 0;
		for (App application : appList) {
			saveOrUpdate(application);
			count++;
		}
		return count == appList.size();
	}

	public App updateApp(App application) {
		update(application, App.class);
		return application;
	}

	public Boolean updateApps(List<App> appList) {
		int count = 0;
		for (App application : appList) {
			update(application, App.class);
			count++;
		}
		return count == appList.size();
	}

	public App refreshApp(App application) {
		return refreshObject(application, App.class);
	}

	public List<App> getAllApps() {
		return getEntireCollection(App.class);
	}

	public App getByIdentifier(Integer id) {
		List<App> appList = getCollectionByMatchingValue(ID_FIELD_NAME, id,
				App.class);
		return (appList != null && appList.size() > 0) ? appList.get(0) : null;
	}

	public List<App> getByPtype(Integer ptype) {
		List<App> appList = getCollectionByMatchingValue(PTYPE_FIELD_NAME,
				ptype, App.class);
		return (appList != null && appList.size() > 0) ? appList : null;
	}

	public List<App> getByDeveloper(String developer) {
		List<App> appList = getCollectionByMatchingValue(DEVELOPER_FIELD_NAME,
				developer, App.class);
		return (appList != null && appList.size() > 0) ? appList : null;
	}

	public List<App> getByStatus(Integer status) {
		List<App> appList = getCollectionByMatchingValue(STATUS_FIELD_NAME,
				status, App.class);
		return (appList != null && appList.size() > 0) ? appList : null;
	}

	public Boolean deleteApp(App application) {
		return delete(application, App.class);
	}

}
