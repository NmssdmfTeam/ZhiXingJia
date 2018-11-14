package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantAdapter;
import com.zhihangjia.mainmodule.callback.MerchantFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MerchantFragmentVM;

import java.util.List;

/**
 * 商家fragment
 */
public class MerchantFragment extends BaseRecyclerViewFragment implements MerchantFragmentCB {
    private final String TAG = MerchantFragment.class.getSimpleName();
    private MerchantFragmentVM vm;
    private MerchantAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MerchantAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
