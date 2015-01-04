package com.androidinsettegiorni_cap3.fragment;

import java.util.ArrayList;

import com.androidinsettegiorni_cap3.adapter.LabelListAdapter;
import com.androidinsettegiorni_cap3.model.Label;


import android.app.ListFragment;
import android.os.Bundle;

public class LabelListFragment extends ListFragment {
	
private ArrayList<Label> list;
	private LabelListAdapter adapter;
	
	public LabelListFragment(ArrayList<Label> list) {
		this.list = list;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setListAdapter(null);
		
		if (adapter == null) {		
			adapter = new LabelListAdapter(getActivity(), list);
		}
		
		setListAdapter(adapter);
	}
	
}
