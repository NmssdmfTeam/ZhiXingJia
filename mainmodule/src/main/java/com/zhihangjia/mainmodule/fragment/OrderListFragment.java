package com.zhihangjia.mainmodule.fragment;


import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.callback.OrderListFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.OrderListFragmentVM;

import java.util.List;

/**
 *
 */
public class OrderListFragment extends BaseRecyclerViewFragment implements OrderListFragmentCB {

    private final String TAG = OrderListFragment.class.getSimpleName();
    private OrderListFragmentVM vm;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new OrderListFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        return null;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
