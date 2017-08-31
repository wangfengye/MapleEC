package com.ascend.wangfeng.latte.net.interceptors;

import com.ascend.wangfeng.latte.util.storage.LattePreference;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fengye on 2017/8/31.
 * email 1040441325@qq.com
 */

public class AddCookieInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder =chain.request().newBuilder();
        Observable.just(LattePreference.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //add cookie
                        builder.addHeader("cookie",s);
                    }
                });
        return chain.proceed(builder.build());
    }
}
