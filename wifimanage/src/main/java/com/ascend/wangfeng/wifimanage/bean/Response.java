package com.ascend.wangfeng.wifimanage.bean;

/**
 * Created by fengye on 2018/5/24.
 * email 1040441325@qq.com
 */

public class Response<T> {
    private String message;
    private int statusCode;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
