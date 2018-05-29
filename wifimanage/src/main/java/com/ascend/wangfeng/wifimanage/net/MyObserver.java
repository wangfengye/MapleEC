package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fengye on 2018/5/28.
 * email 1040441325@qq.com
 */

public abstract class MyObserver<T> implements Observer<T> {

    public static final String TAG = MyObserver.class.getSimpleName();

    @Override
    public void onSubscribe(Disposable d) {
       // d.dispose(); //解除订阅;
    }

    @Override
    public void onError(Throwable e) {
        if (MainApp.mDemo){
            MainApp.toast(R.string.demo_hint);
        }else {
            MainApp.toast(R.string.error);
        }

    }

    @Override
    public void onComplete() {

    }
}
