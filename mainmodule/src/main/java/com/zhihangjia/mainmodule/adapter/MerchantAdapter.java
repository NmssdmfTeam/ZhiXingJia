package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchandiseDetailActivity;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.databinding.ItemMerchantBinding;
import com.zhixingjia.bean.mainmodule.Seller;

import java.util.List;

/**
 * 商家adapter
 */
public class MerchantAdapter extends BaseDataBindingAdapter<Seller, ItemMerchantBinding> {
    private LatLng location;

    public MerchantAdapter(@Nullable List<Seller> data) {
        super(R.layout.item_merchant, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantBinding> helper, Seller item, int position) {
        ItemMerchantBinding binding = helper.getBinding();
        binding.setData(item);
        LatLng latLng = null;
        try {
            latLng = new LatLng(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude()));
        } catch (Exception e) {

        }
        //测量距离
        float distance = AMapUtils.calculateLineDistance(location, latLng);
        //精确到小数点后两位
        distance = ((int) ((distance / 1000f) * 100 + 0.05)) / 100f;
        binding.tvDistance.setText(distance + "km");
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, MerchantMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, item.getMember_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        if (item.getGoods_info().size() > 0) {
            binding.ivGoodFirst.setOnClickListener((View.OnClickListener) v -> {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(mContext, MerchandiseDetailActivity.class);
                bundle.putString(IntentConfig.COMMODITY_ID, item.getGoods_info().get(0).getCommodity_id());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            });
            if (item.getGoods_info().size() > 1) {
                binding.ivGoodSecond.setOnClickListener((View.OnClickListener) v -> {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    intent.setClass(mContext, MerchandiseDetailActivity.class);
                    bundle.putString(IntentConfig.COMMODITY_ID, item.getGoods_info().get(1).getCommodity_id());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                });
            }
            if (item.getGoods_info().size() > 2) {
                binding.ivGoodThird.setOnClickListener((View.OnClickListener) v -> {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    intent.setClass(mContext, MerchandiseDetailActivity.class);
                    bundle.putString(IntentConfig.COMMODITY_ID, item.getGoods_info().get(2).getCommodity_id());
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                });
            }
        }
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(double longitude, double latitude) {
        location = new LatLng(latitude, longitude);
        for (int i = 0; i < getData().size(); i++) {
            notifyItemChanged(i + 1);
        }
    }
}
