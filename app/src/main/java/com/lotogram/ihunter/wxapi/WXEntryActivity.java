package com.lotogram.ihunter.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lotogram.ihunter.util.ToastUtil;
import com.lotogram.ihunter.util.WechatUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    public static String IMEI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        WechatUtil.getApi().handleIntent(intent, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechatUtil.getApi().handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(@NonNull BaseResp resp) {
        Log.d(TAG, "Response");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED://授权拒绝
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
//                        ToastUtil.showWithLog(TAG, "登录授权拒绝");
                        Log.d(TAG, "登录授权拒绝: ");
                        break;
                    case RETURN_MSG_TYPE_SHARE:
//                        ToastUtil.showWithLog(TAG, "分享授权拒绝");
                        Log.d(TAG, "分享授权拒绝: ");
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
//                        ToastUtil.showWithLog(TAG, "登录失败");
                        Log.d(TAG, "登录失败: ");
                        break;
                    case RETURN_MSG_TYPE_SHARE:
//                        ToastUtil.showWithLog(TAG, "分享失败");
                        Log.d(TAG, "分享失败: ");
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        String code = ((SendAuth.Resp) resp).code;
//                        login(code);
                        ToastUtil.show("登录成功");
                        Log.d(TAG, "登录成功: ");
                        break;
                    case RETURN_MSG_TYPE_SHARE:
//                        ToastUtil.showWithLog(TAG, "分享成功");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        finish();
    }

//    private void login(String code) {
//        Map<String, Object> map = RequestBodyBuilder.getBaseMap();
//        map.put(Key.CODE, code);
//        map.put(Key.IMEI, IMEI);
//        RequestBody body = RequestBodyBuilder.getBody(map);
//        HttpEngine.loginByWeChat(body, new BaseObserver<WeChatLoginResp>() {
//            @Override
//            public void onNext(@NonNull WeChatLoginResp response) {
//                super.onNext(response);
//                if (response.isOk()) {
//                    MMKVUtil.setUserInfo(response.getUser());
//                    MMKVUtil.setUserToken(response.getToken());
//                    MMKVUtil.setAdultMode(response.getAdultMode());
//
//                    LoginEvent event = new LoginEvent();
//                    if (response.getUser().getIdcard() == 1) {
//                        if (response.getUser().getAge() >= 18) {
//                            event.setStatus(1);
//                            Log.d(TAG, "已成年，登录成功");
//                        } else {
//                            event.setStatus(0);
//                            Log.d(TAG, "未成年,禁止登录");
//                        }
//                    } else {
//                        event.setStatus(1);
//                        Log.d(TAG, "未实名（允许登录，登录后需进行实名）");
//                    }
//                    EventBus.getDefault().post(event);
//                }
//            }
//        });
//    }
}