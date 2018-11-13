package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.text.Html;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.RegisterCB;
import com.zhihangjia.loginmodule.databinding.ActivityRegisterBinding;
import com.zhihangjia.loginmodule.viewmodel.RegisterVM;

public class RegisterActivity extends BaseTitleActivity implements RegisterCB{
    private final String TAG = RegisterActivity.class.getSimpleName();
    private ActivityRegisterBinding binding;
    private RegisterVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new RegisterVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityRegisterBinding)baseViewBinding;
        String str="已阅读并同意《<font color='#FF000000'>用户服务协议</font>》";
        binding.tvAgreement.setText(Html.fromHtml(str));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }
}
