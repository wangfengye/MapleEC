package com.ascend.wangfeng.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.ascend.wangfeng.latte.app.Latte;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class DimenUtil {
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dp2px(float dp) {
        final float scale = Latte.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = Latte.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale);
    }

}
