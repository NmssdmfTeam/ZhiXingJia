package com.zhihangjia.mainmodule.adapter;

import android.support.annotation.Nullable;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ItemMerchandiseDetailChooseCouponBinding;
import com.zhixingjia.bean.mainmodule.CommodityDetail;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/30 0030.
 */

public class MerchandiseDetailChooseCouponAdapter extends BaseDataBindingAdapter<CommodityDetail.SellerCoupon, ItemMerchandiseDetailChooseCouponBinding> {
    private MerchandiseDetailChooseCouponAdapterListener listener;
    public MerchandiseDetailChooseCouponAdapter(@Nullable List<CommodityDetail.SellerCoupon> data, MerchandiseDetailChooseCouponAdapterListener listener) {
        super(R.layout.item_merchandise_detail_choose_coupon, data);
        this.listener = listener;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMerchandiseDetailChooseCouponBinding> helper, CommodityDetail.SellerCoupon item, int position) {
        ItemMerchandiseDetailChooseCouponBinding binding = helper.getBinding();
        binding.setData(item);

        binding.tvGet.setOnClickListener(v -> listener.getCoupon(item.getCoupon_id()));
    }

    public interface MerchandiseDetailChooseCouponAdapterListener{
        void getCoupon(String id);
    }
}
