package com.ascend.wangfeng.latte.net.rx;

import android.content.Context;

import com.ascend.wangfeng.latte.net.HttpMethod;
import com.ascend.wangfeng.latte.net.RestCreator;
import com.ascend.wangfeng.latte.ui.loader.LatteLoader;
import com.ascend.wangfeng.latte.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class RxRestClient {
    public final static  int TYPE_DEFAULT=0;//io ,main
    private final static  int TYPE_DIY=1;//自行添加工作线程的切换
    private final String URL;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;
    private final int  TYPE;//type决定observer,subject的工作线程


    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        LoaderStyle loaderStyle,
                        Context context,int type) {
        this.URL = url;
        this.PARAMS.putAll(params);

        this.BODY = body;
        this.FILE = file;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
        this.TYPE = type;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName());
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        if (observable != null&& TYPE == TYPE_DEFAULT) {
            observable=observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return observable;
    }


    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (PARAMS != null) {
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (PARAMS != null) {
                throw new RuntimeException("params must be null");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download() {
        final Observable<ResponseBody> responseBodyObservable = RestCreator.getRxRestService()
                .download(URL, PARAMS);
        return responseBodyObservable;
    }

    //target:简化设置线程的工作,未完成
    public Observable<String> subscribeBase() {
        return get().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
