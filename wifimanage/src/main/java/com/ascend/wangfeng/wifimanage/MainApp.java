package com.ascend.wangfeng.wifimanage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.StringRes;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.wifimanage.greendao.DaoMaster;
import com.ascend.wangfeng.wifimanage.greendao.DaoSession;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by fengye on 2018/4/25.
 * email 1040441325@qq.com
 */

public class MainApp extends MultiDexApplication {
    private static Context mContext;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Latte.init(this).withIcon(new FontAwesomeModule()).configure();
        initGreenDao();
        Stetho.initializeWithDefaults(this);


    }
    public static Context getContent(){
        return mContext;
    }
    public static void toast(@StringRes int id){
        Toast.makeText(mContext,id,Toast.LENGTH_SHORT).show();
    }
    private void initGreenDao(){
        // fing.db 自己命名数据库名称
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"fing.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        mDaoSession = master.newSession();
    }
    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
