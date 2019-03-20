package com.maple.webview.fun1;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maple.webview.R;

import java.util.List;

/**
 * @author maple on 2019/3/20 11:32.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class CommentAdapter extends BaseQuickAdapter<String,BaseViewHolder>{



    public CommentAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
