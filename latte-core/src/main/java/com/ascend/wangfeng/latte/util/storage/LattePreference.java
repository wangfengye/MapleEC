package com.ascend.wangfeng.latte.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.app.Latte;

import java.lang.reflect.Type;

/**
 * Created by fengye on 2017/8/21.
 * email 1040441325@qq.com
 */

public class LattePreference {
    /**
     * 提示:
     * Activity.getPreferences(int mode)生成 Activity名.xml 用于Activity内部存储
     * PreferenceManager.getDefaultSharedPreferences(Context)生成 包名_preferences.xml
     * Context.getSharedPreferences(String name,int mode)生成name.xml
     */

    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Latte.getApplicationContext());
    private static final String APP_PREFERENCES_KEY = "profile";

    public static SharedPreferences getAppPreference() {
        return PREFERENCES;
    }
    public static void setAppProfile(String val){
        getAppPreference().edit().putString(APP_PREFERENCES_KEY,val).apply();
    }
    public static String getAppProfile(){
       return getAppPreference().getString(APP_PREFERENCES_KEY,null);
    }
    public static JSONObject getAppProfileJson(){
        return JSON.parseObject(getAppProfile());
    }
    public static void removeAppProfile(){
        getAppPreference().edit()
                .remove(APP_PREFERENCES_KEY)
                .apply();
    }
    public static void setAppFlag(String key,boolean flag){
        getAppPreference().edit()
                .putBoolean(key,flag)
                .apply();
    }
    public static boolean getAppFlag(String key) {
        return getAppPreference()
                .getBoolean(key, false);
    }
    public static void addCustomAppProfile(String key,String val){
        getAppPreference().edit()
                .putString(key,val)
                .apply();
    }
    public static void removeCustomAppProfile(String key){
        getAppPreference().edit()
                .remove(key)
                .apply();
    }
    public static String getCustomAppProfile(String key) {
        return getAppPreference().getString(key, "");
    }
    public static boolean setJson(String key,Object object){
        String json = JSON.toJSONString(object);
        return getAppPreference().edit()
                .putString(key,json)
                .commit();
    }
    public static Object getJson(String key, Type type){
        String json = getAppPreference().getString(key,"");
        return JSON.parseObject(json,type);
    }
}
