package com.fireplace.market.fads.dal.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class BaseDBHelper extends OrmLiteSqliteOpenHelper {

	private static BaseDBHelper helperInstance;

	public static BaseDBHelper getInstance(Context context, String db,
			int version) {
		if (helperInstance == null)
			helperInstance = new BaseDBHelper(context, db, version);
		return helperInstance;
	}

	public BaseDBHelper(Context context, String db, int version) {
		super(context, db, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
	}

	@Override
	public void close() {
		super.close();
		helperInstance = null;
	}

}