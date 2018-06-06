package com.ascend.wangfeng.wifimanage.delegates.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonAdapter;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/31.
 * email 1040441325@qq.com
 */

public class AttentionChoiceDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    @BindView(R.id.btn_sure)
    Button mBtnSure;
    private ArrayList<Person> mPeople;
    private PersonAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_attention_choice;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("关注选择");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(view -> pop());
        initView();
        initData();
    }

    @OnClick(R.id.btn_sure)
    void clickBtnSure() {
        Person person = null;
        for (int i = 0; i < mPeople.size(); i++) {
            if (mPeople.get(i).isSelected()){person = mPeople.get(i);break;}
        }
        person.setAttention(true);
        Client.getInstance().updatePersonWithAttention(person)
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<Person>>() {
                    @Override
                    public void onSuccess(Response<Person> response) {
                        MainApp.toast(R.string.set_attention_success);
                        pop();
                    }
                });
    }

    private void initData() {
        Client.getInstance().getPersons()
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Person>>>() {
                    @Override
                    public void onSuccess(Response<List<Person>> response) {
                        mPeople.clear();
                        mPeople.addAll(response.getData());
                        // 默认选中第一个
                        if (mPeople.size()>0) mPeople.get(0).setSelected(true);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {
        mPeople = new ArrayList<>();
        mAdapter = new PersonAdapter(mPeople, this);
        mAdapter.setEdit();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvPeople.setLayoutManager(manager);
        mRvPeople.setAdapter(mAdapter);
        mRvPeople.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(R.color.colorBg,getActivity().getTheme()), 1));
    }
}
