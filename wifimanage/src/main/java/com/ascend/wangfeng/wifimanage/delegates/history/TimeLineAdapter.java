package com.ascend.wangfeng.wifimanage.delegates.history;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Event;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/6/5.
 * email 1040441325@qq.com
 */

public class TimeLineAdapter extends BaseMultiItemQuickAdapter<Event, MultipleViewHolder> {
    public static int mPosition;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public TimeLineAdapter(List<Event> data) {
        super(data);
        addItemType(0,R.layout.item_line);
    }


    @Override
    protected void convert(MultipleViewHolder helper, Event item) {
        if (item.getOnline()==1){
            helper.setBackgroundRes(R.id.img_point,R.drawable.tv_round_blue);
            helper.setBackgroundRes(R.id.v_line,R.color.textThi);
            helper.setText(R.id.tv_desc,"上线时间: "+  com.ascend.wangfeng.latte.util.TimeUtil.format(item.getTime()));
        }else {
            helper.setBackgroundRes(R.id.img_point,R.drawable.tv_round_gray);
            helper.setBackgroundRes(R.id.v_line,R.color.colorBlue);
            helper.setText(R.id.tv_desc,"离线时间: "+ com.ascend.wangfeng.latte.util.TimeUtil.format(item.getTime()));
        }
        if (mPosition+1== getItemCount()){
            helper.setVisible(R.id.v_line,false);
        }else {
            helper.setVisible(R.id.v_line,true);
        }
    }

    @Override
    public void onBindViewHolder(MultipleViewHolder holder, int positions) {
        mPosition = positions;
        super.onBindViewHolder(holder, positions);
        // 用于判断当前是否是最后一个item;

    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
