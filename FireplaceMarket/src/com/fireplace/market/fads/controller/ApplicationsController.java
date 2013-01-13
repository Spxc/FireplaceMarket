package com.fireplace.market.fads.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.adapter.ApplicationAdapter;
import com.fireplace.market.fads.bll.App;
import com.fireplace.market.fads.database.GetDataFromDB;

public class ApplicationsController extends SherlockFragmentActivity {

	EditText inputSearch;
	ArrayAdapter<App> adapter;

	// final List<Model> list = new ArrayList<Model>();
	// ArrayAdapter<Model> adapter = new MyCustomArrayAdapter(this, list);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		inputSearch = (EditText) findViewById(R.id.inputSearchApps);
		inputSearch.setVisibility(View.GONE);

		// Get url from GetDataFromDB
		final List<App> list = new ArrayList<App>();
		final GetDataFromDB getvalues = new GetDataFromDB();
		final ProgressDialog dialog = ProgressDialog.show(
				ApplicationsController.this, "", "Gettting information", true);

		new Thread(new Runnable() {
			@Override
			public void run() {
				String response = getvalues.getImageURLAndDesciptionFromDB();
				System.out.println("Response : " + response);
				dismissDialog(dialog);
				if (!response.equalsIgnoreCase("")) {
					if (!response.equalsIgnoreCase("error")) {
						dismissDialog(dialog);
						// Split desc and image
						String all[] = response.split("##");
						for (int k = 0; k < all.length; k++) {
							String urls_and_desc[] = all[k].split(",");
							// Description
							list.add(get(urls_and_desc[1], urls_and_desc[0]));
						}
					}
				} else {
					dismissDialog(dialog);
				}
			}
		}).start();
		// Getting items from server//
		adapter = new ApplicationAdapter(this, list);
		// setListAdapter(adapter);

		// Making the list searchable//
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs != null) {
					ApplicationsController.this.adapter.getFilter().filter(cs);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	public void dismissDialog(final ProgressDialog dialog) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		});
	}

	private App get(String s, String url) {
		return new App(s, url);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.finish();
			break;
		case R.id.search_button:
			if (inputSearch.getVisibility() == View.GONE) {
				inputSearch.setVisibility(View.VISIBLE);
			} else {
				inputSearch.setVisibility(View.GONE);
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.abs_buttons, menu);
		return super.onCreateOptionsMenu(menu);
	}
}