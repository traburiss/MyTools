package com.traburiss.tools.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * com.traburiss.common.utils
 *
 * @author traburiss
 * @version 1.0
 *          create at 2017/4/28 18:33
 */

public class UnitUtils {

    private UnitUtils(){

        throw new UnsupportedOperationException("don't instantiate this class");
    }
    /**
     * dp转px
     * @param context 上下文
     * @param dpValue dp
     * @return px
     */
    public static int dp2px(Context context, float dpValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     * @param context 上下文
     * @param spValue sp
     * @return px
     */
    public static int sp2px(Context context, float spValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     * @param context 上下文
     * @param pxValue px
     * @return dp
     */
    public static float px2dp(Context context, float pxValue) {

        return (pxValue / (context.getResources().getDisplayMetrics().density));
    }

    /**
     * px转sp
     * @param context 上下文
     * @param pxValue px
     * @return sp
     */
    public static float px2sp(Context context, float pxValue) {

        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
