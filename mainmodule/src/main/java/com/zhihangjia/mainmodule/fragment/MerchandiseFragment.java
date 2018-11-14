package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchandiseAdapter;
import com.zhihangjia.mainmodule.callback.MerchandiseFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MerchandiseFragmentVM;

import java.util.List;

/**
 * 商品fragment
 */
public class MerchandiseFragment extends BaseRecyclerViewFragment implements MerchandiseFragmentCB {
    private final String TAG = MerchandiseFragment.class.getSimpleName();
    private MerchandiseFragmentVM vm;
    private MerchandiseAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchandiseFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MerchandiseAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
