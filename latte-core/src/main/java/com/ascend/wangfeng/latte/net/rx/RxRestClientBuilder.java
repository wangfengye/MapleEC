package com.ascend.wangfeng.latte.net.rx;

import android.content.Context;

import com.ascend.wangfeng.latte.net.RestCreator;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RxRestClientBuilder {
    private String mUrl;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private Context mContext;
    private int mType = 0;

    public final RxRestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }


    public final RxRestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RxRestClientBuilder loader(LoaderStyle style, Context context) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRestClientBuilder type(int type) {
        this.mType = type;
        return this;
    }

    public final RxRestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClient build() {
        if (mType == 0) {
            mType = RxRestClient.TYPE_DEFAULT;
        }
        return new RxRestClient(mUrl, PARAMS,
                mBody, mFile, mLoaderStyle,
                mContext, mType);
    }
}
