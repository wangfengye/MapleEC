package com.ascend.wangfeng.latte.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.ascend.wangfeng.latte.ui.R;
import com.joanzapata.iconify.widget.IconTextView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 * 评分控件
 */

public class StatLayout extends LinearLayoutCompat implements View.OnClickListener {
    private static final CharSequence ICON_UN_SECLECT = "{fa-star-o}";
    private static final CharSequence ICON_SECLECT = "{fa-star}";
    private static final int STAR_TOTAL_COUNT = 5;
    private  ArrayList<IconTextView> mStars = new ArrayList<>();

    public StatLayout(Context context) {
        this(context, null);
    }

    public StatLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public StatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStarIcon();
    }

    private void initStarIcon() {
        Logger.i("initStarIcon");
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            star.setLayoutParams(lp);
            star.setText(ICON_UN_SECLECT);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_select, false);
            star.setOnClickListener(this);
            mStars.add(star);
            this.addView(star);
        }
    }

    public int getStarCount() {
        int count = 0;
        for (int i = 0; i < STAR_TOTAL_COUNT; i++) {
            final IconTextView star = mStars.get(i);
            final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
            if (isSelect) {
                count++;
            }
        }
        return count;
    }

    private void selectStar(int count) {
        for (int i = 0; i <= count; i++) {
            final IconTextView star = mStars.get(i);
            star.setText(ICON_SECLECT);
            star.setTextColor(Color.RED);
            star.setTag(R.id.star_is_select, true);
        }
    }

    private void unSelectStar(int count) {
        for (int i = STAR_TOTAL_COUNT - 1; i > count; i--) {
            final IconTextView star = mStars.get(i);
            star.setText(ICON_UN_SECLECT);
            star.setTextColor(Color.GRAY);
            star.setTag(R.id.star_is_select, false);
        }
    }

    @Override
    public void onClick(View view) {
        final IconTextView star = (IconTextView) view;
        final int count = (int) star.getTag(R.id.star_count);
        final boolean isSelect = (boolean) star.getTag(R.id.star_is_select);
        if (!isSelect){
            selectStar(count);
        }else {
            unSelectStar(count);
        }
    }
}
