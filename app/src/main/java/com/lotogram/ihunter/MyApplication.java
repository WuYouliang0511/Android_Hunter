package com.lotogram.ihunter;

import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.lotogram.ihunter.network.http.BaseObserver;
import com.lotogram.ihunter.network.http.HttpEngine;
import com.lotogram.ihunter.network.http.response.AppInfoResp;
import com.lotogram.ihunter.util.MMKVUtil;
import com.lotogram.ihunter.util.WechatUtil;
import com.tencent.mmkv.MMKV;

public class MyApplication extends MultiDexApplication {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initMMKV();
        initAppInfo();
    }

    private void initMMKV() {
        MMKV.initialize(this);
        MMKVUtil.initSystemMMKV();
        MMKVUtil.initUserMMKV();
    }

    private void initAppInfo() {
        HttpEngine.getAppInfo(new BaseObserver<AppInfoResp>() {
            @Override
            public void onNext(@NonNull AppInfoResp response) {
                super.onNext(response);
                if (response.isOk()) {
                    String wechat = response.getWechatId();
                    WechatUtil.createWxapi(wechat);
                }
            }
        });
    }

    @Override

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}