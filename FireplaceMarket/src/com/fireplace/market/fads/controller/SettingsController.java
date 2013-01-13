package com.fireplace.market.fads.controller;

import java.io.File;
import java.io.IOException;

import org.holoeverywhere.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fireplace.market.fads.R;
import com.fireplace.market.fads.R.id;
import com.fireplace.market.fads.R.layout;
import com.fireplace.market.fads.bll.ChangeLog;

public class SettingsController extends FireplaceController {

	private ListView lvSettingsApp;
	private ChangeLog cl = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Settings");

		lvSettingsApp = (ListView) findViewById(R.id.lvSettings);
		cl = new ChangeLog(this);

	    String[] items = new String[] {"Delete Cache", "Reset Application", "Refresh Apps", "Manage Repositories", "Changelog", "About Us", "Donate", "About device"};
	    ArrayAdapter<String> adapter =
	      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		  
	    // TextView txtApp = new TextView(getApplicationContext());
        // txtApp.setText("Application Settings");

        //lvSettingsApp.addHeaderView(txtApp); 
	    lvSettingsApp.setClickable(true);
	    lvSettingsApp.setAdapter(adapter);
	    lvSettingsApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    
	    	@Override
	   	  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

	    		 switch (position) {
	    		    case 0:	    				
	    		    	ClearCache();
	    		        break;
	    		    case 1:
	    		    	Toast.makeText(SettingsController.this,
	    						"Reset", Toast.LENGTH_LONG).show();
	    		        break;
	    		    case 2:
	    		    	Toast.makeText(SettingsController.this,
	    						"Refresh", Toast.LENGTH_LONG).show();
	    		        break;
	    		    case 3:
	    		    	Intent intentRepos = new Intent(getBaseContext(), RepositoriesController.class);                      
	    		    	startActivity(intentRepos);
	    		        break;
	    		    case 4:
	    		    	SettingsController.this.cl.getFullLogDialog().show();
	    		        break;
	    		    case 5:
	    		    	AboutDialog();
	    		    	break;
	    		    case 6:
	    		    	Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=CVT4SNRTBSJCU") );
	    		        startActivity(browse);
	    		    	break;
	    		    case 7:
	    		    	AboutDevice();
	    		    	break;
	    		        
	    		    default:
	    		    	break;
	    		    }
	   	  		}
	    });
	}
	
	public void AboutDialog(){            
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("About Us");
        alertbox.setMessage("Fireplace Market is a 3rd party app store which contain apps and tweaks which didn't get into Android Market"
				+ "\n\nThis software comes without any kind of warranty!"
				+ "\n\nProject started by Spxc"
				+ "\n\nCopyright 2013"
				+ "\nRooted Dev Team"
				+ "\nStian Instebø & Simon Ponder");
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
               
            }
        });
        alertbox.show();
	}
	
        public void AboutDevice(){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle("About device");                                                                                                                                                                
        alertbox.setMessage("Application v. 3.0.1"
        		+ "\nModel: " + android.os.Build.MODEL
				+ "\nVersion: " + android.os.Build.VERSION.RELEASE);
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
               
            }
        });
         
        // show it
        alertbox.show();
	}

	public void ClearCache(){
		File dirApps = new File("/sdcard/com.fireplace.market.fads/");

		if (dirApps.exists()) {
			String deleteCmd = "rm -r " + "/sdcard/com.fireplace.market.fads/";
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(deleteCmd);
			} catch (IOException e) {
				Toast.makeText(SettingsController.this,
						"Removed", Toast.LENGTH_LONG).show();
			}
			
			dirApps.mkdirs();
			Toast.makeText(SettingsController.this, "Cache has been cleared. " + Environment.getExternalStorageDirectory().getPath()+"/"+getPackageName()+"/", Toast.LENGTH_LONG)
			.show();
		} else {
			dirApps.mkdirs();
			//Log.e("Fireplace directory did not exist, so one was created on sdcard partition.");
			Toast.makeText(SettingsController.this,
					"Dir dosent exist", Toast.LENGTH_LONG).show();
		}
	}
}
