package com.ascend.wangfeng.latte.net;

import android.content.Context;

import com.ascend.wangfeng.latte.net.callback.IError;
import com.ascend.wangfeng.latte.net.callback.IFailure;
import com.ascend.wangfeng.latte.net.callback.IRequest;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.ui.LatteLoader;
import com.ascend.wangfeng.latte.ui.LoaderStyle;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RestClient {
    private final String URL;
    private static final Map<String,Object> PARAMS=RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      Context context) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT =context;
    }
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }
    private void request(HttpMethod method){
        final  RestService service=RestCreator.getRestService();
        Call<String> call=null;
        if (REQUEST!=null){
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call= service.get(URL,PARAMS);
                break;
            case POST:
                call= service.post(URL,PARAMS);
                break;
            case PUT:
                call= service.put(URL,PARAMS);
                break;
            case DELETE:
                call= service.delete(URL,PARAMS);
                break;
            default:
                break;
        }
        if (call!=null){
            call.enqueue(getRequestCallback());
        }
    }
private Callback<String> getRequestCallback(){
    return new RequestCallback(REQUEST,SUCCESS,ERROR,FAILURE,LOADER_STYLE);
}
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        request(HttpMethod.POST);
    }
    public final void put(){
        request(HttpMethod.PUT);
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
}
