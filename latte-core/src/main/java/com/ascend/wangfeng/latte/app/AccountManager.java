package com.ascend.wangfeng.latte.app;

import com.ascend.wangfeng.latte.util.storage.LattePreference;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }
    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }
    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSingIn();
        }
    }
}
