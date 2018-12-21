package com.zhihangjia.mainmodule.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.commonlib.config.IntentConfig;
import com.nmssdmf.commonlib.glide.util.GlideUtil;
import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.activity.MerchantMainActivity;
import com.zhihangjia.mainmodule.databinding.ItemSellerMemberBinding;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
 * Create by chenbin on 2018/11/14
 * <p>
 * <p>
 */
public class IndexSellerAdapter extends BaseDataBindingAdapter<IndexBean.SellerBean, ItemSellerMemberBinding> {

    public IndexSellerAdapter(@Nullable List<IndexBean.SellerBean> data) {
        super(R.layout.item_seller_member, data);
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemSellerMemberBinding> helper, IndexBean.SellerBean item, int position) {
        helper.getBinding().setData(item);
        helper.getBinding().rb.setNumStars(5);
        try {
            helper.getBinding().rb.setRating(Float.valueOf(item.getScore()));
        }catch (Exception e) {

        }
        helper.getBinding().getRoot().setOnClickListener(v -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(IntentConfig.ID, item.getMember_id());
            intent.putExtras(bundle);
            intent.setClass(mContext, MerchantMainActivity.class);
            mContext.startActivity(intent);
        });
    }
}
