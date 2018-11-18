package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.FocusNewsAdapter;
import com.zhihangjia.mainmodule.viewmodel.DailyHotNewsVM;

import java.util.List;

/**
 * 24小时热点新闻fragment
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class DailyHotNewsFragment extends BaseRecyclerViewFragment {
    private DailyHotNewsVM vm;
    private FocusNewsAdapter adapter;
    private String TAG = DailyHotNewsFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new DailyHotNewsVM(this);
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
