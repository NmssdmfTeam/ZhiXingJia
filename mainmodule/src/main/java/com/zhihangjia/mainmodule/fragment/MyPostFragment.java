package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MyPostAdapter;
import com.zhihangjia.mainmodule.viewmodel.MyPostFragmentVM;

import java.util.List;

/**
* @description 我的发帖fragment
* @author chenbin
* @date 2018/11/20 17:40
* @version v3.2.0
*/
public class MyPostFragment extends BaseRecyclerViewFragment {
    private MyPostFragmentVM vm;
    private MyPostAdapter adapter;
    private final String TAG = MyPostFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MyPostFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MyPostAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
