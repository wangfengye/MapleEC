package com.ascend.wangfeng.latte.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.ascend.wangfeng.latte.app.Latte;

import java.lang.reflect.Method;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */
@SuppressWarnings("unused")
public class DimenUtil {
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 可用距离: 全屏高度- 状态栏- 虚拟按键
     *
     * @param context activity
     * @return 可用高度
     */
    public static int getScreenHeightCanUse(Context context) {
        return getScreenHeightReal(context) - getStatusHeight() - getNavigationBarHeightIfRoom(context);
    }

    /**
     * 不包括虚拟按键.不包括状态栏 ,全面屏手机,获取的高度不准确,不建议使用
     *
     * @return 返回屏幕高度
     */
    @Deprecated
    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取整个屏幕的真实高度
     *
     * @param context activity
     * @return realHeight
     */
    public static int getScreenHeightReal(Context context) {
        //之前 getRealMetrics方法被隐藏,使用反射调用方法
/*        int dpi = 0;
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);//反射获取被隐藏的方法
            method.invoke(display, displayMetrics);//display,被反射类的实例,displayMetrics:参数
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;*/
        Display display = getDisplay(context);
        if (display == null) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        display.getRealMetrics(dm);
        return dm.heightPixels;
    }

    private static Display getDisplay(Context context) {
        WindowManager wm;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            wm = activity.getWindowManager();
        } else {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (wm != null) {
            return wm.getDefaultDisplay();
        }
        return null;
    }

    public static int dp2px(float dp) {
        final float scale = Latte.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = Latte.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale);
    }

    /**
     * 状态栏高度;
     *
     * @return 虚拟按键高度
     */
    public static int getStatusHeight() {
        int height = 0;
        int resourceId = Latte.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = Latte.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 返回虚拟按键高度
     *
     * @param context activity
     * @return 虚拟按键高度, 若虚拟按键不存在, 隐藏, 则返回0
     */
    public static int getNavigationBarHeightIfRoom(Context context) {
        if (navigationGestureEnabled(context)) {
            return 0;
        }
        return getCurrentNavigationBarHeight(((Activity) context));
    }

    /**
     * 非全面屏下 虚拟键实际高度(隐藏后高度为0)
     *
     * @param activity
     * @return
     */
    private static int getCurrentNavigationBarHeight(Activity activity) {
        if (isNavigationBarShown(activity)) {
            return getNavigationBarHeight(activity);
        } else {
            return 0;
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     *
     * @param activity activity
     * @return 虚拟按键是否打开
     */
    private static boolean isNavigationBarShown(Activity activity) {
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view = activity.findViewById(android.R.id.navigationBarBackground);
        if (view == null) {
            return false;
        }
        int visible = view.getVisibility();
        if (visible == View.GONE || visible == View.INVISIBLE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 非全面屏下 虚拟键高度(无论是否隐藏)
     *
     * @param context activity
     * @return 虚拟按键高度(无论是否隐藏)
     */
    private static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 全面屏（是否开启全面屏开关 0 关闭  1 开启）
     *
     * @param context activity
     * @return 是否是前面屏
     */
    private static boolean navigationGestureEnabled(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), getDeviceInfo(), 0);
        return val != 0;
    }

    /**
     * 获取设备信息（目前支持几大主流的全面屏手机，亲测华为、小米、oppo、魅族、vivo都可以）
     * 锤子无虚拟按键不考虑.
     *
     * @return 设备类型字符串
     */
    private static String getDeviceInfo() {
        String brand = Build.BRAND;
        if (TextUtils.isEmpty(brand)) return "navigationbar_is_min";

        if (brand.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        } else if (brand.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        } else if (brand.equalsIgnoreCase("VIVO")) {
            return "navigation_gesture_on";
        } else if (brand.equalsIgnoreCase("OPPO")) {
            return "navigation_gesture_on";
        } else {
            return "navigationbar_is_min";
        }
    }

}
