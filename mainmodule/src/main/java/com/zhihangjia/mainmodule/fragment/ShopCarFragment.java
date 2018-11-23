package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ShopCarAdapter;
import com.zhihangjia.mainmodule.callback.ShopCarFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentShopcarBinding;
import com.zhihangjia.mainmodule.viewmodel.ShopCarFragmentVM;

/**
* @description 知行家首页-- 建材家居fragment
* @author chenbin
* @date 2018/11/13 15:53
* @version v3.2.0
*/
public class ShopCarFragment extends BaseFragment implements ShopCarFragmentCB{
    private final String TAG = ShopCarFragment.class.getSimpleName();
    private FragmentShopcarBinding binding;

    private ShopCarAdapter adapter;
    private ShopCarFragmentVM vm;

    @Override
    public BaseVM initViewModel() {
        vm = new ShopCarFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_shopcar;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentShopcarBinding) baseBinding;
        binding.setVm(vm);
        vm.getData();
        adapter = new ShopCarAdapter(vm.getList());
        binding.crv.setAdapter(adapter);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
