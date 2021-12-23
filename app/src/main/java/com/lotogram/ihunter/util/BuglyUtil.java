package com.lotogram.ihunter.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lotogram.ihunter.BuildConfig;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.CrashHandleCallback;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class BuglyUtil {

    private static final String TAG = "BuglyUtil";

    public static void init(@NonNull Context context, String appId) {
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(Process.myPid());
        // 设置是否为上报进程
        UserStrategy strategy = new UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setAppChannel(BuildConfig.FLAVOR);
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
        strategy.setAppPackageName(context.getPackageName());
        strategy.setCrashHandleCallback(new CrashHandleCallback() {

            public Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("crashType", String.valueOf(crashType));
                map.put("errorType", errorType);
                map.put("errorMessage", errorMessage);
                map.put("errorStack", errorStack);
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("crashType", String.valueOf(crashType));
                map.put("errorType", errorType);
                map.put("errorMessage", errorMessage);
                map.put("errorStack", errorStack);
                return map.toString().getBytes(StandardCharsets.UTF_8);
            }
        });

        CrashReport.putUserData(context, "1111", "12345");
        CrashReport.putUserData(context, "2222", "54321");
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);

        // 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly
        // 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        // 1、输出详细的Bugly SDK的Log
        // 2、每一条Crash都会被立即上报
        // 3、自定义日志将会在Logcat中输出
        // 建议在测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport(context, appId, false, strategy);
    }

    public static void setUserId(String userId) {
        if (TextUtils.isEmpty(userId)) {
            CrashReport.setUserId("unknown");
        } else {
            CrashReport.setUserId(userId);
        }
    }

    // 上报后的Crash会显示该标签
    public static void setUserSceneTag(Context context, @IntRange(from = 1, to = Integer.MAX_VALUE) int tagId) {
        CrashReport.setUserSceneTag(context, tagId);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    @Nullable

    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            String fileName = "/proc/" + pid + "/cmdline";
            FileReader fileReader = new FileReader(fileName);
            reader = new BufferedReader(fileReader);
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void checkUpdate(@NonNull Context context, boolean isManual) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            long firstInstallTime = packageInfo.firstInstallTime;
            long lastUpdateTime = packageInfo.lastUpdateTime;
            Log.d(TAG, "firstInstallTime: " + firstInstallTime);
            Log.d(TAG, "lastUpdateTime: " + lastUpdateTime);
            long firstInstallInterval = System.currentTimeMillis() - firstInstallTime;
            long lastUpdateInterval = System.currentTimeMillis() - lastUpdateTime;
            if (firstInstallInterval > 2 * 60 * 1000 || lastUpdateInterval > 2 * 60 * 1000) {
                Log.d(TAG, "Bugly Check Update");
                Beta.checkUpgrade(isManual, false);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}