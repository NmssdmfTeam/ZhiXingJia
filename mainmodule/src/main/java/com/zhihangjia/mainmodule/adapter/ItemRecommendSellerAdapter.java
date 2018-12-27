package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.databinding.ItemRecommendSellerInfoBinding;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

public class ItemRecommendSellerAdapter extends BaseDataBindingAdapter<HouseBean.SellerBean,ItemRecommendSellerInfoBinding> {
    private LatLng location;

    public ItemRecommendSellerAdapter(@Nullable List<HouseBean.SellerBean> data) {
        super(R.layout.item_recommend_seller_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemRecommendSellerInfoBinding> helper, HouseBean.SellerBean item, int position) {
        ItemRecommendSellerInfoBinding itemRecommendSellerInfoBinding = helper.getBinding();
        itemRecommendSellerInfoBinding.setData(item);
        itemRecommendSellerInfoBinding.rb.setNumStars(5);
        LatLng latLng = null;
        try {
            itemRecommendSellerInfoBinding.rb.setRating(Float.valueOf(item.getScore()));
            latLng = new LatLng(Double.valueOf(item.getLatitude()), Double.valueOf(item.getLongitude()));
        }catch (Exception e) {

        }

        //测量距离
        float distance = AMapUtils.calculateLineDistance(location,latLng);
        //精确到小数点后两位
        distance = ((int)((distance / 1000f) * 100 + 0.05)) / 100f;
        itemRecommendSellerInfoBinding.tvDistance.setText(distance+"km");
        itemRecommendSellerInfoBinding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            intent.setClass(mContext, MerchantMainActivity.class);
            bundle.putString(IntentConfig.ID, item.getMember_id());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
