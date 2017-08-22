package com.ascend.wangfeng.latte.net;

import android.content.Context;

import com.ascend.wangfeng.latte.net.callback.IError;
import com.ascend.wangfeng.latte.net.callback.IFailure;
import com.ascend.wangfeng.latte.net.callback.IRequest;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RestClientBuilder {
    private String mUrl;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mRequest;
    private String mDownloadUrl;
    private String mExtension;
    private String mName;
    private ISuccess mSuccess;
    private IError mError;
    private IFailure mFailure;
    private RequestBody mBody;
    private LoaderStyle mLoaderStyle;
    private File mFile;
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

    public final RestClientBuilder downloadUrl(String url) {
        mDownloadUrl = url;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        mName = name;
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

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder loader(LoaderStyle style, Context context) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mRequest,
                mDownloadUrl, mExtension, mName,
                mSuccess, mError, mFailure,
                mBody, mFile, mLoaderStyle,
                mContext);
    }
}
