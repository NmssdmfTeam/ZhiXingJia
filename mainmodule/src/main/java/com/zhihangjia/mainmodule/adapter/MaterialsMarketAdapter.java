package com.zhihangjia.mainmodule.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.bean.MainBean;

import java.util.List;

/**
 * 首页list adapter
 * Create by chenbin on 2018/11/13
 * <p>
 * <p>
 */
public class MaterialsMarketAdapter extends BaseDataBindingMultiItemQuickAdapter<MainBean> {


    public MaterialsMarketAdapter(@Nullable List data) {
        super(data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, MainBean item, int position) {

    }
}
