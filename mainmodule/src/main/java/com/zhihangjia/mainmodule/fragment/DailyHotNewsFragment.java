package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.DailyHotNewsAdapter;
import com.zhihangjia.mainmodule.callback.DailyHotNewsCB;
import com.zhihangjia.mainmodule.viewmodel.DailyHotNewsVM;

import java.util.List;

/**
 * 24小时热点新闻fragment
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class DailyHotNewsFragment extends BaseRecyclerViewFragment implements DailyHotNewsCB {
    private DailyHotNewsVM vm;
    private DailyHotNewsAdapter adapter;
    private String TAG = DailyHotNewsFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new DailyHotNewsVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new DailyHotNewsAdapter(list);
        adapter.setCurrentPageIndex(vm.types);
        return adapter;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.types = bundle.getInt(IntentConfig.TYPE);
        }
        super.initAll(view, savedInstanceState);
        if (vm.types == 0 || vm.types == 2) {
            binding.crv.setLoadMoreEnable(false);
            adapter.setEnableLoadMore(false);
        }
    }

    public void freshData() {
        if (vm != null) {
            vm.initData(true);
        }
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String pages() {
        return adapter.getData().get(adapter.getData().size() - 1).getBbs_id();
    }

    @Override
    public void removeItemNotify(int position) {
        adapter.getData().remove(position);
        adapter.notifyItemRemoved(position);
    }
}
