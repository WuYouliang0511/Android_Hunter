package com.lotogram.ihunter.mvvm;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.lotogram.ihunter.bean.User;
import com.lotogram.ihunter.event.IBaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        registerEventBus();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    public void initViews() {
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
    public void postDelay(Runnable task, long delay) {
        Window window = getWindow();
        View view = window.getDecorView();
        view.postDelayed(task, delay);
    }

    @Override
    public void postNow(Runnable task) {
        Window window = getWindow();
        View view = window.getDecorView();
        view.post(task);
    }

    @Override
    public void postAtTime(Runnable task, long timeMillis) {
        long delay = timeMillis - System.currentTimeMillis();
        postDelay(task, delay);
    }

    @Override
    public void postEvent(IBaseEvent event) {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            eventBus.post(event);
        } else {
            Log.d(TAG, "未注册EventBus，事件发送失败！");
        }
    }

    @Override
    public void registerEventBus() {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            Log.d(TAG, "不可重复注册EventBus");
        } else {
            Class<?> clazz = this.getClass();
            for (Method method : clazz.getMethods()) {
                boolean pa = method.isAnnotationPresent(Subscribe.class);
                if (pa) {
                    Log.d(TAG, "method: " + method.getName());
                    eventBus.register(this);
                    return;
                }
            }
            Log.d(TAG, "未发现事件订阅，注册失败");
        }
    }

    @Override
    public void unregisterEventBus() {
        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        } else {
            Log.d(TAG, "未注册EventBus");
        }
    }

    @Override
    public void onUpdateUserInfo(User user) {

    }

    @Override
    public void updateUserInfo() {
        User user = new User();
        onUpdateUserInfo(user);
    }

    @Override
    public int getScreenWidth() {
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    public int getScreenHeight() {
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        return metrics.heightPixels;
    }

    @Override
    public int getStatusBarHeight() {
        Resources resources = getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }
}