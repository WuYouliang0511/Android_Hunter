package com.lotogram.ihunter.mvvm;

import androidx.databinding.ViewDataBinding;

public abstract class BaseVMActivity<T extends ViewDataBinding, MV extends BaseViewModel>
        extends BaseActivity<T>   {

    private T  mBinding;
    private MV mViewModel;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
//        initViewModel();
//    }
//
//    @Override
//    public void initViewModel() {
//
//    }
}