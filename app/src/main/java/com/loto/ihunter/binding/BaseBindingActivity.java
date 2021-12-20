package com.loto.ihunter.binding;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.loto.ihunter.mvvm.BaseActivity;
import com.loto.ihunter.mvvm.BaseViewModel;

public abstract class BaseBindingActivity<T extends ViewDataBinding, MV extends BaseViewModel>
        extends BaseActivity implements IBaseBindingView {

    private T  mBinding;
    private MV mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initViewModel();
    }

    @Override
    public void initViewModel() {

    }
}