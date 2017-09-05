package com.ascend.wangfeng.latte.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Created by fengye on 2017/8/25.
 * email 1040441325@qq.com
 */

public class MultipleViewHolder extends BaseViewHolder {
    private MultipleViewHolder(View view) {
        super(view);
    }
    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);
    }
}
