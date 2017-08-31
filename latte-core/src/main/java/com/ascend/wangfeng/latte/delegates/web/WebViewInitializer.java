package com.ascend.wangfeng.latte.delegates.web;

import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class WebViewInitializer {
    public WebView createWebView(WebView webView){
        webView.setWebContentsDebuggingEnabled(true);
        //cookie
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
        cookieManager.setAcceptThirdPartyCookies(webView,true);}
        cookieManager.setAcceptFileSchemeCookies(true);

        //do not allow scroll on horizon
        webView.setHorizontalScrollBarEnabled(false);
        //do not allow scroll on verticl
        webView.setVerticalScrollBarEnabled(false);
        //allow the screenshot
        webView.setDrawingCacheEnabled(true);
        // do not allow longclick
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        //init web settings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua =settings.getUserAgentString();
        String name = Latte.getConfiguration(ConfigType.JAVASCRIPT_INTERFACE);
        settings.setUserAgentString(ua+name);
        //hide zoom widget
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //do not allow zoom
        settings.setSupportZoom(false);
        //file allow
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        //cache
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
