package com.maple.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Scroller;

/**
 * @author maple on 2019/3/18 18:14.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class DetailScrollView extends ViewGroup {
    private Scroller mScroller;
    private int mMinimumVelocity;
    private int mTouchSlop;
    private OnScrollBarShowListener mScrollBarShowListener;
    private int maxScrollY;

    public DetailScrollView(Context context) {
        super(context);
        init(context);
    }


    public DetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();//fling 最小速度值
        mTouchSlop = configuration.getScaledTouchSlop();
        mScrollBarShowListener = new OnScrollBarShowListener();
    }

    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        final int count = getChildCount();
        final int parentLeft = getPaddingLeft();
        int parentHeight = b - t;
        int lasBottom = getPaddingTop();
        maxScrollY = 0;
        int webHeight = 0;
        int listHeight = 0;
        int otherHeight = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                if (height == 0) height = parentHeight;
                int childLeft = parentLeft + lp.leftMargin;
                int childTop = lasBottom + lp.topMargin;
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
                lasBottom = childTop + height + lp.bottomMargin;
                maxScrollY += lp.topMargin;
                maxScrollY += lp.bottomMargin;
                otherHeight += height;
            }
        }
        maxScrollY = webHeight + listHeight + otherHeight - parentHeight;
        if (maxScrollY < 0) maxScrollY = 0;
    }

    class OnScrollBarShowListener {
        private long mOldTimeMills;

        public void onShow() {
            long timeMills = AnimationUtils.currentAnimationTimeMillis();
            if (timeMills - mOldTimeMills > 16) {
                mOldTimeMills = timeMills;
                awakenScrollBars();
            }
        }
    }
}
