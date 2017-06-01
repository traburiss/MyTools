package com.traburiss.tools.recycle.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.traburiss.tools.recycle.RecycleViewAdapter;
import com.traburiss.tools.recycle.itemdecoration.base.ItemDivider;

/**
 * Created by traburiss on 2017/5/21.
 * describe:
 */

public class StaggeredGridDivider extends ItemDivider {

    public StaggeredGridDivider(int horizonHeight, int verticalWeight, int colorValue) {
        super(horizonHeight, verticalWeight, colorValue);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = manager.getSpanCount();
        for (int i = 0; i < parent.getChildCount(); i++) {

            View view = parent.getChildAt(i);

            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = lp.getSpanIndex();
            int count = lp.isFullSpan() ? manager.getSpanCount() : 1;

            if (manager.getOrientation() == LinearLayoutManager.VERTICAL){

                boolean isStart = isStart(view, parent, spanCount);
                boolean isSide = isSide(view, parent, spanIndex, count, spanCount);

                float right;
                if (i + 1 < parent.getChildCount()){

                    right = parent.getChildAt(i + 1).getLeft();
                }else {

                    right = view.getRight() + verticalWeight;
                }

                if (!isStart){

                    c.drawRect(view.getLeft(),view.getTop() - horizonHeight,view.getRight(),view.getTop(),paint);
                }
                if (!isSide){

                    c.drawRect(view.getRight(),view.getTop(), right, view.getBottom(), paint);
                }
                if (!isStart && !isSide){

                    c.drawRect(view.getRight(),view.getTop() - horizonHeight, right, view.getTop(), paint);
                }
            }else {

                boolean isStart = isStart(view, parent, spanCount);
                boolean isSide = isSide(view, parent, spanIndex, count, spanCount);
                float bottom;
                if (i + 1 < parent.getChildCount()){

                    bottom = parent.getChildAt(i + 1).getTop();
                }else {

                    bottom = view.getBottom() + horizonHeight;
                }
                if (!isStart){

                    c.drawRect(view.getLeft()-verticalWeight,view.getTop(),view.getLeft(),view.getBottom(),paint);
                }
                if (!isSide){

                    c.drawRect(view.getLeft(),view.getBottom(), view.getRight(), bottom, paint);
                }
                if (!isStart && !isSide){

                    c.drawRect(view.getLeft()-verticalWeight,view.getBottom(),view.getLeft(), bottom,paint);
                }
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = manager.getSpanCount();

        int spanIndex = lp.getSpanIndex();
        int count = lp.isFullSpan() ? manager.getSpanCount() : 1;

        if (manager.getOrientation() == GridLayoutManager.VERTICAL){

            if (isStart(view, parent, spanCount)){
                outRect.top = horizonHeight;
            }
            if (isSide(view, parent, spanIndex, count, spanCount)){
                outRect.right = verticalWeight;
            }
            outRect.bottom = horizonHeight;
            outRect.left = verticalWeight;
        }else {

            if (isStart(view, parent, spanCount)){
                outRect.left = verticalWeight;
            }
            if (isSide(view, parent, spanIndex, count, spanCount)){
                outRect.bottom = horizonHeight;
            }
            outRect.right = verticalWeight;
            outRect.top = horizonHeight;
        }
    }

    private boolean isStart(View view, RecyclerView parent, int spanCount) {

        RecycleViewAdapter adapter = (RecycleViewAdapter) parent.getAdapter();
        int position = parent.getChildAdapterPosition(view) - adapter.getHeaderCount();
        return (adapter.getHeaderCount() > 0 && isHeader(view, parent)) ||
                (adapter.getHeaderCount() == 0 && position < spanCount);
    }

    private boolean isSide(View view, RecyclerView parent, int spanIndex, int count, int spanCount) {

        return isHeader(view, parent) ||
                isFooter(view, parent) ||
                spanIndex + count == spanCount;
    }

    private boolean isHeader(View view, RecyclerView parent){

        return parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(view)) == RecycleViewAdapter.HeaderAndFooterSetter.HEADER;
    }

    private boolean isFooter(View view, RecyclerView parent){

        return parent.getAdapter().getItemViewType(parent.getChildAdapterPosition(view)) == RecycleViewAdapter.HeaderAndFooterSetter.FOOTER;
    }
}
