package com.loto.ihunter.util;

import com.loto.ihunter.consts.SystemKey;
import com.tencent.mmkv.MMKV;

public class MMKVUtil {

    private static final String TAG = "MMKVUtil";

    private static MMKV userMMKV;
    private static MMKV systemKKMV;

    public static void initSystemMMKV() {
        systemKKMV = MMKV.mmkvWithID(SystemKey.SYSTEM, MMKV.MULTI_PROCESS_MODE, "9527");
        systemKKMV.encode("name", "WYL");

    }

    public static void setName(String name) {
        systemKKMV.encode("name", name);
    }

    public static String getName() {
        return systemKKMV.decodeString("name", "name");
    }
}