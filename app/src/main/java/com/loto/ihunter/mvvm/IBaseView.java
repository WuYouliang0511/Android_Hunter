package com.loto.ihunter.mvvm;

import com.loto.ihunter.bean.User;
import com.loto.ihunter.event.IBaseEvent;

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