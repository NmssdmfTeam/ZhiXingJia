package com.zhihangjia.mainmodule.activity;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.DailyHotNewsAdapter;
import com.zhihangjia.mainmodule.viewmodel.BbsTopCommentVM;

import java.util.ArrayList;
import java.util.List;

/**
* @description 精华置顶贴
* @author chenbin
* @date 2018/12/21 13:43
* @version v3.2.0
*/
public class BbsTopCommentActivity extends BaseTitleRecyclerViewActivity {
    private final static String TAG = BbsTopCommentActivity.class.getSimpleName();
    private BbsTopCommentVM vm;
    private DailyHotNewsAdapter adapter;

    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new BbsTopCommentVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new DailyHotNewsAdapter(new ArrayList<>());
        return adapter;
    }

    @Override
    public String setTitle() {
        return "精华置顶";
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
