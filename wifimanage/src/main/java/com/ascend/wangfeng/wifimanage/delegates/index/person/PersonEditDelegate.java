package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/10.
 * email 1040441325@qq.com
 */

public class PersonEditDelegate extends LatteDelegate {
    public static final String PERSON = "person";
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.et_name)
    EditText mEtName;
    private Person mPerson;

    @OnClick(R.id.btn_save)
    void save() {
        mPerson.setName(mEtName.getText().toString());
        PersonDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        if (mPerson.getId() != null) {
            dao.insert(mPerson);
        } else {
            dao.update(mPerson);
        }
        pop();
    }

    public static PersonEditDelegate newInstance(Bundle args) {
        PersonEditDelegate fragment = new PersonEditDelegate();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_person_edit;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mIcBack.setVisibility(View.VISIBLE);
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop();
            }
        });
        mToolbarTitle.setText("成员编辑");
        init();
    }

    public void init() {
        mPerson = (Person) getArguments().getSerializable(PERSON);
        if (mPerson != null) {
            mCimgIcon.setImage(mPerson.getImgUrl());
            mEtName.setText(mPerson.getName());
        } else {
            mPerson = new Person();
            mPerson.setImgUrl(R.mipmap.p_2);
        }
    }
}
