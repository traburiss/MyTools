package com.traburiss.tools.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * com.traburiss.common.utils
 *
 * @author traburiss
 * @version 1.0
 *          create at 2017/4/28 18:53
 */
@SuppressWarnings("unused")
public class ToastUtils {

    private static Toast toast;

    private ToastUtils() {
    }

    /**
     * 显示短toast
     *
     * @param context 一个context 用以创建toast
     * @param msg     需要显示的信息
     */
    public static void showShort(Context context, String msg) {

        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短toast
     *
     * @param context 一个context 用以创建toast
     * @param resId   需要显示的信息的资源id
     */
    public static void showShort(Context context, int resId) {

        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长toast
     *
     * @param context 一个context 用以创建toast
     * @param msg     需要显示的信息
     */
    public static void showLong(Context context, String msg) {

        showToast(context, msg, Toast.LENGTH_LONG);
    }


    /**
     * 显示长toast
     *
     * @param context 一个context 用以创建toast
     * @param resId   需要显示的信息的资源id
     */
    public static void showLong(Context context, int resId) {

        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示字符串创建的toast
     *
     * @param context  一个用以创建toast的context
     * @param msg      需要显示的信息
     * @param Duration 持续时间
     */
    private static void showToast(Context context, String msg, int Duration) {

        if (toast == null) {
            toast = Toast.makeText(context, msg, Duration);
        } else {
            toast.setText(msg);
            toast.setDuration(Duration);
        }
        toast.show();
    }


    /**
     * 显示字符串创建的toast
     *
     * @param context  一个用以创建toast的context
     * @param resId    需要显示的信息的资源文件id
     * @param Duration 持续时间
     */
    private static void showToast(Context context, int resId, int Duration) {

        if (toast == null) {
            toast = Toast.makeText(context, resId, Duration);
        } else {
            toast.setText(resId);
            toast.setDuration(Duration);
        }
        toast.show();
    }
}
