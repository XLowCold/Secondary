package com.example.secondary.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.secondary.R;
import com.example.secondary.ui.activity.NerveTimeActivity;
import com.example.secondary.ui.activity.FibonacciActivity;
import com.example.secondary.ui.adapter.RVAdapter;
import com.example.secondary.ui.activity.WeatherForecastActivity;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * 主页工具fragment
 */
public class HomePageToolFragment extends Fragment {
	private static final String TAG = "HomePageToolFragment";
	private RecyclerView mRvGameList;
	private RVAdapter mRvAdapter;
	private static final String[] mToolNames = {"反应测速", "计算斐波那契数", "天气预报"};

	public HomePageToolFragment() {
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_homepage_tool, container, false);
		initView(view);

		return view;
	}

	private void initView(View v) {
		mRvGameList = (RecyclerView) v.findViewById(R.id.rvGameList);
		mRvGameList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
		initAdapter();
	}

	private void initAdapter() {
		Class[] classes = {
				NerveTimeActivity.class,
				FibonacciActivity.class,
				WeatherForecastActivity.class
		};
		//适配管理器
		RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
		mRvGameList.setLayoutManager(layoutManager);
		//适配器
		mRvAdapter = new RVAdapter<String>(getActivity(), Arrays.asList(mToolNames)
				, R.layout.rv_item_homepage_tool_rvitem) {
			@Override
			public void bind(VH vh, String d, int pos) {
				vh.setTxt(R.id.tvDescript, d);
				ImageView imgIcon = vh.itemView.findViewById(R.id.imgIcon);
				//点击事件
				vh.itemView.setOnClickListener(v -> {
					Log.d(TAG, "bind: 打开" + mToolNames[pos]);
					Intent intent = new Intent();
					intent.setClass(getContext(), classes[pos]);
					getActivity().startActivity(intent);
				});
				//长按
				vh.itemView.setOnLongClickListener(v -> {
					AlertDialog builder = new AlertDialog.Builder(getContext())
							.setMessage(((TextView) vh.fbi(R.id.tvDescript)).getText().toString())
							.show();
					return false;
				});
			}
		};
		mRvGameList.setAdapter(mRvAdapter);
	}
}
