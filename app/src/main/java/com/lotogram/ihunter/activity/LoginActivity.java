package com.lotogram.ihunter.activity;

import android.util.Log;
import android.view.View;

import com.lotogram.ihunter.databinding.ActivityLoginBinding;
import com.lotogram.ihunter.mvvm.BaseActivity;
import com.lotogram.ihunter.util.WechatUtil;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    public void onLogin(View view) {
        Log.d(TAG, "onLogin: ");
        WechatUtil.login();
    }
}