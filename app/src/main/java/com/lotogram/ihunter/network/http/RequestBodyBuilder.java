package com.lotogram.ihunter.network.http;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lotogram.ihunter.consts.SystemKey;
import com.lotogram.ihunter.consts.UserKey;
import com.lotogram.ihunter.util.JsonUtil;
import com.lotogram.ihunter.util.MMKVUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.TreeMap;

import okhttp3.RequestBody;

public class RequestBodyBuilder {

    private static final String TAG = "RequestBodyBuilder";

    @NonNull
    public static TreeMap<String, Object> getBaseMap() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put(SystemKey.APP_TYPE, MMKVUtil.getAppType());
        map.put(SystemKey.APP_ID, MMKVUtil.getAppId());
        map.put(SystemKey.VERSION, MMKVUtil.getVersion());
        map.put(SystemKey.CHANNEL, MMKVUtil.getChannel());
        map.put(SystemKey.TS, System.currentTimeMillis());
        map.put(SystemKey.OS, MMKVUtil.getOS());
        map.put(UserKey.TOKEN, MMKVUtil.getToken());
        return map;
    }

    @NonNull
    public static RequestBody getBaseBody() {
        TreeMap<String, Object> map = getBaseMap();
        map.put(SystemKey.SIGN, sign(map));
        String json = JsonUtil.generateJson(map).toString();
        return RequestBody.create(MediaTypes.JSON, json);
    }

    @NonNull
    private static String sign(@NonNull TreeMap<String, Object> map) {
        map.put(SystemKey.KEY, MMKVUtil.getKey());
        String rawMap = raw(map);
        Log.d(TAG, "raw: " + rawMap);
        String sign = md5(rawMap);
        map.remove(SystemKey.KEY);
        return sign;
    }

    @NonNull
    public static String raw(@NonNull TreeMap<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        for (String key : map.keySet()) {
            Object object = map.get(key);
            Log.d(TAG, "key: " + key);
            if (object == null) {
                Log.d(TAG, "the value,which  of the key is" + key + " is null");
            } else if (object instanceof String) {
                builder.append(",\"").append(key).append("\":\"").append(object).append("\"");
            } else if (object instanceof Number) {
                builder.append(",\"").append(key).append("\":").append(object);
            } else if (object instanceof Boolean) {
                boolean a = (boolean) object;
                builder.append(",\"").append(key).append("\":").append(a ? 1 : 0);
            } else if (object instanceof List) {
                String str = new Gson().toJson(object);
                builder.append(",\"").append(key).append("\":").append(str);
            }
//            else if (object instanceof Map) {
//                Map<String, Object> map1 = JSONObject.parseObject(JSON.toJSONString(object));
//                builder.append(",\"").append(key).append("\":").append(raw(map1));
//            }
        }
        String raw = builder.toString();
        if (raw.length() > 0) {
            return "{" + raw.substring(1) + "}";
        }
        return "";
    }

    @NonNull
    public static String md5(String message) {
        if (TextUtils.isEmpty(message)) {
            throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
        }
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(message.getBytes());
            byte[] hash = md5.digest();

            for (byte b : hash) {
                if ((0xff & b) < 0x10) {
                    builder.append("0").append(Integer.toHexString(0xff & b));
                } else {
                    builder.append(Integer.toHexString(0xff & b));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

//    @NonNull
//    public static Map<String, Object> getLoginMap() {
//        Map<String, Object> map = new HashMap<>();
//        map.put(Key.MOBILE, "11011110025");
//        map.put(Key.VERICODE, "123456");
//        map.put(Key.APP_ID, MMKVUtil.getAppId());
//        map.put(Key.APP_VERSION, MMKVUtil.getAppVersion());
//        map.put(Key.CHANNEL, MMKVUtil.getChannel());
//        map.put(Key.TS, System.currentTimeMillis());
//        map.put(Key.OS, MMKVUtil.getOS());
//        map.put(Key.APP_TYPE, MMKVUtil.getAppType());
//        return map;
//    }
//
//    @NonNull
//    public static Map<String, Object> getMobileLoginMap() {
//        Map<String, Object> map = new HashMap<>();
//        map.put(Key.APP_ID, MMKVUtil.getAppId());
//        map.put(Key.APP_VERSION, MMKVUtil.getAppVersion());
//        map.put(Key.CHANNEL, MMKVUtil.getChannel());
//        map.put(Key.TS, System.currentTimeMillis());
//        map.put(Key.OS, MMKVUtil.getOS());
//        map.put(Key.APP_TYPE, MMKVUtil.getAppType());
//        return map;
//    }


//    @NonNull
//    public static RequestBody getBaseBody() {
//        Map<String, Object> map = getBaseMap();
//        map.put(Key.SIGN, SignUtil.getSign(map));
//        String json = JsonUtil.generateJson(map).toString();
//        return RequestBody.create(json, MediaTypes.JSON);
//    }
//
//    @NonNull
//    public static RequestBody getBody(@NonNull Map<String, Object> map) {
//        map.put(Key.SIGN, SignUtil.getSign(map));
//        String json = JsonUtil.generateJson(map).toString();
//        return RequestBody.create(json, MediaTypes.JSON);
//    }
}