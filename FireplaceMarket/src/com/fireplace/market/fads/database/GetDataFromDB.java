package com.fireplace.market.fads.database;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class GetDataFromDB {
	public String getImageURLAndDesciptionFromDB() {
		try {
			HttpPost httppost;
			HttpClient httpclient;
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(
					"http://strongpixel.com/development/getDBinfo.php"); // change
																			// this
																			// to
																			// your
																			// URL.....
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);
			return response;
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return "error";
		}
	}

}
