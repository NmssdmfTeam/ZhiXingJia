package com.zhihangjia.mainmodule.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.activity.SmartLifeCommodityActivity;
import com.zhihangjia.mainmodule.bean.MessageCategory;
import com.zhihangjia.mainmodule.databinding.ItemMessageCategoryBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxtelecomCommodityBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxtelecomCommodityitemBinding;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.ArrayList;
import java.util.List;

/**
 * 宜兴电信广告内容
 */
public class YXTelecomViewPagerAdapter extends LoopPagerAdapter {

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private List<YXTelecom.CommodityBean> yxTelecoms = new ArrayList<>();
    private Context context;
    // SetScaleType(ImageView.ScaleType.CENTER_CROP);
    // 按比例扩大图片的size居中显示，使得图片长(宽)等于或大于View的长(宽)

    public YXTelecomViewPagerAdapter(RollPagerView viewPager) {
        super(viewPager);
        this.context = viewPager.getContext();
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        ItemYxtelecomCommodityBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_yxtelecom_commodity,null,false);
        YXTelecom.CommodityBean commodityBeans = yxTelecoms.get(position);
        for (YXTelecom.CommodityBean.CommodityInfoBean commodityInfoBean : commodityBeans.getCommodity_info()) {
            ItemYxtelecomCommodityitemBinding itemYxtelecomCommodityitemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_yxtelecom_commodityitem,null,false);
            itemYxtelecomCommodityitemBinding.setData(commodityInfoBean);
            binding.tlCommodity.addView(itemYxtelecomCommodityitemBinding.getRoot());
            itemYxtelecomCommodityitemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(IntentConfig.COMMODITY_ID, commodityInfoBean.getCommodity_id());
                    intent.putExtras(bundle);
                    intent.setClass(context, MerchandiseDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        binding.tvMoreCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMoreComodityActivity(commodityBeans.getCate_id());
            }
        });
        binding.ivNextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMoreComodityActivity(commodityBeans.getCate_id());
            }
        });
        return binding.getRoot();
    }

    private void toMoreComodityActivity(String cateId) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.CAT_ID, cateId);
        intent.putExtras(bundle);
        intent.setClass(context, SmartLifeCommodityActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getRealCount() {
        return yxTelecoms.size();
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    public List<YXTelecom.CommodityBean> getYxTelecoms() {
        return yxTelecoms;
    }

    public void setYxTelecoms(List<YXTelecom.CommodityBean> yxTelecoms) {
        this.yxTelecoms = yxTelecoms;
    }
}
