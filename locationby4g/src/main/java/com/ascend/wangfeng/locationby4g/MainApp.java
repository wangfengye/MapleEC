package com.ascend.wangfeng.locationby4g;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.ascend.wangfeng.latte.app.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by fengye on 2018/3/20.
 * email 1040441325@qq.com
 */

public class MainApp extends MultiDexApplication {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Latte.init(this).withIcon(new FontAwesomeModule()).configure();
    }
    public static Context getContent(){
        return mContext;
    }
    public static void toast(@StringRes int id){
        Toast.makeText(mContext,id,Toast.LENGTH_SHORT).show();
    }
}
