package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ui.recycler.BaseDecoration;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/10.
 * email 1040441325@qq.com
 */

public class PersonListDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    private ArrayList<Person> mPeople;
    private PersonAdapter mAdapter;

    public static PersonListDelegate newInstance( Bundle args) {
        PersonListDelegate fragment = new PersonListDelegate();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_person_list;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("成员列表");
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(view-> onBackPressedSupport());
        initRv();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        add(Client.getInstance().getPersons()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new MyObserver<Response<List<Person>>>() {
                    @Override
                    public void onSuccess(Response<List<Person>> response) {
                        mPeople.clear();
                        mPeople.addAll(response.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                }));
    }

    private void initRv() {
        mPeople = new ArrayList<>();
        mAdapter = new PersonAdapter(mPeople,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvPeople.setAdapter(mAdapter);
        mRvPeople.setLayoutManager(manager);
        mRvPeople.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray,getActivity().getTheme()), 1));
    }


    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
