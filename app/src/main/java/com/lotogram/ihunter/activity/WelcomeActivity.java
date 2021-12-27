package com.lotogram.ihunter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.lotogram.ihunter.databinding.ActivityWelcomeBinding;
import com.lotogram.ihunter.mvvm.BaseActivity;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}