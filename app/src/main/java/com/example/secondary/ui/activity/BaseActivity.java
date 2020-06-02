package com.example.secondary.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.secondary.R;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * copyright:
 * author:qaq
 * description：基础Activity
 * createTime:2020/4/26
 */
public abstract class BaseActivity extends AppCompatActivity {
	//标题栏
	private LinearLayout mRootLayout;
	private View mToolbarLayout;
	private TextView mTvLeftBack;
	private TextView mTvTitle;


	protected abstract int getLayout();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.title_root);
		setContentView(getLayout());
		initToolbar();
	}

	public void setShowProgressBar(boolean b) {
		if (b) {

		}
	}


	@Override
	public void setContentView(@LayoutRes int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		mRootLayout = (LinearLayout) findViewById(R.id.root_layout);
		if (mRootLayout != null) {
			mRootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			initToolbar();
		}
	}

	/**
	 * 初始化 toolbar 内容布局
	 */
	private void initToolbar() {
		mTvLeftBack = f(R.id.tv_back);
		mTvTitle = f(R.id.tv_title);
		mToolbarLayout = f(R.id.ll_toolbar);
	}

	/**
	 * 设置返回按钮
	 */
	protected void setBack() {
		if (mTvLeftBack != null) {
			mTvLeftBack.setVisibility(View.VISIBLE);
			mTvLeftBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	/**
	 * 设置当前 Activity 标题
	 *
	 * @param title
	 */
	protected void setTitle(String title) {
		if (mTvTitle != null) {
			mTvTitle.setVisibility(View.VISIBLE);
			mTvTitle.setText(title);
		}
	}

	/**
	 * 隐藏头部标题栏
	 */
	protected void hideToolbar() {
		if (mToolbarLayout != null) {
			mToolbarLayout.setVisibility(View.GONE);
		}
	}

	protected <T extends View> T f(int id) {
		return (T) findViewById(id);
	}

}
//	private List<Long> mergeSort(List<Long> list) {
//		if (list.size() <= 1) {
//			return list;
//		}
//		int middle = list.size() / 2;
//		List<Long> left = mergeSort(mergeSort(list.subList(0, middle)));
//		List<Long> right = mergeSort(list.subList(middle, list.size()));
//		return merge(left, right);
//	}
//
//	private List<Long> merge(List<Long> left, List<Long> right) {
//		int i = 0, j = 0;
//		ArrayList<Long> result = new ArrayList<>(left.size() + right.size());
//		while (i < left.size() && j < right.size()) {
//			if (left.get(i) <= right.get(j)) {
//				result.add(left.get(i));
//				i++;
//			} else {
//				result.add(right.get(j));
//				j++;
//			}
//		}
//		result.addAll(left.subList(i, left.size()));
//		result.addAll(right.subList(j, right.size()));
//		return result;
//	}