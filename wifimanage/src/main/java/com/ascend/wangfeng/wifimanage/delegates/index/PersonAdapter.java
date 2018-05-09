package com.ascend.wangfeng.wifimanage.delegates.index;

import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.vo.PersonVo;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonAdapter extends BaseMultiItemQuickAdapter<PersonVo,MultipleViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonAdapter(List<PersonVo> data) {
        super(data);
        addItemType(0, R.layout.item_person);
    }

    @Override
    protected void convert(MultipleViewHolder helper, PersonVo item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_desc,"描述性信息");
        CircleImageView cimg = helper.getView(R.id.cimg_icon);
        cimg.setImage(getImg(item.getItemType()));
    }

    private int getImg(int type) {
        return R.drawable.test;
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
