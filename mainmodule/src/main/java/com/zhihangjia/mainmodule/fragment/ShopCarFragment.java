package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.nmssdmf.commonlib.config.PrefrenceConfig;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.commonlib.util.PreferenceUtil;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.BaseQuickAdapter;
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
    public void onResume() {
        super.onResume();
        //是否登录
        String token = PreferenceUtil.getString(PrefrenceConfig.TOKEN, "");
        if (TextUtils.isEmpty(token)) {//未登录
            RxBus.getInstance().send(RxEvent.LoginEvent.RE_LOGIN, null);
        }
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentShopcarBinding) baseBinding;
        binding.setVm(vm);
        vm.getData(false);
        adapter = new ShopCarAdapter(vm.getList(), () -> {
            vm.countTotalPrice();
            vm.changeSelect();
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

        binding.rbSelectAll.setOnClickListener(v -> vm.select.set(!vm.select.get()));
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
