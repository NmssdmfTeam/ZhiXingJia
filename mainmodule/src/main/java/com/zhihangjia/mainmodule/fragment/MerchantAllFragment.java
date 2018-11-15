package com.zhihangjia.mainmodule.fragment;


import android.support.v4.app.Fragment;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantAllAdapter;
import com.zhihangjia.mainmodule.adapter.MerchantMainAdapter;
import com.zhihangjia.mainmodule.callback.MerchantAllFragmentCB;
import com.zhihangjia.mainmodule.viewmodel.MerchantAllFragmentVM;
import com.zhihangjia.mainmodule.viewmodel.MerchantMainFragmentVM;

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
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
