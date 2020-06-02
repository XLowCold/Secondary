package com.example.secondary.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 淘宝：http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp
 */

public class GetTimestamp {

	//接口地址
	public static final String BASE_URL = "http://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp";

	/**
	 * api : mtop.common.getTimestamp
	 * v : *
	 * ret : ["SUCCESS::接口调用成功"]
	 * data : {"t":"1588071226859"}
	 */

	private String api;
	private String v;
	private DataBean data;
	private List<String> ret;

	public static GetTimestamp objectFromData(String str) {

		return new Gson().fromJson(str, GetTimestamp.class);
	}

	public static GetTimestamp objectFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);

			return new Gson().fromJson(jsonObject.getString(str), GetTimestamp.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<GetTimestamp> arrayGetTimestampFromData(String str) {

		Type listType = new TypeToken<ArrayList<GetTimestamp>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public static List<GetTimestamp> arrayGetTimestampFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			Type listType = new TypeToken<ArrayList<GetTimestamp>>() {
			}.getType();

			return new Gson().fromJson(jsonObject.getString(str), listType);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ArrayList();


	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public List<String> getRet() {
		return ret;
	}

	public void setRet(List<String> ret) {
		this.ret = ret;
	}

	public static class DataBean {
		/**
		 * t : 1588071226859
		 */

		private String t;

		public static DataBean objectFromData(String str) {

			return new Gson().fromJson(str, DataBean.class);
		}

		public static DataBean objectFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);

				return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		public static List<DataBean> arrayDataBeanFromData(String str) {

			Type listType = new TypeToken<ArrayList<DataBean>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public static List<DataBean> arrayDataBeanFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);
				Type listType = new TypeToken<ArrayList<DataBean>>() {
				}.getType();

				return new Gson().fromJson(jsonObject.getString(str), listType);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return new ArrayList();


		}

		public String getT() {
			return t;
		}

		public void setT(String t) {
			this.t = t;
		}
	}
}
