package com.zhihangjia.mainmodule.activity;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.YXHeadLineAdapter;
import com.zhihangjia.mainmodule.viewmodel.YXHeadLineVM;

import java.util.List;

/**
* @description 宜兴头条
* @author chenbin
* @date 2018/11/16 15:27
* @version v3.2.0
*/
public class YXHeadLineActivity extends BaseTitleRecyclerViewActivity {
    private YXHeadLineVM vm;
    private final String TAG = YXHeadLineActivity.class.getSimpleName();
    private YXHeadLineAdapter adapter;

    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new YXHeadLineVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new YXHeadLineAdapter(list);
        return adapter;
    }

    @Override
    public String setTitle() {
        return "宜兴头条";
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
