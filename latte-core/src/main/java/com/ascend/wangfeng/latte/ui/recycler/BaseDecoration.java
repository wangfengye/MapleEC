package com.ascend.wangfeng.latte.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;

/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class BaseDecoration extends DividerItemDecoration {
    public BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup( new LookupImpl(color,size));
    }
    public static BaseDecoration create(@ColorInt int color, int size){
        return new BaseDecoration(color,size);
    }

    public class LookupImpl implements DividerLookup {
        private final int COLOR;
        private final int SIZE;

        public LookupImpl(int color, int size) {
            this.COLOR = color;
            this.SIZE = size;
        }

        @Override
        public Divider getVerticalDivider(int position) {
            return new Divider.Builder()
                    .size(SIZE)
                    .color(COLOR)
                    .build();
        }

        @Override
        public Divider getHorizontalDivider(int position) {
            return new Divider.Builder()
                    .size(SIZE)
                    .color(COLOR)
                    .build();
        }
    }
}
