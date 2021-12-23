package com.lotogram.ihunter.util;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.lotogram.ihunter.MyApplication;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
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
        try {
            Log.d(TAG, "跳转至商店安装微信");
            Uri uri = Uri.parse("market://details?id=com.tencent.mm");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            MyApplication.getInstance().startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "跳转至官网安装微信");
            String urlStr = "http://android.myapp.com/myapp/detail.htm?apkName=com.tencent.mm";
            Uri uri = Uri.parse(urlStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            MyApplication.getInstance().startActivity(intent);
        }
    }

    public static void sendText(String text, int scene) {
        //创建一个用于封装分享文本的WXTextObject对象
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;//text为String类型
        //创建WXMediaMessage对象，该对象用于Android客户端向微信发送数据
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;//text为String类型，设置描述，可省略
        send(msg, scene);
    }

    public static void sendImage(String path, int scene) {
        //获取二进制图形的Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        //创建WXImageObject对象，并包装bitmap
        WXImageObject imageObject = new WXImageObject(bitmap);
        //创建WXMediaMessage对象，并包装imageObject
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        //图像缩略图
        //Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        //bitmap1.recycle();
        //msg.thumbData = b
        send(msg, scene);
    }

    public static void sendImage(Bitmap bitmap, int scene) {
        //创建WXImageObject对象，并包装bitmap
        WXImageObject imageObject = new WXImageObject(bitmap);
        //创建WXMediaMessage对象，并包装imageObject
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        //图像缩略图
        //Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
        //bitmap1.recycle();
        //msg.thumbData = b
        send(msg, scene);
    }

    private static void send(WXMediaMessage msg, int scene) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.scene = scene;
        //微信是否安装
        if (IWXAPI.isWXAppInstalled()) {
            boolean send = IWXAPI.sendReq(req);
            Log.d(TAG, "发送信息: " + send);
        } else {
            Log.d(TAG, "未安装微信");
            install();
        }
    }
}