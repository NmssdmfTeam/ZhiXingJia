package com.zhixingjia.goodsmanagemodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
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
    private String keyword;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new GoodManageFragmentVM(this);
        return vm;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.type = bundle.getInt(IntentConfig.TYPE);
        }
        vm.keyword = keyword;
        super.initAll(view, savedInstanceState);
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

    public void setKeyword(String keyword) {
        if (vm != null) {
            vm.keyword = keyword;
            binding.crv.setRefreshing(true);
            vm.initData(true);
        } else {
            this.keyword = keyword;
        }
    }
}
