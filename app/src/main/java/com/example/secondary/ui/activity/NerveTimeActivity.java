package com.example.secondary.ui.activity;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.secondary.R;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class NerveTimeActivity extends BaseActivity {

	private static final String TAG = NerveTimeActivity.class.getSimpleName();
	private Runnable mRunnable;//计时线程
	private long mStartTime = 0;//开始时间
	private long mWaitTime = 0;//等待时间
	private Boolean mGameSwitch = false;//状态
	//控件
	private Button mNerveTestStart;
	private LinearLayout mLayout;

	private static MyHandler mHandler = null;

	private static class MyHandler extends Handler {
		private WeakReference<NerveTimeActivity> mReference;

		public MyHandler(NerveTimeActivity activity) {
			mReference = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(@NonNull Message msg) {
			super.handleMessage(msg);
			NerveTimeActivity activity = mReference.get();
			switch (msg.what) {
				case 0:
					Log.d(TAG, "handleMessage: " + activity.mStartTime);
					break;
				default:

					break;
			}
		}
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_nerve_time;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		mHandler = new MyHandler(this);
		setTitle("反应测试");
		setBack();
	}

	private void initView() {
		mNerveTestStart = findViewById(R.id.BtnStart);
		//按钮点击事件
		mNerveTestStart.setOnTouchListener(new OnListener());
		mLayout = (LinearLayout) findViewById(R.id.ly);
	}

	private class OnListener implements View.OnTouchListener {
		private StringBuffer sb = new StringBuffer();
		private boolean mCloseSwitch = true;

		@RequiresApi(api = Build.VERSION_CODES.P)
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			AlertDialog.Builder builder = new AlertDialog.Builder(NerveTimeActivity.this)
					.setTitle("测试结束");
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (mGameSwitch & mCloseSwitch) {
						mCloseSwitch = closeGame("按下", sb, mStartTime);
					}
					break;
				case MotionEvent.ACTION_UP:
					if (!mGameSwitch) {
						mGameSwitch = true;
						mNerveTestStart.setText(R.string.stop);
						startGame();
					} else {
						if (mCloseSwitch) mCloseSwitch = closeGame("抬起", sb, mStartTime);
						builder.setMessage(sb).show();
						sb.setLength(0);
						mLayout.setBackgroundResource(android.R.color.white);
						mGameSwitch = false;
						mNerveTestStart.setText(R.string.start);
					}
					break;
				default:
					Log.d(TAG, "onKey: " + event.getAction());
					break;
			}
			return false;
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.P)
	private void startGame() {
		mWaitTime = new Random().nextInt(3000) + 600;
		mRunnable = () -> {
			mLayout.setBackgroundResource(android.R.color.holo_red_dark);
		};
		mStartTime = System.currentTimeMillis();
		mHandler.postDelayed(mRunnable, mWaitTime);
		Log.d(TAG, "startGame: 开始");
	}

	private boolean closeGame(String string, StringBuffer sb, long startGameTime) {
		if (System.currentTimeMillis() >= startGameTime) {
			long usedTime = System.currentTimeMillis() - mStartTime;
			sb.append(string + "时间：" + usedTime + "ms");
			return true;
		} else {
			sb.append("你点太快了，不要作弊哟~");
		}
		//移除未完成任务
		mHandler.removeCallbacks(mRunnable);
		mRunnable = null;
		return false;
	}


	private void updateWeather() {
		new Thread(() -> {
			//耗时操作，完成之后发送消息给Handler，完成UI更新；
			mHandler.sendEmptyMessage(0);

			//需要数据传递，用下面方法；
			Message msg = new Message();
			msg.obj = "数据";//可以是基本类型，可以是对象，可以是List、map等；
			mHandler.sendMessage(msg);
		}).start();
	}
}
