package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.MyPostReplyAdapter;
import com.zhihangjia.mainmodule.callback.MyPostReplyCB;
import com.zhihangjia.mainmodule.viewmodel.MyPostReplyFragmentVM;

import java.util.List;

/**
* @description 我的帖子 -- 回帖
* @author chenbin
* @date 2018/11/20 18:24
* @version v3.2.0
*/
public class MyPostReplyFragment extends BaseRecyclerViewFragment implements MyPostReplyCB {
    private MyPostReplyFragmentVM vm;
    private MyPostReplyAdapter adapter;
    private final String TAG = MyPostReplyFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new MyPostReplyFragmentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new MyPostReplyAdapter(list);
        return adapter;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void removeItemNotify(int position) {
        adapter.notifyItemRemoved(position);
    }
}
