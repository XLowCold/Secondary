package com.example.secondary;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
public class HttpHelpTools {

	public static final String USER_NAME = "UserName";
	private static final String TAG = HttpHelpTools.class.getCanonicalName();
	private static final String BASE_HOST = "http://121.9.253.237:8888/traffic/api/v2/";
	private OkHttpClient mOkHttpClient = new OkHttpClient();
	private Request.Builder mRequest = new Request.Builder();
	private FormBody.Builder mRequestBody = new FormBody.Builder();
	private JSONObject obj;
	private int method = 0;

	public HttpHelpTools(String url) {
		if (url.startsWith("http")) {
			mRequest.url(url);
		} else {
			mRequest.url(BASE_HOST + url);
		}
	}

	public <T> T getData(Class<T> tClass) {
		try {
			ready();
			return new Gson().fromJson(mOkHttpClient.newCall(mRequest.build()).execute().body().string(), tClass);

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "getData: " + e.toString());
		}
		return null;
	}

	private void ready() {
		switch (method) {
			case 0:
				mRequest.get();
				break;
			case 1:
				mRequest.post(mRequestBody.build());
				break;
			case 2:
				MediaType type = MediaType.parse("application/json;charset=utf-8");
				RequestBody requestBody = RequestBody.create(type, obj.toString());
				mRequest.post(requestBody);
				break;
		}
	}

	public HttpHelpTools post(String k, String v) {
		if (obj == null)
			obj = new JSONObject();
		try {
			obj.put(k, v);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		method = 2;
		return this;
	}

	public HttpHelpTools setParameter(String name, Object value) {
		mRequestBody.add(name, value + "");
		method = 1;
		return this;
	}

	public HttpHelpTools get() {
		method = 0;
		return this;
	}

	public HttpHelpTools post() {
		method = 1;
		return this;
	}
}
