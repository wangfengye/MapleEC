package com.ascend.wangfeng.latte.net;

import android.content.Context;

import com.ascend.wangfeng.latte.net.callback.IError;
import com.ascend.wangfeng.latte.net.callback.IFailure;
import com.ascend.wangfeng.latte.net.callback.IRequest;
import com.ascend.wangfeng.latte.net.callback.ISuccess;
import com.ascend.wangfeng.latte.net.download.DownloadHandler;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;

    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request,
                      String downloadDir,
                      String extension,
                      String name,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody body,
                      File file,
                      LoaderStyle loaderStyle,
                      Context context) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.DOWNLOAD_DIR=downloadDir;
        this.EXTENSION =extension;
        this.NAME=name;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.FILE = file;
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
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call= service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call=service.putRaw(URL,BODY);
                break;
            case DELETE:
                call= service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file",FILE.getName());
                call =service.upload(URL,body);
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
        if (BODY==null) {
            request(HttpMethod.POST);
        }else {
            if (PARAMS!=null){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }
    public final void put(){
        if (BODY==null) {
            request(HttpMethod.PUT);
        }else {
            if (PARAMS!=null){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void upload(){
        request(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,ERROR,FAILURE)
                .handleDownload();
    }
}
