package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 * 人员列表,提供新增功能
 */

public class PersonListEditDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_add)
    LinearLayout mLlAdd;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    private ArrayList<Person> mPeople;
    private PersonAdapter mAdapter;


    @Override
    public Object setLayout() {
        return R.layout.delegate_person_list_edit;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("成员列表");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(view -> onBackPressedSupport());
        mLlAdd.setOnClickListener(view -> {
            // 进入成员信息编辑页面
            start(PersonEditDelegate.newInstance(null));
        });
        mPeople = new ArrayList<>();
        mAdapter = new PersonAdapter(mPeople, this);
        mAdapter.setEdit();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRvPeople.setLayoutManager(manager);
        mRvPeople.setAdapter(mAdapter);
        mRvPeople.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray, getActivity().getTheme()), 1));
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initList();
    }

    private void initList() {
        Client.getInstance().getPersons()
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<List<Person>>>() {
                    @Override
                    public void onSuccess(Response<List<Person>> response) {
                        mPeople.clear();
                        mPeople.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }


    public static ISupportFragment newInstance() {
        return new PersonListEditDelegate();
    }

    @Override
    public boolean onBackPressedSupport() {
        // 返回上一页携带选中数据
        Bundle bundle = new Bundle();
        for (Person p : mPeople) {
            if (p.isSelected()) bundle.putSerializable("person", p);
            setFragmentResult(1, bundle);
        }
        pop();
        return true;
    }
}
