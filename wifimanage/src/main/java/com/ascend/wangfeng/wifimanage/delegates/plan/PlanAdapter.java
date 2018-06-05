package com.ascend.wangfeng.wifimanage.delegates.plan;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Plan;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.time2Str;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 */

public class PlanAdapter extends BaseMultiItemQuickAdapter<Plan, MultipleViewHolder> {
    private OnClickListener mListener;

    public PlanAdapter(List<Plan> data) {
        super(data);
        addItemType(0, R.layout.item_plan);
    }

    public void setListener(OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final Plan item) {
        helper.setText(R.id.tv_time, time2Str(item.getStarttime()) + " ~ "
                + time2Str(item.getEndtime()));
        helper.setText(R.id.tv_repeat, item.getTypeStr());
        helper.getView(R.id.rl_content).setOnClickListener( view -> {
            if (mListener != null) mListener.click(item);
        });
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    public interface OnClickListener {
        void click(Plan plan);
    }
}
