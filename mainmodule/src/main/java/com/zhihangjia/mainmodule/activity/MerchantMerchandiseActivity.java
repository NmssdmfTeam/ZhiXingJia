package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;
import com.zhihangjia.mainmodule.databinding.ActivityMerchantMerchandiseBinding;
import com.zhihangjia.mainmodule.fragment.MerchandiseFragment;
import com.zhihangjia.mainmodule.fragment.MerchantFragment;
import com.zhihangjia.mainmodule.viewmodel.MerchantMerchandiseVM;

import java.util.ArrayList;
import java.util.List;

public class MerchantMerchandiseActivity extends BaseActivity implements MerchantMerchandiseCB {
    private final String TAG = MerchantMerchandiseActivity.class.getSimpleName();

    private ActivityMerchantMerchandiseBinding binding;

    private MerchantMerchandiseVM vm;

    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;

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

        MerchantFragment merchantFragment = new MerchantFragment();
        MerchandiseFragment merchandiseFragment = new MerchandiseFragment();
        list.add(merchantFragment);
        list.add(merchandiseFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);
        binding.vp.setAdapter(adapter);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
