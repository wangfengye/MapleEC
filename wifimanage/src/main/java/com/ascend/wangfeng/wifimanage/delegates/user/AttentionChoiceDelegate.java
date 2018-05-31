package com.ascend.wangfeng.wifimanage.delegates.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.delegates.index.person.PersonAdapter;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;

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
        initView();
    }

    private void initView() {
        mPeople = new ArrayList<>();
        mAdapter = new PersonAdapter(mPeople,this);
    }
}
