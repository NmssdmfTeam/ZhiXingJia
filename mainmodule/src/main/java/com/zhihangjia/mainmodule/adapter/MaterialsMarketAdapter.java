package com.zhihangjia.mainmodule.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.databinding.ItemHotGoodsBinding;
import com.zhihangjia.mainmodule.databinding.ItemRecommendSellerBinding;

import java.util.ArrayList;
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
        addItemType(0,R.layout.item_hot_goods);
        addItemType(1,R.layout.item_recommend_seller);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, MainBean item, int position) {
        if (item.getItemType() == 0) {
            ItemHotGoodsBinding itemHotGoodsBinding = (ItemHotGoodsBinding)helper.getBinding();
            if (itemHotGoodsBinding.crv.getLayoutManager() == null) {
                itemHotGoodsBinding.crv.setLayoutManager(new GridLayoutManager(mContext,3));
            }
            List<Base> list = new ArrayList<>();
            for (int i=0; i < 9; i++) {
                list.add(new Base());
            }
            ItemGoodsInfoAdapter adapter = null;
            if (itemHotGoodsBinding.crv.getAdapter() == null) {
                adapter = new ItemGoodsInfoAdapter(list);
                itemHotGoodsBinding.crv.setAdapter(adapter);
            } else {
                adapter = (ItemGoodsInfoAdapter) itemHotGoodsBinding.crv.getAdapter();
                adapter.setNewData(list);
            }
        } else if (item.getItemType() == 1) {
            ItemRecommendSellerBinding itemRecommendSellerBinding = (ItemRecommendSellerBinding)helper.getBinding();
            if (itemRecommendSellerBinding.crv.getLayoutManager() == null) {
                itemRecommendSellerBinding.crv.setLayoutManager(new LinearLayoutManager(mContext));
            }
            List<Base> list = new ArrayList<>();
            for (int i=0; i < 9; i++) {
                list.add(new Base());
            }
            ItemRecommendSellerAdapter adapter = null;
            if (itemRecommendSellerBinding.crv.getAdapter() == null) {
                adapter = new ItemRecommendSellerAdapter(list);
                itemRecommendSellerBinding.crv.setAdapter(adapter);
            } else {
                adapter = (ItemRecommendSellerAdapter) itemRecommendSellerBinding.crv.getAdapter();
                adapter.setNewData(list);
            }
        }
    }
}
