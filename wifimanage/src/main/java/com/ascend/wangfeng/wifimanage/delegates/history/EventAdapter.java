package com.ascend.wangfeng.wifimanage.delegates.history;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.index.DeviceType;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/16.
 * email 1040441325@qq.com
 */

public class EventAdapter extends BaseMultiItemQuickAdapter<Event, MultipleViewHolder> {
    OnClickListener mClickListener;

    public EventAdapter(List<Event> data) {
        super(data);
        addItemType(0, R.layout.item_history);
    }

    public void setClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Event item) {
        CircleImageView cimg = helper.getView(R.id.cimg_icon);
        cimg.setImage(Icon.getImgUrl(item.getPerson().getPimage()));
        cimg.setIcon(DeviceType.getTypes().get(item.getDevice().getDtype()).getImgId());
        cimg.setState(item.getOnline() == 1);
        helper.setText(R.id.tv_name, item.getPerson().getPname() + " [" + item.getDevice().getDname() + "] ");
        helper.setText(R.id.tv_desc, com.ascend.wangfeng.latte.util.TimeUtil.format(item.getTime(), "HH:mm") + item.getEventStr());
        cimg.setOnClickListener(view -> {
            if (mClickListener != null) mClickListener.Click(item.getPerson());
        });
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    interface OnClickListener {
        void Click(Person person);
    }
}
