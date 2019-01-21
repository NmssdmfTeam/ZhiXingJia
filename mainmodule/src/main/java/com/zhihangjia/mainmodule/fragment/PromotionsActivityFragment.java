package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.PromotionsActivityAdapter;
import com.zhihangjia.mainmodule.viewmodel.PromotionsActivityVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 展示中心--促销活动
* @author chenbin
* @date 2019/1/21 17:35
* @version v3.2.0
*/
public class PromotionsActivityFragment extends BaseRecyclerViewFragment {
    private PromotionsActivityVM vm;
    private final String TAG = PromotionsActivityFragment.class.getSimpleName();
    private PromotionsActivityAdapter adapter;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new PromotionsActivityVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new PromotionsActivityAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
