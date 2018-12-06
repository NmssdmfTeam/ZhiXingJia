package com.zhihangjia.mainmodule.activity;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.DailyHotNewsAdapter;
import com.zhihangjia.mainmodule.viewmodel.BbsStickyPostVM;

import java.util.List;

/**
* @description 搜索帖子,精华帖子
* @author chenbin
* @date 2018/12/6 16:47
* @version v3.2.0
*/
public class BbsStickyPostActivity extends BaseTitleRecyclerViewActivity {
    private final String TAG = BbsStickyPostActivity.class.getSimpleName();
    private BbsStickyPostVM vm;
    private DailyHotNewsAdapter adapter;

    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new BbsStickyPostVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new DailyHotNewsAdapter(list);
        return adapter;
    }

    @Override
    public String setTitle() {
        return "万家灯火";
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
