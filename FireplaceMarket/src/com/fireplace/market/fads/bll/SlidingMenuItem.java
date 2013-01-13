package com.fireplace.market.fads.bll;

import android.graphics.Bitmap;
import android.os.Bundle;

public class SlidingMenuItem {

	public SlidingMenuItem() {

	}

	public SlidingMenuItem(String title, String target) {
		this.title = title;
		this.target = target;
	}

	public SlidingMenuItem(Bitmap bitmap, String title, String target,
			Bundle bundle) {
		this.icon = bitmap;
		this.title = title;
		this.target = target;
		this.bundle = bundle;
	}

	private Bitmap icon;
	private String title;
	private String target;
	private Bundle bundle;

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

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

}
