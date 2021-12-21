package com.lotogram.ihunter.network.http;


import android.util.Log;

import androidx.annotation.NonNull;

import com.lotogram.ihunter.util.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {

    private final String TAG = this.getClass().getSimpleName();


    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T response) {
        if (!response.isOk()) {
            Log.d(TAG, response.getMessage());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.d(TAG, "发生错误，请查看日志！");
        ToastUtil.show(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}