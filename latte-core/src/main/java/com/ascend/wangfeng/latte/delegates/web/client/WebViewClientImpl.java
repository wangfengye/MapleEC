package com.ascend.wangfeng.latte.delegates.web.client;

import android.graphics.Bitmap;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.delegates.web.WebDelegate;
import com.ascend.wangfeng.latte.delegates.web.route.Router;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.orhanobut.logger.Logger;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class WebViewClientImpl extends WebViewClient {
    private final WebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    //new api
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Logger.d("shouldOverrideUrlLoading:" + url);
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());

    }

    private void syncCookie() {
        final CookieManager manager = CookieManager.getInstance();
        String webHost = Latte.getConfiguration(ConfigType.WEB_HOST);
        if (webHost != null) {
            final String cookieStr = manager.getCookie(webHost);
            if (cookieStr != null && !cookieStr.isEmpty()) {
                LattePreference.addCustomAppProfile("cookie", cookieStr);
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadFinish();
        }
        LatteLoader.stopLoading();
    }
}
