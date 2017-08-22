package com.ascend.wangfeng.latte.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class DatabaseManager {
    private DaoSession mDaoSession = null;
    private UserDao mDao =null;

    private DatabaseManager() {
    }
    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }
    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }
    private static final class Holder{
        private static final DatabaseManager INSTANCE =new DatabaseManager();
    }

    public void initDao(Context context){
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context,"fast_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDao = mDaoSession.getUserDao();
    }
    public final UserDao getDao(){
        return mDao;
    }

}
