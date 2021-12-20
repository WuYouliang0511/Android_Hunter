package com.loto.ihunter.event;

public class BaseEvent implements IBaseEvent {

    private int action;
    private int code;

    @Override
    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public int getAction() {
        return action;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }
}