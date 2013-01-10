package com.fireplace.market.fads;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Toast;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.database.DBAdapter;

public class RepositoriesActivity extends SherlockActivity implements OnClickListener{
	
	DBAdapter db = new DBAdapter(this); 
	private DBAdapter mDbHelper;
	private ListView list;
	EditText txtUrlRepoView;
	Button btnAddRepo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repositories);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Repositories");
		
		btnAddRepo = (Button) findViewById(R.id.btnAddRepo);
		btnAddRepo.setOnClickListener(this);
		btnAddRepo.setText("Add");
		btnAddRepo.setVisibility(View.GONE);
		
		txtUrlRepoView = (EditText) findViewById(R.id.txtUrlRepo);
		txtUrlRepoView.setVisibility(View.GONE);
		
		list = (ListView) findViewById(R.id.lvRepos);
		
        db.open();
        getRepos();
        
	}

	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	super.finish();
	        break;
	    case R.id.add_repo_button:
        	if (txtUrlRepoView.getVisibility() == View.GONE){
        		txtUrlRepoView.setVisibility(View.VISIBLE);
        		btnAddRepo.setVisibility(View.VISIBLE);
        	}
        	else{
        		txtUrlRepoView.setVisibility(View.GONE);
        		btnAddRepo.setVisibility(View.GONE);
        	}
            
            return true;
        default:
            return super.onOptionsItemSelected(item);
	    }
		return true;
	}
	
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	    case R.id.btnAddRepo:
	    	final EditText txtUrlRepo = (EditText) findViewById(R.id.txtUrlRepo);
	    	long id;
            id = db.insertTitle(
            		txtUrlRepo.getText().toString());
            Log.d("Add repo", "Added repo " + txtUrlRepo.getText().toString());
            txtUrlRepo.setText("");
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE); 

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                       InputMethodManager.HIDE_NOT_ALWAYS);
            getRepos();
            break;
	    }   
	}
	
	public void getRepos(){
		final Cursor cursor = db.getAllTitles();
        startManagingCursor(cursor);

        String[] from = new String[]{DBAdapter.KEY_REPOURL};
        int[] to = new int[]{R.id.app_name};

        SimpleCursorAdapter cursorAdapter =
         new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to);
                                                                                                                                                                                 
        list.setAdapter(cursorAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position,long id)
            {
            	final CharSequence[] items = {"Open", "Edit", "Delete"};
            	Log.d("Get longpress", "Got longpress from listitem " + position + cursor.getString(1));
            	AlertDialog.Builder dia = new AlertDialog.Builder(parent.getContext());
                dia.setTitle(cursor.getString(1));
                dia.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	switch (arg1) {
                	case 0:
                		Log.d("Open Repo","Open repo");
                		break;
                	case 1:
                		Log.d("Edit Repo","Edit repo");
                		break;
                	case 2:
                		Log.d("Delete Repo","Delete repo");
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
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
            	Log.d("Get shortpress", "Got shortpress from listitem " + position);

            }                 
      });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.abs_buttons_add, menu);
       return super.onCreateOptionsMenu(menu);
    }
}
