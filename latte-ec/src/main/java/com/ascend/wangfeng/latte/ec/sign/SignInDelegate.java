package com.ascend.wangfeng.latte.ec.sign;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class SignInDelegate extends LatteDelegate {
    @BindView(R2.id.account)
    TextInputEditText mAccount;
    @BindView(R2.id.password)
    TextInputEditText mPassword;
    private ISignListener mSignListener;
    private String account;
    private String password;

    @OnClick(R2.id.submit)
    void onClickSubmit() {
        if (checkForm()) {
            //进入主页
            //检查账号密码
            RxRestClient.builder()
                    .url("user")
                    .params("account",account)
                    .params("password",password)
                    .build()
                    .get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<String>() {
                        @Override
                        public Context getContext() {
                            return SignInDelegate.this.getContext();
                        }

                        @Override
                        public void onNext(@NonNull String s) {
                            SignHandler.onSignIn(s,mSignListener);

                        }
                    });
        }
    }

    @OnClick(R2.id.sign_up)
    void onClickSignUp() {
        start(new SignUpDelegate(), SINGLETASK);
    }

    private boolean checkForm() {
         account = mAccount.getText().toString();
        password = mPassword.getText().toString();
        if (account.isEmpty()) {
            mAccount.setError("请输入账号");
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请输入六位数的密码");
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mSignListener =(ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initData();
    }

    private void initData() {

    }

}
