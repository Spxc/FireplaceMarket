package com.fireplace.market.fads.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.bll.Repo;
import com.fireplace.market.fads.bll.SlidingMenuItem;
import com.fireplace.market.fads.controller.ApplicationsController;
import com.fireplace.market.fads.controller.MainController;
import com.fireplace.market.fads.controller.RepositoriesController;
import com.fireplace.market.fads.controller.SearchController;
import com.fireplace.market.fads.controller.SettingsController;
import com.fireplace.market.fads.controller.StorageController;

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
	 * @return a list of {@link com.fireplace.market.fads.bll.SlidingMenuItem
	 *         SlidingMenuItem}'s.
	 */
	public List<SlidingMenuItem> getAllSlidingMenuItems() {
		List<SlidingMenuItem> list = new ArrayList<SlidingMenuItem>();
		SlidingMenuItem item = new SlidingMenuItem();

		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_search));
		item.setTitle("Search");
		item.setTarget(SearchController.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher));
		item.setTitle("Home");
		item.setTarget(MainController.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_storage));
		item.setTitle("Sections");
		item.setTarget(StorageController.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_storage));
		item.setTitle("Applications");
		item.setTarget(ApplicationsController.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setIcon(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_action_settings));
		item.setTitle("Settings");
		item.setTarget(SettingsController.class.getName());
		list.add(item);

		item = new SlidingMenuItem();
		item.setTitle("Repositories");
		list.add(item);

		List<Repo> repoList = Repo.getAll();
		if (repoList != null) {
			for (Repo repo : repoList) {
				item = new SlidingMenuItem();
				item.setIcon(BitmapFactory.decodeResource(
						mContext.getResources(), R.drawable.ic_action_settings));
				item.setTitle(repo.getName());
				item.setTarget(RepositoriesController.class.getName());
				Bundle args = new Bundle();
				args.putInt(FireplaceApplication.REPO_KEY, repo.getId());
				item.setBundle(args);
				list.add(item);
			}
		}

		return list;
	}
}
