package com.ascend.wangfeng.locationby4g.delegates.analyse;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;
import com.ascend.wangfeng.locationby4g.util.ImsiUtil;
import com.ascend.wangfeng.locationby4g.util.TimeUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/4/4.
 * email 1040441325@qq.com
 */

public class ResultAdapter extends BaseMultiItemQuickAdapter<CellMeaureAckEntry, MultipleViewHolder> {
    private OnClickListener mListener;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ResultAdapter(List<CellMeaureAckEntry> data) {
        super(data);
        addItemType(CellMeaureAckEntry.TYPE_NORMAL, R.layout.item_searh_result);
        addItemType(CellMeaureAckEntry.TYPE_IMSI_RESULT, R.layout.item_search_imsi);
    }
    public void setListener(OnClickListener listener){
        this.mListener = listener;
    }
    @Override
    protected void convert(MultipleViewHolder helper, final CellMeaureAckEntry item) {
        switch (helper.getItemViewType()){
            case CellMeaureAckEntry.TYPE_NORMAL:
                helper.setText(R.id.tv_name,item.getImsi());
                helper.setText(R.id.tv_start, ImsiUtil.getImsiOperator(item.getImsi()));
                helper.setText(R.id.tv_end,String.valueOf(item.getCrashCounter()));
                helper.setOnClickListener(R.id.tv_name, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener!=null) mListener.onclick(item);
                    }
                });
                break;
            case CellMeaureAckEntry.TYPE_IMSI_RESULT:
                helper.setText(R.id.tv_name,item.getFileName());
                helper.setText(R.id.tv_time, TimeUtil.format(item.getTimestamp()));
                break;
        }
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
    interface  OnClickListener{
        void onclick(CellMeaureAckEntry entry);
    }
}
