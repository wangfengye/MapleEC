package com.ascend.wangfeng.wifimanage.delegates.index;

import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Device;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/8.
 * email 1040441325@qq.com
 */

public class NewDeviceAdapter extends BaseMultiItemQuickAdapter<Device,MultipleViewHolder> {
    private LatteDelegate mDelegate;
    private OnClickListener mListener;
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
    public void setListener(OnClickListener listener){
        mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Device item) {
        helper.setText(R.id.tv_name,item.getDname());
        helper.setText(R.id.tv_ip,item.getDevIp());
        helper.setText(R.id.tv_brand,item.getVendor());
        helper.setText(R.id.tv_mac,item.getDmac());
        helper.setOnClickListener(R.id.ll_main, view-> {
                if (mListener!=null)mListener.click(item);
        });
        CircleImageView cimg = helper.getView(R.id.cimg);
        cimg.setImage(DeviceType.getTypes().get(item.getDtype()).getImgId());
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
    public interface OnClickListener{
        void click(Device device);
    }
}
