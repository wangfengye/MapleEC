package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class NewDeviceAdapter extends BaseMultiItemQuickAdapter<Device,MultipleViewHolder> {
    private LatteDelegate mDelegate;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewDeviceAdapter(List<Device> data,LatteDelegate delegate) {
        super(data);
        this.mDelegate = delegate;
        addItemType(0, R.layout.item_device);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Device item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_ip,item.getIp());
        helper.setText(R.id.tv_brand,item.getBrand());
        helper.setText(R.id.tv_mac,item.getMac());
        helper.setOnClickListener(R.id.ll_main, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("device",item);
                mDelegate.start(DeviceDetailDelegate.newInstance(bundle));
            }
        });
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

}
