package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
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

    }
}
