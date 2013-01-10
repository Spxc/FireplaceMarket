package com.fireplace.market.fads;

import java.util.ArrayList;
import java.util.HashMap;

import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.adapter.DiffAdapter;
import com.fireplace.market.fads.controller.FireplaceController;

public class DetailedAppsActivity extends SherlockActivity{
	
	private ViewFlow viewFlow;
	private ListView lv;
    ArrayAdapter<String> adapterLv;
    ArrayList<HashMap<String, String>> productList;
    TextView resultView; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflow_holder);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Will be replaced with app name");
		
		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        DiffAdapter adapter = new DiffAdapter(this);
        viewFlow.setAdapter(adapter);
        
        
        
        resultView = (TextView) findViewById(R.id.app_name);
		
		String products[] = {"App 1", "App 2", "App 3", "App 4", "App 5",
                "App 6", "App 7", "App 8", "App 9", "App 10", "App 11"};

		lv = (ListView) findViewById(R.id.lvComments);
		
		//Will be replaced with Sql Comments
		adapterLv = new ArrayAdapter<String>(this, R.layout.list_item, R.id.app_name, products);
		lv.setAdapter(adapterLv); 		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	super.finish();
	        break;
        default:
            return super.onOptionsItemSelected(item);
	    }
		return true;
	}

}
