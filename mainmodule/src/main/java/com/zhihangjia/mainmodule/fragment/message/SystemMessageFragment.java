package com.zhihangjia.mainmodule.fragment.message;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseFragment;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.SystemMessageAdapter;
import com.zhihangjia.mainmodule.viewmodel.SystemMessageFragmentVM;

import java.util.List;

public class SystemMessageFragment extends BaseRecyclerViewFragment {
    private final String TAG = SystemMessageFragment.class.getSimpleName();
    private SystemMessageFragmentVM vm;
    private SystemMessageAdapter adapter;
    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new SystemMessageFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        adapter = new SystemMessageAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
