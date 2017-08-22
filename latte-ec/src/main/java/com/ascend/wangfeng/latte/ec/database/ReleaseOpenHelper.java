package com.ascend.wangfeng.latte.ec.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class ReleaseOpenHelper extends DaoMaster.OpenHelper{

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
}
