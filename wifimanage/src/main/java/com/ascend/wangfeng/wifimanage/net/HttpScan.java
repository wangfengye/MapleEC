package com.ascend.wangfeng.wifimanage.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fengye on 2018/5/30.
 * email 1040441325@qq.com
 * 扫描局域网中的Hbox(即请求可以成功的ip)
 */

public class HttpScan {
    boolean doCallback = false;// 保证doCallback执行一次

    public static final String TAG = HttpScan.class.getSimpleName();
    private ExecutorService pool;
    Callback mCallback;

    public void init() {
        pool = Executors.newFixedThreadPool(10);
        for (int i = 1; i < 256; i++) {
            String url = String.format("http://192.168.168.%d", i);
            Runnable runnable = () -> {
                boolean has = createHttp(url + "/get_sta_mac");
                Log.i(TAG, "run: " + url + "---" + has);
                if (has) {
                    if (mCallback != null && !doCallback) {
                        doCallback = true;
                        mCallback.callback(url, has);
                    }
                }
            };
            pool.execute(runnable);
        }
        pool.shutdown();// 禁止新任务提交
        new Thread(() -> {
            while (!pool.isTerminated()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mCallback != null && !doCallback) {
                doCallback = true;
                mCallback.callback(null, false);
            }
        }).start();
    }

    public void scan(Callback callback) {
        this.mCallback = callback;
        init();
        ;
    }


    private boolean createHttp(String urlStr) {
        URL url = null;
        boolean success = false;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(2000);
            conn.connect();

            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                success = getResponse(is);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
            return success;
        }

    }

    private boolean getResponse(InputStream is) throws IOException {
        InputStreamReader iReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(iReader);
        String a = reader.readLine();
        Log.i(TAG, "getResponse: " + a);
        // 返回json中有mac字段说明请求地址正确
        JSONObject object = JSON.parseObject(a);
        String s = object.getString("mac");
        if (s != null) {
            return true;
        }
        return false;
    }

    //  注销回调
    public void unRegister() {
        doCallback = true;
    }

    public interface Callback {
        void callback(String url, boolean has);
    }
}
