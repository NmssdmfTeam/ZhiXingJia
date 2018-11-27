package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.rxbus.EventInfo;
import com.nmssdmf.commonlib.rxbus.RxBus;
import com.nmssdmf.commonlib.rxbus.RxEvent;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.adapter.message.MessageAdapter;
import com.zhihangjia.mainmodule.bean.MainBean;
import com.zhihangjia.mainmodule.databinding.ItemExcellentSellerBinding;
import com.zhihangjia.mainmodule.databinding.ItemMessageCenterBinding;
import com.zhihangjia.mainmodule.databinding.ItemRecommendGoodsBinding;
import com.zhihangjia.mainmodule.databinding.ItemRecommendGoodsInfoBinding;
import com.zhihangjia.mainmodule.databinding.ItemXyLifeServiceBinding;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页list adapter
 * Create by chenbin on 2018/11/13
 * <p>
 * <p>
 */
public class MainAdapter extends BaseDataBindingMultiItemQuickAdapter<MainBean> {
    public String[] serviceNames = new String[]{"宜兴紫砂","政务信息","特色美食","医疗养老","教育培训","汽车","百货","房产","旅游","找工作"};

    public MainAdapter(@Nullable List data) {
        super(data);
        addItemType(1, R.layout.item_recommend_goods);
        addItemType(2, R.layout.item_xy_life_service);
        addItemType(3, R.layout.item_message_center);
        addItemType(0, R.layout.item_excellent_seller);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ViewDataBinding> helper, final MainBean item, int position) {
        if (item.getItemType() == 1) {//推荐商品
            ItemRecommendGoodsBinding itemRecommendGoodsBinding = (ItemRecommendGoodsBinding) helper.getBinding();
            int childeCount = itemRecommendGoodsBinding.llContent.getChildCount();
            for (int i=1; i < childeCount - 1; i++) {
                itemRecommendGoodsBinding.llContent.removeViewAt(1);
            }
            if (item.getCommodityBeans() != null) {
                for (final IndexBean.CommodityBean commodityBean : item.getCommodityBeans()) {
                    ItemRecommendGoodsInfoBinding itemRecommendGoodsInfoBinding = DataBindingUtil
                            .inflate(LayoutInflater.from(mContext), R.layout.item_recommend_goods_info, null, false);
                    itemRecommendGoodsInfoBinding.setData(commodityBean);
                    itemRecommendGoodsInfoBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            intent.setClass(mContext, MerchandiseDetailActivity.class);
                            bundle.putString(IntentConfig.COMMODITY_ID, commodityBean.getCommodity_id());
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        }
                    });
                    itemRecommendGoodsBinding.llContent.addView(itemRecommendGoodsInfoBinding.getRoot(), 1);
                }
            }
            itemRecommendGoodsBinding.setData(item);
            if (item.getBannerMiddle() == null)
                itemRecommendGoodsBinding.ivBottomAdvertisement.setVisibility(View.GONE);
            else
                itemRecommendGoodsBinding.ivBottomAdvertisement.setVisibility(View.VISIBLE);
        } else if (item.getItemType() == 2) {
            ItemXyLifeServiceBinding itemLifeServiceBinding = (ItemXyLifeServiceBinding) helper.getBinding();
            if (itemLifeServiceBinding.rvService.getLayoutManager() == null)
                itemLifeServiceBinding.rvService.setLayoutManager(new GridLayoutManager(mContext, 5));
            List<Base> bases = new ArrayList<>();
            for (String name : serviceNames) {
                Base base = new Base();
                base.setMessage(name);
                bases.add(base);
            }
            ServiceAdapter adapter;
            if (itemLifeServiceBinding.rvService.getAdapter() == null) {
                adapter = new ServiceAdapter(bases);
                itemLifeServiceBinding.rvService.setAdapter(adapter);
                adapter.loadMoreEnd(false);
            } else {
                adapter = (ServiceAdapter) itemLifeServiceBinding.rvService.getAdapter();
                adapter.setNewData(bases);
            }
            adapter.notifyDataSetChanged();
        } else if (item.getItemType() == 3) {
            ItemMessageCenterBinding binding = (ItemMessageCenterBinding)helper.getBinding();
            if (binding.rvMessage.getLayoutManager() == null) {
                binding.rvMessage.setLayoutManager(new LinearLayoutManager(mContext
                        , LinearLayoutManager.VERTICAL, false));
            }
            MessageAdapter adapter;
            if (binding.rvMessage.getAdapter() == null) {
                adapter = new MessageAdapter(item.getForumBeans());
                binding.rvMessage.setAdapter(adapter);
                adapter.loadMoreEnd(false);
            } else {
                adapter = (MessageAdapter) binding.rvMessage.getAdapter();
                adapter.setNewData(item.getForumBeans());
            }
            binding.tvMessageCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //切换到信息中心页面
                    EventInfo eventInfo = new EventInfo();
                    eventInfo.setIndex(2);
                    RxBus.getInstance().send(RxEvent.BbsEvent.INDEX_SWITCH, eventInfo);
                }
            });
            adapter.notifyDataSetChanged();
        } else if (item.getItemType() == 0) {
            ItemExcellentSellerBinding itemExcellentSellerBinding = (ItemExcellentSellerBinding) helper.getBinding();
            if (itemExcellentSellerBinding.rvSeller.getLayoutManager() == null)
                itemExcellentSellerBinding.rvSeller.setLayoutManager(new GridLayoutManager(mContext, 4));
            IndexSellerAdapter adapter;
            if (itemExcellentSellerBinding.rvSeller.getAdapter() == null) {
                adapter = new IndexSellerAdapter(item.getSellerBeans());
                itemExcellentSellerBinding.rvSeller.setAdapter(adapter);
                adapter.loadMoreEnd(false);
            } else {
                adapter = (IndexSellerAdapter) itemExcellentSellerBinding.rvSeller.getAdapter();
                adapter.setNewData(item.getSellerBeans());
            }
        }
    }
}
