package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ShopCarAdapter;
import com.zhihangjia.mainmodule.callback.ShopCarFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentShopcarBinding;
import com.zhihangjia.mainmodule.viewmodel.ShopCarFragmentVM;
import com.zhixingjia.bean.mainmodule.ShopCar;

import java.util.List;

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
        vm.getData(false);
        adapter = new ShopCarAdapter(vm.getList(), new ShopCarAdapter.ShopCarAdapterListener() {
            @Override
            public void changePrice() {
                vm.countTotalPrice();
            }
        });
        binding.crv.setAdapter(adapter);
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getData(true);
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void refreshData(List<ShopCar> list, boolean refresh) {
        if (refresh)
            binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(refresh, list);
    }
}
