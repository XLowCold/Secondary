package com.example.secondary.app;

import android.app.Application;
import android.os.Handler;


public class App extends Application {
	public static final Handler mainHandler = new Handler();
	private static App app = null;

	public static App getInstance() {
		return app;
	}

	public static Handler getMainHandler() {
		return mainHandler;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
	}
}
