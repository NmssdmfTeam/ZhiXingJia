package com.zhixingjia.personmodule.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.nmssdmf.customerviewlib.databindingbase.BaseBindingViewHolder;
import com.nmssdmf.customerviewlib.databindingbase.BaseDataBindingAdapter;
import com.zhixingjia.bean.personmodule.Coupon;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.databinding.ItemMyCouponBinding;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class MyCouponAdater extends BaseDataBindingAdapter<Coupon, ItemMyCouponBinding> {
    private MyCouponAdaterListner listner;

    public MyCouponAdater(@Nullable List<Coupon> data, MyCouponAdaterListner listner) {
        super(R.layout.item_my_coupon, data);
        this.listner = listner;
    }

    @Override
    protected void convert2(BaseBindingViewHolder<ItemMyCouponBinding> helper, final Coupon item, int position) {
        ItemMyCouponBinding binding = helper.getBinding();
        binding.tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.showCouponDescriptionWindow(item.getDescription());
            }
        });
    }

    public interface MyCouponAdaterListner {
        void showCouponDescriptionWindow(String des);
    }
}
