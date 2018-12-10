package com.zhixingjia.personmodule.activity;

import android.os.Bundle;
import android.text.InputType;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.callback.ChangePwdCB;
import com.zhixingjia.personmodule.databinding.ActivityChangePwdBinding;
import com.zhixingjia.personmodule.viewmodule.ChangePwdVM;

public class ChangePwdActivity extends BaseTitleActivity implements ChangePwdCB {
    private final String TAG = ChangePwdActivity.class.getSimpleName();

    private ActivityChangePwdBinding binding;
    private ChangePwdVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ChangePwdVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityChangePwdBinding) baseViewBinding;
        binding.setVm(vm);

        setISlenderLineGone();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_change_pwd;
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
