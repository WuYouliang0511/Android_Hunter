package com.lotogram.ihunter.network.http;

import com.lotogram.ihunter.MyApplication;
import com.lotogram.ihunter.consts.Url;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {

    private static ApiRequest instance;
    private Retrofit retrofit = null;

    public static ApiRequest getInstance() {
        if (instance == null) {
            synchronized (ApiRequest.class) {
                if (instance == null) {
                    instance = new ApiRequest();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        initRetrofit();
        return retrofit.create(service);
    }

    private void initRetrofit() {
        try {
            final String type = KeyStore.getDefaultType();
            final KeyStore keyStore = KeyStore.getInstance(type);
            keyStore.load(null, null);

            HostnameVerifier verifier = (hostname, session) -> true;

            File file = MyApplication.getInstance().getCacheDir();
            File cacheFile = new File(file, "HunterCache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);

            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(new OKHttpInterceptor())
                    // .sslSocketFactory(ssl.getSSLContext().getSocketFactory(), ssl.getTrustManager())
                    .hostnameVerifier(verifier)
                    .build();

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(0, TimeUnit.SECONDS)//设置缓存数据的有效期，超出有效期自动清空
                    .maxStale(28, TimeUnit.DAYS)//设置缓存数据过时时间，超出时间自动清空
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.getHttp())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }
}