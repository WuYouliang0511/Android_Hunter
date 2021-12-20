package com.loto.ihunter.mvvm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements IBaseView {

    protected final String TAG = this.getClass().getSimpleName();

    protected T mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        setFullScreen();
        initViews();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setContentView() {
        //获得带有泛型的父类
        Type type = getClass().getGenericSuperclass();
        //ParameterizedType参数化类型，即泛型
        ParameterizedType pt = (ParameterizedType) type;
        if (pt == null) return;
        //获取参数化类型的数组，泛型可能有多个
        Type[] types = pt.getActualTypeArguments();
        Class<?> clazz = (Class<?>) types[0];
        try {
            Method inflate = clazz.getMethod("inflate", LayoutInflater.class);
            inflate.setAccessible(true);
            mBinding = (T) inflate.invoke(null, getLayoutInflater());
            if (mBinding == null) return;
            setContentView(mBinding.getRoot());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void setFullScreen() {
        Window window = getWindow();
        View view = window.getDecorView();
        int mSystemUiFlag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        view.setSystemUiVisibility(mSystemUiFlag);
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    @Override
    public void initViews() {
    }
}