package com.example.secondary;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.secondary.app.App;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, MainActivity.class));
		App.getMainHandler().postDelayed(() -> {
		}, 2000);
		finish();
	}
}
