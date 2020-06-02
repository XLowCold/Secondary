package com.example.secondary.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.secondary.R;

import java.math.BigInteger;

import androidx.annotation.Nullable;

public class FibonacciActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = "Fibonacci";
	//控件
	private EditText mEditNum;
	private TextView mTvCount;
	private Button mBtnStart;
	private ProgressBar mProBar;
	//
	private BigInteger mCount;
	private Thread mThread;//计算的线程
	private int mSchedule;

	@Override
	protected int getLayout() {
		return R.layout.activity_fibonacci_tool;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		setTitle("计算斐波那契数!");
		setBack();
	}

	private Thread fibonacci(int num) {
		mProBar.setMax(num);
		mProBar.setVisibility(View.VISIBLE);
		Thread thread = new Thread(() -> {
			BigInteger a = new BigInteger("0");
			BigInteger b = new BigInteger("1");
			BigInteger c = new BigInteger("0");
			double start = System.currentTimeMillis();
			for (int i = 2; i <= num; i++) {
				c = a.add(b);
				a = a.subtract(a);
				a = a.add(b);
				b = b.subtract(b);
				b = b.add(c);
				mSchedule = i;
				mProBar.post(() -> {
					mProBar.setProgress(mSchedule);
				});
			}
			double end = System.currentTimeMillis();
			mCount = c;
			mTvCount.post(() -> {
				mProBar.setVisibility(View.INVISIBLE);
				mTvCount.setText("F[" + mSchedule + "]=" + mCount.toString() + "\n" + "耗时" + (end - start));
				this.mThread = null;
			});
			//Log.d("TAG", "onCreate: " + "F[" + 0 + "]=" + c.toString());
			//Log.d(TAG, "onCreate: " + (end - start));
		});
		thread.start();
		return thread;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnStart) {
			if (submit()) {
				if (mThread == null) {
					mThread = fibonacci(Integer.valueOf(mEditNum.getText().toString()));
				} else {
					Toast.makeText(this, "正在计算，请稍候。", Toast.LENGTH_LONG).show();
				}
			}
		}
	}


	private void initView() {
		mEditNum = (EditText) findViewById(R.id.editNum);
		mTvCount = (TextView) findViewById(R.id.tvCount);
		mBtnStart = (Button) findViewById(R.id.btnStart);
		mBtnStart.setOnClickListener(this);
		mProBar = (ProgressBar) findViewById(R.id.proBar);
		mProBar.setVisibility(View.INVISIBLE);
	}

	private boolean submit() {
		// validate
		String editNumString = mEditNum.getText().toString().trim();
		if (TextUtils.isEmpty(editNumString)) {
			Toast.makeText(this, "N不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!isDigit(editNumString)) {
			Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
			return false;
		}

		// TODO validate success, do something
		return true;
	}

	private boolean isDigit(String s) {
		if (s.isEmpty()) {
			return false;
		} else {
			char c;
			for (int i = 0; i < s.length(); i++) {
				c = s.charAt(i);
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}

	private void stsrtNum(int num) {
		for (int i = 0; i < num; i++) {
			new Thread(new MyFibonacci()).start();
		}
	}

	public static class MyFibonacci implements Runnable {
		public static long num = 2;   //用于记录角标，静态变量实现多线程共享
		public long numRe;     //用于记录角标，常量实现线程内角标持久化
		public static long a = 0;     //f[n-2]
		public static long b = 1;     //f[n-1]
		public long c;          //f[n]

		public MyFibonacci() {
			c = a + b;
			a = b;
			b = c;
			numRe = num++;
		}

		@Override
		public void run() {
			Log.d("TAG", "onCreate: " + "F[" + numRe + "]=" + c);
			Thread.yield();
		}
	}
}
