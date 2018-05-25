package com.ascend.wangfeng.wifimanage;

import android.support.annotation.StringRes;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.wifimanage.api.Api;
import com.ascend.wangfeng.wifimanage.api.DemoApi;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class MainApp extends MultiDexApplication {
    private static MainApp mContext;
    private static Api sApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Latte.init(this).withIcon(new FontAwesomeModule()).configure();
        Stetho.initializeWithDefaults(this);
        SDKInitializer.initialize(this);
        //初始化sApi;
        sApi = new DemoApi();
    }
    public static MainApp getContent(){
        return mContext;
    }
    public static void toast(@StringRes int id){
        Toast.makeText(mContext,id,Toast.LENGTH_SHORT).show();
    }

    public static Api getApi() {
        return sApi;
    }
}
