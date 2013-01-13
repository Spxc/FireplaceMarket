package com.fireplace.market.fads.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fireplace.market.fads.FireplaceApplication;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.bll.SlidingMenuItem;

public class ApplicationsView extends ListView {

	public ApplicationsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private Context mContext;

	public class MenuAdapter extends ArrayAdapter<SlidingMenuItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
			mContext = context;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.sliding_menu_item, null);
			}

			final SlidingMenuItem item = getItem(position);

			ImageView icon = (ImageView) convertView
					.findViewById(R.id.activity_icon);
			if (item.getIcon() != null) {
				icon.setImageBitmap(item.getIcon());
				icon.setVisibility(View.VISIBLE);
			} else {
				icon.setVisibility(View.GONE);
			}

			TextView title = (TextView) convertView
					.findViewById(R.id.activity_title);
			title.setText(item.getTitle());

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onMenuItemClick(item.getTarget(), item.getBundle());
				}
			});

			return convertView;
		}
	}

	private void onMenuItemClick(String target, Bundle args) {
		Class<?> className = null;
		try {
			className = Class.forName(target);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (className != null) {
			Intent intent = new Intent(mContext, className);
			if (args != null && args.getInt(FireplaceApplication.REPO_KEY) != 0) {
				intent.putExtras(args);
			}
			mContext.startActivity(intent);
		}
	}
}
