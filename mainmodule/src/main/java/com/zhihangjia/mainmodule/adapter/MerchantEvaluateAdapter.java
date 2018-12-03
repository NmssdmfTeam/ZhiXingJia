package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemCommentDetailBinding;
import com.zhihangjia.mainmodule.databinding.ItemMerchantEvaluateBinding;
import com.zhihangjia.mainmodule.databinding.ItemMerchantMainBinding;
import com.zhixingjia.bean.mainmodule.Comment;

import java.util.List;

/**
 * 店铺主页，评价
 */
public class MerchantEvaluateAdapter extends BaseDataBindingAdapter<Comment, ItemMerchantEvaluateBinding> {
    public MerchantEvaluateAdapter(@Nullable List<Comment> data) {
        super(R.layout.item_merchant_evaluate, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchantEvaluateBinding> helper, Comment item, int position) {
        ItemMerchantEvaluateBinding binding = helper.getBinding();
        binding.setData(item);
        binding.tl.removeAllViews();
        if (item.getContents().size() > 0) {
            binding.tvContent.setText(item.getContents().get(0).getNote());
            int index = 0;
            for (Comment.ContentsBean.ImgsBean imgsBean : item.getContents().get(0).getImgs()) {
                if (index > 2)
                    break;
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dpToPx(mContext, 111), DensityUtil.dpToPx(mContext, 111));
                if (index != 2)
                    layoutParams.rightMargin = DensityUtil.dpToPx(mContext, 5);
                imageView.setLayoutParams(layoutParams);
                GlideUtil.load(imageView, imgsBean.getM_url());
                binding.tl.addView(imageView);
                index++;
            }
        }
    }
}
