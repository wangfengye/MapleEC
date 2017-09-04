package com.ascend.wangfeng.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.ascend.wangfeng.latte.app.AccountManager;
import com.ascend.wangfeng.latte.app.IUserChecker;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ec.R2;
import com.ascend.wangfeng.latte.ui.launcher.ILauncherListener;
import com.ascend.wangfeng.latte.ui.launcher.OnLauncherFinishTag;
import com.ascend.wangfeng.latte.ui.launcher.ScrollLauncherTag;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.latte.util.timer.BaseTimerTask;
import com.ascend.wangfeng.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by fengye on 2017/8/21.
 * email 1040441325@qq.com
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvLauncherTimer;
    private Timer mTimer;
    private int mCount = 5;
    private ILauncherListener mLauncherListener;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        checkIsShowScroll();
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }
    private void checkIsShowScroll(){
        if(!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){

            start(new LauncherScrollDelegate(),SINGLETASK);
        }else{
            //检查登录
            checkIsLogin();
        }
    }

    private void checkIsLogin() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
            if (mLauncherListener!=null){
                mLauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
            }
            }

            @Override
            public void onNotSingIn() {
                if (mLauncherListener!=null){
                    mLauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mLauncherListener =(ILauncherListener)activity;
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvLauncherTimer != null) {
                    mTvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}
