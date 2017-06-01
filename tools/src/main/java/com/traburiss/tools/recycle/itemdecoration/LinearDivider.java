package com.traburiss.tools.recycle.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.traburiss.tools.recycle.itemdecoration.base.ItemDivider;

/**
 * Created by traburiss on 2017/5/21.
 * describe:
 */

public class LinearDivider extends ItemDivider {


    public LinearDivider(int horizonHeight, int verticalWeight, int colorValue) {
        super(horizonHeight, verticalWeight, colorValue);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        for (int i = 0; i < parent.getChildCount() - 1; i++) {

            View view = parent.getChildAt(i);
            if (manager.getOrientation() == LinearLayoutManager.VERTICAL){

                c.drawRect(verticalWeight,view.getBottom(),view.getRight(),view.getBottom() + horizonHeight,paint);
            }else {

                c.drawRect(view.getRight(),view.getTop(),view.getRight()+verticalWeight,view.getBottom(),paint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        if (manager.getOrientation() == LinearLayoutManager.VERTICAL){

            outRect.left = verticalWeight;
            outRect.right = verticalWeight;

            outRect.bottom = horizonHeight;
            if (isFirstItem(view,parent)){

                outRect.top = horizonHeight;
            }
        }else {

            outRect.top = horizonHeight;
            outRect.bottom = horizonHeight;

            outRect.right = verticalWeight;
            if (isFirstItem(view,parent)){

                outRect.left = verticalWeight;
            }
        }
    }

    private boolean isFirstItem(View view, RecyclerView parent){

        return parent.getChildAdapterPosition(view) == 0;
    }
}
