package com.ascend.wangfeng.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fengye on 2017/9/4.
 * email 1040441325@qq.com
 */

public class UserProfileDelegate extends LatteDelegate {
    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        ListBean image = new ListBean.Builder().setItemType(ListBean.TYPE_AVATAR)
                .setId(0)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .build();
        ListBean name = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(1)
                .setText("name")
                .setValue("maple")
                .build();
        ListBean sex = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(2)
                .setText("sex")
                .setValue("secret")
                .build();
        ListBean birthday = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(3)
                .setText("birthday")
                .setValue("1994-04-18")
                .build();
        ArrayList<ListBean> been = new ArrayList<>();
        been.add(image);
        been.add(name);
        been.add(sex);
        been.add(birthday);

        ListAdapter adapter = new ListAdapter(been);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.gray), 1));
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }
}
