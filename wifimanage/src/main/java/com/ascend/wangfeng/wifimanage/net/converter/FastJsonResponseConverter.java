package com.ascend.wangfeng.wifimanage.net.converter;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by fengye on 2018/5/29.
 * email 1040441325@qq.com
 */

public class FastJsonResponseConverter<T> implements Converter<ResponseBody,T>{
    private final Type type;

    public FastJsonResponseConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String temp = bufferedSource.readUtf8();
        return JSON.parseObject(temp,type);
    }
}
