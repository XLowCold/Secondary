package com.example.secondary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.secondary.ui.activity.BaseActivity;
import com.example.secondary.ui.fragment.HomePageToolFragment;
import com.example.secondary.ui.fragment.HomePagePersonalFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

	private static final String TAG = "MainActivity";
	//控件
	private ViewPager mMainViewPager;
	private List<RadioButton> mRdoBtnMains;
	private RadioGroup mMainRadioGroup;
	//适配器
	private FPAdapter mFPAdapter;

	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate: 新建主界面");
		initView();
		setTitle("标题");
	}

	private void initView() {
		//导航栏按钮初始化
		mMainRadioGroup = (RadioGroup) findViewById(R.id.mainRadioGroup);
		mMainRadioGroup.setOnCheckedChangeListener(this);
		int[] ids = {R.id.rdoBtnMain_01, R.id.rdoBtnMain_02, R.id.rdoBtnMain_03, R.id.rdoBtnMain_04};
		ClickButton click = new ClickButton();//实例化监听器对象
		for (int i = 0; i < ids.length; i++) {
			if (mRdoBtnMains == null) {
				mRdoBtnMains = new ArrayList<>(ids.length);
			}
			mRdoBtnMains.add((RadioButton) findViewById(ids[i]));
			mRdoBtnMains.get(i).setOnClickListener(click);//添加监听器
		}
		//ViewPager初始化
		mMainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
		mFPAdapter = new FPAdapter(getSupportFragmentManager());
		mMainViewPager.setAdapter(mFPAdapter);
		mMainViewPager.addOnPageChangeListener(this);
		mRdoBtnMains.get(2).setChecked(true);//选择第一页
	}

	private void initHomePage(int index) {
	}

	// 导航栏按钮监听事件
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for (int i = 0; i < mRdoBtnMains.size(); i++) {
			if (mRdoBtnMains.get(i).getId() == checkedId) {
				mMainViewPager.setCurrentItem(i);
				return;
			}
		}
	}


	// ViewPager监听事件
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if (state == 2) {
			mRdoBtnMains.get(mMainViewPager.getCurrentItem()).setChecked(true);
		}
	}

	//主页按钮事件
	class ClickButton implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.rdoBtnMain_01:

					break;
				case R.id.rdoBtnMain_02:
					/*
					//手机振动
					Vibrator myVibrator;
					myVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(new long[]{0, 2000}, 0);*/
					break;
			}
		}
	}


	// FragmentPagerAdapter适配器
	class FPAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragments = new ArrayList<>();

		public FPAdapter(FragmentManager fm) {
			super(fm);
			fragments.add(new HomePagePersonalFragment());
			fragments.add(new HomePagePersonalFragment());
			fragments.add(new HomePageToolFragment());
			fragments.add(new HomePagePersonalFragment());
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = fragments.get(position);
			return fragment;
		}

		@Override
		public int getCount() {
			return fragments.size();
		}
	}
}