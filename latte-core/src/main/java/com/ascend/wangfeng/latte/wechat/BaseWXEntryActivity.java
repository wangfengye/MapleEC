package com.ascend.wangfeng.latte.wechat;

import com.alibaba.fastjson.JSONObject;
import com.ascend.wangfeng.latte.net.rx.BaseObserver;
import com.ascend.wangfeng.latte.net.rx.RxRestClient;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {
    /**
     * 用户登录成功后回调
     */
    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求后的回调
    @Override
    public void onReq(BaseReq req) {

    }

    //第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp resp) {
        final String code = ((SendAuth.Resp) resp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        Logger.d("authUrl:" + authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        RxRestClient.builder()
                .url(authUrl)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        Logger.i("wechat_"+s);
                        final JSONObject authObj = JSONObject.parseObject(s);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");
                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("%lang=zh_CN");
                        getUserInfo(userInfoUrl.toString());
                    }
                });
    }

    private void getUserInfo(String userInfoUrl) {
        RxRestClient.builder()
                .url(userInfoUrl)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        onSignInSuccess(s);
                    }
                });
    }
}
