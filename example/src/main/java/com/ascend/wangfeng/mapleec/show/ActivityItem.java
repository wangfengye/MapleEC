package com.ascend.wangfeng.mapleec.show;

/**
 * @author maple on 2019/4/19 15:49.
 * @version v1.0
 * @see 1040441325@qq.com
 */
import android.util.Log;

import java.lang.reflect.Field;


public class ActivityItem {
    public static final String TAG ="ActivityItem";
    public String title;
    public Class activity;

    public ActivityItem(Class activity) {
        this.activity = activity;
        try {
            // 反射
            Field field = activity.getField("TITLE");
            title = (String) field.get(activity);
        } catch (Exception e) {
            Log.e(TAG, "ActivityItem:未定义TITLE");
        }
    }}