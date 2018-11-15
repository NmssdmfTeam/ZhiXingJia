package com.nmssdmf.commonlib.util;

import android.app.Activity;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.nmssdmf.commonlib.R;

/**
 * Created by ${nmssdmf} on 2018/7/4 0004.
 */

public class WindowUtil {
    /**
     * 设置一体化,设置状态栏透明
     */
    public static boolean setWindowStatusBarTransParent(Activity activity) {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            return true;
        }
        return false;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarView(Activity activity, int color) {
        Window window = activity.getWindow();
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());
        int statusBarHeight = getStatusBarHeight(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        decorViewGroup.addView(statusBarView);
    }

    /**
     * 设置状态栏的颜色
     * @param activity
     * @param color
     */
    public static void setStatusbarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //判断是否能将状态栏的字体颜色改为黑色
            if (BarTextColorUtils.StatusBarLightMode(activity)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = activity.getWindow();
                    // Translucent status bar
                    window.addFlags(
                            WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(activity.getResources().getColor(color));
                } else {
                    setTranslucentSystemBarfitsWindows(activity, color);
                }
            } else {
                //若不能修改字体为黑色，设置状态栏一体化半透明黑色的背景。
                setTranslucentSystemBarfitsWindows(activity, R.color.black_statusbar);
            }
        }
    }

    public static void setTranslucentSystemBarfitsWindows(Activity activity, int color) {
        if (setWindowStatusBarTransParent(activity)) {
            setStatusBarView(activity, activity.getResources().getColor(color));
            fitsSystemWindows(activity);
        }
    }

    /**
     * 设置布局不占用状态栏高度
     */
    public static void fitsSystemWindows(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(true);
        }
    }

}