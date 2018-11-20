package com.zhihangjia.mainmodule.fragment;


import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.MerchantEvaluateAdapter;
import com.zhihangjia.mainmodule.callback.MerchantEvaluateFragmentCB;
import com.zhihangjia.mainmodule.databinding.HeaderMerchantEvaluateBinding;
import com.zhihangjia.mainmodule.viewmodel.MerchantEvaluateFragmentVM;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 店铺主页 口碑评价
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
        HeaderMerchantEvaluateBinding headerMerchantEvaluateBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_merchant_evaluate, null, false);
        adapter = new MerchantEvaluateAdapter(list);
        adapter.setHeaderView(headerMerchantEvaluateBinding.getRoot());
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
