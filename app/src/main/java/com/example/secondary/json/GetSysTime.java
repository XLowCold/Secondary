package com.example.secondary.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 苏宁：http://quan.suning.com/getSysTime.do
 */
public class GetSysTime {

	//接口地址
	public static final String BASE_URL = "http://quan.suning.com/getSysTime.do";

	/**
	 * sysTime2 : 2020-04-28 18:47:29
	 * sysTime1 : 20200428184729
	 */

	private String sysTime2;
	private String sysTime1;

	public static GetSysTime objectFromData(String str) {

		return new Gson().fromJson(str, GetSysTime.class);
	}

	public static GetSysTime objectFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);

			return new Gson().fromJson(jsonObject.getString(str), GetSysTime.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<GetSysTime> arrayGetSysTimeFromData(String str) {

		Type listType = new TypeToken<ArrayList<GetSysTime>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public static List<GetSysTime> arrayGetSysTimeFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			Type listType = new TypeToken<ArrayList<GetSysTime>>() {
			}.getType();

			return new Gson().fromJson(jsonObject.getString(str), listType);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ArrayList();


	}

	public String getSysTime2() {
		return sysTime2;
	}

	public void setSysTime2(String sysTime2) {
		this.sysTime2 = sysTime2;
	}

	public String getSysTime1() {
		return sysTime1;
	}

	public void setSysTime1(String sysTime1) {
		this.sysTime1 = sysTime1;
	}
}
