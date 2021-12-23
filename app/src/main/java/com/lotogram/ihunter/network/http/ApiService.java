package com.lotogram.ihunter.network.http;

import com.lotogram.ihunter.network.http.response.AppInfoResp;
import com.lotogram.ihunter.network.http.response.WechatLoginResp;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    //获取应用信息
    @POST("app/info")
    Observable<AppInfoResp> getAppInfo(@Body RequestBody body);

    //微信登录
    @POST("user/loginByWechat")
    Observable<WechatLoginResp> loginByWeChat(@Body RequestBody body);
}