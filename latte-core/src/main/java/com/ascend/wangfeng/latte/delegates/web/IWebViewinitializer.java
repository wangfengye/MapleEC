package com.ascend.wangfeng.latte.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public interface IWebViewinitializer {
    WebView initWebView(WebView webView);
    WebViewClient initWebViewClient();
    WebChromeClient initWebChromeClient();
}
