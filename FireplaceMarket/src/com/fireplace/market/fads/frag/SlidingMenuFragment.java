package com.fireplace.market.fads.frag;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.fireplace.market.fads.R;
import com.fireplace.market.fads.dal.SlidingMenuItemDao;
import com.fireplace.market.fads.model.SlidingMenuItem;
import com.fireplace.market.fads.view.SlidingMenuView;

public class SlidingMenuFragment extends SherlockListFragment {

	private SlidingMenuView view;
	private ArrayAdapter<SlidingMenuItem> menuAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = (SlidingMenuView) View.inflate(getActivity(), R.layout.menu,
				null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		menuAdapter = view.new MenuAdapter(getSherlockActivity());
		List<SlidingMenuItem> list = SlidingMenuItemDao.getInstance(
				getSherlockActivity()).getAllSlidingMenuItems();
		for (SlidingMenuItem item : list) {
			menuAdapter.add(item);
		}
		// view.addHeaderView(new View(getActivity()));
		setListAdapter(menuAdapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

}