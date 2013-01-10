package com.fireplace.market.fads;

import java.util.ArrayList; 
import java.util.List; 

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.adapter.MyCustomArrayAdapter;
import com.fireplace.market.fads.database.GetDataFromDB;
import com.fireplace.market.fads.model.Model;

import android.app.ProgressDialog; 
import android.os.Bundle; 
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter; 
import android.widget.EditText;

public class ApplicationsActivity extends SherlockListActivity {
	
	EditText inputSearch;
	ArrayAdapter<Model> adapter;
	//final List<Model> list = new ArrayList<Model>();
	//ArrayAdapter<Model> adapter = new MyCustomArrayAdapter(this, list);
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R. layout.contact_list); 
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		inputSearch = (EditText) findViewById(R.id.inputSearchApps);
		inputSearch.setVisibility(View.GONE);
		
		//Get url from GetDataFromDB
		final List<Model> list = new ArrayList<Model>();
		final GetDataFromDB getvalues = new GetDataFromDB(); 
		final ProgressDialog dialog = ProgressDialog.show(ApplicationsActivity.this, "", "Gettting information", true);
		
		new Thread (new Runnable() { public void run() {
			String response = getvalues.getImageURLAndDesciptionFromDB(); System.out.println("Response : " + response);
			dismissDialog(dialog); 
			if (!response.equalsIgnoreCase("")) {
				if (!response.equalsIgnoreCase("error")) { 
					dismissDialog(dialog);
				//Split desc and image
				String all[] = response.split("##"); for(int k = 0; k < all.length; k++){
					String urls_and_desc[] = all[k].split(",");
					//Description 
					list.add(get(urls_and_desc[1],urls_and_desc[0])); 
					} 
				} 
				}
			else { 
				dismissDialog(dialog); 
				} 
			} 
		})
		.start();
		//Getting items from server// 
		adapter = new MyCustomArrayAdapter(this, list);
		setListAdapter(adapter); 
		
		//Making the list searchable//
				inputSearch.addTextChangedListener(new TextWatcher() {
					 
					@Override 
					public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
						if(cs!=null){
							ApplicationsActivity.this.adapter.getFilter().filter(cs); 
							}
						}
				 
				    @Override
				    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				            int arg3) {
				        // TODO Auto-generated method stub
				 
				    }
				 
				    @Override
				    public void afterTextChanged(Editable arg0) {
				        // TODO Auto-generated method stub
				    }
				}); 
		} 
	
	public void dismissDialog(final ProgressDialog dialog){
		runOnUiThread(new Runnable() {
		public void run() {
			dialog.dismiss(); 
			} 
		}); 
		} 
	private Model get(String s, String url) {
		return new Model(s, url); 
		} 
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	super.finish();
	        break;
	    case R.id.search_button:
        	if (inputSearch.getVisibility() == View.GONE){
        		inputSearch.setVisibility(View.VISIBLE);
        	}
        	else{
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