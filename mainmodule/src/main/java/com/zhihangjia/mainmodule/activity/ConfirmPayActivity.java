package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ConfirmPayCB;
import com.zhihangjia.mainmodule.databinding.ActivityConfirmPayBinding;
import com.zhihangjia.mainmodule.viewmodel.ConfirmPayVM;

public class ConfirmPayActivity extends BaseTitleActivity implements ConfirmPayCB {
    private final String TAG = ConfirmPayActivity.class.getSimpleName();
    private ConfirmPayVM vm;
    private ActivityConfirmPayBinding binding;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ConfirmPayVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "确认付款";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityConfirmPayBinding) baseViewBinding;
        vm.payInfo();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_pay;
    }

    @Override
    public void setListener() {
        binding.setVm(vm);
    }
}
