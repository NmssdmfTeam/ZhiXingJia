package com.nmssdmf.commonlib.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.nmssdmf.commonlib.R;
import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;
import com.nmssdmf.commonlib.databinding.FragmentBaseRecyclerviewBinding;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.OnDataChangeListener;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;

import java.util.List;

public abstract class BaseTitleRecyclerViewFragment extends BaseTitleFragment implements BaseRecyclerViewFragmentCB {
    protected FragmentBaseRecyclerviewBinding binding;
    private BaseRecyclerViewFragmentVM baseRecyclerViewFragmentVM;
    private BaseDataBindingAdapter adapter;
    @Override
    public BaseVM initViewModel() {
        baseRecyclerViewFragmentVM = initRecyclerViewFragmentVM();
        return baseRecyclerViewFragmentVM;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_base_recyclerview;
    }

    @Override
    public void initContent(Bundle savedInstanceState) {
        binding = (FragmentBaseRecyclerviewBinding) baseViewBinding;

        adapter = initAdapter(baseRecyclerViewFragmentVM.getList());

        binding.crv.setAdapter(adapter);

        binding.crv.setOnDataChangeListener(new OnDataChangeListener() {
            @Override
            public void onRefresh() {
                baseRecyclerViewFragmentVM.initData(true);
            }

            @Override
            public void onLoadMore() {
                baseRecyclerViewFragmentVM.initData(false);
            }
        });

        baseRecyclerViewFragmentVM.initData(false);
    }

    public abstract BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM();
    public abstract BaseDataBindingAdapter initAdapter(List list);

    @Override
    public void refreshAdapter(final boolean isRefresh,final  List dataList) {
        if (isRefresh)
            binding.crv.setRefreshing(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataChangedAfterLoadMore(isRefresh, dataList);
            }
        });
    }

    @Override
    public void stopRefreshAnim() {
        binding.crv.setRefreshing(false);
    }
}
