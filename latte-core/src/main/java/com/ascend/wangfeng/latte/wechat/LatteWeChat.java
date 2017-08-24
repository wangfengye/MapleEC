package com.ascend.wangfeng.latte.wechat;

import android.content.Context;

import com.ascend.wangfeng.latte.app.ConfigType;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.wechat.callback.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by fengye on 2017/8/23.
 * email 1040441325@qq.com
 */

public class LatteWeChat {
    static final String APP_ID = Latte.getConfiguration(ConfigType.WECHAT_APP_ID);
    static final String APP_SECRET = Latte.getConfiguration(ConfigType.WECHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mWeChatSignInCallback;
    private static final class Holder{
        private static final LatteWeChat INSTANCE =new LatteWeChat();
    }
    public static LatteWeChat getInstance(){
        return Holder.INSTANCE;
    }
    private LatteWeChat(){
        //建议使用Activity的content,暂未使用
        final Context context =Latte.getApplicationContext();
        WXAPI = WXAPIFactory.createWXAPI(context,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }
    public final IWXAPI getWXAPI(){
        return WXAPI;
    }
    public LatteWeChat onSignSucess(IWeChatSignInCallback callback){
        this.mWeChatSignInCallback =callback;
        return  this;
    }
    public IWeChatSignInCallback getWeChatSignInCallback(){
        return mWeChatSignInCallback;
    }
    public final void signIn(){
        final SendAuth.Req req =new SendAuth.Req();
        req.scope="snsapi_userinfo";
        req.state ="random_state";
        WXAPI.sendReq(req);
    }


}
