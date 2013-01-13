package com.fireplace.market.fads.task;

import java.io.InputStream; 
import java.net.URL; 

import com.fireplace.market.fads.bll.PbAndImage;

import android.graphics.Bitmap; 
import android.graphics.BitmapFactory; 
import android.graphics.Matrix; 
import android.os.AsyncTask; 
import android.view.View; 
import android.widget.ImageView; 
import android.widget.ProgressBar; 
public class DownloadImageTask extends AsyncTask<PbAndImage, Void, Bitmap> {
	ImageView imageView = null; ProgressBar pb = null; 
	protected Bitmap doInBackground(PbAndImage... pb_and_images) {
		this.imageView = (ImageView)pb_and_images[0].getImg();
		this.pb = (ProgressBar)pb_and_images[0].getPb();
		return getBitmapDownloaded((String) imageView.getTag()); 
		} 
	protected void onPostExecute(Bitmap result) {
		System.out.println("Downloaded " + imageView.getId());
		imageView.setVisibility(View.VISIBLE);
		pb.setVisibility(View.GONE);
		imageView.setImageBitmap(result); //set the bitmap to the imageview. 
		} 
	/** This function downloads the image and returns the Bitmap **/
	private Bitmap getBitmapDownloaded(String url) { 
		System.out.println("String URL " + url);
		Bitmap bitmap = null; try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(url) .getContent());
			bitmap = getResizedBitmap(bitmap, 50, 50); 
			return bitmap; 
			} 
		catch (Exception e) {
			e.printStackTrace(); 
			} 
		return bitmap; } 
	/** decodes image and scales it to reduce memory consumption **/ 
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix(); 
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP 
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap; 
	} 
}