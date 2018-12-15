package com.zhixingjia.goodsmanagemodule.activity;

import android.os.Bundle;
import android.view.Gravity;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.callback.WheelPickerWindowCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.commonlib.window.WheelPickerWindow;
import com.zhixingjia.goodsmanagemodule.R;
import com.zhixingjia.goodsmanagemodule.callback.PriceSettingCB;
import com.zhixingjia.goodsmanagemodule.databinding.ActivityPriceSettingBinding;
import com.zhixingjia.goodsmanagemodule.viewmodel.PriceSettingVM;

/**
* @description 设置价格库存
* @author chenbin
* @date 2018/11/21 14:21
* @version v3.2.0
*/
public class PriceSettingActivity extends BaseTitleActivity implements WheelPickerWindowCB, PriceSettingCB {
    private final String TAG = PriceSettingActivity.class.getSimpleName();
    private PriceSettingVM vm;
    private WheelPickerWindow unitWheelPickerWindow;       //单位选择
    private ActivityPriceSettingBinding binding;

    @Override
    public String setTitle() {
        return "设置价格库存";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityPriceSettingBinding) baseViewBinding;
        unitWheelPickerWindow = new WheelPickerWindow(this, vm.unit, this);
        binding.setVm(vm);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_price_setting;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new PriceSettingVM(this);
        return vm;
    }

    @Override
    public void tvSureClick(String item, int position) {
        vm.priceSet.get().setUnit(item);
    }

    @Override
    public void showUnitWheelPickWindow() {
        unitWheelPickerWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);
    }
}
