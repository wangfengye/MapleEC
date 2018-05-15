package com.ascend.wangfeng.wifimanage.delegates.plan;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.vo.PlanVo;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

import static com.ascend.wangfeng.wifimanage.utils.TimeUtil.time2Str;

/**
 * Created by fengye on 2018/5/15.
 * email 1040441325@qq.com
 */

public class PlanAdapter extends BaseMultiItemQuickAdapter<PlanVo,MultipleViewHolder>{
    private OnClickListener mListener;
    public PlanAdapter(List<PlanVo> data) {
        super(data);
        addItemType(0,R.layout.item_plan);
    }
    public void setListener(OnClickListener listener){
        this.mListener = listener;
    }
    @Override
    protected void convert(MultipleViewHolder helper, final PlanVo item) {
        helper.setText(R.id.tv_time,time2Str(item.getStarttime())+ " ~ "
        + time2Str(item.getEndtime()));
        helper.setText(R.id.tv_repeat,item.getTypeStr());
        helper.setOnClickListener(R.id.rl_content, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null)mListener.click(item);
            }
        });
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
    public interface OnClickListener{
        void click(PlanVo planVo);
    }
}
