package com.ascend.wangfeng.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.latte.app.AccountManager;
import com.ascend.wangfeng.latte.ec.database.DatabaseManager;
import com.ascend.wangfeng.latte.ec.database.User;

/**
 * Created by fengye on 2017/8/22.
 * email 1040441325@qq.com
 */

public class SignHandler {
    public static void onSignIn(String response,ISignListener signListener){
        final User user = JSON.parseObject(response,User.class);
        DatabaseManager.getInstance().getDao().insert(user);
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }
}
