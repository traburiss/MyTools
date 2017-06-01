package com.traburiss.tools.recycle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 这是一个RecycleView的adapter,实现了多种viewType的支持，并且在此基础上实现了一个footer和一个header
 * @param <T> adapter持有的list的数据类型
 */
public abstract class RecycleViewAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder>{

	private List<T> datas;
	private LayoutInflater layoutInflater;
	private DifferentItemSetter setter;
	private OnItemClickListener onItemClickListener;
	private OnItemLongClickListener onItemLongClickListener;
	private HeaderAndFooterSetter hafSetter = new HeaderAndFooterSetter();

	/**
	 * 这是一个RecycleView的adapter,实现了多种viewType的支持，并且在此基础上实现了一个footer和一个header
	 * @param context 一个上下文，主要用来生成LayoutInflater
	 * @param resId 一个layout的id
	 * @param datas 数据源
	 */
	public RecycleViewAdapter(Context context, final int resId, List<T> datas){

		this(context, new DifferentItemSetter() {
			@Override
			public int getViewIdByType(int viewType) {
				return resId;
			}

			@Override
			public int getTypeByPosition(int position) {
				return 0;
			}
		}, datas);
	}

	/**
	 * 这是一个RecycleView的adapter,实现了多种viewType的支持，并且在此基础上实现了一个footer和一个header
	 * @param context 一个上下文，主要用来生成LayoutInflater
	 * @param support 一个多种viewType产生多种视图形态的支持器
	 * @param datas 数据源
	 */
	public RecycleViewAdapter(Context context, DifferentItemSetter support, List<T> datas){

		this.setter = support;
		this.datas = datas;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 设置数据源
	 * @param datas 数据源
	 */
	public void setDatas(List<T> datas) {

		this.datas = datas;
		notifyDataSetChanged();
	}

	/**
	 * 增加数据源
	 * @param datas 数据源
	 */
	public void addDatas(List<T> datas){

		int oldSize = this.datas.size();
		this.datas.addAll(datas);
//		notifyDataSetChanged();
		notifyItemRangeInserted(hafSetter.getHeaderCount() + oldSize, datas.size());
	}

	public void insertData(int position, T t){

		if (isPositionInRange(position)){

			datas.add(position, t);
			notifyOneItem(position, NotifyType.insert);
		}
	}

	public void removeData(int position){

		if (isPositionInRange(position)) {

			this.datas.remove(position);
			notifyOneItem(position, NotifyType.remove);
		}
	}

	public void changeData(int position, T t){

		if (isPositionInRange(position)) {

			this.datas.set(position, t);
			notifyOneItem(position, NotifyType.change);
		}
	}

	private boolean isPositionInRange(int position){

		return position >= 0 && position < datas.size();
	}

	private enum NotifyType {remove, insert, change}

	private void notifyOneItem(int position,NotifyType type){

		int notifyPosition = hafSetter.getHeaderCount() + position;
		int range = getItemCount() - notifyPosition - 1;
		switch (type){

			case remove:
				notifyItemRemoved(notifyPosition);
				break;
			case insert:
				notifyItemInserted(notifyPosition);
				break;
			case change:
				notifyItemChanged(notifyPosition);
				break;
			default:
		}
		if (type != NotifyType.change && notifyPosition < getItemCount()){

			notifyItemRangeChanged(notifyPosition, range);
		}
	}

	public T getData(int position){

		return datas.get(position);
	}

	public int getDataNum(){

		return datas.size();
	}

	@Override
	public int getItemCount() {

		return hafSetter.getHeaderCount() + datas.size() + hafSetter.getFooterCount();
	}
	
	@Override
	public int getItemViewType(int position) {

		if (position < hafSetter.getHeaderCount()) {

			return HeaderAndFooterSetter.HEADER;
		}
		else if (position >= getItemCount() - hafSetter.getFooterCount()) {

			return HeaderAndFooterSetter.FOOTER;
		}
		else {

			return setter.getTypeByPosition(position - hafSetter.getHeaderCount());
		}
	}

	@Override
	public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		switch (viewType){

			case HeaderAndFooterSetter.HEADER:

				return new RecycleViewHolder(hafSetter.getHeaderView());
			case HeaderAndFooterSetter.FOOTER:

				return new RecycleViewHolder(hafSetter.getFooterView());
			default:

				return new RecycleViewHolder(layoutInflater.inflate(setter.getViewIdByType(viewType), parent, false));
		}
	}

	@Override
	public void onBindViewHolder(RecycleViewHolder holder, int position) {

		if (position >= hafSetter.getHeaderCount() && position < getItemCount() - hafSetter.getFooterCount()){

			bindListener(holder, position - hafSetter.getHeaderCount());
			bindDataWitHView(holder, position - hafSetter.getHeaderCount(), getItemViewType(position));
		}
	}

	private void bindListener(RecycleViewHolder holder, final int position){

		holder.getConvertView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (null != onItemClickListener) onItemClickListener.onItemClick(position, RecycleViewAdapter.this);
			}
		});
		holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				return null != onItemLongClickListener && onItemLongClickListener.onItemLongClick(position, RecycleViewAdapter.this);
			}
		});
	}

	public interface OnItemClickListener {

		void onItemClick(int position, RecycleViewAdapter adapter);
	}

	public interface OnItemLongClickListener {

		boolean onItemLongClick(int position, RecycleViewAdapter adapter);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

		this.onItemClickListener = onItemClickListener;
	}

	public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {

		this.onItemLongClickListener = onItemLongClickListener;
	}

	public abstract void bindDataWitHView(RecycleViewHolder holder, int position, int viewType);

	public class HeaderAndFooterSetter {

		public final static int HEADER = -0X1001;
		public final static int FOOTER = -0X1002;

		private View headerView = null;
		private View footerView = null;

		private int getHeaderCount(){

			return null == headerView? 0: 1;
		}

		private void setHeaderView(View headerView) {

			this.headerView = headerView;
		}

		private View getHeaderView() {

			return headerView;
		}

		private int getFooterCount(){

			return null == footerView? 0: 1;
		}

		private void setFooterView(View footerView) {

			this.footerView = footerView;
		}

		private View getFooterView() {

			return footerView;
		}
	}

	public boolean isFooterOrHeader(int position){

		return position < hafSetter.getHeaderCount() || position >= getItemCount() - hafSetter.getFooterCount();
	}

	public int getFooterCount(){

		return hafSetter.getFooterCount();
	}

	public int getHeaderCount(){

		return hafSetter.getHeaderCount();
	}

	public void setHeaderView(View view){

		hafSetter.setHeaderView(view);
	}

	public void setFooterView(View view){

		hafSetter.setFooterView(view);
	}

	/**
	 * 使Header 和 Footer 兼容GridLayoutManager，重复设置gridlayoutManager会失效，
	 * 需要单独为GridLayoutManager设置setSpanSizeLookup事件
	 * @param recyclerView recyclerView
	 */
	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {

		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if (manager instanceof GridLayoutManager) {
			final GridLayoutManager gridManager = ((GridLayoutManager) manager);
			gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {

					return isFooterOrHeader(position) ? gridManager.getSpanCount() : 1;
				}
			});
		}
	}

	/**
	 * 使Header 和 Footer 兼容StaggeredGridLayoutManager
	 * @param holder holder
	 */
	@Override
	public void onViewAttachedToWindow(RecycleViewHolder holder) {

		super.onViewAttachedToWindow(holder);
		ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
		if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams){

			if (isFooterOrHeader(holder.getLayoutPosition())) {
				StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
				p.setFullSpan(true);
			}
		}
	}

	public interface DifferentItemSetter {

        int getViewIdByType(int viewType);
        int getTypeByPosition(int position);
    }
}
