package com.lotogram.ihunter.mvvm;

import com.lotogram.ihunter.bean.User;
import com.lotogram.ihunter.event.IBaseEvent;

public interface IBaseView {

    void setContentView();

    void initViews();

    void postDelay(Runnable task, long delay);

    void postNow(Runnable task);

    void postAtTime(Runnable task, long timeMillis);

    void postEvent(IBaseEvent event);

    void onUpdateUserInfo(User user);

    void updateUserInfo();

    int getScreenWidth();

    int getScreenHeight();

    int getStatusBarHeight();

    int getNavigationBarHeight();

    void registerEventBus();

    void unregisterEventBus();
}