package com.ascend.wangfeng.latte.net;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.net.converter.scalars.ScalarsConverterFactory;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RestCreator {

    public static final class ParamsHolder{
        public static final WeakHashMap<String,Object>PARAMS =new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT
                .create(RestService.class);
    }
}
