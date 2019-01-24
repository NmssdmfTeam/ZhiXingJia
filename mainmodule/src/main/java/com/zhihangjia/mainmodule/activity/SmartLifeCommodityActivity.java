package com.zhihangjia.mainmodule.activity;

import android.support.v7.widget.GridLayoutManager;

import com.nmssdmf.commonlib.activity.BaseTitleRecyclerViewActivity;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.viewmodel.BaseTitleRecyclerViewVM;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.adapter.SmartLifeCommodityAdapter;
import com.zhihangjia.mainmodule.viewmodel.SmartLifeCommodityVM;

import java.util.List;

/**
* @description 智慧生活
* @author chenbin
* @date 2019/1/24 11:44
* @version v3.2.0
*/
public class SmartLifeCommodityActivity extends BaseTitleRecyclerViewActivity {
    private SmartLifeCommodityAdapter adapter;
    private final String TAG = SmartLifeCommodityActivity.class.getSimpleName();
    private SmartLifeCommodityVM vm;

    @Override
    public BaseTitleRecyclerViewVM initTitleRecyclerViewViewModel() {
        vm = new SmartLifeCommodityVM(this);
        return vm;
    }

    @Override
    public BaseDataBindingAdapter initAdapter(List list) {
        adapter = new SmartLifeCommodityAdapter(list);
        binding.crv.setLayoutManager(new GridLayoutManager(this, 2));
        binding.crv.setPadding(DensityUtil.dpToPx(this, 8.5f), 0, DensityUtil.dpToPx(this, 8.5f), 0);
        return adapter;
    }

    @Override
    public String setTitle() {
        return "智慧生活";
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
