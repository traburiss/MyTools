package com.traburiss.tools.recycle.itemdecoration.base;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by traburiss on 2017/5/21.
 * describe:
 */

public abstract class ItemDivider extends RecyclerView.ItemDecoration{

    protected Paint paint;
    protected final static String TAG = ItemDivider.class.getSimpleName();

    protected int horizonHeight;
    protected int verticalWeight;
    protected int colorValue;

    public ItemDivider(int horizonHeight, int verticalWeight, int colorValue){

        this.horizonHeight = horizonHeight;
        this.verticalWeight = verticalWeight;
        this.colorValue = colorValue;
        paint = new Paint();
        paint.setColor(colorValue);
    }

    @Override
    public abstract void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state);

    @Override
    public abstract void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state);

    @Override
    public abstract void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state);
}
