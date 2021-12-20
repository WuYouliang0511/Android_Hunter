package com.loto.ihunter.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.loto.ihunter.databinding.ActivityWelcomeBinding;
import com.loto.ihunter.mvvm.BaseActivity;

public class WelcomeActivity extends BaseActivity<ActivityWelcomeBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "AA", Toast.LENGTH_LONG).show();
    }
}