package com.traburiss.tools.recycle;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;

public class RecycleViewHolder extends ViewHolder {

	private SparseArray<View> mViews;
	private View mConvertView;

	public RecycleViewHolder(View itemView) {

		super(itemView);
		mConvertView = itemView;
		mViews = new SparseArray<>();
	}
	
	public View getConvertView() {

		return mConvertView;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
}
