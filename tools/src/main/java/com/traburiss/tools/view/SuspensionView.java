package com.traburiss.tools.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

/**
 * com.traburiss.common.ui
 *
 * @author traburiss
 * @version 1.0
 * create at 2017/4/23 23:10
 */
public abstract class SuspensionView extends Service {

    private ViewGroup root;
    private WindowManager windowManager;
    private LayoutParams params;
    private int layoutID;
    private Intent intent;

    public SuspensionView(int layoutID){

        this.layoutID = layoutID;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.intent = intent;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {

        super.onCreate();
        initReady(intent);
        initRoot();
        initView(root);
        initEven();
        initData(intent);
    }

    private void initRoot() {

        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        params = getParams();
        LayoutInflater inflate = LayoutInflater.from(getApplication());
        root = (LinearLayout) inflate.inflate(layoutID, null);
        root.setOnTouchListener(onTouchListener);
        windowManager.addView(root, params);
    }

    private LayoutParams getParams(){

        LayoutParams layoutParams = new LayoutParams();
        layoutParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags =  LayoutParams.FLAG_NOT_FOCUSABLE|
                LayoutParams.FLAG_NOT_TOUCH_MODAL|
                LayoutParams.FLAG_LAYOUT_IN_SCREEN|
                LayoutParams.FLAG_LAYOUT_INSET_DECOR|
                LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.START| Gravity.TOP;
        layoutParams.x = 0;
        layoutParams.y = 0;

        return layoutParams;
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        private int onTouchStartX, onTouchStartY;
        private int onTouchEndX, onTouchEndY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:

                    onTouchStartX = (int)event.getRawX();
                    onTouchStartY = (int)event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    onTouchEndX = (int) event.getRawX();
                    onTouchEndY = (int) event.getRawY();
                    params.x += onTouchEndX - onTouchStartX;
                    params.y += onTouchEndY - onTouchStartY;
                    onTouchStartX = onTouchEndX;
                    onTouchStartY = onTouchEndY;

                    windowManager.updateViewLayout(root, params);

                    break;
            }
            return false;
        }
    };

    public abstract void initReady(Intent intent);
    public abstract void initView(ViewGroup root);
    public abstract void initEven();
    public abstract void initData(Intent intent);
}
