package com.ascend.wangfeng.latte.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by fengye on 2017/8/18.
 * email 1040441325@qq.com
 */

public abstract class BaseInterceptor implements Interceptor{

    protected LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl url =chain.request().url();
        int size =url.querySize();
        final LinkedHashMap<String,String >params=new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }
    protected  String getUrlParameters(Chain chain,String key){
        final HttpUrl url =chain.request().url();
        return url.queryParameter(key);
    }

    protected LinkedHashMap<String,String> getBodyparamters(Chain chain){
        final FormBody formBody= (FormBody) chain.request().body();
        final LinkedHashMap<String,String >params=new LinkedHashMap<>();
        int size =formBody.size();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }
    protected String getBodyparamters(Chain chain,String key){
        return getBodyparamters(chain).get(key);
    }
}
