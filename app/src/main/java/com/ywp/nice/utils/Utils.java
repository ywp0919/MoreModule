package com.ywp.nice.utils;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return px
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void setToolbarPadding(Context context , Toolbar mToolbar){
        int paddingTop = mToolbar.getPaddingTop();
        int paddingBottom = mToolbar.getPaddingBottom();
        int paddingLeft = mToolbar.getPaddingLeft();
        int paddingRight = mToolbar.getPaddingRight();
        int statusHeight = Utils.getStatusHeight(context);
        ViewGroup.LayoutParams params = mToolbar.getLayoutParams();
        int height = params.height;
        paddingTop += statusHeight;
        height += statusHeight;
        params.height = height;
        mToolbar.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }
}