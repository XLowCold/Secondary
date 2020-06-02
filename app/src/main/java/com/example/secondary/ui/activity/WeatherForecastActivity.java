package com.example.secondary.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secondary.R;
import com.example.secondary.utils.RequestUtilsProxy;
import com.example.secondary.json.CNWeatherJson;
import com.example.secondary.ui.adapter.RVAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherForecastActivity extends BaseActivity {
	private static final String TAG = WeatherForecastActivity.class.getSimpleName();
	private Handler mHandler = null;
	//城市id 文件地址
	private final String CHINA_CITY_URL = "weather/ChinaCityCode.txt";
	//城市id Map<city,code>
	private Map<String, String> sChinaCityCode = null;
	//控件
	private RecyclerView mRVWeather;//
	private SearchView mSearch;//搜索框
	private TextView mTvCityName;
	private ImageView mImgIcon;
	private TextView mTvTemperature, mTvHumidity, mTvWindSpeed, mTvQuality;
	//天气数据适配器
	private RVAdapter mWeatherRVAdapter = null;
	//城市代码数据适配器
	private RVAdapter mCityRVAdapter = null;

	private CNWeatherJson mData;//网络天气数据

	@Override
	protected int getLayout() {
		return R.layout.activity_weather;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取天气布局
		initView();
		String key = inquireCode("珠海");
		getWeatherData(CNWeatherJson.BASE_URL + key);
		setTitle("天气");
		setBack();
	}

	//初始化视图
	private void initView() {
		mTvCityName = (TextView) findViewById(R.id.tvCityName);
		mImgIcon = (ImageView) findViewById(R.id.imgIcon);
		mTvTemperature = (TextView) findViewById(R.id.tvTemperature);
		mTvHumidity = (TextView) findViewById(R.id.tvHumidity);
		mTvWindSpeed = (TextView) findViewById(R.id.tvWindSpeed);
		mTvQuality = (TextView) findViewById(R.id.tvQuality);
		mRVWeather = (RecyclerView) findViewById(R.id.rvWeather);
		mSearch = (SearchView) findViewById(R.id.search);
		searchAttributes();
	}

	//搜索框
	private void searchAttributes() {
		mSearch.setIconifiedByDefault(false);//设置默认是否缩小为图标
		mSearch.setSubmitButtonEnabled(true);//设置是否显示搜索按钮
		mSearch.setQueryHint("搜索城市天气");//设置默认提示字符
		mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Log.d(TAG, "onQueryTextSubmit: 提交字符==>" + query);
				String key = inquireCode(query.trim());
				getWeatherData(CNWeatherJson.BASE_URL + key);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				Log.d(TAG, "onQueryTextChange: 值改变了==>" + newText);
				Log.d(TAG, "onQueryTextChange: ");
				return false;
			}
		});
		mSearch.setOnSearchClickListener(v -> {
			Log.d(TAG, "searchAttributes: ");
		});
		mSearch.setOnCloseListener(() -> {
			Log.d(TAG, "onClose: ");
			return false;
		});
	}

	private String getImgName(String type) {
		return "";
	}

	private static class WeatherHandler extends Handler {
		private WeakReference<WeatherForecastActivity> mReference;

		public WeatherHandler(WeatherForecastActivity activity) {
			mReference = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(@NonNull Message msg) {
			super.handleMessage(msg);
			WeatherForecastActivity activity = mReference.get();
			if (activity == null) return;
			switch (msg.what) {
				case 1:
					CNWeatherJson mData = (CNWeatherJson) msg.obj;
					if (mData.getStatus() == 200) {
						Log.d(TAG, "成功获取天气数据 代码：" + mData.getStatus());
						activity.showWeatherData(mData, 0);
					} else {
						String s = "暂无该地区天气 Code：" + mData.getStatus();
						Log.d(TAG, s);
						Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
					}
					break;
				case -1:
					Toast.makeText(activity, "无法连接网络，请检查网络后重试。", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}

	//获取天气数据
	private void getWeatherData(String cityName) {
		if (mHandler == null) mHandler = new WeatherHandler(this);
		//获取连接类
		new RequestUtilsProxy<CNWeatherJson>() {
			@Override
			public void success(CNWeatherJson d) {
				Message message = Message.obtain();
				message.obj = d;
				message.what = 1;
				mHandler.sendMessage(message);
			}
		}.requestData(cityName, CNWeatherJson.class)
				.onFailureListener(e -> {
					Message message = Message.obtain();
					message.obj = e;
					message.what = -1;
					mHandler.sendMessage(message);
				});
	}

	//初始化天气数据适配器
	private void showWeatherData(CNWeatherJson cnWeatherJson, int pos) {
		if (mWeatherRVAdapter == null) {
			mWeatherRVAdapter = new RVAdapter<CNWeatherJson.DataBean.ForecastBean>(this
					, cnWeatherJson.getData().getForecast(), R.layout.rv_itm_weather) {
				@Override
				public void bind(VH vh, CNWeatherJson.DataBean.ForecastBean d, int pos) {
					vh.setTxt(R.id.tvDate, d.getDate() + "号" + d.getWeek());//天数 星期
					vh.setTxt(R.id.tvType, d.getType());//天气

					String low = transformInteger(d.getLow());
					String high = transformInteger(d.getHigh());
					vh.setTxt(R.id.tvTemperature, integerSume(low, high) / 2);//温度
				}

				@NonNull
				@Override
				public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
					VH vh = super.onCreateViewHolder(parent, viewType);
					//点击事件
					vh.itemView.setOnClickListener(v -> {
						Log.d(TAG, "onCreateViewHolder: 天气点击事件" + vh.getLayoutPosition());

					});
					return vh;
				}
			};
			mRVWeather.setAdapter(mWeatherRVAdapter);
			LinearLayoutManager layoutManager = new LinearLayoutManager(this);
			mRVWeather.setLayoutManager(layoutManager);
		} else {
			mWeatherRVAdapter.setList(cnWeatherJson.getData().getForecast()).notifyDataSetChanged();
		}
		CNWeatherJson.DataBean.ForecastBean bean = cnWeatherJson.getData().getForecast().get(pos);
		mTvCityName.setText(cnWeatherJson.getCityInfo().getCity() + "\\"
				+ cnWeatherJson.getData().getForecast().get(pos).getType());//城市名称和天气

		String low = transformInteger(bean.getLow());
		String high = transformInteger(bean.getHigh());
		mTvTemperature.setText(integerSume(low, high) / 2 + "℃");
		mTvHumidity.setText(cnWeatherJson.getData().getShidu());
		mTvWindSpeed.setText(bean.getFl());
		mTvQuality.setText(cnWeatherJson.getData().getQuality());
	}

	private String transformInteger(String s) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			String trim = matcher.replaceAll("").trim();
			return trim;
		}
		return null;
	}

	private Integer integerSume(String numA, String numB) {
		return Integer.valueOf(numA) + Integer.valueOf(numB);
	}

	//初始化城市数据适配器
	private void initCityAdapter() {
		/*
		runOnUiThread(() -> {
			Iterator<String> iterator = sChinaCityCode.keySet().iterator();
			List<String> citys = new ArrayList<>(sChinaCityCode.size());
			while (iterator.hasNext()) {
				String next = iterator.next();
				citys.add(next);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citys);//配置Adaptor
			mAutocomplete_city.setAdapter(adapter);
		});*/
	}

	//使用城市查询城市代码
	public String inquireCode(String key) {
		try {
			//获取城市code集合
			Map<String, String> cityCodeMap = null;
			cityCodeMap = loadFromSDFile(CHINA_CITY_URL, sChinaCityCode);
			if (!cityCodeMap.equals(sChinaCityCode)) {
				sChinaCityCode = cityCodeMap;
			}
			//通过key获取code值
			Iterator<String> iterator = sChinaCityCode.keySet().iterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				if (next.equals(key)) {
					Log.d(TAG, "getCode: 城市:" + key + "==>" + "code:" + sChinaCityCode.get(key));
					return sChinaCityCode.get(key);
				}
			}
			Log.d(TAG, "getCode: 没有城市代码 key==>" + key);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//读取本地文件=>城市代码
	private Map<String, String> loadFromSDFile(String fName, Map<String, String> stringMap) throws
			IOException {
		if (stringMap == null) {
			stringMap = new HashMap<>();
			InputStreamReader inputStreamReader = null;
			BufferedReader bufReader = null;
			try {
				inputStreamReader = new InputStreamReader(getAssets().open(fName));
				bufReader = new BufferedReader(inputStreamReader);
				String line;//定义临时字符串
				while ((line = bufReader.readLine()) != null) {
					if (line.length() <= 0) {
						//字符串空
						continue;
					}
					String[] strings = line.split("=");//拆分如==> 101190101=南京 的值
					if (strings.length == 2) {
						stringMap.put(strings[1], strings[0]);//键是城市 值是代码
					}
				}
				Log.d(TAG, "downFile: 成功获取城市代码");
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "loadFromSDFile: 无法找到城市代码文件===Could not find city code file" + e.toString());
			} finally {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (bufReader != null) {
					bufReader.close();
				}
			}
		}
		return stringMap;
	}

	public Bitmap getImageBitmap(String url) {
		URL imgUrl = null;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	private Bitmap getBitmap(final String imgUrl) {
		HttpURLConnection connection = null;
		try {
			URL bitmapUrl = new URL(imgUrl);
			connection = (HttpURLConnection) bitmapUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			//通过返回码判断网络是否请求成功
			if (connection != null && connection.getResponseCode() == 200) {
				InputStream inputStream = connection.getInputStream();
				Bitmap shareBitmap = BitmapFactory.decodeStream(inputStream);
				return shareBitmap;
			} else {
				Log.d(TAG, "getBitmap: 图片获取失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//销毁进行中的消息
		mHandler.removeCallbacksAndMessages(null);
	}
}
