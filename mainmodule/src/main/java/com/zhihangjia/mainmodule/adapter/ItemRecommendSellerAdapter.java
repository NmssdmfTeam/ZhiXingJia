package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
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
        distance = ((int)(distance * 100 + 0.005)) / 100f;
        itemRecommendSellerInfoBinding.tvDistance.setText(distance+"km");
        //初始化图片
        if (item.getGoods_info() != null && item.getGoods_info().size() > 0) {
            helper.getBinding().llPics.setVisibility(View.VISIBLE);
            helper.getBinding().llPics.removeAllViews();
            int i = 0;
            for (HouseBean.SellerBean.GoodsInfo goodsInfo : item.getGoods_info()) {
                GlideImageView imageView = new GlideImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 110), DensityUtil.dpToPx(mContext, 82.5f));
                if (i != 2)
                    layoutParams.rightMargin = DensityUtil.dpToPx(mContext, 6.5f);
                imageView.setLayoutParams(layoutParams);
                GlideUtil.load(imageView, goodsInfo.getImgs());
                helper.getBinding().llPics.addView(imageView);
                i++;
            }
        } else {
            helper.getBinding().llPics.removeAllViews();
            helper.getBinding().llPics.setVisibility(View.GONE);
        }
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
