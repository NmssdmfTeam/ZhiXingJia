package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.FragmentMineCustomerBinding;
import com.zhihangjia.mainmodule.viewmodel.MineCustomerVM;

/**
* @description 我的买家版
* @author chenbin
* @date 2018/11/20 13:25
* @version v3.2.0
*/
public class MineCustomerFragment extends BaseFragment {
    private final String TAG = MineCustomerFragment.class.getSimpleName();
    private FragmentMineCustomerBinding binding;
    private MineCustomerVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new MineCustomerVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_customer;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentMineCustomerBinding) baseBinding;
        binding.msfl.setSwipeableChildren(R.id.sv_customer_my);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
