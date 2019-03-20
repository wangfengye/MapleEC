package com.maple.webview.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.maple.webview.widget.WebDetail;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author maple on 2019/3/20 13:27.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class X5WebView extends WebView {
    public static final String JSBRAGE = "maple_x5";
    private static boolean sDebug;
    private WebViewClient mClient = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            //添加图片展示
            /**
             * javascript:(function  pic(){
             *  var imgList = "";
             *  var imgs = document.getElementsByTagName("img");
             *  for(var i=0;i<imgs.length;i++){
             *      var img = imgs[i];
             *      imgList = imgList + img.src +";";
             *      img.onclick = function(){window.maple_x5.openImg(this.src);
             *  }
             * }
             window.maple_x5.getImgArray(imgList);})()
             */
            webView.loadUrl("javascript:(function  pic(){\n" +
                    "\tvar imgList = \"\";\n" +
                    "\tvar imgs = document.getElementsByTagName(\"img\");\n" +
                    "\tfor(var i=0;i<imgs.length;i++){\n" +
                    "\t\tvar img = imgs[i];\n" +
                    "\t\timgList = imgList + img.src +\";\";\n" +
                    "\t\timg.onclick = function(){window.maple_x5.openImg(this.src);\n" +
                    "\t}\n" +
                    "}\n" +
                    "window.maple_x5.getImgArray(imgList);})()");
            if (mLoadWebListener != null) mLoadWebListener.onLoaded();
            super.onPageFinished(webView, s);
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context content, AttributeSet arg1) {
        super(content, arg1);
        this.setWebViewClient(mClient);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
        //this.addJavascriptInterface(new WebViewJavaScriptFunction(content), JSBRAGE);
    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (!sDebug) return ret;
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        if (getX5WebViewExtension() != null) {
            canvas.drawText(this.getContext().getPackageName() + "-pid:"
                    + android.os.Process.myPid(), 10, 50, paint);
            canvas.drawText(
                    "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
                    100, paint);
        } else {
            canvas.drawText(this.getContext().getPackageName() + "-pid:"
                    + android.os.Process.myPid(), 10, 50, paint);
            canvas.drawText("Sys Core", 10, 100, paint);
        }
        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
        canvas.drawText(Build.MODEL, 10, 200, paint);
        canvas.restore();
        return ret;
    }


    private LoadWebListener mLoadWebListener;

    public interface LoadWebListener {
        void onLoaded();
    }

    public void addLoadWebListener(LoadWebListener listener) {
        this.mLoadWebListener = listener;
    }
}
