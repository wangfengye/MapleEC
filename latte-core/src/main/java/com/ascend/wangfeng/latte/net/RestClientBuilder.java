package com.ascend.wangfeng.latte.net;

import android.content.Context;

import com.ascend.wangfeng.latte.net.callback.IError;
import com.ascend.wangfeng.latte.net.callback.IFailure;
import com.ascend.wangfeng.latte.net.callback.IRequest;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.ui.LoaderStyle;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RestClientBuilder {
    private String mUrl;
    private static final Map<String,Object>PARAMS=RestCreator.getParams();
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IError mError;
    private IFailure mFailure;
    private RequestBody mBody;
    private LoaderStyle mLoaderStyle;
    private Context mContext;

    public final RestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RestClientBuilder params(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        mSuccess = success;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        mError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        mFailure = failure;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }
    public final RestClientBuilder loader(LoaderStyle style,Context context){
        this.mContext =context;
        this.mLoaderStyle =style;
        return this;
    }
    public final RestClientBuilder loader(Context context){
        this.mContext =context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mRequest,mSuccess,mError,mFailure,mBody,mLoaderStyle,mContext);
    }
}
