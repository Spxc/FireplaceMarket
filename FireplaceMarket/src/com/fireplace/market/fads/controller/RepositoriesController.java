package com.fireplace.market.fads.controller;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.R.id;
import com.fireplace.market.fads.R.layout;
import com.fireplace.market.fads.R.menu;
import com.fireplace.market.fads.bll.Repo;
import com.fireplace.market.fads.bll.SlidingMenuItem;
import com.fireplace.market.fads.dal.RepoRepository;
import com.fireplace.market.fads.database.DBAdapter;

public class RepositoriesController extends SherlockFragmentActivity {

	DBAdapter db = new DBAdapter(this);
	private DBAdapter mDbHelper;
	private ListView list;
	EditText txtUrlRepoView;
	Button btnAddRepo;
	
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repositories);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Repositories");

		btnAddRepo = (Button) findViewById(R.id.btnAddRepo);
		btnAddRepo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		btnAddRepo.setText("Add");
		btnAddRepo.setVisibility(View.GONE);

		txtUrlRepoView = (EditText) findViewById(R.id.txtUrlRepo);
		txtUrlRepoView.setVisibility(View.GONE);

		list = (ListView) findViewById(R.id.lvRepos);
		

		db.open();
		getRepos();
	}

	public void getRepoItems(){
		List<Repo> repoList = Repo.getAll();
		List<Repo> list = new ArrayList<Repo>();
		Repo item = new Repo();
		if(repoList !=null){
			for (Repo repo : repoList) {
				item = new Repo();
				item.setUrl("http://www.url.com");
				item.setName(repo.getName());
				list.add(item);
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.finish();
			break;
		case R.id.add_repo_button:
			addDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void getRepos() {
		final Cursor cursor = db.getAllTitles();
		startManagingCursor(cursor);

		String[] from = new String[] { DBAdapter.KEY_REPOURL };
		int[] to = new int[] { R.id.app_name };

		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
				R.layout.list_item, cursor, from, to);

		list.setAdapter(cursorAdapter);
		list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				final CharSequence[] items = { "Open", "Edit", "Delete" };
				Log.d("Get longpress", "Got longpress from listitem "
						+ position + cursor.getString(1));
				AlertDialog.Builder dia = new AlertDialog.Builder(parent
						.getContext());
				dia.setTitle(cursor.getString(1));
				dia.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:
							Log.d("Open Repo", "Open repo");
							break;
						case 1:
							Log.d("Edit Repo", "Edit repo");
							editDialog();
							break;
						case 2:
							Log.d("Delete Repo", "Delete repo");
							db.deleteTitle(cursor.getLong(0));
							getRepos();
							break;

						}
					}
				});
				dia.create();
				dia.show();
				return true;
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> myAdapter, View myView,
					int position, long mylng) {
				Log.d("Get shortpress", "Got shortpress from listitem "
						+ position);

			}
		});
	}

	public void addDialog() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Add repository");
		alert.setMessage("Add the url to the server with the right config...");

		// Set an EditText view to get user input
		final EditText inputAdd = new EditText(this);
		alert.setView(inputAdd);
		alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				final Cursor cursor = db.getAllTitles();
				db.insertTitle(inputAdd.getText().toString());
				Log.d("Add repo", "Added repo " + inputAdd.getText().toString());
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(inputAdd.getWindowToken(), 0);
				getRepos();

			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(inputAdd.getWindowToken(),
								0);
					}
				});

		alert.show();
	}

	public void editDialog() {

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Edit repo");
		// alert.setMessage("Enter the url of which the software is on!");

		// Set an EditText view to get user input

		final Cursor cursor = db.getAllTitles();

		final EditText input = new EditText(this);

		alert.setView(input);
		// input.setText(c.getString(1));
		alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				
				boolean id;
				if (id = db.updateTitle(cursor.getLong(-1), input.getText().toString()))
					Log.d("Update DB", "Updated: " + input.getText().toString());
				else
					Log.d("Update DB", "Update Failed");
				
				getRepos();
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
					}
				});

		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.abs_buttons_add, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
