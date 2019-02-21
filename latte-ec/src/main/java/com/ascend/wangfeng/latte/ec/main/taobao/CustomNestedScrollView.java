package com.ascend.wangfeng.latte.ec.main.taobao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author maple on 2019/2/21 15:04.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class CustomNestedScrollView extends NestedScrollView{
    public static final String TAG = CustomNestedScrollView.class.getSimpleName();
    final int MAX_SCROLL_LENGTH = 400;
    /**
     * 该控件滑动的高度，高于这个高度后交给子滑动控件
     */
    int mParentScrollHeight ;
    int mScrollY ;
    public CustomNestedScrollView(Context context) {
        super(context);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollHeight(int scrollLength) {
        this.mParentScrollHeight = scrollLength;
    }




    /**
     * 子控件告诉父控件 开始滑动了
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * 如果有就返回true
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
        Log.i(TAG, "onNestedPreScroll: "+ dx +" "+dy);
        if (mScrollY >= mParentScrollHeight||(mScrollY<=0&&dy<0)) {

        }else {
            consumed[0] = dx;
            consumed[1] = dy;
            scrollBy(0, dy);
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
    }
}
