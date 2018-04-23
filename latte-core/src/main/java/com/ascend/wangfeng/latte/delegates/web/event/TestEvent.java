package com.ascend.wangfeng.latte.delegates.web.event;

import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by fengye on 2017/8/30.
 * email 1040441325@qq.com
 */

public class TestEvent extends Event{
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
        if (getAction().equals("share")){
            final WebView webView =getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
