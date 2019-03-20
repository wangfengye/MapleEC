package com.maple.webview.util;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.util.LinkedList;

public class WebViewJavaScriptFunction {
	private Context mContext;
    private LinkedList<String> mUrls;

    public WebViewJavaScriptFunction(Context context) {
		this.mContext = context;
	}
	@JavascriptInterface
	public void openImg(String url){
		Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
	}
    /**页面加载时JS调用的Java代码*/
    @JavascriptInterface
    public void getImgArray(String urlArray){
        String[] urls = urlArray.split(";");//url拼接成的字符串，有分号隔开
        for (String url : urls) {
            mUrls.add(url);
        }
    }
}
