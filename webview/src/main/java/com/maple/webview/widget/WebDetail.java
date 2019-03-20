package com.maple.webview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.maple.webview.R;
import com.maple.webview.util.X5WebView;

/**
 * @author maple on 2019/3/19 12:03.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class WebDetail extends FrameLayout {
    private X5WebView mWeb;

    public WebDetail(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public WebDetail(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WebDetail(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void setListener(LoadListener listener){
        this.mListener =listener;
    }
    private void initView(Context context) {
        inflate(getContext(), R.layout.detail_web, this);
        mWeb = findViewById(R.id.web);
        mWeb.addLoadWebListener(new X5WebView.LoadWebListener() {
            @Override
            public void onLoaded() {
                if (mListener!=null)mListener.loaded();
            }
        });
        mWeb.loadUrl("http://www.ahyouth.com/news/20190319/1372831.shtml");
    }
    private LoadListener mListener;
    public interface  LoadListener{
        void loaded();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.i("X%", "onDetachedFromWindow: ");
        if (mWeb!=null)mWeb.destroy();
        super.onDetachedFromWindow();
    }
}
