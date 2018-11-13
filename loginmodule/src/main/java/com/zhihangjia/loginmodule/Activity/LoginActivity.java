package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.text.Html;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.LoginCB;
import com.zhihangjia.loginmodule.databinding.ActivityLoginBinding;
import com.zhihangjia.loginmodule.viewmodel.LoginVM;

public class LoginActivity extends BaseTitleActivity implements LoginCB{
    private final String TAG = LoginActivity.class.getSimpleName();

    private LoginVM vm;
    private ActivityLoginBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LoginVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLoginBinding)baseViewBinding;
        String str="登录即代表您已同意我们的 <font color='#FF9A14'>服务协议</font> 和 <font color='#FF9A14'>隐私政策</font>";
        binding.tvAgreement.setText(Html.fromHtml(str));

        baseTitleBinding.tTitle.inflateMenu(R.menu.menu_login);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }
}
