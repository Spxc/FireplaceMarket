package com.fireplace.market.fads.dal.helpers;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fireplace.market.fads.Fireplace;
import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.bll.Repo;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class FireplaceDBHelper extends BaseDBHelper {

	private static final String TAG = FireplaceDBHelper.class.getSimpleName();
	public static final String APPLICATION_TABLE = App.class.getSimpleName();
	public static final String REPOSITORY_TABLE = Repo.class
			.getSimpleName();

	private static FireplaceDBHelper helperInstance;

	public static FireplaceDBHelper getInstance(FireplaceApplication application) {
		if (helperInstance == null)
			helperInstance = new FireplaceDBHelper(application);
		return helperInstance;
	}

	private FireplaceDBHelper(Context context) {
		super(context, FireplaceApplication.DATABASE_NAME,
				FireplaceApplication.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		createTables();
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		dropTables();
	}

	public void resetDB() {
		clearTables();
	}

	private void clearTables() {
		try {
			TableUtils.clearTable(connectionSource, App.class);
			TableUtils.clearTable(connectionSource, Repo.class);

		} catch (Exception e) {
			Fireplace.errorLog(TAG, "Problem creating Fireplace DB tables.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void createTables() {
		try {
			TableUtils.createTableIfNotExists(connectionSource, App.class);
			TableUtils.createTableIfNotExists(connectionSource,
					Repo.class);

		} catch (Exception e) {
			Fireplace.errorLog(TAG, "Problem creating Fireplace DB tables.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void dropTables() {
		try {
			TableUtils.dropTable(connectionSource, App.class, true);
			TableUtils.dropTable(connectionSource, Repo.class, true);

		} catch (SQLException e) {
			Fireplace.errorLog(TAG, "Problem dropping Fireplace DB tables.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}