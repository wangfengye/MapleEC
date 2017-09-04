package com.ascend.wangfeng.latte.ec.main.personal;

import com.ascend.wangfeng.latte.ec.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean,BaseViewHolder> {
    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListBean.TYPE_NORMAL, R.layout.item_arrow);
        addItemType(ListBean.TYPE_AVATAR, R.layout.item_arrow_avatar);
        addItemType(ListBean.TYPE_SWITCH, R.layout.item_arrow_switch);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (item.getItemType()){
            case ListBean.TYPE_NORMAL:
                helper.setText(R.id.tv_arrow_text,item.getText());
                helper.setText(R.id.tv_arrow_value,item.getValue());
                break;
            case ListBean.TYPE_AVATAR:
                break;
            case ListBean.TYPE_SWITCH:
                break;

        }
    }
}
