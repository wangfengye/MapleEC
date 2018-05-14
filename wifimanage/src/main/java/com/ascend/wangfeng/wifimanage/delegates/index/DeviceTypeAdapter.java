package com.ascend.wangfeng.wifimanage.delegates.index;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/11.
 * email 1040441325@qq.com
 */

public class DeviceTypeAdapter extends BaseMultiItemQuickAdapter<DeviceType, MultipleViewHolder> {
    private OnClickListener mListener;

    protected DeviceTypeAdapter(List<DeviceType> data) {
        super(data);
        addItemType(0, R.layout.item_device_square);
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final DeviceType item) {
        helper.setImageResource(R.id.img_icon, item.getImgId());
        helper.setText(R.id.tv_name, item.getName());
        helper.setBackgroundRes(R.id.rl_content, item.isChose()
                ? R.drawable.ll_round_choosed : R.drawable.ll_round_normal);
        helper.setOnClickListener(R.id.rl_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (DeviceType t :
                        getData()) {
                    if (t.isChose()) t.setChose(false);
                }
                item.setChose(true);
                notifyDataSetChanged();
                if (mListener != null) mListener.click(item);
            }
        });

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    interface OnClickListener {
        void click(DeviceType deviceType);
    }
}
