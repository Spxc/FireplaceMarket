package com.fireplace.market.fads.adapter;

import java.util.List; 

import com.fireplace.market.fads.R;
import com.fireplace.market.fads.model.Model;
import com.fireplace.market.fads.task.DownloadImageTask;
import com.fireplace.market.fads.view.PbAndImage;

import android.app.Activity; 
import android.graphics.Color; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.ArrayAdapter; 
import android.widget.ImageView; 
import android.widget.ProgressBar; 
import android.widget.TextView; 

public class MyCustomArrayAdapter extends ArrayAdapter<Model> {
	private final Activity context; 
	private final List<Model> list;
	public MyCustomArrayAdapter(Activity context, List<Model> list) {
		super(context, R.layout.list_layout, list); 
		this.context = context; this.list = list; 
		} 
	static class ViewHolder { 
		protected TextView text; 
		protected ImageView image; 
		protected ProgressBar pb; 
		} 
	@Override public View getView(int position, View convertView, ViewGroup parent) {
		View view = null; 
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater(); 
			view = inflator.inflate(R.layout.list_layout, null);
			final ViewHolder viewHolder = new ViewHolder(); 
			viewHolder.text = (TextView) view.findViewById(R.id.label); 
			viewHolder.text.setTextColor(Color.BLACK); viewHolder.image = (ImageView) 
			view.findViewById(R.id.image); viewHolder.image.setVisibility(View.GONE); 
			viewHolder.pb = (ProgressBar) view.findViewById(R.id.progressBar1); 
			view.setTag(viewHolder); 
			} 
		else {
			view = convertView; 
			} 
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName());
		holder.image.setTag(list.get(position).getURL()); 
		holder.image.setId(position);
		PbAndImage pb_and_image = new PbAndImage();
		pb_and_image.setImg(holder.image);
		pb_and_image.setPb(holder.pb); 
		new DownloadImageTask().execute(pb_and_image);
		return view; 
		} 
	}