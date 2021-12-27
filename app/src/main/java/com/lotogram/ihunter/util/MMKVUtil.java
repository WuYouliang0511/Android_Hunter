package com.lotogram.ihunter.util;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lotogram.ihunter.BuildConfig;
import com.lotogram.ihunter.bean.User;
import com.lotogram.ihunter.consts.SystemKey;
import com.lotogram.ihunter.consts.UserKey;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {

    private static final String TAG = "MMKVUtil";

    private static MMKV userMMKV;
    private static MMKV systemKKMV;

    public static void initSystemMMKV() {
        Log.d(TAG, "初始化系统信息本地存储");
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

    public static void setUser(@NonNull User user) {
        String id = user.get_id();
        Log.d(TAG, "保存当前用户ID: " + id);
        systemKKMV.encode(SystemKey.USER, id);

        initUserMMKV();
        set_id(user.get_id());
        setNickname(user.getNickname());
    }

    public static String getUser() {
        String id = systemKKMV.decodeString(SystemKey.USER);
        Log.d(TAG, "获取当前用户ID: " + id);
        return id;
    }
    //----------------------------------------------------------------------------------------------

    public static void initUserMMKV() {
        String id = getUser();
        if (TextUtils.isEmpty(id)) return;
        Log.d(TAG, "初始化用户信息本地存储");
        userMMKV = MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, "9527");
    }

    public static void set_id(String id) {
        if (userMMKV == null) return;
        Log.d(TAG, "保存当前用户id: " + id);
        userMMKV.encode(UserKey.ID, id);
    }

    @Nullable
    public static String get_id() {
        if (userMMKV == null) return null;
        String id = userMMKV.decodeString(UserKey.ID);
        Log.d(TAG, "获取当前用户id: " + id);
        return id;
    }

    public static void setNickname(String nickname) {
        if (userMMKV == null) return;
        Log.d(TAG, "保存当前用户nickname: " + nickname);
        userMMKV.encode(UserKey.NICKNAME, nickname);
    }

    @Nullable
    public static String getNickname() {
        if (userMMKV == null) return null;
        String nickname = userMMKV.decodeString(UserKey.NICKNAME);
        Log.d(TAG, "获取当前用户nickname: " + nickname);
        return nickname;
    }

    public static void setDescription(String description) {
        if (userMMKV == null) return;
        Log.d(TAG, "保存当前用户description: " + description);
        userMMKV.encode(UserKey.DESCRIPTION, description);
    }

    @Nullable
    public static String getDescription() {
        if (userMMKV == null) return null;
        String description = userMMKV.decodeString(UserKey.DESCRIPTION);
        Log.d(TAG, "获取当前用户description: " + description);
        return description;
    }

    public static void setToken(String token) {
        if (userMMKV == null) return;
        Log.d(TAG, "保存当前用户token: " + token);
        userMMKV.encode(UserKey.TOKEN, token);
    }

    @Nullable
    public static String getToken() {
        if (userMMKV == null) return null;
        String token = userMMKV.decodeString(UserKey.TOKEN);
        Log.d(TAG, "获取当前用户token: " + token);
        return token;
    }
}