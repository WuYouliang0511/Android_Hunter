package com.lotogram.ihunter.util;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtil {

    private static final String TAG = "JsonUtil";

    @NonNull
    public static JSONObject generateJson(@NonNull Map<String, Object> map) {
        Map<String, Object> res = new HashMap<>();
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                res.put(key, value);
            }
        }
        return new JSONObject(res);
    }

    public static String toPrettyFormat(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(JsonParser.parseString(json));
    }
}