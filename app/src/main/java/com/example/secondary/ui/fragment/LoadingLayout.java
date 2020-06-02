package com.example.secondary.ui.fragment;

import android.content.Context;

import com.example.secondary.R;

public class LoadingLayout {

	private int emptyView, errorView, loadingView;

	public LoadingLayout(Context context) {
		emptyView = R.layout.loading_null;
		errorView = R.layout.loading_error;
		loadingView = R.layout.loading;

	}
}