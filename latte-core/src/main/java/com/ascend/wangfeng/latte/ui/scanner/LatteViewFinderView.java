package com.ascend.wangfeng.latte.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class LatteViewFinderView extends ViewFinderView{
    public LatteViewFinderView(Context context) {
        this(context,null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder =true;
        mBorderPaint.setColor(Color.BLUE);
    }
}
