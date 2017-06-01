package com.traburiss.tools.view.headerlayout;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 自定义的顶部栏
 *
 * @author traburiss  create at 2017/4/26 0:38
 * @version 1.0
 */
public class TitleBar extends RelativeLayout {

    private LinearLayout leftContainer;
    private LinearLayout centerContainer;
    private LinearLayout rightContainer;
    private TextView titleText;

    private static final int WHITE = 0XFFFFFFFF;
    private static final int TRANSPARENT = 0X00000000;

    public TitleBar(Context context) {
        super(context);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        initViews();
    }

    private void initViews() {

        initLeftContainer();
        initRightContainer();
        initMiddleContainer();
        initTitle();
    }

    private void initLeftContainer() {

        leftContainer = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        leftContainer.setLayoutParams(params);
        leftContainer.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        leftContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(leftContainer);
    }

    private void initRightContainer(){

        rightContainer = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightContainer.setLayoutParams(params);
        rightContainer.setGravity(Gravity.CENTER_VERTICAL| Gravity.END);
        rightContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(rightContainer);
    }

    private void initMiddleContainer(){

        centerContainer = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        centerContainer.setLayoutParams(params);
        centerContainer.setGravity(Gravity.CENTER);
        centerContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(centerContainer);
    }

    private void initTitle(){

        titleText = new TextView(getContext());
        titleText.setTextSize(21);
        titleText.setTextColor(WHITE);
        titleText.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        titleText.setLayoutParams(params);
        titleText.setSingleLine();
        centerContainer.addView(titleText);
    }

    /**
     * 用于HeadLayoutSetter调用的初始化工具
     *
     * @param headLayoutSetter 设置好的Setter
     */
    void init(TitleBarSetter headLayoutSetter) {

        initTitle(headLayoutSetter.titleText, headLayoutSetter.titleFontSize, headLayoutSetter.titleFontColor);
        addViews(headLayoutSetter.addViewsInfo);
    }

    /**
     * 初始化标题
     *
     * @param title    标题文本
     * @param fontSize 标题颜色
     * @param color    标题文字
     */
    private void initTitle(String title, float fontSize, int color) {

        leftContainer.removeAllViews();
        rightContainer.removeAllViews();

        titleText.setTextSize(fontSize);
        titleText.setTextColor(color);

        if (null != title) {
            titleText.setText(title);
        } else {
            titleText.setVisibility(View.GONE);
        }
    }

    /**
     * 添加所有Views
     *
     * @param addViewsInfo 存放view数据的list
     */
    private void addViews(ArrayList<HashMap<String, Object>> addViewsInfo) {

        for (int i = 0; i < addViewsInfo.size(); i++) {

            HashMap<String, Object> addViewInfo = addViewsInfo.get(i);
            TitleBarSetter.ViewType type = (TitleBarSetter.ViewType) addViewInfo.get(TitleBarSetter.TYPE);
            switch (type) {
                case TEXT_VIEW:
                    addText(addViewInfo);
                    break;
                case BUTTON:
                    addButton(addViewInfo);
                    break;
                case IMAGE_BUTTON:
                    addImageButton(addViewInfo);
                    break;
                case VIEW:
                    addView(addViewInfo);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 添加文本框
     *
     * @param addViewInfo 文本框数据
     */
    private void addText(HashMap<String, Object> addViewInfo) {

        TitleBarSetter.ViewLocation part = (TitleBarSetter.ViewLocation) addViewInfo.get(TitleBarSetter.PART);
        String text = addViewInfo.get(TitleBarSetter.TEXT).toString();

        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(TRANSPARENT);
        textView.setClickable(false);
        textView.setFocusable(false);
        textView.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textView.setTextSize(21);
        textView.setTextColor(WHITE);

        switch (part) {
            case LEFT:
                leftContainer.addView(textView);
                break;
            case RIGHT:
                rightContainer.addView(textView);
                break;
            case CENTER:
                centerContainer.addView(textView);
            default:
                break;
        }

        if (addViewInfo.containsKey(TitleBarSetter.FONT_SIZE)) {

            textView.setTextSize((float) addViewInfo.get(TitleBarSetter.FONT_SIZE));
        }
        if (addViewInfo.containsKey(TitleBarSetter.COLOR)) {

            textView.setTextColor((int) addViewInfo.get(TitleBarSetter.COLOR));
        }

        if (null != text) {
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 添加按钮
     *
     * @param addViewInfo 按钮数据
     */
    private void addButton(HashMap<String, Object> addViewInfo) {

        TitleBarSetter.ViewLocation part = (TitleBarSetter.ViewLocation) addViewInfo.get(TitleBarSetter.PART);
        String text = addViewInfo.get(TitleBarSetter.TEXT).toString();

        Button button = new Button(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        button.setLayoutParams(params);
        button.setBackgroundColor(TRANSPARENT);
        button.setTextColor(WHITE);

        switch (part) {
            case LEFT:
                leftContainer.setVisibility(View.VISIBLE);
                leftContainer.addView(button);
                break;
            case RIGHT:
                rightContainer.setVisibility(View.VISIBLE);
                rightContainer.addView(button);
                break;
            case CENTER:
                centerContainer.addView(button);
            default:
                break;
        }

        if (addViewInfo.containsKey(TitleBarSetter.BACK_ID)) {

            button.setBackgroundResource((int) addViewInfo.get(TitleBarSetter.BACK_ID));
        }
        if (addViewInfo.containsKey(TitleBarSetter.FONT_SIZE)) {

            button.setTextSize((float) addViewInfo.get(TitleBarSetter.FONT_SIZE));
        }
        if (addViewInfo.containsKey(TitleBarSetter.COLOR)) {

            button.setTextColor((int) addViewInfo.get(TitleBarSetter.COLOR));
        }
        if (addViewInfo.containsKey(TitleBarSetter.LISTENER)) {

            button.setOnClickListener((OnClickListener) addViewInfo.get(TitleBarSetter.LISTENER));
        }

        if (null != text) {
            button.setText(text);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    /**
     * 添加imageButton
     *
     * @param addViewInfo imageButton数据
     */
    private void addImageButton(HashMap<String, Object> addViewInfo) {

        TitleBarSetter.ViewLocation part = (TitleBarSetter.ViewLocation) addViewInfo.get(TitleBarSetter.PART);

        ImageButton imageButton = new ImageButton(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        imageButton.setLayoutParams(params);
        imageButton.setBackgroundColor(TRANSPARENT);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        switch (part) {
            case LEFT:
                leftContainer.setVisibility(View.VISIBLE);
                leftContainer.addView(imageButton);
                break;
            case RIGHT:
                rightContainer.setVisibility(View.VISIBLE);
                rightContainer.addView(imageButton);
                break;
            case CENTER:
                centerContainer.addView(imageButton);
            default:
                break;
        }

        if (addViewInfo.containsKey(TitleBarSetter.BACK_ID)) {

            imageButton.setBackgroundResource((int) addViewInfo.get(TitleBarSetter.BACK_ID));
        }
        if (addViewInfo.containsKey(TitleBarSetter.IMAGE_ID)) {

            imageButton.setImageResource((int) addViewInfo.get(TitleBarSetter.IMAGE_ID));
        }
        if (addViewInfo.containsKey(TitleBarSetter.LISTENER)) {

            OnClickListener onClickListener = (OnClickListener) addViewInfo.get(TitleBarSetter.LISTENER);
            imageButton.setOnClickListener(onClickListener);
        }
    }

    /**
     * 添加自定义view
     *
     * @param addViewInfo view数据
     */
    private void addView(HashMap<String, Object> addViewInfo) {

        TitleBarSetter.ViewLocation part = (TitleBarSetter.ViewLocation) addViewInfo.get(TitleBarSetter.PART);
        View view = (View) addViewInfo.get(TitleBarSetter.VIEW);
        switch (part) {
            case LEFT:
                leftContainer.setVisibility(View.VISIBLE);
                leftContainer.addView(view);
                break;
            case RIGHT:
                rightContainer.setVisibility(View.VISIBLE);
                rightContainer.addView(view);
                break;
            case CENTER:
                centerContainer.addView(view);
            default:
                break;
        }
    }
}
