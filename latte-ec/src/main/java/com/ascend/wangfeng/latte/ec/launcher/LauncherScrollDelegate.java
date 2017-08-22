package com.ascend.wangfeng.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ascend.wangfeng.latte.app.AccountManager;
import com.ascend.wangfeng.latte.app.IUserChecker;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.ascend.wangfeng.latte.ec.R;
import com.ascend.wangfeng.latte.ui.launcher.ILauncherListener;
import com.ascend.wangfeng.latte.ui.launcher.LauncherHolderCreator;
import com.ascend.wangfeng.latte.ui.launcher.OnLauncherFinishTag;
import com.ascend.wangfeng.latte.ui.launcher.ScrollLauncherTag;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener{
    private ConvenientBanner<Integer> mConvenientBanner =null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mLauncherListener;

    private void  initBanner(){
        INTEGERS.add(R.mipmap.launcher_0);
        INTEGERS.add(R.mipmap.launcher_1);
        INTEGERS.add(R.mipmap.launcher_2);
        INTEGERS.add(R.mipmap.launcher_3);
        mConvenientBanner.setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
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
        mConvenientBanner =new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        if (position ==INTEGERS.size()-1){
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            checkLogin();
        }
    }

    private void checkLogin() {
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
}
