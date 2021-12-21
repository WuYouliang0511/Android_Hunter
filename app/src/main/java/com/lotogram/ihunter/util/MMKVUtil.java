package com.lotogram.ihunter.util;

import android.text.TextUtils;
import android.util.Log;

import com.lotogram.ihunter.BuildConfig;
import com.lotogram.ihunter.consts.SystemKey;
import com.lotogram.ihunter.consts.UserKey;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {

    private static final String TAG = "MMKVUtil";

    private static MMKV userMMKV;
    private static MMKV systemKKMV;

    public static void initSystemMMKV() {
        systemKKMV = MMKV.mmkvWithID(SystemKey.SYSTEM, MMKV.MULTI_PROCESS_MODE, "9527");
        systemKKMV.encode(SystemKey.APP_ID, "10008");
        systemKKMV.encode(SystemKey.OS, "android");
        systemKKMV.encode(SystemKey.VERSION, BuildConfig.VERSION_NAME);
        systemKKMV.encode(SystemKey.CHANNEL, BuildConfig.FLAVOR);
        systemKKMV.encode(SystemKey.APP_TYPE, 1);
        systemKKMV.encode(SystemKey.KEY, "21o8329AsdjD129W2csamkjd129aasd11sc0cmx9nnxish12p1Osada");
    }

    public static int getAppType() {
        int appType = systemKKMV.decodeInt(SystemKey.APP_TYPE);
        Log.d(TAG, "获取appType: " + appType);
        return appType;
    }

    public static String getAppId() {
        String appId = systemKKMV.decodeString(SystemKey.APP_ID);
        Log.d(TAG, "获取appId: " + appId);
        return appId;
    }

    public static String getOS() {
        String os = systemKKMV.decodeString(SystemKey.OS);
        Log.d(TAG, "获取操作系统: " + os);
        return os;
    }

    public static String getChannel() {
        String channel = systemKKMV.decodeString(SystemKey.CHANNEL);
        Log.d(TAG, "获取渠道: " + channel);
        return channel;
    }

    public static String getVersion() {
        String version = systemKKMV.decodeString(SystemKey.VERSION);
        Log.d(TAG, "获取应用版本: " + version);
        return version;
    }

    public static String getKey() {
        String key = systemKKMV.decodeString(SystemKey.KEY);
        Log.d(TAG, "获取应用签名key: " + key);
        return key;
    }

    public static void setUser(String uid) {
        Log.d(TAG, "保存当前用户ID: " + uid);
        systemKKMV.encode(SystemKey.USER, uid);
    }

    public static String getUser() {
        String uid = systemKKMV.decodeString(SystemKey.USER);
        Log.d(TAG, "获取当前用户ID: " + uid);
        return uid;
    }
    //----------------------------------------------------------------------------------------------

    public static void initUserMMKV() {
        String uid = getUser();
        if (TextUtils.isEmpty(uid)) return;
        userMMKV = MMKV.mmkvWithID(uid, MMKV.MULTI_PROCESS_MODE, "9527");
    }

    public static String getToken() {
        if (userMMKV == null) return null;
        String token = userMMKV.decodeString(UserKey.TOKEN);
        Log.d(TAG, "获取当前用户token: " + token);
        return token;
    }
}