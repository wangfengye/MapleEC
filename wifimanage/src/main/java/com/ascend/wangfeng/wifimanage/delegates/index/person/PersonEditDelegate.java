package com.ascend.wangfeng.wifimanage.delegates.index.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Person;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.delegates.icon.Icon;
import com.ascend.wangfeng.wifimanage.delegates.icon.IconChooseDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.views.CircleImageView;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2018/5/10.
 * email 1040441325@qq.com
 */

public class PersonEditDelegate extends LatteDelegate {
    public static final String PERSON = "person";
    public static final int REQUEST_CODE = 1;
    public static final String TAG = PersonEditDelegate.class.getSimpleName();
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.cimg_icon)
    CircleImageView mCimgIcon;
    @BindView(R.id.et_name)
    EditText mEtName;
    private Person mPerson;

    @OnClick(R.id.cimg_icon)
    void clickCimgIcon() {
        startForResult(IconChooseDelegate.newInstance(), REQUEST_CODE);
    }

    @OnClick(R.id.btn_save)
    void save() {
        mPerson.setPname(mEtName.getText().toString());
        if (mPerson.getPid() == null || mPerson.getPid() == 0) {
            add(Client.getInstance().addPerson(mPerson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new MyObserver<Response<Person>>() {
                        @Override
                        public boolean showLoading() {
                            return true;
                        }

                        @Override
                        public void onSuccess(Response<Person> response) {
                            Toast.makeText(getActivity(), R.string.add_success, Toast.LENGTH_SHORT).show();
                            pop();
                        }
                    }));
        } else {
            add(Client.getInstance().updatePerson(mPerson)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new MyObserver<Response<Person>>() {
                        @Override
                        public boolean showLoading() {
                            return true;
                        }

                        @Override
                        public void onSuccess(Response<Person> response) {
                            Toast.makeText(getActivity(), R.string.update_success, Toast.LENGTH_SHORT).show();
                            pop();
                        }
                    }));
        }

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
        mIcBack.setOnClickListener(view -> pop());
        mToolbarTitle.setText("成员编辑");
        init();
    }

    public void init() {
        mPerson = (Person) getArguments().getSerializable(PERSON);
        if (mPerson != null) {
            mCimgIcon.setImage(Icon.getImgUrl(mPerson.getPimage()));
            mEtName.setText(mPerson.getPname());
        } else {
            mPerson = new Person();
            mPerson.setPimage(0);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        Log.i("TAG", "onFragmentResult: ");
        if (data != null) {
            int res = data.getInt(IconChooseDelegate.ICON);
            if (res != 0) {
                mPerson.setPimage(res);
                mCimgIcon.setImage(Icon.getImgUrl(res));
            }
        }
    }
}
