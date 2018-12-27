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

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class OrderListWaitForPayFragment extends BaseFragment implements OrderListWaitForPayFragmentCB {

    private final String TAG = OrderListWaitForPayFragment.class.getSimpleName();
    private OrderWaitForPayAdapter adapter;
    private OrderListWaitForPayFragmentVM vm;
    private FragmentOrderListWaitForPayBinding binding;

    private boolean current;//是否是当前显示的fragment

    @Override
    public BaseVM initViewModel() {
        if (vm == null) {
            vm = new OrderListWaitForPayFragmentVM(this);
        }
        return vm;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_order_list_wait_for_pay;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        binding = (FragmentOrderListWaitForPayBinding) baseBinding;
        binding.setVm(vm);
        adapter = new OrderWaitForPayAdapter(vm.getList(), vm);
        adapter.setIdentify(vm.getIdentity());
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

    @Override
    public void onResume() {
        super.onResume();
        vm.getData(true);
    }

    public void setInfo(){
        if (vm == null) {
            vm = new OrderListWaitForPayFragmentVM(this);
        }
    }

    public void initData() {
        vm.getData(true);
    }

    @Override
    public void cancelOrder() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void nofityItem(int index) {
        adapter.notifyItemChanged(index);
    }

    @Override
    public List<String> getSelectedOrderIds() {
        List<String> ids = new ArrayList<>();
        for (Order order : vm.getList()) {
            if (order.isIs_selected()) {
                ids.add(order.getOrder_id());
            }
        }
        return ids;
    }

    public OrderListWaitForPayFragmentVM getVm() {
        return vm;
    }

    public void setVm(OrderListWaitForPayFragmentVM vm) {
        this.vm = vm;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
