package com.zhihangjia.mainmodule.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ConfirmOrderAdapter;
import com.zhihangjia.mainmodule.callback.ConfirmOrderAdapterCB;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;
import com.zhihangjia.mainmodule.databinding.ActivityConfirmOrderBinding;
import com.zhihangjia.mainmodule.databinding.HeaderConfirmOrderBinding;
import com.zhihangjia.mainmodule.viewmodel.ConfirmOrderVM;
import com.zhihangjia.mainmodule.window.ChooseCouponWindow;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseTitleActivity implements ConfirmOrderCB, ConfirmOrderAdapterCB {
    private final String TAG = ConfirmOrderActivity.class.getSimpleName();
    private ActivityConfirmOrderBinding binding;
    private ConfirmOrderVM vm;

    private ConfirmOrderAdapter adapter;
    private WheelPickerWindow deliveryMethodWindow;
    private ChooseCouponWindow chooseCouponWindow;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new ConfirmOrderVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "确认订单";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityConfirmOrderBinding) baseViewBinding;
        binding.setVm(vm);

        vm.getIntentData();
        adapter = new ConfirmOrderAdapter(vm.getList(), this);
        HeaderConfirmOrderBinding headerBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.header_confirm_order, null, false);
        adapter.addHeaderView(headerBinding.getRoot());
        binding.crv.setAdapter(adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void chooseDeliveryMethod() {
        if (deliveryMethodWindow == null) {
            deliveryMethodWindow = new WheelPickerWindow(this, vm.getDeliveryMethodList(), vm);
        }

        deliveryMethodWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void chooseCoupon() {
        if (chooseCouponWindow == null) {
            chooseCouponWindow = new ChooseCouponWindow(this, ChooseCouponWindow.TYPE_MERCHANT, vm.getCouponList());
        }

        chooseCouponWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }
}
