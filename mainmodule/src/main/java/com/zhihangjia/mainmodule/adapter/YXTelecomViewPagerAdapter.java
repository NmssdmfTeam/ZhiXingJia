package com.zhihangjia.mainmodule.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.rollviewpager.RollPagerView;
import com.nmssdmf.commonlib.rollviewpager.adapter.LoopPagerAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.bean.MessageCategory;
import com.zhihangjia.mainmodule.databinding.ItemMessageCategoryBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxtelecomCommodityBinding;
import com.zhihangjia.mainmodule.databinding.ItemYxtelecomCommodityitemBinding;

import java.util.List;

/**
 * 宜兴电信广告内容
 */
public class YXTelecomViewPagerAdapter extends LoopPagerAdapter {

    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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
        for (int i = 0; i < 4; i++) {
            ItemYxtelecomCommodityitemBinding itemYxtelecomCommodityitemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_yxtelecom_commodityitem,null,false);
            binding.tlCommodity.addView(itemYxtelecomCommodityitemBinding.getRoot());
        }
        return binding.getRoot();
    }

    @Override
    public int getRealCount() {
        return 3;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }
}
