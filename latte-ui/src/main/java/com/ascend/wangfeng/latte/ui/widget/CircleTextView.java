package com.ascend.wangfeng.latte.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by fengye on 2017/9/7.
 * email 1040441325@qq.com
 */

public class CircleTextView extends AppCompatTextView{
    private final Paint PAINT;
    private final PaintFlagsDrawFilter FILTER;
    public CircleTextView(Context context) {
        this(context,null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        PAINT = new Paint();
        FILTER = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        PAINT.setColor(Color.WHITE);
        PAINT.setAntiAlias(true);
    }
    public final void setCircleBackground(@ColorInt int color){
        PAINT.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final  int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final int max = Math.max(width,height);
        setMeasuredDimension(max,max);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setDrawFilter(FILTER);
        float radius = Math.max(getWidth()/2,getHeight()/2);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,PAINT);
        super.draw(canvas);
    }
}
