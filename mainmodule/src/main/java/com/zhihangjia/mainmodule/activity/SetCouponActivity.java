package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.SetCouponCB;
import com.zhihangjia.mainmodule.databinding.ActivitySetCouponBinding;
import com.zhihangjia.mainmodule.viewmodel.SetCouponVM;

public class SetCouponActivity extends BaseTitleActivity implements SetCouponCB{
    private final String TAG = SetCouponActivity.class.getSimpleName();

    private SetCouponVM vm;
    private ActivitySetCouponBinding binding;
    private WheelPickerWindow useConditionWindow;
    private WheelPickerWindow timeTypeWindow;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new SetCouponVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "设置优惠券";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivitySetCouponBinding) baseViewBinding;
        binding.setVm(vm);

        vm.getData();

        addAnimation();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_set_coupon;
    }

    @Override
    public void showUseConditionWindow() {
        if (useConditionWindow == null) {
            useConditionWindow = new WheelPickerWindow(this, vm.getUseConditionList(), vm);
        }

        useConditionWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void chooseTimeTypeWindow() {
        if (timeTypeWindow == null) {
            timeTypeWindow = new WheelPickerWindow(this, vm.getTimeTypeList(), vm);
        }

        timeTypeWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }
}
