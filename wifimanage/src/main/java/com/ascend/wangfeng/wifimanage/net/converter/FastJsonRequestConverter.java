package com.ascend.wangfeng.wifimanage.net.converter;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by fengye on 2018/5/29.
 * email 1040441325@qq.com
 */

public class FastJsonRequestConverter<T> implements Converter<T, RequestBody>{
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
