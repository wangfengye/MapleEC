package com.ascend.wangfeng.locationby4g;

import android.app.Application;

import com.ascend.wangfeng.latte.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 */

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this).withIcon(new FontAwesomeModule()).configure();
    }
}
