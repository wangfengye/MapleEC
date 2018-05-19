package com.ascend.wangfeng.wifimanage.net;




import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fengye on 2018/5/18.
 * email 1040441325@qq.com
 */

public interface AliApi {
    @GET("data")
    Call<ResponseBody> getData();
}
