package com.lotogram.ihunter.event;

public class LoginEvent extends BaseEvent {

    public static final int WECHAT = 0x0001;

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}