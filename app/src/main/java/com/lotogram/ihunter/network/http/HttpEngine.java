package com.lotogram.ihunter.network.http;

import androidx.annotation.NonNull;

import com.lotogram.ihunter.network.http.response.AppInfoResp;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HttpEngine {

    private static ApiService api = null;

    private static ApiService getApi() {
        if (api == null) {
            ApiRequest request = ApiRequest.getInstance();
            Class<ApiService> clazz = ApiService.class;
            api = request.create(clazz);
        }
        return api;
    }

    private static <E extends BaseResponse> void subscribe(@NonNull Observable<E> observable, BaseObserver<E> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //获取应用信息
    public static void getAppInfo(BaseObserver<AppInfoResp> observer) {
        subscribe(getApi().getAppInfo(RequestBodyBuilder.getBaseBody()), observer);
    }
}