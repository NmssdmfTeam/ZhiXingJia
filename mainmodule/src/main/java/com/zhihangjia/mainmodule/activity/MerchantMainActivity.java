package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.activity.BaseActivity;
import com.nmssdmf.commonlib.adapter.FragmentPagerAdapter;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.MerchantMainCB;
import com.zhihangjia.mainmodule.databinding.ActivityMerchantMainBinding;
import com.zhihangjia.mainmodule.databinding.IncludeMerchantMainHeaderBinding;
import com.zhihangjia.mainmodule.fragment.MerchantAllFragment;
import com.zhihangjia.mainmodule.fragment.MerchantEvaluateFragment;
import com.zhihangjia.mainmodule.fragment.MerchantMainFragment;
import com.zhihangjia.mainmodule.viewmodel.MerchantMainVM;

import java.util.ArrayList;
import java.util.List;

public class MerchantMainActivity extends BaseActivity implements MerchantMainCB {
    private final String TAG = MerchantMainActivity.class.getSimpleName();

    private ActivityMerchantMainBinding binding;

    private MerchantMainFragment mainFragment;
    private MerchantAllFragment allFragment;
    private MerchantEvaluateFragment evaluateFragment;
    private List<Fragment> list = new ArrayList<>();
    private FragmentPagerAdapter adapter;

    private MerchantMainVM vm;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_merchant_main;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new MerchantMainVM(this);
        return vm;
    }

    @Override
    protected void initAll(Bundle savedInstanceState) {
        binding = (ActivityMerchantMainBinding) baseBinding;

        initTabLayout();

        initView();
    }


    private void initView(){
        IncludeMerchantMainHeaderBinding headerBinding = binding.iHeader;
        headerBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        headerBinding.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        headerBinding.ivAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initTabLayout(){
        mainFragment = new MerchantMainFragment();
        allFragment = new MerchantAllFragment();
        evaluateFragment = new MerchantEvaluateFragment();
        list.add(mainFragment);
        list.add(allFragment);
        list.add(evaluateFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager(), this, list);

        binding.vp.setAdapter(adapter);
        binding.tl.setupWithViewPager(binding.vp);

        binding.tl.getTabAt(0).setText("店铺首页");
        binding.tl.getTabAt(1).setText("全部商品");
        binding.tl.getTabAt(2).setText("口碑评价");
    }
}
