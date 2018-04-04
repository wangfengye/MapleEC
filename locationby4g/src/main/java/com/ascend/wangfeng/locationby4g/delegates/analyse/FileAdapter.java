package com.ascend.wangfeng.locationby4g.delegates.analyse;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.util.TimeUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

import static com.ascend.wangfeng.locationby4g.util.TimeUtil.STAND_FIR;

/**
 * Created by fengye on 2018/4/4.
 * email 1040441325@qq.com
 */

public class FileAdapter extends BaseMultiItemQuickAdapter<FileContent, MultipleViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public FileAdapter(List<FileContent> data) {
        super(data);
        addItemType(0, R.layout.item_file);
    }

    @Override
    protected void convert(MultipleViewHolder helper, FileContent item) {
        helper.setText(R.id.tv_name,item.getFileName());
        long start = item.getEntries().get(0).getTimeStamps().size()>0? item.getEntries().get(0).getTimeStamps().get(0):  item.getEntries().get(0).getTimestamp();
        long end  = item.getEntries().get(item.getEntries().size()-1).getTimestamp();
        helper.setText(R.id.tv_start, TimeUtil.format(start,STAND_FIR));
        helper.setText(R.id.tv_end, TimeUtil.format(end,STAND_FIR));
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
