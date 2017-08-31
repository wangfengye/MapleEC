package com.ascend.wangfeng.latte.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ascend.wangfeng.latte.delegates.web.chromeclient.WebChromeClientImpl;
import com.ascend.wangfeng.latte.delegates.web.client.WebViewClientImpl;
import com.ascend.wangfeng.latte.delegates.web.route.Routekeys;
import com.ascend.wangfeng.latte.delegates.web.route.Router;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class WebDelegateImpl extends WebDelegate{
    public static WebDelegateImpl create(String url){
        final Bundle args =new Bundle();
        args.putString(Routekeys.URL.name(),url);
       final WebDelegateImpl delegate =new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }
    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        if (getUrl()!=null){
            //use native function jump
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    @Override
    public IWebViewinitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}
