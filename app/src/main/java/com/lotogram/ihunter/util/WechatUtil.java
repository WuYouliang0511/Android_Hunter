package com.lotogram.ihunter.util;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.lotogram.ihunter.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WechatUtil {

    private static final String TAG = "WechatUtil";

    //表示发送给朋友
    public static final int SCENE_FRIEND = SendMessageToWX.Req.WXSceneSession;
    //表示发送到朋友圈
    public static final int SCENE_MOMENT = SendMessageToWX.Req.WXSceneTimeline;

    private static IWXAPI IWXAPI;

    public static void createWxapi(String appId) {
        Log.d(TAG, "createWxapi");
        MyApplication application = MyApplication.getInstance();
        IWXAPI = WXAPIFactory.createWXAPI(application, appId, false);
        IWXAPI.registerApp(appId);
    }

    public static IWXAPI getApi() {
        return IWXAPI;
    }

    public static void login() {
        Log.d(TAG, "发起微信登陆");
        //微信是否安装
        if (installed()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_login";
            req.transaction = String.valueOf(System.currentTimeMillis());
            boolean send = IWXAPI.sendReq(req);
            Log.d(TAG, "login: " + send);
        } else {
            install();
        }
    }

    public static boolean installed() {
        if (IWXAPI == null) {
            Log.d(TAG, "未实例化微信API");
            return false;
        }
        if (IWXAPI.isWXAppInstalled()) {
            Log.d(TAG, "微信已安装");
            return true;
        } else {
            Log.d(TAG, "微信未安装");
            return false;
        }
    }

    public static void install() {
        Log.d(TAG, "安装微信");
        if (!installInMarket()) {
            installInBrowser();
        }
    }

    //跳转至商店安装微信
    private static boolean installInMarket() {
        try {
            Uri uri = Uri.parse("market://details?id=com.tencent.mm");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            return false;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    //网页安装微信
    public static void installInBrowser() {
        try {
            String urlStr = "http://android.myapp.com/myapp/detail.htm?apkName=com.tencent.mm";
            Uri uri = Uri.parse(urlStr);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}