package com.lotogram.ihunter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lotogram.ihunter.databinding.ActivityLoginBinding;
import com.lotogram.ihunter.mvvm.BaseActivity;
import com.lotogram.ihunter.util.WechatUtil;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements LoginView,
        CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding.setClick(this);
        mBinding.agree.setOnCheckedChangeListener(this);
    }

    public void onLogin(View view) {
        Log.d(TAG, "onLogin: ");
        WechatUtil.login();
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
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged: " + isChecked);
    }
}