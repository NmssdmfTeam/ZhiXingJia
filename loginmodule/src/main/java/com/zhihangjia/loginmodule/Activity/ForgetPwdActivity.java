package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.text.InputType;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.ForgetPwdCB;
import com.zhihangjia.loginmodule.databinding.ActivityForgetPwdBinding;
import com.zhihangjia.loginmodule.viewmodel.ForgetPwdVM;

public class ForgetPwdActivity extends BaseTitleActivity implements ForgetPwdCB {
    private final String TAG = ForgetPwdActivity.class.getSimpleName();

    private ActivityForgetPwdBinding binding;
    private ForgetPwdVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ForgetPwdVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityForgetPwdBinding) baseViewBinding;
        binding.setVm(vm);

        setISlenderLineGone();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void setInputType(boolean pwdShow) {
        if (pwdShow) {
            binding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        } else {
            binding.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        binding.etPwd.setSelection(binding.etPwd.getText().length());
    }
}
