package com.example.secondary.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 国家气象局提供的天气预报接口 返回JSON数据
 * http://t.weather.sojson.com/api/weather/city/101280701
 **/
public class CNWeatherJson {

	// 通过id获得天气数据，Json数据
	public static final String BASE_URL = "http://t.weather.sojson.com/api/weather/city/";

	/**
	 * message : success感谢又拍云(upyun.com)提供CDN赞助
	 * status : 200
	 * date : 20200428
	 * time : 2020-04-28 12:08:12
	 * cityInfo : {"city":"珠海市","citykey":"101280701","parent":"广东","updateTime":"11:56"}
	 * data : {"shidu":"47%","pm25":21,"pm10":38,"quality":"优","wendu":"25","ganmao":"各类人群可自由活动","forecast":[{"date":"28","high":"高温 28℃","low":"低温 21℃","ymd":"2020-04-28","week":"星期二","sunrise":"05:57","sunset":"18:51","aqi":50,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"29","high":"高温 27℃","low":"低温 21℃","ymd":"2020-04-29","week":"星期三","sunrise":"05:56","sunset":"18:51","aqi":84,"fx":"东风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"30","high":"高温 27℃","low":"低温 22℃","ymd":"2020-04-30","week":"星期四","sunrise":"05:55","sunset":"18:52","aqi":38,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"01","high":"高温 27℃","low":"低温 23℃","ymd":"2020-05-01","week":"星期五","sunrise":"05:55","sunset":"18:52","aqi":27,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"02","high":"高温 28℃","low":"低温 24℃","ymd":"2020-05-02","week":"星期六","sunrise":"05:54","sunset":"18:53","aqi":21,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","high":"高温 29℃","low":"低温 24℃","ymd":"2020-05-03","week":"星期日","sunrise":"05:53","sunset":"18:53","aqi":18,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"04","high":"高温 29℃","low":"低温 23℃","ymd":"2020-05-04","week":"星期一","sunrise":"05:53","sunset":"18:53","fx":"无持续风向","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"05","high":"高温 28℃","low":"低温 23℃","ymd":"2020-05-05","week":"星期二","sunrise":"05:52","sunset":"18:54","fx":"西南风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","high":"高温 28℃","low":"低温 23℃","ymd":"2020-05-06","week":"星期三","sunrise":"05:51","sunset":"18:54","fx":"西南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","high":"高温 28℃","low":"低温 22℃","ymd":"2020-05-07","week":"星期四","sunrise":"05:51","sunset":"18:55","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"08","high":"高温 29℃","low":"低温 22℃","ymd":"2020-05-08","week":"星期五","sunrise":"05:50","sunset":"18:55","fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"09","high":"高温 29℃","low":"低温 23℃","ymd":"2020-05-09","week":"星期六","sunrise":"05:50","sunset":"18:56","fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"10","high":"高温 29℃","low":"低温 22℃","ymd":"2020-05-10","week":"星期日","sunrise":"05:49","sunset":"18:56","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 28℃","low":"低温 22℃","ymd":"2020-05-11","week":"星期一","sunrise":"05:49","sunset":"18:56","fx":"东风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"12","high":"高温 26℃","low":"低温 21℃","ymd":"2020-05-12","week":"星期二","sunrise":"05:48","sunset":"18:57","fx":"东南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}],"yesterday":{"date":"27","high":"高温 25℃","low":"低温 20℃","ymd":"2020-04-27","week":"星期一","sunrise":"05:57","sunset":"18:51","aqi":60,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}}
	 */

	private String message;
	private int status;
	private String date;
	private String time;
	private CityInfoBean cityInfo;
	private DataBean data;

	public static CNWeatherJson objectFromData(String str) {

		return new Gson().fromJson(str, CNWeatherJson.class);
	}

	public static CNWeatherJson objectFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);

			return new Gson().fromJson(jsonObject.getString(str), CNWeatherJson.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<CNWeatherJson> arrayWeatherSojsonFromData(String str) {

		Type listType = new TypeToken<ArrayList<CNWeatherJson>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public static List<CNWeatherJson> arrayWeatherSojsonFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			Type listType = new TypeToken<ArrayList<CNWeatherJson>>() {
			}.getType();

			return new Gson().fromJson(jsonObject.getString(str), listType);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ArrayList();


	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public CityInfoBean getCityInfo() {
		return cityInfo;
	}

	public void setCityInfo(CityInfoBean cityInfo) {
		this.cityInfo = cityInfo;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public static class CityInfoBean {
		/**
		 * city : 珠海市
		 * citykey : 101280701
		 * parent : 广东
		 * updateTime : 11:56
		 */

		private String city;
		private String citykey;
		private String parent;
		private String updateTime;

		public static CityInfoBean objectFromData(String str) {

			return new Gson().fromJson(str, CityInfoBean.class);
		}

		public static CityInfoBean objectFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);

				return new Gson().fromJson(jsonObject.getString(str), CityInfoBean.class);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		public static List<CityInfoBean> arrayCityInfoBeanFromData(String str) {

			Type listType = new TypeToken<ArrayList<CityInfoBean>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public static List<CityInfoBean> arrayCityInfoBeanFromData(String str, String key) {

			try {
				JSONObject jsonObject = new JSONObject(str);
				Type listType = new TypeToken<ArrayList<CityInfoBean>>() {
				}.getType();

				return new Gson().fromJson(jsonObject.getString(str), listType);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return new ArrayList();


		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCitykey() {
			return citykey;
		}

		public void setCitykey(String citykey) {
			this.citykey = citykey;
		}

		public String getParent() {
			return parent;
		}

		public void setParent(String parent) {
			this.parent = parent;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
	}

	public static class DataBean {
		/**
		 * shidu : 47%
		 * pm25 : 21.0
		 * pm10 : 38.0
		 * quality : 优
		 * wendu : 25
		 * ganmao : 各类人群可自由活动
		 * forecast : [{"date":"28","high":"高温 28℃","low":"低温 21℃","ymd":"2020-04-28","week":"星期二","sunrise":"05:57","sunset":"18:51","aqi":50,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"29","high":"高温 27℃","low":"低温 21℃","ymd":"2020-04-29","week":"星期三","sunrise":"05:56","sunset":"18:51","aqi":84,"fx":"东风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"30","high":"高温 27℃","low":"低温 22℃","ymd":"2020-04-30","week":"星期四","sunrise":"05:55","sunset":"18:52","aqi":38,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"01","high":"高温 27℃","low":"低温 23℃","ymd":"2020-05-01","week":"星期五","sunrise":"05:55","sunset":"18:52","aqi":27,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"02","high":"高温 28℃","low":"低温 24℃","ymd":"2020-05-02","week":"星期六","sunrise":"05:54","sunset":"18:53","aqi":21,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"03","high":"高温 29℃","low":"低温 24℃","ymd":"2020-05-03","week":"星期日","sunrise":"05:53","sunset":"18:53","aqi":18,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"04","high":"高温 29℃","low":"低温 23℃","ymd":"2020-05-04","week":"星期一","sunrise":"05:53","sunset":"18:53","fx":"无持续风向","fl":"<3级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"05","high":"高温 28℃","low":"低温 23℃","ymd":"2020-05-05","week":"星期二","sunrise":"05:52","sunset":"18:54","fx":"西南风","fl":"3-4级","type":"阴","notice":"不要被阴云遮挡住好心情"},{"date":"06","high":"高温 28℃","low":"低温 23℃","ymd":"2020-05-06","week":"星期三","sunrise":"05:51","sunset":"18:54","fx":"西南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"07","high":"高温 28℃","low":"低温 22℃","ymd":"2020-05-07","week":"星期四","sunrise":"05:51","sunset":"18:55","fx":"南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"08","high":"高温 29℃","low":"低温 22℃","ymd":"2020-05-08","week":"星期五","sunrise":"05:50","sunset":"18:55","fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"09","high":"高温 29℃","low":"低温 23℃","ymd":"2020-05-09","week":"星期六","sunrise":"05:50","sunset":"18:56","fx":"南风","fl":"3-4级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"10","high":"高温 29℃","low":"低温 22℃","ymd":"2020-05-10","week":"星期日","sunrise":"05:49","sunset":"18:56","fx":"南风","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"},{"date":"11","high":"高温 28℃","low":"低温 22℃","ymd":"2020-05-11","week":"星期一","sunrise":"05:49","sunset":"18:56","fx":"东风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"},{"date":"12","high":"高温 26℃","low":"低温 21℃","ymd":"2020-05-12","week":"星期二","sunrise":"05:48","sunset":"18:57","fx":"东南风","fl":"3-4级","type":"小雨","notice":"雨虽小，注意保暖别感冒"}]
		 * yesterday : {"date":"27","high":"高温 25℃","low":"低温 20℃","ymd":"2020-04-27","week":"星期一","sunrise":"05:57","sunset":"18:51","aqi":60,"fx":"无持续风向","fl":"<3级","type":"多云","notice":"阴晴之间，谨防紫外线侵扰"}
		 */

		private String shidu;
		private double pm25;
		private double pm10;
		private String quality;
		private String wendu;
		private String ganmao;
		private YesterdayBean yesterday;
		private List<ForecastBean> forecast;

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

		public String getShidu() {
			return shidu;
		}

		public void setShidu(String shidu) {
			this.shidu = shidu;
		}

		public double getPm25() {
			return pm25;
		}

		public void setPm25(double pm25) {
			this.pm25 = pm25;
		}

		public double getPm10() {
			return pm10;
		}

		public void setPm10(double pm10) {
			this.pm10 = pm10;
		}

		public String getQuality() {
			return quality;
		}

		public void setQuality(String quality) {
			this.quality = quality;
		}

		public String getWendu() {
			return wendu;
		}

		public void setWendu(String wendu) {
			this.wendu = wendu;
		}

		public String getGanmao() {
			return ganmao;
		}

		public void setGanmao(String ganmao) {
			this.ganmao = ganmao;
		}

		public YesterdayBean getYesterday() {
			return yesterday;
		}

		public void setYesterday(YesterdayBean yesterday) {
			this.yesterday = yesterday;
		}

		public List<ForecastBean> getForecast() {
			return forecast;
		}

		public void setForecast(List<ForecastBean> forecast) {
			this.forecast = forecast;
		}

		public static class YesterdayBean {
			/**
			 * date : 27
			 * high : 高温 25℃
			 * low : 低温 20℃
			 * ymd : 2020-04-27
			 * week : 星期一
			 * sunrise : 05:57
			 * sunset : 18:51
			 * aqi : 60
			 * fx : 无持续风向
			 * fl : <3级
			 * type : 多云
			 * notice : 阴晴之间，谨防紫外线侵扰
			 */

			private String date;
			private String high;
			private String low;
			private String ymd;
			private String week;
			private String sunrise;
			private String sunset;
			private int aqi;
			private String fx;
			private String fl;
			private String type;
			private String notice;

			public static YesterdayBean objectFromData(String str) {

				return new Gson().fromJson(str, YesterdayBean.class);
			}

			public static YesterdayBean objectFromData(String str, String key) {

				try {
					JSONObject jsonObject = new JSONObject(str);

					return new Gson().fromJson(jsonObject.getString(str), YesterdayBean.class);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			public static List<YesterdayBean> arrayYesterdayBeanFromData(String str) {

				Type listType = new TypeToken<ArrayList<YesterdayBean>>() {
				}.getType();

				return new Gson().fromJson(str, listType);
			}

			public static List<YesterdayBean> arrayYesterdayBeanFromData(String str, String key) {

				try {
					JSONObject jsonObject = new JSONObject(str);
					Type listType = new TypeToken<ArrayList<YesterdayBean>>() {
					}.getType();

					return new Gson().fromJson(jsonObject.getString(str), listType);

				} catch (JSONException e) {
					e.printStackTrace();
				}

				return new ArrayList();


			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getHigh() {
				return high;
			}

			public void setHigh(String high) {
				this.high = high;
			}

			public String getLow() {
				return low;
			}

			public void setLow(String low) {
				this.low = low;
			}

			public String getYmd() {
				return ymd;
			}

			public void setYmd(String ymd) {
				this.ymd = ymd;
			}

			public String getWeek() {
				return week;
			}

			public void setWeek(String week) {
				this.week = week;
			}

			public String getSunrise() {
				return sunrise;
			}

			public void setSunrise(String sunrise) {
				this.sunrise = sunrise;
			}

			public String getSunset() {
				return sunset;
			}

			public void setSunset(String sunset) {
				this.sunset = sunset;
			}

			public int getAqi() {
				return aqi;
			}

			public void setAqi(int aqi) {
				this.aqi = aqi;
			}

			public String getFx() {
				return fx;
			}

			public void setFx(String fx) {
				this.fx = fx;
			}

			public String getFl() {
				return fl;
			}

			public void setFl(String fl) {
				this.fl = fl;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getNotice() {
				return notice;
			}

			public void setNotice(String notice) {
				this.notice = notice;
			}
		}

		public static class ForecastBean {
			/**
			 * date : 28
			 * high : 高温 28℃
			 * low : 低温 21℃
			 * ymd : 2020-04-28
			 * week : 星期二
			 * sunrise : 05:57
			 * sunset : 18:51
			 * aqi : 50
			 * fx : 无持续风向
			 * fl : <3级
			 * type : 多云
			 * notice : 阴晴之间，谨防紫外线侵扰
			 */

			private String date;
			private String high;
			private String low;
			private String ymd;
			private String week;
			private String sunrise;
			private String sunset;
			private int aqi;
			private String fx;
			private String fl;
			private String type;
			private String notice;

			public static ForecastBean objectFromData(String str) {

				return new Gson().fromJson(str, ForecastBean.class);
			}

			public static ForecastBean objectFromData(String str, String key) {

				try {
					JSONObject jsonObject = new JSONObject(str);

					return new Gson().fromJson(jsonObject.getString(str), ForecastBean.class);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			public static List<ForecastBean> arrayForecastBeanFromData(String str) {

				Type listType = new TypeToken<ArrayList<ForecastBean>>() {
				}.getType();

				return new Gson().fromJson(str, listType);
			}

			public static List<ForecastBean> arrayForecastBeanFromData(String str, String key) {

				try {
					JSONObject jsonObject = new JSONObject(str);
					Type listType = new TypeToken<ArrayList<ForecastBean>>() {
					}.getType();

					return new Gson().fromJson(jsonObject.getString(str), listType);

				} catch (JSONException e) {
					e.printStackTrace();
				}

				return new ArrayList();


			}

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getHigh() {
				return high;
			}

			public void setHigh(String high) {
				this.high = high;
			}

			public String getLow() {
				return low;
			}

			public void setLow(String low) {
				this.low = low;
			}

			public String getYmd() {
				return ymd;
			}

			public void setYmd(String ymd) {
				this.ymd = ymd;
			}

			public String getWeek() {
				return week;
			}

			public void setWeek(String week) {
				this.week = week;
			}

			public String getSunrise() {
				return sunrise;
			}

			public void setSunrise(String sunrise) {
				this.sunrise = sunrise;
			}

			public String getSunset() {
				return sunset;
			}

			public void setSunset(String sunset) {
				this.sunset = sunset;
			}

			public int getAqi() {
				return aqi;
			}

			public void setAqi(int aqi) {
				this.aqi = aqi;
			}

			public String getFx() {
				return fx;
			}

			public void setFx(String fx) {
				this.fx = fx;
			}

			public String getFl() {
				return fl;
			}

			public void setFl(String fl) {
				this.fl = fl;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getNotice() {
				return notice;
			}

			public void setNotice(String notice) {
				this.notice = notice;
			}
		}
	}
}
