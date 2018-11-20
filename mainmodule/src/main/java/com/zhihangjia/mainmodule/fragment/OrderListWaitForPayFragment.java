package com.zhihangjia.mainmodule.fragment;


import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.OrderWaitForPayAdapter;
import com.zhihangjia.mainmodule.callback.OrderListWaitForPayFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentOrderListWaitForPayBinding;


/**
 *
 */
public class OrderListWaitForPayFragment extends BaseFragment implements OrderListWaitForPayFragmentCB {

    private final String TAG = OrderListWaitForPayFragment.class.getSimpleName();
    private OrderWaitForPayAdapter adapter;
    private OrderListWaitForPayFragmentVM vm;
    private FragmentOrderListWaitForPayBinding binding;

    @Override
    public BaseVM initViewModel() {
        vm = new OrderListWaitForPayFragmentVM(this);
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_order_list_wait_for_pay;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentOrderListWaitForPayBinding) baseBinding;
        vm.getList().add(new Base());
        vm.getList().add(new Base());
        vm.getList().add(new Base());
        vm.getList().add(new Base());
        vm.getList().add(new Base());
        vm.getList().add(new Base());
        adapter = new OrderWaitForPayAdapter(vm.getList());

        binding.crv.setAdapter(adapter);
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
