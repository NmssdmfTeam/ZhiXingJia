package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.text.InputType;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.RegisterCB;
import com.zhihangjia.loginmodule.databinding.ActivityBindRegisterBinding;
import com.zhihangjia.loginmodule.databinding.ActivityRegisterBinding;
import com.zhihangjia.loginmodule.viewmodel.BindRegisterVM;
import com.zhihangjia.loginmodule.viewmodel.RegisterVM;

/**
* @description 微信绑定页面
* @author chenbin
* @date 2018/12/6 15:31
* @version v3.2.0
*/
public class BindRegisterActivity extends BaseTitleActivity{
    private final String TAG = BindRegisterActivity.class.getSimpleName();
    private ActivityBindRegisterBinding binding;
    private BindRegisterVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new BindRegisterVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityBindRegisterBinding)baseViewBinding;
        binding.setVm(vm);

        setISlenderLineGone();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_bind_register;
    }
}
