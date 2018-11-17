package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.fragment.BaseTitleRecyclerViewFragment;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.CommentDetailListAdatper;
import com.zhihangjia.mainmodule.viewmodel.CommentDetailListVM;

import java.util.List;

/**
 * 评论详情fragment
 * Create by chenbin on 2018/11/17
 * <p>
 * <p>
 */
public class CommentDetailListFragment extends BaseTitleRecyclerViewFragment {
    private CommentDetailListVM vm;
    private CommentDetailListAdatper adatper;
    private final String TAG = CommentDetailListFragment.class.getSimpleName();

    @Override
    public BaseRecyclerViewFragmentVM initRecyclerViewFragmentVM() {
        vm = new CommentDetailListVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adatper = new CommentDetailListAdatper(list);
        return adatper;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public String setTitle() {
        return "";
    }
}
