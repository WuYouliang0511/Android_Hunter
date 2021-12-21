package com.lotogram.ihunter.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lotogram.ihunter.databinding.ActivityMainBinding;
import com.lotogram.ihunter.mvvm.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onClick(View view) {
//        ToastUtil.show(getApplicationContext(), "AAA");
        Toast.makeText(this, "AAA", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onClick: " + Build.VERSION.SDK_INT);
    }
}