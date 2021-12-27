package com.lotogram.ihunter.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.lotogram.ihunter.BR;

public class User extends BaseObservable {

    private String _id;
    private int uid;
    private String nickname;
    private String avatar;
    private int age;
    private int coins;
    private int score;
    private int vip;
    private Address address;
    private int idcard;//是否实名: 0否  1是

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }
}