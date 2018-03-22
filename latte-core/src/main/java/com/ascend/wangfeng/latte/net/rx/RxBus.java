package com.ascend.wangfeng.latte.net.rx;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * Created by fengye on 2018/3/21.
 * email 1040441325@qq.com
 *
 */

public class RxBus {
    private final Relay<Object> mBus;

    public RxBus() {
        mBus = PublishRelay.create().toSerialized();
    }
    public static RxBus getDefault(){
        return Holder.BUS;
    }
    public void post(Object obj){
        mBus.accept(obj);
    }
    public <T> Observable<T> toObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }
    public  Observable<Object> toObservable(){
        return mBus;
    }
    public  boolean hasObservers(){
        return mBus.hasObservers();
    }
    private static class Holder{
        private static final RxBus BUS = new RxBus();
    }
}
