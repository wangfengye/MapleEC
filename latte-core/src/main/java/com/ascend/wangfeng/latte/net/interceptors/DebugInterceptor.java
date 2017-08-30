package com.ascend.wangfeng.latte.net.interceptors;

import android.support.annotation.RawRes;

import com.ascend.wangfeng.latte.util.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by fengye on 2017/8/18.
 * email 1040441325@qq.com
 */

public class DebugInterceptor extends BaseInterceptor{

    private static final long DELAY_TIME = 500;//ms
    private final String DEBUG_URL;
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int debugRawId) {
        this.DEBUG_URL = debugUrl;
        this.DEBUG_RAW_ID = debugRawId;
    }
    private Response getResponse(Chain chain,String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("appliction/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
    private Response debugResponse(Chain chain,@RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        try {

            Thread.sleep(DELAY_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getResponse(chain,json);
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url =chain.request().url().toString();
        if (url.contains(DEBUG_URL)){
            return debugResponse(chain,DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}
