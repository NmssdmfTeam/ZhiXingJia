package com.zhihangjia.mainmodule.fragment;


import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantEvaluateAdapter;
import com.zhihangjia.mainmodule.callback.MerchantEvaluateFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MerchantEvaluateFragmentVM;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 店铺主页评价
 */
public class MerchantEvaluateFragment extends BaseRecyclerViewFragment implements MerchantEvaluateFragmentCB {

    private final String TAG = MerchantEvaluateFragment.class.getSimpleName();

    private MerchantEvaluateFragmentVM vm;
    private MerchantEvaluateAdapter adapter;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantEvaluateFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        adapter = new MerchantEvaluateAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
