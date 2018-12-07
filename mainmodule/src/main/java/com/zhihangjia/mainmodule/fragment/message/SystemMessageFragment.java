package com.zhihangjia.mainmodule.fragment.message;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.message.SystemMessageAdapter;
import com.zhihangjia.mainmodule.viewmodel.message.SystemMessageFragmentVM;

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
        adapter = new SystemMessageAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
