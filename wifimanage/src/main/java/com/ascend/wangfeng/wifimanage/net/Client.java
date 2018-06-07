package com.ascend.wangfeng.wifimanage.net;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.util.NetUtil;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.ascend.wangfeng.wifimanage.MainApp;
import com.ascend.wangfeng.wifimanage.net.converter.FastJsonConverterFactory;
import com.ascend.wangfeng.wifimanage.utils.SpKey;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by fengye on 2018/5/18.
 * email 1040441325@qq.com
 */

public class Client {
    private static final String BASE_URL = "http://192.168.168.61:8080";

    public static AliApi getInstance() {
        if (MainApp.mDemo) return Build.mDemo;
        return Build.mAliApi;
    }

    public static LocalApi getLocalApi() {
        return Build.mLocalApi;
    }

    public static void resetLocalApi() {
        String url = LattePreference.getAppPreference().getString(SpKey.LOCAL_URL, "");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        Build.mLocalApi = retrofit.create(LocalApi.class);
    }

    private static class Build {
        public static DemoApi mDemo = new DemoApi();
        public static AliApi mAliApi;

        static {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getUnsafeOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
            mAliApi = retrofit.create(AliApi.class);
        }

        public static LocalApi mLocalApi;

        static {
            // 不规范的url会导致 retrofit创建时illegalArgumentException,
            String url = LattePreference.getAppPreference().getString(SpKey.LOCAL_URL, "http://192.168.168.112");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(2, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit1 = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();
            mLocalApi = retrofit1.create(LocalApi.class);
        }

        /**
         * 跳过https证书认证
         *
         * @return
         */
        private static OkHttpClient getUnsafeOkHttpClient() {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[0];
                            }
                        }
                };
                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                HostnameVerifier verifier = new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                // 添加cookie
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MainApp.getContent()));
                // 缓存
                File cacheDir = new File(Latte.getApplicationContext().getCacheDir(), "response");
                //缓存的最大尺寸10m
                Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);


                return new OkHttpClient.Builder()
                        .sslSocketFactory(sslSocketFactory)
                        .hostnameVerifier(verifier)
                        .cookieJar(cookieJar)
                        .cache(cache)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                boolean netAvaiable = NetUtil
                                        .isNetAvaiable(Latte.getApplicationContext());
                                if (netAvaiable) {
                                    // 强制走网络
                                    request = request.newBuilder()
                                            .cacheControl(CacheControl.FORCE_NETWORK)
                                            .build();
                                } else {
                                    request = request.newBuilder()
                                            .cacheControl(CacheControl.FORCE_CACHE)
                                            .build();
                                }
                                Response response = chain.proceed(request);
                                if (!netAvaiable) {
                                    response = response.newBuilder()
                                            .removeHeader("Pragma")//移除干扰的头信息
                                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
                                            .build();
                                }
                                return response;
                            }
                        })
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
