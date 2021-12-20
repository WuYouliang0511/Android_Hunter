package com.loto.ihunter.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.loto.ihunter.BR;

public class User extends BaseObservable {

    private String _id;

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }
}
