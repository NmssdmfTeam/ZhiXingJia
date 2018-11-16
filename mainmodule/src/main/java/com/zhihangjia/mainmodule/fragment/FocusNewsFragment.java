package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.FocusNewsAdapter;
import com.zhihangjia.mainmodule.viewmodel.FocusNewsVM;

import java.util.List;

/**
* @description 要闻动态
* @author chenbin
* @date 2018/11/16 16:50
* @version v3.2.0
*/
public class FocusNewsFragment extends BaseRecyclerViewFragment {
    private FocusNewsVM vm;
    private FocusNewsAdapter adapter;
    private final String TAG = FocusNewsFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new FocusNewsVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new FocusNewsAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
