package com.lotogram.ihunter.activity;

import android.util.Log;
import android.view.View;

import com.lotogram.ihunter.consts.UserKey;
import com.lotogram.ihunter.databinding.ActivityMainBinding;
import com.lotogram.ihunter.mvvm.BaseActivity;
import com.lotogram.ihunter.util.MMKVUtil;
import com.tencent.mmkv.MMKV;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public void onClick(View view) {
//        ToastUtil.show(getApplicationContext(), "AAA");
//        Toast.makeText(this, "AAA", Toast.LENGTH_LONG).show();
//        Log.d(TAG, "onClick: " + Build.VERSION.SDK_INT);
//
//        try {
//            MediaCodec codec = MediaCodec.createByCodecName("");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String description = mBinding.description.getText().toString().trim();

        MMKVUtil.setDescription(description);

    }

    public void show(View view) {

        MMKV mmkv = MMKV.mmkvWithID("61727211e01cbfe9881bd00a", MMKV.SINGLE_PROCESS_MODE, "9527");

        String a = mmkv.decodeString(UserKey.DESCRIPTION);

        Log.d(TAG, "show: " + a);

        Log.d(TAG, "show: " + MMKVUtil.getDescription());
    }
}