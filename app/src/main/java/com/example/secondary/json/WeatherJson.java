package com.example.secondary.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 免费天气预报 Api
 * http://wthrcdn.etouch.cn/weather_mini?citykey=101280701
 *
 * 通过城市id获得天气数据，xml文件数据,
 * http://wthrcdn.etouch.cn/WeatherApi?citykey=101070101
 * 通过城市名字获得天气数据，xml文件数据
 * http://wthrcdn.etouch.cn/WeatherApi?city=沈阳
 * 城市代码
 * https://www.cnblogs.com/wf225/p/4090737.html
 **/
public class WeatherJson {

	//通过城市名字获得天气数据，Json数据
	public static final String BASE_URL_BYCITY = "http://wthrcdn.etouch.cn/weather_mini?city=";
	// 通过id获得天气数据，Json数据
	public static final String BASE_URL_BYCODE = BASE_URL_BYCITY + "key=";

	/**
	 * data : {"yesterday":{"date":"25日星期六","high":"高温 16℃","fx":"北风","low":"低温 2℃","fl":"<![CDATA[5-6级]]>","type":"晴"},"city":"沈阳","forecast":[{"date":"26日星期天","high":"高温 16℃","fengli":"<![CDATA[4-5级]]>","low":"低温 2℃","fengxiang":"东北风","type":"多云"},{"date":"27日星期一","high":"高温 19℃","fengli":"<![CDATA[3-4级]]>","low":"低温 8℃","fengxiang":"西北风","type":"晴"},{"date":"28日星期二","high":"高温 24℃","fengli":"<![CDATA[5-6级]]>","low":"低温 11℃","fengxiang":"西南风","type":"多云"},{"date":"29日星期三","high":"高温 23℃","fengli":"<![CDATA[5-6级]]>","low":"低温 9℃","fengxiang":"西南风","type":"多云"},{"date":"30日星期四","high":"高温 23℃","fengli":"<![CDATA[4-5级]]>","low":"低温 12℃","fengxiang":"西南风","type":"多云"}],"ganmao":"虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。","wendu":"6"}
	 * status : 1000
	 * desc : OK
	 */

	private DataBean data;
	private int status;
	private String desc;

	public static WeatherJson objectFromData(String str) {

		return new Gson().fromJson(str, WeatherJson.class);
	}

	public static WeatherJson objectFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);

			return new Gson().fromJson(jsonObject.getString(str), WeatherJson.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<WeatherJson> arrayweFromData(String str) {

		Type listType = new TypeToken<ArrayList<WeatherJson>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public static List<WeatherJson> arrayweFromData(String str, String key) {

		try {
			JSONObject jsonObject = new JSONObject(str);
			Type listType = new TypeToken<ArrayList<WeatherJson>>() {
			}.getType();

			return new Gson().fromJson(jsonObject.getString(str), listType);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return new ArrayList();


	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static class DataBean {
		/**
		 * yesterday : {"date":"25日星期六","high":"高温 16℃","fx":"北风","low":"低温 2℃","fl":"<![CDATA[5-6级]]>","type":"晴"}
		 * city : 沈阳
		 * forecast : [{"date":"26日星期天","high":"高温 16℃","fengli":"<![CDATA[4-5级]]>","low":"低温 2℃","fengxiang":"东北风","type":"多云"},{"date":"27日星期一","high":"高温 19℃","fengli":"<![CDATA[3-4级]]>","low":"低温 8℃","fengxiang":"西北风","type":"晴"},{"date":"28日星期二","high":"高温 24℃","fengli":"<![CDATA[5-6级]]>","low":"低温 11℃","fengxiang":"西南风","type":"多云"},{"date":"29日星期三","high":"高温 23℃","fengli":"<![CDATA[5-6级]]>","low":"低温 9℃","fengxiang":"西南风","type":"多云"},{"date":"30日星期四","high":"高温 23℃","fengli":"<![CDATA[4-5级]]>","low":"低温 12℃","fengxiang":"西南风","type":"多云"}]
		 * ganmao : 虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。
		 * wendu : 6
		 */

		private YesterdayBean yesterday;
		private String city;
		private String ganmao;
		private String wendu;
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

		public YesterdayBean getYesterday() {
			return yesterday;
		}

		public void setYesterday(YesterdayBean yesterday) {
			this.yesterday = yesterday;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getGanmao() {
			return ganmao;
		}

		public void setGanmao(String ganmao) {
			this.ganmao = ganmao;
		}

		public String getWendu() {
			return wendu;
		}

		public void setWendu(String wendu) {
			this.wendu = wendu;
		}

		public List<ForecastBean> getForecast() {
			return forecast;
		}

		public void setForecast(List<ForecastBean> forecast) {
			this.forecast = forecast;
		}

		public static class YesterdayBean {
			/**
			 * date : 25日星期六
			 * high : 高温 16℃
			 * fx : 北风
			 * low : 低温 2℃
			 * fl : <![CDATA[5-6级]]>
			 * type : 晴
			 */

			private String date;
			private String high;
			private String fx;
			private String low;
			private String fl;
			private String type;

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

			public String getFx() {
				return fx;
			}

			public void setFx(String fx) {
				this.fx = fx;
			}

			public String getLow() {
				return low;
			}

			public void setLow(String low) {
				this.low = low;
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
		}

		public static class ForecastBean {
			/**
			 * date : 26日星期天
			 * high : 高温 16℃
			 * fengli : <![CDATA[4-5级]]>
			 * low : 低温 2℃
			 * fengxiang : 东北风
			 * type : 多云
			 */

			private String date;
			private String high;
			private String fengli;
			private String low;
			private String fengxiang;
			private String type;

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

			public String getFengli() {
				return fengli;
			}

			public void setFengli(String fengli) {
				this.fengli = fengli;
			}

			public String getLow() {
				return low;
			}

			public void setLow(String low) {
				this.low = low;
			}

			public String getFengxiang() {
				return fengxiang;
			}

			public void setFengxiang(String fengxiang) {
				this.fengxiang = fengxiang;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}
		}
	}
}
