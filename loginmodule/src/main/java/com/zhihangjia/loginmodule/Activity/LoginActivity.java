package com.zhihangjia.loginmodule.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.MenuItem;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.LoginCB;
import com.zhihangjia.loginmodule.databinding.ActivityLoginBinding;
import com.zhihangjia.loginmodule.viewmodel.LoginVM;


public class LoginActivity extends BaseTitleActivity implements LoginCB {
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
        binding = (ActivityLoginBinding) baseViewBinding;
        binding.setVm(vm);

        String str = "登录即代表您已同意我们的 <font color='#FF9A14'>服务协议</font> 和 <font color='#FF9A14'>隐私政策</font>";
        binding.tvAgreement.setText(Html.fromHtml(str));

        initMenu();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    private void initMenu() {
        baseTitleBinding.tTitle.inflateMenu(R.menu.menu_login);
        baseTitleBinding.tTitle.setNavigationIcon(R.drawable.login_close);

        setISlenderLineGone();

        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.iRegister) {
                    doIntentForResult(RegisterActivity.class, null, LoginVM.REGISTER_REQUEST_CODE);
                }
                return true;
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            switch (requestCode) {
                case LoginVM.REGISTER_REQUEST_CODE:{
                    Bundle bundle = data.getExtras();
                    vm.phoneNum.set(bundle.getString(IntentConfig.PHONE_NUM));
                    break;
                }
                 case LoginVM.FORGET_PWD_REQUEST_CODE:{
                     Bundle bundle = data.getExtras();
                     vm.phoneNum.set(bundle.getString(IntentConfig.PHONE_NUM));
                     break;
                 }
            }
    }
}
