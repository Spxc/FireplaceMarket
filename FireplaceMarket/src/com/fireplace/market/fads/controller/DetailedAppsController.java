package com.fireplace.market.fads.controller;

import java.io.File;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.thread.DownloaderThread;

public class DetailedAppsController extends SherlockActivity implements OnClickListener {
	
	// Used to communicate state changes in the DownloaderThread
    public static final int MESSAGE_DOWNLOAD_STARTED = 1000;
    public static final int MESSAGE_DOWNLOAD_COMPLETE = 1001;
    public static final int MESSAGE_UPDATE_PROGRESS_BAR = 1002;
    public static final int MESSAGE_DOWNLOAD_CANCELED = 1003;
    public static final int MESSAGE_CONNECTING_STARTED = 1004;
    public static final int MESSAGE_ENCOUNTERED_ERROR = 1005;
    
    // instance variables
    private DetailedAppsController thisActivity;
    private Thread downloaderThread;
    private ProgressDialog progressDialog;
    String fileName;
    
    String appName;
    String appDeveloper;
    String appDescription;
    String appUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_details);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Apps");
		
		thisActivity = this;
        downloaderThread = null;
        progressDialog = null;
		
		Bundle extras = getIntent().getExtras();
		
		appName = extras.getString("appName");
		appUrl = extras.getString("appUrl");
		appDeveloper = extras.getString("appDeveloper");
		appDescription = extras.getString("appDescription");
		
		//Log.d("Getting: ", appName); //This works so appName isnt null
		
		Button button = (Button) this.findViewById(R.id.btnInstall);
        button.setOnClickListener(this);
		
		TextView txtAppName = (TextView) findViewById(R.id.txtAppName);
		TextView txtDeveloper = (TextView) findViewById(R.id.txtAppDeveloper);
		TextView txtDesc = (TextView) findViewById(R.id.txtDescription);
		
		txtAppName.setText(appName);
		txtDeveloper.setText(appDeveloper);
		txtDesc.setText(appUrl);
		
		
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
	
	public void onClick(View view)
	{
	    String urlInput = appUrl;
	    downloaderThread = new DownloaderThread(thisActivity, urlInput);
	    downloaderThread.start();
	}

	public Handler activityHandler = new Handler()
    {
            public void handleMessage(Message msg)
            {
                    switch(msg.what)
                    {
                            /*
                             * Handling MESSAGE_UPDATE_PROGRESS_BAR:
                             * 1. Get the current progress, as indicated in the arg1 field
                             *    of the Message.
                             * 2. Update the progress bar.
                             */
                            case MESSAGE_UPDATE_PROGRESS_BAR:
                                    if(progressDialog != null)
                                    {
                                            int currentProgress = msg.arg1;
                                            progressDialog.setProgress(currentProgress);
                                    }
                                    break;
                            
                            /*
                             * Handling MESSAGE_CONNECTING_STARTED:
                             * 1. Get the URL of the file being downloaded. This is stored
                             *    in the obj field of the Message.
                             * 2. Create an indeterminate progress bar.
                             * 3. Set the message that should be sent if user cancels.
                             * 4. Show the progress bar.
                             */
                            case MESSAGE_CONNECTING_STARTED:
                                    if(msg.obj != null && msg.obj instanceof String)
                                    {
                                            String url = (String) msg.obj;
                                            // truncate the url
                                            if(url.length() > 16)
                                            {
                                                    String tUrl = url.substring(0, 15);
                                                    tUrl += "...";
                                                    url = tUrl;
                                            }
                                            String pdTitle = thisActivity.getString(R.string.progress_dialog_title_connecting);
                                            String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_connecting);
                                            pdMsg += " " + url;
                                            
                                            dismissCurrentProgressDialog();
                                            progressDialog = new ProgressDialog(thisActivity);
                                            progressDialog.setTitle(pdTitle);
                                            progressDialog.setMessage(pdMsg);
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                            progressDialog.setIndeterminate(true);
                                            // set the message to be sent when this dialog is canceled
                                            Message newMsg = Message.obtain(this, MESSAGE_DOWNLOAD_CANCELED);
                                            progressDialog.setCancelMessage(newMsg);
                                            progressDialog.show();
                                    }
                                    break;
                                    
                            /*
                             * Handling MESSAGE_DOWNLOAD_STARTED:
                             * 1. Create a progress bar with specified max value and current
                             *    value 0; assign it to progressDialog. The arg1 field will
                             *    contain the max value.
                             * 2. Set the title and text for the progress bar. The obj
                             *    field of the Message will contain a String that
                             *    represents the name of the file being downloaded.
                             * 3. Set the message that should be sent if dialog is canceled.
                             * 4. Make the progress bar visible.
                             */
                            case MESSAGE_DOWNLOAD_STARTED:
                                    // obj will contain a String representing the file name
                                    if(msg.obj != null && msg.obj instanceof String)
                                    {
                                            int maxValue = msg.arg1;
                                            fileName = (String) msg.obj;
                                            String pdTitle = thisActivity.getString(R.string.progress_dialog_title_downloading);
                                            String pdMsg = thisActivity.getString(R.string.progress_dialog_message_prefix_downloading);
                                            pdMsg += " " + fileName;
                                            
                                            dismissCurrentProgressDialog();
                                            progressDialog = new ProgressDialog(thisActivity);
                                            progressDialog.setTitle(pdTitle);
                                            progressDialog.setMessage(pdMsg);
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                            progressDialog.setProgress(0);
                                            progressDialog.setMax(maxValue);
                                            // set the message to be sent when this dialog is canceled
                                            Message newMsg = Message.obtain(this, MESSAGE_DOWNLOAD_CANCELED);
                                            progressDialog.setCancelMessage(newMsg);
                                            progressDialog.setCancelable(true);
                                            progressDialog.show();
                                    }
                                    break;
                            
                            /*
                             * Handling MESSAGE_DOWNLOAD_COMPLETE:
                             * 1. Remove the progress bar from the screen.
                             * 2. Display Toast that says download is complete.
                             */
                            case MESSAGE_DOWNLOAD_COMPLETE:
                                    dismissCurrentProgressDialog();
                                    displayMessage(getString(R.string.user_message_download_complete));
                                    
                                    break;
                                    
                            /*
                             * Handling MESSAGE_DOWNLOAD_CANCELLED:
                             * 1. Interrupt the downloader thread.
                             * 2. Remove the progress bar from the screen.
                             * 3. Display Toast that says download is complete.
                             */
                            case MESSAGE_DOWNLOAD_CANCELED:
                                    if(downloaderThread != null)
                                    {
                                            downloaderThread.interrupt();
                                    }
                                    dismissCurrentProgressDialog();
                                    displayMessage(getString(R.string.user_message_download_canceled));
                                    break;
                            
                            /*
                             * Handling MESSAGE_ENCOUNTERED_ERROR:
                             * 1. Check the obj field of the message for the actual error
                             *    message that will be displayed to the user.
                             * 2. Remove any progress bars from the screen.
                             * 3. Display a Toast with the error message.
                             */
                            case MESSAGE_ENCOUNTERED_ERROR:
                                    // obj will contain a string representing the error message
                                    if(msg.obj != null && msg.obj instanceof String)
                                    {
                                            String errorMessage = (String) msg.obj;
                                            dismissCurrentProgressDialog();
                                            displayMessage(errorMessage);
                                    }
                                    break;
                                    
                            default:
                                    // nothing to do here
                                    break;
                    }
            }
    };
    
    public void dismissCurrentProgressDialog()
    {
            if(progressDialog != null)
            {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    progressDialog = null;
            }
    }

    public void displayMessage(String message)
    {
            if(message != null)
            {
                    Toast.makeText(thisActivity, message, Toast.LENGTH_SHORT).show();
            }
    }
    
}

		
		
		