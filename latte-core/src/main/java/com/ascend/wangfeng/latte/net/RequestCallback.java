package com.ascend.wangfeng.latte.net;

import com.ascend.wangfeng.latte.net.callback.IError;
import com.ascend.wangfeng.latte.net.callback.IFailure;
import com.ascend.wangfeng.latte.net.callback.IRequest;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RequestCallback implements retrofit2.Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderStyle LOADER_STYLE;

    public RequestCallback(IRequest REQUEST, ISuccess SUCCESS, IError ERROR, IFailure FAILURE,
                           LoaderStyle loaderStyle) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        if (LOADER_STYLE != null) {
            LatteLoader.stopLoading();
        }
    }

    @Override
    public void onFailure(retrofit2.Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        if (LOADER_STYLE != null) {
            LatteLoader.stopLoading();
        }
    }
}
