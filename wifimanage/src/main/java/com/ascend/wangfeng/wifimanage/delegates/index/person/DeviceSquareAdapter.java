package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceType;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/10.
 * email 1040441325@qq.com
 */

public class DeviceSquareAdapter extends BaseMultiItemQuickAdapter<Device, MultipleViewHolder> {

    private OnClickListener mListener;

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    public DeviceSquareAdapter(List<Device> data) {
        super(data);
        addItemType(0, R.layout.item_device_square);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Device item) {
        helper.setImageResource(R.id.img_icon, DeviceType.getTypes().get(item.getDtype()).getImgId());
        helper.setText(R.id.tv_name, item.getDname());
        if (mListener != null) {
            helper.getView(R.id.rl_content).setOnClickListener(view -> mListener.click(item));
        }
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    public interface OnClickListener {
        void click(Device device);
    }
}
