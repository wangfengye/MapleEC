package com.ascend.wangfeng.latte.ec.sign;

import android.app.Activity;
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

public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.account)
    TextInputEditText mAccount;
    @BindView(R2.id.password)
    TextInputEditText mPassword;
    @BindView(R2.id.repassword)
    TextInputEditText mRepassword;
    private ISignListener mSignListener;
    private String account;
    private String password;

    @OnClick(R2.id.submit)
    void onclickSubmit(){
        if (checkForm()){
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
                        public void onNext(@NonNull String s) {
                            SignHandler.onSignIn(s,mSignListener);
                        }
                    });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mSignListener = (ISignListener)activity;
        }
    }

    private boolean checkForm(){
        boolean ispass =true;
        account = mAccount.getText().toString();
        password = mPassword.getText().toString();
        final String repassword = mRepassword.getText().toString();
        if (account.isEmpty()){
            mAccount.setError("请输入账号");
            ispass=false;
        }
        if (password.isEmpty()||password.length()<6){
            mPassword.setError("请输入至少六位数密码");
            ispass=false;
        }
        if(!repassword.equals(password)){
            mRepassword.setError("密码验证错误");
            ispass=false;
        }
        return ispass;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

}
