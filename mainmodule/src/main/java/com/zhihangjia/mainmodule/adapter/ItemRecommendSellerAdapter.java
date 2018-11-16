package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.view.GlideImageView;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemRecommendSellerInfoBinding;

import java.util.List;

public class ItemRecommendSellerAdapter extends BaseDataBindingAdapter<Base,ItemRecommendSellerInfoBinding> {
    public ItemRecommendSellerAdapter(@Nullable List<Base> data) {
        super(R.layout.item_recommend_seller_info, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemRecommendSellerInfoBinding> helper, Base item, int position) {
        for (int i=0; i < 3; i++) {
            GlideImageView imageView = new GlideImageView(mContext);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext,110),DensityUtil.dpToPx(mContext,82.5f));
            if (i != 2)
                layoutParams.rightMargin = DensityUtil.dpToPx(mContext,6.5f);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.no_pic);
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.main_color));
            helper.getBinding().llPics.addView(imageView);
        }
    }
}
