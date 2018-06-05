package com.ascend.wangfeng.wifimanage.delegates.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.R;
import com.ascend.wangfeng.wifimanage.bean.Response;
import com.ascend.wangfeng.wifimanage.bean.User;
import com.ascend.wangfeng.wifimanage.delegates.MainDelegate;
import com.ascend.wangfeng.wifimanage.net.Client;
import com.ascend.wangfeng.wifimanage.net.MyObserver;
import com.ascend.wangfeng.wifimanage.net.SchedulerProvider;
import com.ascend.wangfeng.wifimanage.utils.MacUtil;
import com.ascend.wangfeng.wifimanage.utils.SpKey;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fengye on 2018/5/30.
 * email 1040441325@qq.com
 */

public class LoginDelegate extends LatteDelegate {
    @BindView(R.id.ic_back)
    IconTextView mIcBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.ic_edit)
    IconTextView mIcEdit;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_no)
    TextInputEditText mEtNo;
    @BindView(R.id.et_password)
    TextInputEditText mEtPassword;
    @BindView(R.id.btn_register)
    TextView mBtnRegister;

    @OnClick(R.id.btn_login)
    void clickBtnLogin() {
        Long mac = MacUtil.stringToLong(mEtNo.getText().toString().trim());
        String password = mEtPassword.getText().toString().trim();
        Client.getInstance().login(mac, password)
                .compose(SchedulerProvider.applyHttp())
                .subscribe(new MyObserver<Response<User>>() {
                    @Override
                    public boolean showLoading() {
                        return true;
                    }

                    @Override
                    public void onSuccess(Response<User> response) {
                        // 登录; 初始化
                        User user = new User();
                        user.setBmac(mac);
                        user.setUpasswd(password);
                        LattePreference.setJson(SpKey.USER, response.getData());
                        startWithPop(MainDelegate.newInstance());
                    }
                });
    }

    @OnClick(R.id.btn_register)
    void clickBtnRegister() {
        startWithPop(new ScanDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_login;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mToolbarTitle.setText("登录");
        User user = LattePreference.getJson(SpKey.USER, null);
        if (user != null) {
            mEtNo.setText(MacUtil.longToString(user.getBmac()));
            mEtPassword.setText(user.getUpasswd());
        }
    }

}
