package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;
import com.zhihangjia.mainmodule.databinding.ActivityMerchantMerchandiseBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchantMerchandiseVM;

public class MerchantMerchandiseActivity extends BaseActivity implements MerchantMerchandiseCB {
    private final String TAG = MerchantMerchandiseActivity.class.getSimpleName();

    private ActivityMerchantMerchandiseBinding binding;

    private MerchantMerchandiseVM vm;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_merchant_merchandise;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MerchantMerchandiseVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityMerchantMerchandiseBinding) baseBinding;
        binding.setVm(vm);
    }
}
