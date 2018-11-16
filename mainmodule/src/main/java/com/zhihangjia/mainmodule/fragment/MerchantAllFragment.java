package com.zhihangjia.mainmodule.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.MerchantAllAdapter;
import com.zhihangjia.mainmodule.callback.MerchantAllFragmentCB;
import com.zhihangjia.mainmodule.databinding.HeaderMerchantAllBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchantAllFragmentVM;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 店铺主页全部商品
 */
public class MerchantAllFragment extends BaseRecyclerViewFragment implements MerchantAllFragmentCB {

    private final String TAG = MerchantAllFragment.class.getSimpleName();
    private MerchantAllFragmentVM vm;
    private MerchantAllAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MerchantAllFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        adapter = new MerchantAllAdapter(list);
        HeaderMerchantAllBinding headerMerchantMainBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_merchant_all, null, false);
        adapter.addHeaderView(headerMerchantMainBinding.getRoot());
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        super.initAll(view, savedInstanceState);
        binding.crv.getSrl().setEnabled(false);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        binding.crv.setLayoutManager(layoutManager);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == vm.getList().size() + 1 || position == 0) ? layoutManager.getSpanCount() : 1;
            }
        });
    }
}
