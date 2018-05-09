package com.ascend.wangfeng.wifimanage.delegates.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/9.
 * email 1040441325@qq.com
 */

public class PersonDetailDelegate extends LatteDelegate{
    @BindView(R.id.et_name)
    EditText mEtName;
    @OnClick(R.id.btn_save)
    void save(){
        PersonDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        Person person = new Person();
        person.setName(mEtName.getText().toString());
        dao.insert(person);
        pop();
    }
    public static PersonDetailDelegate newInstance(Bundle args) {
        PersonDetailDelegate fragment = new PersonDetailDelegate();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_person_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
