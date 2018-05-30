package com.ascend.wangfeng.wifimanage.net;


import com.ascend.wangfeng.wifimanage.bean.Box;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by fengye on 2018/5/30.
 * email 1040441325@qq.com
 */

public interface LocalApi {
    @GET("get_sta_mac")
    Observable<Box> getBoxInfo();
}
