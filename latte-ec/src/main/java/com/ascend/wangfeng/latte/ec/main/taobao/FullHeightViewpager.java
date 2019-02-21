package com.ascend.wangfeng.latte.ec.main.taobao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.WindowManager;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.util.DimenUtil;

/**
 * @author maple on 2019/2/21 14:41.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class FullHeightViewpager extends ViewPager {
    public FullHeightViewpager(@NonNull Context context) {
        super(context);
    }

    public FullHeightViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(DimenUtil.getScreenHeight() - DimenUtil.dp2px(48 * 2), MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
