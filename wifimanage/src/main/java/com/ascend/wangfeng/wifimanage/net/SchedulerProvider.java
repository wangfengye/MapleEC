package com.ascend.wangfeng.wifimanage.net;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/31.
 * email 1040441325@qq.com
 */

public class SchedulerProvider {
    public static  <T> ObservableTransformer<T, T> applyHttp(){
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
