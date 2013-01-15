package com.fireplace.market.fads.controller;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.frag.ApplicationsFragment;

public class ApplicationsController extends SherlockFragmentActivity {

	private ApplicationsFragment aFrag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list);
		
		getSupportActionBar().setTitle("Applications");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentTransaction fragTransaction = getSupportFragmentManager()
				.beginTransaction();
		aFrag = new ApplicationsFragment();
		fragTransaction.replace(R.id.contentFrame, aFrag);
		fragTransaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.search_button:
			aFrag.toggleSearchDialog();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.apps_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}