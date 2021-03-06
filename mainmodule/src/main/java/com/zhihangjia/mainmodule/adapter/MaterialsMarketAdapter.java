package com.zhihangjia.mainmodule.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.House;
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
public class MaterialsMarketAdapter extends BaseDataBindingMultiItemQuickAdapter<House> {
    private LatLng location;

    public MaterialsMarketAdapter(@Nullable List data) {
        super(data);
        addItemType(0,R.layout.item_hot_goods);
        addItemType(1,R.layout.item_recommend_seller);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, House item, int position) {
        if (item.getItemType() == 0) {
            ItemHotGoodsBinding itemHotGoodsBinding = (ItemHotGoodsBinding)helper.getBinding();
            if (itemHotGoodsBinding.crv.getLayoutManager() == null) {
                itemHotGoodsBinding.crv.setLayoutManager(new GridLayoutManager(mContext,3));
            }
            ItemGoodsInfoAdapter adapter = null;
            if (itemHotGoodsBinding.crv.getAdapter() == null) {
                adapter = new ItemGoodsInfoAdapter(item.getProduct());
                itemHotGoodsBinding.crv.setAdapter(adapter);
            } else {
                adapter = (ItemGoodsInfoAdapter) itemHotGoodsBinding.crv.getAdapter();
                adapter.setNewData(item.getProduct());
            }
        } else if (item.getItemType() == 1) {
            ItemRecommendSellerBinding itemRecommendSellerBinding = (ItemRecommendSellerBinding)helper.getBinding();
            if (itemRecommendSellerBinding.crv.getLayoutManager() == null) {
                itemRecommendSellerBinding.crv.setLayoutManager(new LinearLayoutManager(mContext));
            }
            ItemRecommendSellerAdapter adapter = null;
            if (itemRecommendSellerBinding.crv.getAdapter() == null) {
                adapter = new ItemRecommendSellerAdapter(item.getSeller());
                adapter.setLocation(location);
                itemRecommendSellerBinding.crv.setAdapter(adapter);
            } else {
                adapter = (ItemRecommendSellerAdapter) itemRecommendSellerBinding.crv.getAdapter();
                adapter.setLocation(location);
                adapter.setNewData(item.getSeller());
            }
        }
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setLocation(double longitude,double latitude) {
        location = new LatLng(latitude, longitude);
        for (int i = 0 ;i < getData().size(); i++) {
            if (getData().get(i).getItemType() == 1) {
                notifyItemChanged(i+1);
            }
        }
    }
}
