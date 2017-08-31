package com.ascend.wangfeng.latte.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.delegates.web.WebDelegate;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public abstract class Event implements IEvent{
    private Context mContext =null;
    private String mAction= null;
    private WebDelegate mDelegate =null;
    private String mUrl =null;
    private WebView mWebView;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate delegate) {
        mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public WebView getWebView() {
        return mDelegate.getWebView();
    }

}
