package com.ascend.wangfeng.wifimanage.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ascend.wangfeng.wifimanage.R;

/**
 * Created by fengye on 2018/5/20.
 * email 1040441325@qq.com
 */

public class GithubActivityView extends View {
    private int mWidth;
    private Integer[][] data;
    private Paint mPaint;
    private float mColumnTitleHeight;
    private float mRowTitleWidth;


    private float mSpaceWidth = 5f;

    public GithubActivityView(Context context) {
        this(context, null);
    }

    public GithubActivityView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GithubActivityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setData(Integer[][] data) {
        this.data = data;
        invalidate();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        creatImage(canvas);
    }

    private void creatImage(Canvas canvas) {
        if (data == null) return;
        int column = data[0].length;
        float radius = (mWidth - mSpaceWidth * column) / (column + 1.6f);
        int row = data.length;
        mColumnTitleHeight = radius + mSpaceWidth;
        mRowTitleWidth = (radius * 1.6f + mSpaceWidth) * 1;
        mPaint.setTextSize(radius * .8f);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(getResources().getColor(R.color.textSec));
        for (int i = 0; i < column; i++) {
            if (i % 4 == 0) {
                canvas.drawText(String.valueOf(i) + "h", mRowTitleWidth + i * (radius + mSpaceWidth), radius, mPaint);
                canvas.save();
            }
        }
        mPaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < row; i++) {
            canvas.drawText(getWeek(i), mRowTitleWidth - mSpaceWidth, i * (radius + mSpaceWidth) + radius + mColumnTitleHeight, mPaint);
            canvas.save();
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Path path = new Path();
                path.moveTo(mRowTitleWidth + j * (radius + mSpaceWidth), mColumnTitleHeight + i * (radius + mSpaceWidth));
                path.lineTo(mRowTitleWidth + j * (radius + mSpaceWidth), mColumnTitleHeight + i * (radius + mSpaceWidth) + radius);
                path.lineTo(mRowTitleWidth + j * (radius + mSpaceWidth) + radius, mColumnTitleHeight + i * (radius + mSpaceWidth) + radius);
                path.lineTo(mRowTitleWidth + j * (radius + mSpaceWidth) + radius, mColumnTitleHeight + i * (radius + mSpaceWidth));
                mPaint.setColor(getColor(data[i][j]));
                canvas.drawPath(path, mPaint);
                canvas.save();
            }
        }
    }

    public int getColor(int i) {
        switch (i) {
            case 0:
                return Color.parseColor("#ededed");
            case 1:
                return Color.parseColor("#acd5f2");
            case 2:
                return Color.parseColor("#7fa8c9");
            case 3:
                return Color.parseColor("#527ba0");
            case 4:
                return Color.parseColor("#254e77");
            default:
                return Color.parseColor("#ededed");
        }
    }

    public String getWeek(int day) {
        day = day % 7;
        switch (day) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 0:
                return "周日";
        }
        return "未知";
    }
}
