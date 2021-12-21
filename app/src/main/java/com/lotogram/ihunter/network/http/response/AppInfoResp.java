package com.lotogram.ihunter.network.http.response;

import com.lotogram.ihunter.network.http.BaseResponse;

public class AppInfoResp extends BaseResponse {

    private Wechat wechat;
    private Splash splash;
    private Bugly bugly;

    public String getWechatId() {
        if (wechat == null) {
            return null;
        }
        return wechat.getAppid();
    }

    public String getBuglyId() {
        if (bugly == null) {
            return null;
        }
        return bugly.getAppid();
    }


    static class Wechat {
        private String appid;

        public String getAppid() {
            return appid;
        }
    }

    static class Bugly {
        private String appid;

        public String getAppid() {
            return appid;
        }
    }

    static class Splash {
        private String title;
        private String link;
        private String img;

        public String getImg() {
            return img;
        }

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }
    }
}