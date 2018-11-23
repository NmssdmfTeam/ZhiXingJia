package com.zhihangjia.mainmodule.activity;

import android.os.Bundle;

import com.nmssdmf.commonlib.activity.BaseTitleActivity;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.LifeServiceAdapter;
import com.zhihangjia.mainmodule.callback.LifeServiceCB;
import com.zhihangjia.mainmodule.databinding.ActivityLifeServiceBinding;
import com.zhihangjia.mainmodule.viewmodel.LifeServiceVM;

public class LifeServiceActivity extends BaseTitleActivity implements LifeServiceCB {
    private final String TAG = LifeServiceActivity.class.getSimpleName();

    private ActivityLifeServiceBinding binding;
    private LifeServiceVM vm;
    private LifeServiceAdapter adapter;
    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public BaseVM initViewModel() {
        vm = new LifeServiceVM(this);
        return vm;
    }

    @Override
    public String setTitle() {
        return "生活服务";
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (ActivityLifeServiceBinding) baseViewBinding;
        vm.getData();
        adapter = new LifeServiceAdapter(vm.getList());
        binding.crv.setAdapter(adapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_life_service;
    }
}
