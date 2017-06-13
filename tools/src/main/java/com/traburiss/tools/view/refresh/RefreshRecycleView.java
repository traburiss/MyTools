package com.traburiss.tools.view.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.traburiss.tools.recycle.RecycleViewAdapter;
import com.traburiss.tools.utils.UnitUtils;

/**
 * com.traburiss.common.ui.refresh
 *
 * @author traburiss
 * @version 1.0
 *          create at 2017/4/30 0:22
 */
public class RefreshRecycleView extends SwipeRefreshLayout {

    private RecyclerView mListView;
    private RecycleViewAdapter adapter;
    private RecyclerView.OnScrollListener mOnScrollListener;

    private LoadMoreStatus mLoadMoreStatus = LoadMoreStatus.CLICK_TO_LOAD;
    private OnLoadListener mOnLoadListener;
    private TextView mLoadMoreView;
    private String errorString = "";

    public enum LoadMoreStatus {

        NO_DATA, CLICK_TO_LOAD, LOADING, LOADED_ALL
    }
    private String noData = "暂无数据";
    private String startLoad = "";
    private String clickToLoad = "加载更多";
    private String loading = "正在加载";
    private String loadAll = "加载完毕";

    public interface OnLoadListener {

        void onLoad(boolean isRefresh);
    }

    public RefreshRecycleView(Context context) {

        super(context);
        init(context, null);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attr){

        initRecycleView(context, attr);
    }

    private void initRecycleView(Context context, AttributeSet attrs) {

        mListView = new RecyclerView(context, attrs);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mListView.setLayoutManager(layoutManager);
        addView(mListView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mListView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {

                if (mOnScrollListener != null) {

                    mOnScrollListener.onScrollStateChanged(view, scrollState);
                }
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (isEnd(view) && mLoadMoreStatus == LoadMoreStatus.CLICK_TO_LOAD && !isRefreshing()) {

                        if (mLoadMoreStatus != null) {

                            setLoadMoreStatus(LoadMoreStatus.LOADING);
                            mOnLoadListener.onLoad(false);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (mOnScrollListener != null) {

                    mOnScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }

            private boolean isEnd(RecyclerView recyclerView) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition = 0;
                if(layoutManager instanceof GridLayoutManager){

                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                }else if(layoutManager instanceof LinearLayoutManager){

                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }else if(layoutManager instanceof StaggeredGridLayoutManager) {

                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                }
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int state = recyclerView.getScrollState();
                return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == RecyclerView.SCROLL_STATE_IDLE;
            }
        });

        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (mLoadMoreStatus != LoadMoreStatus.LOADING) {

                    if (mOnLoadListener != null) {

                        setLoadMoreStatus(LoadMoreStatus.LOADING);
                        mOnLoadListener.onLoad(true);
                    }
                } else {

                    RefreshRecycleView.super.setRefreshing(false);
                }
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    @Deprecated
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
    }

    @Override
    @Deprecated
    public void setRefreshing(boolean refreshing) {
    }

    public void setAdapter(RecycleViewAdapter adapter) {

        if (adapter == null) {

            return;
        }
        this.adapter = adapter;
        initFooter();
        mListView.setAdapter(adapter);
        setLoadMoreStatus(LoadMoreStatus.CLICK_TO_LOAD);
    }

    private void initFooter(){

        if (mLoadMoreView == null) {

            mLoadMoreView = new TextView(getContext());
            mLoadMoreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mLoadMoreView.setTextColor(0xff333333);
            mLoadMoreView.setTextSize(14);
            mLoadMoreView.setGravity(Gravity.CENTER);
            mLoadMoreView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mLoadMoreStatus == LoadMoreStatus.CLICK_TO_LOAD
                            && !isRefreshing()) {

                        if (mLoadMoreStatus != null) {

                            setLoadMoreStatus(LoadMoreStatus.LOADING);
                            mOnLoadListener.onLoad(false);
                        }
                    }
                }
            });

            mLoadMoreView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            int px = UnitUtils.dp2px(getContext(), 5);
            mLoadMoreView.setPadding(px, px, px, px);
            adapter.setFooterView(mLoadMoreView);
        }
    }

    private void setLoadMoreStatus(LoadMoreStatus status) {

        mLoadMoreStatus = status;
        if (mLoadMoreView != null) {

            if (mLoadMoreStatus == LoadMoreStatus.LOADED_ALL) {

                mLoadMoreView.setText(loadAll);
            } else if (mLoadMoreStatus == LoadMoreStatus.LOADING) {

                mLoadMoreView.setText(loading);
            } else if (mLoadMoreStatus == LoadMoreStatus.NO_DATA) {

                mLoadMoreView.setText(errorString.isEmpty() ? noData : errorString);
            } else {

                mLoadMoreView.setText(adapter.getDataNum() == 0 ? startLoad : clickToLoad);
            }
        }
    }

    public RecycleViewAdapter getAdapter() {
        return adapter;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration divider) {

        mListView.addItemDecoration(divider);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){

        mListView.setLayoutManager(layoutManager);
    }

    public void setSelection(final int position) {

        post(new Runnable() {
            @Override
            public void run() {

                mListView.smoothScrollToPosition(position);
            }
        });
    }

    public void setHeaderView(View view){

        if (adapter != null){

            adapter.setHeaderView(view);
        }
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {

        mOnScrollListener = listener;
    }

    public void setOnItemClickListener(RecycleViewAdapter.OnItemClickListener listener) {

        adapter.setOnItemClickListener(listener);
    }

    public void setOnLoadListener(OnLoadListener listener) {
        mOnLoadListener = listener;
    }

    public void setNoData(String noData) {
        this.noData = noData;
    }

    public void setStartLoad(String startLoad) {
        this.startLoad = startLoad;
    }

    public void setClickToLoad(String clickToLoad) {
        this.clickToLoad = clickToLoad;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public void setLoadAll(String loadAll) {
        this.loadAll = loadAll;
    }

    public void startRefresh() {

        RefreshRecycleView.super.setRefreshing(true);
        if (mLoadMoreStatus != LoadMoreStatus.LOADING) {

            if (mOnLoadListener != null) {

                setLoadMoreStatus(LoadMoreStatus.LOADING);
                mOnLoadListener.onLoad(true);
            }
        } else {

            RefreshRecycleView.super.setRefreshing(false);
        }
    }

    public void finishLoadWithNoData() {

        finishLoadWithNoData("");
    }

    public void finishLoadWithNoData(int stringId) {

        String errorString = getContext().getString(stringId);
        finishLoadWithNoData(errorString);
    }

    public void finishLoadWithNoData(String errorString) {

        this.errorString = errorString;
        super.setRefreshing(false);
        setLoadMoreStatus(LoadMoreStatus.NO_DATA);
    }

    public void finishLoad(boolean loadAll) {

        this.errorString = "";
        super.setRefreshing(false);
        setLoadMoreStatus(loadAll ?
                (adapter.getDataNum() > 0 ? LoadMoreStatus.LOADED_ALL : LoadMoreStatus.NO_DATA)
                : LoadMoreStatus.CLICK_TO_LOAD);
    }

    public void stopRefresh() {

        super.setRefreshing(false);
        setLoadMoreStatus(LoadMoreStatus.CLICK_TO_LOAD);
    }
}