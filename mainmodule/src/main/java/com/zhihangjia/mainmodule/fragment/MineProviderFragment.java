package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.zhihangjia.mainmodule.R;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.databinding.FragmentMineProviderBinding;
import com.zhihangjia.mainmodule.viewmodel.MineProviderVM;

/**
* @description 知行家首页-- 建材家居fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class MineProviderFragment extends BaseFragment {
    private final String TAG = MineProviderFragment.class.getSimpleName();
    private FragmentMineProviderBinding binding;
    private MineProviderVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MineProviderVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_provider;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMineProviderBinding) baseBinding;
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
