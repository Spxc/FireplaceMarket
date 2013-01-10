package com.fireplace.market.fads.model;

import android.graphics.Bitmap;

public class SlidingMenuItem {

	public SlidingMenuItem() {

	}

	public SlidingMenuItem(String title, String target) {
		this.title = title;
		this.target = target;
	}

	public SlidingMenuItem(Bitmap bitmap, String title, String target) {
		this.icon = bitmap;
		this.title = title;
		this.target = target;
	}

	private Bitmap icon;
	private String title;
	private String target;

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
