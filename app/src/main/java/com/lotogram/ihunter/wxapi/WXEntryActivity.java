package com.lotogram.ihunter.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lotogram.ihunter.consts.SystemKey;
import com.lotogram.ihunter.databinding.LayoutEmptyPageBinding;
import com.lotogram.ihunter.event.LoginEvent;
import com.lotogram.ihunter.mvvm.BaseActivity;
import com.lotogram.ihunter.network.http.BaseObserver;
import com.lotogram.ihunter.network.http.HttpEngine;
import com.lotogram.ihunter.network.http.RequestBodyBuilder;
import com.lotogram.ihunter.network.http.response.WechatLoginResp;
import com.lotogram.ihunter.util.MMKVUtil;
import com.lotogram.ihunter.util.WechatUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import okhttp3.RequestBody;

public class WXEntryActivity extends BaseActivity<LayoutEmptyPageBinding> implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

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
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(@NonNull BaseResp resp) {
        String code = ((SendAuth.Resp) resp).code;
        Log.d(TAG, "code: " + code);
        switch (resp.getType()) {
            case RETURN_MSG_TYPE_LOGIN:
                Log.d(TAG, "处理登录事件");
                dealWithLogin(resp.errCode, code);
                break;
            case RETURN_MSG_TYPE_SHARE:
                Log.d(TAG, "处理登录事件");
                dealWithShare(resp.errCode);
                break;
            default:
                break;
        }
    }

    private void dealWithLogin(int errCode, String authCode) {
        switch (errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED://登录授权拒绝
                Log.d(TAG, "登录授权拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消登录
                Log.d(TAG, "用户取消登录");
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK://用户登录成功
                Log.d(TAG, "用户登录成功");
                loginByWechat(authCode);
                break;
        }
    }

    private void dealWithShare(int errCode) {
        switch (errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED://分享授权拒绝
                Log.d(TAG, "分享授权拒绝");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消分享
                Log.d(TAG, "用户取消分享");
                break;
            case BaseResp.ErrCode.ERR_OK://用户分享成功
                Log.d(TAG, "用户分享成功");
                break;
        }
    }

    private void loginByWechat(String code) {
        TreeMap<String, Object> map = RequestBodyBuilder.getBaseMap();
        map.put(SystemKey.CODE, code);
        RequestBody body = RequestBodyBuilder.getBody(map);
        HttpEngine.loginByWeChat(body, new BaseObserver<WechatLoginResp>() {
            @Override
            public void onNext(@NonNull WechatLoginResp response) {
                super.onNext(response);
                if (response.isOk()) {
                    MMKVUtil.setUser(response.getUser());
                    MMKVUtil.setToken(response.getToken());
//                    MMKVUtil.setAdultMode(response.getAdultMode());
//
                    LoginEvent event = new LoginEvent();
                    event.setType(LoginEvent.WECHAT);
                    EventBus.getDefault().post(event);

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
//
                }
                finish();
            }
        });
    }
}