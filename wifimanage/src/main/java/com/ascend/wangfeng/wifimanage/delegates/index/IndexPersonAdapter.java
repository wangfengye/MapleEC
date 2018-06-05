package com.ascend.wangfeng.wifimanage.delegates.index;

import android.graphics.Color;
import android.view.View;

import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.delegates.OnAdapterListener;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/6/5.
 * email 1040441325@qq.com
 */

public class IndexPersonAdapter extends BaseMultiItemQuickAdapter<Person, MultipleViewHolder> {
    private int mColor = Color.parseColor("#10D7C6");
    private OnAdapterListener<Person> mListener;
    public IndexPersonAdapter(List<Person> data, int color) {
        super(data);
        mColor =color;
        addItemType(0, R.layout.item_index);
    }
    public void setListener(OnAdapterListener<Person> listener){
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        // 最多显示6个
        return getData().size()>5?5:getData().size();
    }

    @Override
    protected void convert(MultipleViewHolder helper, Person item) {
        CircleImageView cImg = helper.getView(R.id.cimg_icon);
        cImg.setImage(Icon.getImgUrl(item.getPimage()));
        cImg.setBg(mColor);
        cImg.setSrcType(CircleImageView.TYPE_NORMAL);
        cImg.setOnClickListener(view -> {
            if (mListener!=null)mListener.onclick(item);
        });
    }
    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}