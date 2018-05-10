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
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.vo.PersonVo;
import com.ascend.wangfeng.wifimanage.greendao.PersonDao;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        mIcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
        initRv();
    }

    private void initRv() {
        PersonDao dao = ((MainApp) getActivity().getApplication()).getDaoSession().getPersonDao();
        List<Person> people = dao.loadAll();
        List<Person> onLinePeople= (List<Person>) getArguments().getSerializable("person");
        ArrayList<PersonVo> personVos = new ArrayList<>();
        for (int i = 0; i < onLinePeople.size(); i++) {
            PersonVo personVo = PersonVo.get(onLinePeople.get(i));
            personVo.setOnline(true);
            personVos.add(personVo);
        }
        for (int i = 0; i< people.size();i++){
            if (!contain(people.get(i),onLinePeople)){
                PersonVo personVo = PersonVo.get(people.get(i));
                personVo.setOnline(false);
                personVos.add(personVo);
            }
        }
        PersonAdapter adapter = new PersonAdapter(personVos,this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvPeople.setAdapter(adapter);
        mRvPeople.setLayoutManager(manager);
        mRvPeople.addItemDecoration(BaseDecoration.create(getResources()
                .getColor(android.R.color.darker_gray), 1));
    }
    private boolean contain(Person p,List<Person> people){
        for (Person p1:people) {
            if (p.getId() == p1.getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }
}
