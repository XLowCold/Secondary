package com.example.secondary.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class HomePagerRsqrtActivity extends BaseActivity {
	@Override
	protected int getLayout() {
		return 0;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	private double rsqrt(double number) {
		long i;
		double x2, y;
		final double threehalfs = 1.5D;
		y = number;
		//i=*(long*)&y;
		return y;
	}
}
