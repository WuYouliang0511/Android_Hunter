package com.loto.ihunter.event;

public interface IBaseEvent {

    void setAction(int action);

    int getAction();

    void setCode(int code);

    int getCode();
}