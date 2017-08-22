package com.ascend.wangfeng.mapleec;

import android.app.Application;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.ec.database.DatabaseManager;
import com.ascend.wangfeng.latte.ec.icon.FontEcModule;
import com.ascend.wangfeng.latte.net.interceptors.DebugInterceptor;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://gank.io/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withInterceptor(new DebugInterceptor("user", R.raw.test))
                .configure();
        DatabaseManager.getInstance().init(this);
        initStetho();
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }
}
