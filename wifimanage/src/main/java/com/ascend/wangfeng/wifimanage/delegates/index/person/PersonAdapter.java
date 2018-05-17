package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.MultipleViewHolder;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.vo.PersonVo;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonAdapter extends BaseMultiItemQuickAdapter<PersonVo, MultipleViewHolder> {
    private boolean mEdit;
    private LatteDelegate mDelegate;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PersonAdapter(List<PersonVo> data, LatteDelegate delegate) {
        super(data);
        addItemType(0, R.layout.item_person);
        mDelegate = delegate;
    }

    /**
     * 开启编辑模式
     */
    public void setEdit() {
        mEdit = true;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final PersonVo item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_desc, "描述性信息");
        CircleImageView cimg = helper.getView(R.id.cimg_icon);
        cimg.setImage(Icon.getImgUrl(item.getImgUrl()));
        RadioButton button = helper.getView(R.id.rb_choose);
        if (mEdit) {
            button.setVisibility(View.VISIBLE);
            helper.setVisible(R.id.it_right, false);
            button.setChecked(item.isChecked());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < getData().size(); i++) {
                        if (getData().get(i).isChecked()) getData().get(i).setChecked(false);
                    }
                    item.setChecked(true);
                    // 通过消息机制,避免绘制时触发更新
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        } else {
            button.setVisibility(View.GONE);
            helper.setVisible(R.id.it_right, true);
            cimg.setState(item.isOnline());
            helper.setOnClickListener(R.id.ll_person, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 进入成员详情;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("person",item);
                    mDelegate.start(PersonDetailDelegate.newInstance(bundle));
                }
            });
        }
    }

    private int getImg(int type) {
        return R.drawable.test;
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }
}
