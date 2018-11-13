package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

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
        binding.setVm(vm);

        String str="登录即代表您已同意我们的 <font color='#FF9A14'>服务协议</font> 和 <font color='#FF9A14'>隐私政策</font>";
        binding.tvAgreement.setText(Html.fromHtml(str));

        initMenu();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    private void initMenu(){
        baseTitleBinding.tTitle.inflateMenu(R.menu.menu_login);
        baseTitleBinding.tTitle.setNavigationIcon(R.drawable.login_close);

        setISlenderLineGone();

        baseTitleBinding.tTitle.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.iRegister :{
                        doIntent(RegisterActivity.class, null);
                        break;
                    }
                }
                return true;
            }
        });
    }
}
