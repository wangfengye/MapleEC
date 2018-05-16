package com.ascend.wangfeng.wifimanage.delegates.history;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.vo.EventVo;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceType;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/16.
 * email 1040441325@qq.com
 */

public class EventAdapter extends BaseMultiItemQuickAdapter<EventVo, MultipleViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public EventAdapter(List<EventVo> data) {
        super(data);
        addItemType(0, R.layout.item_history);
    }

    @Override
    protected void convert(MultipleViewHolder helper, EventVo item) {
        CircleImageView cimg = helper.getView(R.id.cimg_icon);
        cimg.setImage(item.getPerson().getImgUrl());
        cimg.setIcon(DeviceType.getTypes().get(item.getDevice().getType()).getImgId());
        cimg.setState(item.getEvent()==1);
        helper.setText(R.id.tv_name, item.getPerson().getName()+" [" + item.getDevice().getName() + "] ");
        helper.setText(R.id.tv_desc,com.ascend.wangfeng.latte.util.TimeUtil.format(item.getTime(),"HH:mm")+ item.getEventStr());
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
