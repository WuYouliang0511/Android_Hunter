package com.lotogram.ihunter.util;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lotogram.ihunter.MyApplication;
import com.lotogram.ihunter.R;

public class ToastUtil {

    private static Toast mToast = null;

    public static void show(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    public static void show(String message) {
        show(MyApplication.getInstance(), message, Toast.LENGTH_SHORT);
    }

    private static final Handler mHandler = new Handler();
    private static final Runnable runnable = () -> {
        if (mToast != null) {
            mToast.cancel();
        }
    };

    public static void show(Context context, String message, int duration) {
        mHandler.removeCallbacks(runnable);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.widget_loto_toast, null);
        TextView str = (TextView) view.findViewById(R.id.message);
        str.setAlpha(0.5f);
        str.setText(message);
        mToast = new Toast(context);
        mToast.setView(view);
//        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
        mHandler.postDelayed(runnable, 1500);
    }
}
