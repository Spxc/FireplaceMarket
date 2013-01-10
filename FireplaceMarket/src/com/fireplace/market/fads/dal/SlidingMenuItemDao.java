package com.fireplace.market.fads.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.fireplace.market.fads.ApplicationsActivity;
import com.fireplace.market.fads.MainActivity;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.SearchActivity;
import com.fireplace.market.fads.SettingsActivity;
import com.fireplace.market.fads.StorageActivity;
import com.fireplace.market.fads.model.SlidingMenuItem;

public class SlidingMenuItemDao {

	private static SlidingMenuItemDao instance;
	private final Context mContext;

	private SlidingMenuItemDao(Context context) {
		super();
		mContext = context;
	}

	public static SlidingMenuItemDao getInstance(Context context) {
		if (instance == null)
			instance = new SlidingMenuItemDao(context);
		return instance;
	}

	/**
	 * This is a manual list generation for the menu, currently. It can be
	 * change to pull from a database or a resource string array.
	 * 
	 * @return a list of {@link com.fireplace.market.fads.model.SlidingMenuItem
	 *         SlidingMenuItem}'s.
	 */
	public List<SlidingMenuItem> getAllSlidingMenuItems() {
		List<SlidingMenuItem> list = new ArrayList<SlidingMenuItem>();
		SlidingMenuItem item = new SlidingMenuItem();
		
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_search));
		item.setTitle("Search");
		item.setTarget(SearchActivity.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher));
		item.setTitle("Home");
		item.setTarget(MainActivity.class.getName());
		list.add(item);
		
		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_storage));
		item.setTitle("Sections");
		item.setTarget(StorageActivity.class.getName());
		list.add(item);
		
		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_settings));
		item.setTitle("Settings");
		item.setTarget(SettingsActivity.class.getName());
		list.add(item);

		return list;
	}
}
