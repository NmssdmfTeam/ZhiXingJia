package com.zhihangjia.mainmodule.fragment;


import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.OrderAdapter;
import com.zhihangjia.mainmodule.callback.OrderListFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.OrderListFragmentVM;

import java.util.List;

/**
 *
 */
public class OrderListFragment extends BaseRecyclerViewFragment implements OrderListFragmentCB {
    private final String TAG = OrderListFragment.class.getSimpleName();
    private OrderListFragmentVM vm;
    private OrderAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        if (vm == null) {
            vm = new OrderListFragmentVM(this);
        }
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new OrderAdapter(vm.getIdentity(), list, vm);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    public void setInfo(String identity, int status){
        if (vm == null) {
            vm = new OrderListFragmentVM(this);
        }
        vm.setIdentity(identity);
        vm.setStatus(status);
    }

    @Override
    public void refreshAdapter(int index) {
        adapter.notifyDataSetChanged();
    }
}
