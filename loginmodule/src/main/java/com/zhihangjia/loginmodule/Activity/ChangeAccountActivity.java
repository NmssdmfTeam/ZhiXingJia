package com.zhihangjia.loginmodule.Activity;

import android.os.Bundle;
import android.text.InputType;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.loginmodule.R;
import com.zhihangjia.loginmodule.callback.ChangeAccountCB;
import com.zhihangjia.loginmodule.viewmodel.ChangeAccountVM;
import com.zhihangjia.loginmodule.databinding.ActivityChangeAccountBinding;

/**
* @description 变更帐号
* @author chenbin
* @date 2018/12/7 16:01
* @version v3.2.0
*/
public class ChangeAccountActivity extends BaseTitleActivity implements ChangeAccountCB {
    private final String TAG = ChangeAccountActivity.class.getSimpleName();
    private ChangeAccountVM vm;
    private ActivityChangeAccountBinding binding;

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityChangeAccountBinding)baseViewBinding;
        binding.setVm(vm);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_account;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ChangeAccountVM(this);
        return vm;
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
