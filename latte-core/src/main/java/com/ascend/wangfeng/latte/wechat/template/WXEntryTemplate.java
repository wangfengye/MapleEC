package com.ascend.wangfeng.latte.wechat.template;

import com.ascend.wangfeng.latte.wechat.BaseWXEntryActivity;
import com.ascend.wangfeng.latte.wechat.LatteWeChat;

/**
 * Created by fengye on 2017/8/23.
 * email 1040441325@qq.com
 */

public class WXEntryTemplate extends BaseWXEntryActivity{

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getWeChatSignInCallback().onSignInSuccess(userInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }
}
