package com.example.secondary.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public abstract class RequestUtilsProxy<T>  {

	private final String TAG = "ConnectNetWork";
	private ResponseFailure mFailure;
	private long mTimeOut = 10000;//超时时间 毫秒单位

	public RequestUtilsProxy() {
	}

	public RequestUtilsProxy ready() {
		return this;
	}

	public void onFailureListener(ResponseFailure failure) {
		this.mFailure = failure;
	}

	//获取天气数据
	public RequestUtilsProxy requestData(String url, Class<T> tClass) {
		new Thread(() -> {
			try {
				//请求客户端
				OkHttpClient client = new OkHttpClient().newBuilder()
						.connectTimeout(mTimeOut, TimeUnit.MILLISECONDS)
						.build();
				//请求内容
				//使用code获取数据
				Request request = new Request.Builder()
						.get()
						.url(url)
						.build();
				//使用okHttp创建任务
				T t = new Gson().fromJson(client.newCall(request).execute()
						.body().string(), tClass);
				success(t);
			} catch (IOException e) {
				e.printStackTrace();
				Log.d(TAG, "failure: 获取数据失败");
				if (mFailure != null)
					mFailure.failure(e);
			}
		}).start();
		return this;
	}

	//成功
	public abstract void success(T d);

	//失败
	public interface ResponseFailure {
		void failure(IOException e);
	}

	public RequestUtilsProxy setTimeOut(long time) {
		this.mTimeOut = time;
		return this;
	}	public void readFile(String filePath) throws IOException {
		//直接在括号里面定义初始化对象即可
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}finally {

		}
	}
}