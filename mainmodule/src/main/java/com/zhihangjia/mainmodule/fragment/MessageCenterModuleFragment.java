package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.message.MessageAdapter;
import com.zhihangjia.mainmodule.viewmodel.MessageCenterModuleFragmentVM;

import java.util.List;

/**
 * 24小时热点新闻fragment
 * Create by chenbin on 2018/11/18
 * <p>
 * <p>
 */
public class MessageCenterModuleFragment extends BaseRecyclerViewFragment {
    private MessageCenterModuleFragmentVM vm;
    private MessageAdapter adapter;
    private String TAG = MessageCenterModuleFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MessageCenterModuleFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MessageAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
