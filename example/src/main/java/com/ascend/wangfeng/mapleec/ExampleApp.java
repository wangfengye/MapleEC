package com.ascend.wangfeng.mapleec;

import android.app.Application;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.web.event.TestEvent;
import com.ascend.wangfeng.latte.ec.database.DatabaseManager;
import com.ascend.wangfeng.latte.ec.icon.FontEcModule;
import com.ascend.wangfeng.latte.net.interceptors.AddCookieInterceptor;
import com.ascend.wangfeng.latte.net.interceptors.DebugInterceptor;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;
import com.ascend.wangfeng.latte.util.callback.IGlobalCallback;
import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.jpush.android.api.JPushInterface;
import me.yokeyword.fragmentation.Fragmentation;

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
                .withInterceptor(new DebugInterceptor("index",R.raw.index))
                .withInterceptor(new DebugInterceptor("sortlist",R.raw.sortlist))
                .withInterceptor(new DebugInterceptor("sortcontent",R.raw.sortcontent))
                .withInterceptor(new DebugInterceptor("shopcart",R.raw.shopcart))
                .withInterceptor(new DebugInterceptor("orderlist",R.raw.orderlist))
                .withInterceptor(new DebugInterceptor("address",R.raw.address))
                .withInterceptor(new DebugInterceptor("search",R.raw.search))
                .withInterceptor(new AddCookieInterceptor())
                .withWeChatAppId("1")
                .withWeChatAppSecret("2")
                .withJavascriptInterface("latte")
                .withWebEvent("test", new TestEvent())
                .configure();
        DatabaseManager.getInstance().init(this);
        initStetho();
        //初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter());
        //初始化工具类
        Utils.init(this);
        //极光推送
        initJpush();


    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_PUSH_OPEN, new IGlobalCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_PUSH_CLOSE, new IGlobalCallback() {
                    @Override
                    public void executeCallback(Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())){
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }

    private void initFragmentation() {
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .install();
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
