package com.lotogram.ihunter.network.http;

import com.lotogram.ihunter.network.http.response.AppInfoResp;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    //获取应用信息
    @POST("app/info")
    Observable<AppInfoResp> getAppInfo(@Body RequestBody body);

}
