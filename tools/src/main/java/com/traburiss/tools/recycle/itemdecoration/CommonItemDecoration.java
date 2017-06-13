package com.traburiss.tools.recycle.itemdecoration;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.traburiss.tools.recycle.itemdecoration.base.ItemDivider;
import com.traburiss.tools.utils.UnitUtils;

/**
 * Created by traburiss on 2017/5/20.
 * describe:通用的边线，目前水平滑动存在bug
 */

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

    private final static String TAG = CommonItemDecoration.class.getSimpleName();

    private int horizonHeight;
    private int verticalWeight;
    private int colorValue;

    private LinearDivider linearDivider;
    private GridDivider gridDivider;
    private StaggeredGridDivider staggeredGridDivider;

    public CommonItemDecoration(Context context){

        this(context,2);
    }

    public CommonItemDecoration(Context context, float dpSize){

        this(context, dpSize, android.R.color.transparent);
    }

    public CommonItemDecoration(Context context, float dpSize, int colorId){

        this(context, dpSize, dpSize, colorId);
    }

    public CommonItemDecoration(Context context,float horizonHeightDp, float verticalWeightDp, int colorId){

        this(context,
                UnitUtils.dp2px(context, horizonHeightDp),
                UnitUtils.dp2px(context, verticalWeightDp),
                colorId);
    }

    public CommonItemDecoration(Context context, int PxSize){

        this(context, PxSize, android.R.color.transparent);
    }

    public CommonItemDecoration(Context context, int PxSize, int colorId){

        this(context, PxSize, PxSize, colorId);
    }

    public CommonItemDecoration(Context context,int horizonHeightPx, int verticalWeightPx, int colorId){

        this.horizonHeight = horizonHeightPx;
        this.verticalWeight = verticalWeightPx;
        this.colorValue = ContextCompat.getColor(context,colorId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        ItemDivider itemDivider = getItemDivider(parent);
        itemDivider.onDraw(c,parent,state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        ItemDivider itemDivider = getItemDivider(parent);
        itemDivider.onDrawOver(c,parent,state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        ItemDivider itemDivider = getItemDivider(parent);
        itemDivider.getItemOffsets(outRect,view,parent,state);
    }

    private ItemDivider getItemDivider(RecyclerView parent){

        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager){

            if (gridDivider == null) gridDivider = new GridDivider(horizonHeight, verticalWeight, colorValue);
            return gridDivider;
        }else if (manager instanceof LinearLayoutManager){

            if (linearDivider == null) linearDivider = new LinearDivider(horizonHeight, verticalWeight, colorValue);
            return linearDivider;
        }else if (manager instanceof StaggeredGridLayoutManager){

            if (staggeredGridDivider == null) staggeredGridDivider = new StaggeredGridDivider(horizonHeight, verticalWeight, colorValue);
            return staggeredGridDivider;
        }
        return linearDivider;
    }
}
