package com.lotogram.ihunter.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.lotogram.ihunter.BuildConfig;
import com.lotogram.ihunter.databinding.ActivityLoginBinding;
import com.lotogram.ihunter.event.LoginEvent;
import com.lotogram.ihunter.mvvm.BaseActivity;
import com.lotogram.ihunter.util.WechatUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView,
        CompoundButton.OnCheckedChangeListener {

    @Override
    public void initViews() {
        super.initViews();
        mBinding.setClick(this);
        mBinding.agree.setOnCheckedChangeListener(this);
        mBinding.setVersion(BuildConfig.VERSION_NAME);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginEvent event) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onHelp() {
        Log.d(TAG, "onHelp: ");
        Toast.makeText(this, "onHelp", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onReport() {
        Log.d(TAG, "onReport: ");
    }

    @Override
    public void onAgree() {
        Log.d(TAG, "onAgree: ");
    }

    @Override
    public void onLogin() {
        Log.d(TAG, "onLogin: ");
        WechatUtil.login();
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged: " + isChecked);
    }
}