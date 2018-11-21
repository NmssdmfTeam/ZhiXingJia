package com.zhixingjia.goodsmanagemodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.goodsmanagemodule.adapter.GoodManageAdapter;
import com.zhixingjia.goodsmanagemodule.callback.GoodManageFragmentCB;
import com.zhixingjia.goodsmanagemodule.viewmodel.GoodManageFragmentVM;

import java.util.List;


public class GoodManageFragment extends BaseRecyclerViewFragment implements GoodManageFragmentCB{
    private final String TAG = GoodManageFragment.class.getSimpleName();

    private GoodManageAdapter adapter;
    private GoodManageFragmentVM vm;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new GoodManageFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new GoodManageAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
