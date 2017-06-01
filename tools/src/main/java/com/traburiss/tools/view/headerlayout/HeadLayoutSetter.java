package com.traburiss.tools.view.headerlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 自定义的顶部栏的设置器
 *
 * @author traburiss  create at 2017/4/26 0:38
 * @version 1.0
 */
@SuppressWarnings("unused")
public class HeadLayoutSetter {

    final static String TYPE = "type";
    final static String PART = "part";
    final static String TEXT = "text";
    final static String COLOR = "color";
    final static String FONT_SIZE = "font_size";
    final static String BACK_ID = "back_id";
    final static String IMAGE_ID = "image_id";
    final static String LISTENER = "listener";
    final static String VIEW = "view";

    enum ViewType{
        TEXT_VIEW,
        BUTTON,
        IMAGE_BUTTON,
        VIEW
    }

    public enum ViewLocation{
        LEFT,
        CENTER,
        RIGHT
    }


    private HeaderLayout headerLayout;

    String titleText;
    float titleFontSize = 23;
    int titleFontColor = 0xFFFFFFFF;
    ArrayList<HashMap<String, Object>> addViewsInfo;

    public HeadLayoutSetter(HeaderLayout headerLayout) {

        this.headerLayout = headerLayout;
        addViewsInfo = new ArrayList<>();
    }

    /**
     * 设置标题
     *
     * @param title 标题文本
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter setTitle(String title) {

        this.titleText = title;
        return this;
    }

    /**
     * 设置标题
     *
     * @param title          标题文本
     * @param titleFontSize  标题字体大小
     * @param titleFontColor 标题字体颜色
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter setTitle(String title, float titleFontSize, int titleFontColor) {

        this.titleFontSize = titleFontSize;
        this.titleFontColor = titleFontColor;
        return setTitle(title);
    }


    /**
     * 添加文本 添加顺序会影响界面上的显示顺序
     *
     * @param text 文本内容
     * @param part 文本位置，包括PART_LEFT 以及 PART_RIGHT
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addText(String text, ViewLocation part) {

        HashMap<String, Object> textInfo = new HashMap<>();
        textInfo.put(TYPE, ViewType.TEXT_VIEW);
        textInfo.put(PART, part);
        textInfo.put(TEXT, text);
        addViewsInfo.add(textInfo);
        return this;
    }

    /**
     * 添加文本 添加顺序会影响界面上的显示顺序
     *
     * @param text           文本内容
     * @param titleFontSize  文本字体大小
     * @param titleFontColor 文本字体颜色
     * @param part           文本位置，包括PART_LEFT(放到标题左边) 以及 PART_RIGHT（放到标题右边）
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addText(String text, float titleFontSize, int titleFontColor, ViewLocation part) {

        HashMap<String, Object> textInfo = new HashMap<>();
        textInfo.put(TYPE, ViewType.TEXT_VIEW);
        textInfo.put(PART, part);
        textInfo.put(TEXT, text);
        textInfo.put(COLOR, titleFontColor);
        textInfo.put(FONT_SIZE, titleFontSize);
        addViewsInfo.add(textInfo);
        return this;
    }

    /**
     * 添加按钮 添加顺序会影响界面上的显示顺序
     *
     * @param text            按钮文本内容
     * @param onClickListener 按钮点击事件监听器
     * @param part            按钮位置，包括PART_LEFT(放到标题左边) 以及 PART_RIGHT（放到标题右边）
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addButton(String text, ViewLocation part, View.OnClickListener onClickListener) {

        HashMap<String, Object> buttonInfo = new HashMap<>();
        buttonInfo.put(TYPE, ViewType.BUTTON);
        buttonInfo.put(PART, part);
        buttonInfo.put(TEXT, text);
        buttonInfo.put(LISTENER, onClickListener);
        addViewsInfo.add(buttonInfo);
        return this;
    }

    /**
     * 添加按钮 添加顺序会影响界面上的显示顺序
     *
     * @param text            按钮文本内容
     * @param backId          按钮背景资源id
     * @param fontSize        按钮文本字体大小
     * @param fontColor       按钮文本字体颜色
     * @param onClickListener 按钮点击事件监听器
     * @param part            按钮位置，包括PART_LEFT(放到标题左边) 以及 PART_RIGHT（放到标题右边）
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addButton(String text, int backId, float fontSize, int fontColor, ViewLocation part, View.OnClickListener onClickListener) {

        HashMap<String, Object> buttonInfo = new HashMap<>();
        buttonInfo.put(TYPE, ViewType.BUTTON);
        buttonInfo.put(PART, part);
        buttonInfo.put(TEXT, text);
        buttonInfo.put(BACK_ID, backId);
        buttonInfo.put(FONT_SIZE, fontSize);
        buttonInfo.put(COLOR, fontColor);
        buttonInfo.put(LISTENER, onClickListener);
        addViewsInfo.add(buttonInfo);
        return this;
    }

    /**
     * 添加imageButton 添加顺序会影响界面上的显示顺序
     *
     * @param backId          按钮背景资源id
     * @param imageId         按钮图像资源id
     * @param onClickListener 按钮点击事件监听器
     * @param part            按钮位置，包括PART_LEFT(放到标题左边) 以及 PART_RIGHT（放到标题右边）
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addImageButton(int backId, int imageId, ViewLocation part, View.OnClickListener onClickListener) {

        HashMap<String, Object> imageButtonInfo = new HashMap<>();
        imageButtonInfo.put(TYPE, ViewType.IMAGE_BUTTON);
        imageButtonInfo.put(PART, part);
        imageButtonInfo.put(IMAGE_ID, imageId);
        imageButtonInfo.put(BACK_ID, backId);
        imageButtonInfo.put(LISTENER, onClickListener);
        addViewsInfo.add(imageButtonInfo);
        return this;
    }

    /**
     * 添加自定义的view 添加顺序会影响界面上的显示顺序
     *
     * @param view 需要添加进的view
     * @param part 按钮位置，包括PART_LEFT(放到标题左边) 以及 PART_RIGHT（放到标题右边）
     * @return 对象本身，用于链式调用
     */
    public HeadLayoutSetter addView(View view, ViewLocation part) {

        HashMap<String, Object> viewInfo = new HashMap<>();
        viewInfo.put(TYPE, ViewType.VIEW);
        viewInfo.put(PART, part);
        viewInfo.put(VIEW, view);
        addViewsInfo.add(viewInfo);
        return this;
    }

    /**
     * 添加控件完成后后调用设置控件
     */
    public void Set() {

        headerLayout.init(this);
    }
}
