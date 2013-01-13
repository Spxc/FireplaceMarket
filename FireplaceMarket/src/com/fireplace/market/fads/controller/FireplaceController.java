package com.fireplace.market.fads.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.frag.SlidingMenuFragment;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * This activity is the shell activity that should be extended, if the sliding
 * menu is desired. It also extends
 * {@link com.slidingmenu.lib.app.SlidingFragmentActivity
 * SlidingFragmentActivity}, which in turn extends
 * {@link com.actionbarsherlock.app.SherlockFragmentActivity
 * SherlockFragmentActivity}, so you must have both the SlidingMenu and
 * ActionBarSherlock chain referenced.
 */
public class FireplaceController extends SlidingFragmentActivity {

	protected SlidingMenu mSlidingMenu;
	protected FragmentManager mSupportFragManager;
	protected SlidingMenuFragment mSlidingMenuFragment;
	protected ActionBar mSupportActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// do not set front content view in this class, set it in the subclass
		// Example: MainActivity extends FireplaceController

		// set view for behind view
		setBehindContentView(R.layout.behind_view);

		// get reference to the support action bar
		mSupportActionBar = getSupportActionBar();

		// put sliding menu fragment in place as menu
		mSupportFragManager = getSupportFragmentManager();
		FragmentTransaction t = mSupportFragManager.beginTransaction();
		mSlidingMenuFragment = new SlidingMenuFragment();
		t.replace(R.id.behindLayout, mSlidingMenuFragment);
		t.commit();

		setUpMenu();

	}

	/**
	 * This method gets a reference to the sliding menu, and sets attributes on
	 * it. There are other attributes that can be set, see <a
	 * href="https://github.com/jfeinstein10/SlidingMenu">here</a>.
	 */
	private void setUpMenu() {
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.defaultshadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.actionbar_home_width);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setFadeEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			toggle();
		}
		return true;
	}

}
