package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.util.CommonUtils;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingMultiItemQuickAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.databinding.ItemShopCarBinding;
import com.zhihangjia.mainmodule.databinding.ItemShopCarMerchandiseBinding;
import com.zhihangjia.mainmodule.databinding.ItemShopCarMerchandiseSpecificationBinding;
import com.zhihangjia.mainmodule.databinding.ItemShopcarRecommendGoodsBinding;
import com.zhihangjia.mainmodule.view.AddMinusView;
import com.zhixingjia.bean.mainmodule.ShopCar;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class ShopCarAdapter extends BaseDataBindingMultiItemQuickAdapter<ShopCar> {
    private ShopCarAdapterListener listener;

    public ShopCarAdapter(@Nullable List<ShopCar> data, ShopCarAdapterListener listener) {
        super(data);
        addItemType(0, R.layout.item_shop_car);
        addItemType(1, R.layout.item_shopcar_recommend_goods);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder helper, ShopCar item, int position) {
        if (item.getItemType() == 0) {
            ItemShopCarBinding binding = (ItemShopCarBinding)helper.getBinding();
            binding.setData(item);
            binding.llMerchandise.removeAllViews();
            List<ShopCar.ProductListBean> productListBeans = item.getProduct_list();

            binding.rbSelectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setSelect(!item.isSelect());
                    for (int i = 0; i < productListBeans.size(); i++) {
                        item.getProduct_list().get(i).setSelect(item.isSelect());
                        List<ShopCar.ProductListBean.SkuListBean> skuListBeans = item.getProduct_list().get(i).getSku_list();

                        if (skuListBeans != null && skuListBeans.size() > 0) {
                            for (int j = 0; j < skuListBeans.size(); j++) {
                                skuListBeans.get(j).setSelect(item.isSelect());
                            }
                        }
                    }
//                item.setTotalPrice(getTotalPrice(item));
                    if (listener != null) {
                        listener.changePrice();
                    }
                }
            });


            for (int i = 0; i < productListBeans.size(); i++) {
                ShopCar.ProductListBean productListBean = productListBeans.get(i);
                ItemShopCarMerchandiseBinding merchandiseBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_car_merchandise, null, false);
                merchandiseBinding.setData(productListBean);

                List<ShopCar.ProductListBean.SkuListBean> skuListBeans = productListBean.getSku_list();

                merchandiseBinding.rbSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        productListBean.setSelect(!productListBean.isSelect());
                        if (skuListBeans != null && skuListBeans.size() > 0) {
                            for (int j = 0; j < skuListBeans.size(); j++) {
                                skuListBeans.get(j).setSelect(productListBean.isSelect());
                            }
                        }

                        //设置上头的状态
                        boolean select = true;
                        for (int i = 0; i < productListBeans.size(); i++) {
                            if (!productListBeans.get(i).isSelect()) {
                                select = false;
                                break;
                            }
                        }
                        item.setSelect(select);

//                    item.setTotalPrice(getTotalPrice(item));
                        if (listener != null) {
                            listener.changePrice();
                        }
                    }
                });

                if (skuListBeans != null && skuListBeans.size() > 0) {
                    merchandiseBinding.llSpecification.removeAllViews();

                    for (int j = 0; j < skuListBeans.size(); j++) {
                        ShopCar.ProductListBean.SkuListBean skuListBean = skuListBeans.get(j);
                        ItemShopCarMerchandiseSpecificationBinding specificationBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_shop_car_merchandise_specification, null, false);
                        specificationBinding.setData(skuListBean);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dpToPx(mContext, 46));
                        merchandiseBinding.llSpecification.addView(specificationBinding.getRoot(), params);
                        specificationBinding.amv.setListener(new AddMinusView.AddMinusViewListener() {
                            @Override
                            public void currentNumChange(String currentNum) {
                                skuListBean.setSku_sum(currentNum);
                                item.setTotalPrice(getTotalPrice(item));
                                if (listener != null) {
                                    listener.changePrice();
                                }
                            }
                        });
                        specificationBinding.rbSelect.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                skuListBean.setSelect(!skuListBean.isSelect());
                                //设置上头的状态
                                boolean select = true;
                                for (int j = 0; j < skuListBeans.size(); j++) {
                                    if (!skuListBeans.get(j).isSelect()) {
                                        select = false;
                                        break;
                                    }
                                }
                                productListBean.setSelect(select);

                                //设置上头的状态
                                select = true;
                                for (int i = 0; i < productListBeans.size(); i++) {
                                    if (!productListBeans.get(i).isSelect()) {
                                        select = false;
                                        break;
                                    }
                                }
                                item.setSelect(select);


//                            item.setTotalPrice(getTotalPrice(item));
                                if (listener != null) {
                                    listener.changePrice();
                                }
                            }
                        });
                    }
                }
                binding.llMerchandise.addView(merchandiseBinding.getRoot());
            }

            binding.tvPrice.setText(getTotalPrice(item));
        } else {
            ItemShopcarRecommendGoodsBinding binding = (ItemShopcarRecommendGoodsBinding) helper.getBinding();
            item.getCommodity().setSold(CommonUtils.formatSold(item.getCommodity().getSold()));
            binding.setData(item.getCommodity());
            if (position == 0 || getData().get(position - 1).getItemType() == 0) {
                binding.tvTitle.setVisibility(View.VISIBLE);
            } else {
                binding.tvTitle.setVisibility(View.GONE);
            }
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.COMMODITY_ID, item.getCommodity().getCommodity_id());
                intent.putExtras(bundle);
                intent.setClass(mContext, MerchandiseDetailActivity.class);
                mContext.startActivity(intent);
            });
        }
    }

    private String getTotalPrice(ShopCar item) {
        String totalPrice = "0";
        List<ShopCar.ProductListBean> productListBeans = item.getProduct_list();
        for (int i = 0; i < productListBeans.size(); i++) {
            ShopCar.ProductListBean productListBean = productListBeans.get(i);
            List<ShopCar.ProductListBean.SkuListBean> skuListBeans = productListBean.getSku_list();
            if (skuListBeans != null && skuListBeans.size() == 0) {
                continue;
            }
            for (int j = 0; j < skuListBeans.size(); j++) {
                ShopCar.ProductListBean.SkuListBean skuListBean = skuListBeans.get(j);
                if (skuListBean.isSelect())
                    totalPrice = new BigDecimal(totalPrice).add(new BigDecimal(skuListBean.getSku_price()).multiply(new BigDecimal(skuListBean.getSku_sum()))).toString();
            }
        }
        return new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public interface ShopCarAdapterListener {
        void changePrice();
    }
}
