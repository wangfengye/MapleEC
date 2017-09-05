package com.ascend.wangfeng.latte.util.callback;

import java.util.WeakHashMap;

/**
 * Created by fengye on 2017/9/5.
 * email 1040441325@qq.com
 */

public class CallbackManager {
    private final WeakHashMap<Object,IGlobalCallback> CALLBACKS ;

    public CallbackManager() {
        this.CALLBACKS = new WeakHashMap<>();
    }

    private  static class Holder{
        private static final CallbackManager INSTANCE = new CallbackManager();
    }
    public static CallbackManager getInstance(){
        return Holder.INSTANCE;
    }
    public CallbackManager addCallback(Object tag,IGlobalCallback callback){
        CALLBACKS.put(tag,callback);
        return this;
    }
    public IGlobalCallback getCallback(Object tag){
        return  CALLBACKS.get(tag);
    }

}
