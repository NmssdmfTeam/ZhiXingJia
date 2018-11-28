package com.zhihangjia.mainmodule.fragment;


import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.OrderWaitForPayAdapter;
import com.zhihangjia.mainmodule.callback.OrderListWaitForPayFragmentCB;
import com.zhihangjia.mainmodule.databinding.FragmentOrderListWaitForPayBinding;
import com.zhixingjia.bean.mainmodule.Order;

import java.util.List;


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
        adapter = new OrderWaitForPayAdapter(vm.getList());

        binding.crv.setAdapter(adapter);
        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                vm.getData(true);
            }

            @Override
            public void onLoadMore() {
                vm.getData(false);
            }
        });

        vm.getData(false);
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void refreshAdapter(boolean isRefresh, List<Order> list) {
        if (isRefresh)
            binding.crv.setRefreshing(false);
        adapter.notifyDataChangedAfterLoadMore(isRefresh, list);
    }
}
