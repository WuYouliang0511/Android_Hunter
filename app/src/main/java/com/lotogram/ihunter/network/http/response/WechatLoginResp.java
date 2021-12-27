package com.lotogram.ihunter.network.http.response;

import com.lotogram.ihunter.bean.User;
import com.lotogram.ihunter.network.http.BaseResponse;

public class WechatLoginResp extends BaseResponse {

    private String token;
    private int adultMode;
    private User user;

    public String getToken() {
        return token;
    }

    public int getAdultMode() {
        return adultMode;
    }

    public User getUser() {
        return user;
    }
}