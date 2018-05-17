package com.ascend.wangfeng.wifimanage.delegates.icon;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/14.
 * email 1040441325@qq.com
 */

public class IconAdapter extends BaseMultiItemQuickAdapter<Icon, MultipleViewHolder> {

    private OnClicekListener mListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public IconAdapter(List<Icon> data) {
        super(data);
        addItemType(0, R.layout.item_icon_square);
    }

    public void setListener(OnClicekListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Icon item) {
        helper.setImageResource(R.id.img_icon, item.getIconUrl());
        helper.setBackgroundRes(R.id.rl_content, item.isChose()
                ? R.drawable.ll_round_choosed : R.drawable.ll_round_normal);
        helper.setOnClickListener(R.id.rl_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    for (Icon icon :
                            getData()) {
                        icon.setChose(false);
                    }
                    item.setChose(true);
                    notifyDataSetChanged();
                    mListener.click(item);
                }
            }
        });

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    interface OnClicekListener {
        void click(Icon icon);
    }
}
