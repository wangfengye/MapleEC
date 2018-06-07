package com.ascend.wangfeng.latte.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by fengye on 2018/6/7.
 * email 1040441325@qq.com
 */

public class NetUtil {
    /**
     * 判断网络可用
     * @param context application.context;
     * @return
     */
    public static boolean isNetAvaiable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager ==null)return false;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo==null || !networkInfo.isAvailable())return false;
        return true;
    }
}
