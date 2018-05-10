package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/10.
 * email 1040441325@qq.com
 */

public class DeviceSquareAdapter extends BaseMultiItemQuickAdapter<Device,MultipleViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DeviceSquareAdapter(List<Device> data) {
        super(data);
        addItemType(0,R.layout.item_device_square);
    }

    @Override
    protected void convert(MultipleViewHolder helper, Device item) {
        helper.setText(R.id.tv_name,item.getName());
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
