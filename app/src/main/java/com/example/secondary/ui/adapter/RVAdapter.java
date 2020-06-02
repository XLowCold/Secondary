package com.example.secondary.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// 列表适配器
public abstract class RVAdapter<T> extends RecyclerView.Adapter<RVAdapter.VH> {
	private static final String TAG = RVAdapter.class.getSimpleName();
	private Context context;
	private List<T> mList;//数据
	private int mLay;//布局

	public RVAdapter(Context context, List<T> tList, int lay) {
		this.context = context;
		this.mList = tList;
		this.mLay = lay;
	}

	//刷新数据
	public RVAdapter setList(List<T> list) {
		mList = list;
		return this;
	}

	@NonNull
	@Override
	public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(mLay, parent, false);
		VH vh = new VH(view);
		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull RVAdapter.VH vh, int position) {
		bind(vh, mList.get(position), position);
	}

	//抽象绑定方法
	public abstract void bind(VH vh, T d, int pos);

	//展现的item个数
	@Override
	public int getItemCount() {
		return mList.size();
	}

	//VH类
	public static class VH extends RecyclerView.ViewHolder {
		private static int num = 0;

		public VH(@NonNull View view) {
			super(view);

		}

		//设置Text属性值
		public View setTxt(int id, Object s) {
			TextView textView = this.itemView.findViewById(id);
			textView.setText(s + "");
			return textView;
		}

		//找到控件
		public View fbi(int id) {
			View view = itemView.findViewById(id);
			return view;
		}
	}
}
