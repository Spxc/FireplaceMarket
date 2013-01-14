package com.fireplace.market.fads.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;

public class StorageController extends FireplaceController {

	private ListView lv;
	ArrayAdapter<String> adapter;
	EditText inputSearch;
	ArrayList<HashMap<String, String>> productList;
	TextView resultView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sections);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Storage");

		resultView = (TextView) findViewById(R.id.app_name);

		String products[] = { "App 1", "App 2", "App 3", "App 4", "App 5",
				"App 6", "App 7", "App 8", "App 9", "App 10", "App 11" };

		lv = (ListView) findViewById(R.id.lvCategories);
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		inputSearch.setVisibility(View.GONE);

		// Will be replaced with Sql Items
		adapter = new ArrayAdapter<String>(this, R.layout.list_item,
				R.id.app_name, products);
		lv.setAdapter(adapter);

		// Making the list searchable
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				StorageController.this.adapter.getFilter().filter(cs);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.apps_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
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
	}
}
