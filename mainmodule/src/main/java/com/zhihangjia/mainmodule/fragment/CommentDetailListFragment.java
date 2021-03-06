package com.zhihangjia.mainmodule.fragment;

import android.os.Bundle;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
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
    public void initAll(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vm.commodityId = bundle.getString(IntentConfig.COMMODITY_ID);
        }
        super.initAll(view, savedInstanceState);
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

    @Override
    public void initContent(Bundle savedInstanceState) {
        super.initContent(savedInstanceState);
        baseTitleBinding.vStatusBar.setVisibility(View.VISIBLE);
        baseTitleBinding.tTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
