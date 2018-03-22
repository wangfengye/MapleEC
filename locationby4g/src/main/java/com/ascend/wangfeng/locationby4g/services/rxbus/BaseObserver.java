package com.ascend.wangfeng.locationby4g.services.rxbus;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by fengye on 2018/3/19.
 * email 1040441325@qq.com
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private static final String TAG = BaseObserver.class.getSimpleName();
    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: "+ e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}
