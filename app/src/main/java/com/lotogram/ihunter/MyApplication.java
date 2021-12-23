package com.lotogram.ihunter;

import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.lotogram.ihunter.network.http.BaseObserver;
import com.lotogram.ihunter.network.http.HttpEngine;
import com.lotogram.ihunter.network.http.response.AppInfoResp;
import com.lotogram.ihunter.util.BuglyUtil;
import com.lotogram.ihunter.util.MMKVUtil;
import com.lotogram.ihunter.util.WechatUtil;
import com.tencent.mmkv.MMKV;

public class MyApplication extends MultiDexApplication {

    private final String TAG = this.getClass().getSimpleName();

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

                    String bugly = response.getBuglyId();
                    iniBugly(bugly);
                }
            }
        });
    }

    private void iniBugly(String appId) {
        BuglyUtil.init(this, appId);
        BuglyUtil.setUserSceneTag(this, 1997051111);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory: ");
    }
}