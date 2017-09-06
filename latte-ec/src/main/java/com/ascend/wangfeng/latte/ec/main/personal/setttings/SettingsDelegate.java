package com.ascend.wangfeng.latte.ec.main.personal.setttings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ec.main.personal.ListAdapter;
import com.ascend.wangfeng.latte.ec.main.personal.ListBean;
import com.ascend.wangfeng.latte.ec.main.personal.PersonalClickListener;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.latte.util.callback.CallbackManager;
import com.ascend.wangfeng.latte.util.callback.CallbackType;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fengye on 2017/9/6.
 * email 1040441325@qq.com
 */

public class SettingsDelegate extends LatteDelegate {
    @BindView(R2.id.rv_settings)
    RecyclerView mRvSettings;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        ListBean push = new ListBean.Builder().setItemType(ListBean.TYPE_SWITCH)
                .setId(0)
                .setText("消息推送")
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean b) {
                        if (b){
                            CallbackManager.getInstance()
                                    .getCallback(CallbackType.TAG_PUSH_OPEN)
                                    .executeCallback(null);
                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_SHORT).show();
                        }else {
                            CallbackManager.getInstance()
                                    .getCallback(CallbackType.TAG_PUSH_CLOSE)
                                    .executeCallback(null);
                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        ListBean about = new ListBean.Builder().setItemType(ListBean.TYPE_NORMAL)
                .setId(2)
                .setText("关于我们")
                .setDelegate(new AboutDelegate())
                .build();
        final ArrayList<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);
        ListAdapter adapter = new ListAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
        mRvSettings.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.gray), 1));
    }
}
