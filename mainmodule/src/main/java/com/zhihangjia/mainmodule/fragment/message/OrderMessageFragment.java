package com.zhihangjia.mainmodule.fragment.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.message.SystemMessageAdapter;
import com.zhihangjia.mainmodule.viewmodel.message.OrderMessageFragmentVM;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderMessageFragment extends BaseRecyclerViewFragment {
    private final String TAG = OrderMessageFragment.class.getSimpleName();

    private OrderMessageFragmentVM vm;
    private SystemMessageAdapter adapter;

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new OrderMessageFragmentVM(this);
        return vm;
    }

    @Override
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        boolean isLoadData = false;
        if (bundle != null) {
            isLoadData = bundle.getBoolean(IntentConfig.ENABLE_LOAD);
        }
        super.initAll(view, savedInstanceState);
        vm.setEnableLoad(isLoadData);
    }

    public void loadData() {
        vm.setEnableLoad(true);
        vm.initData(true);
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
