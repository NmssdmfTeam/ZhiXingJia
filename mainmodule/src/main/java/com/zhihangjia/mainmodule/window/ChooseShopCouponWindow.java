package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.util.ContextUtil;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.callback.ChooseShopCouponWindowCB;
import com.zhihangjia.mainmodule.databinding.WindowChooseShopCouponBinding;
import com.zhihangjia.mainmodule.viewmodel.ChooseShopCouponWindowVM;

/**
 * 商品详情 选择优惠券
 */
public class ChooseShopCouponWindow extends PopupWindow implements ChooseShopCouponWindowCB{
    private ChooseShopCouponWindowVM vm;
    public ChooseShopCouponWindow(final Context context) {
        WindowChooseShopCouponBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_choose_shop_coupon, null, false);
        setContentView(binding.getRoot());

        vm = new ChooseShopCouponWindowVM(this);
        binding.setVm(vm);
        setWidth(DensityUtil.dpToPx(context, 375));
        setHeight(DensityUtil.dpToPx(context, 477.5f));


        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowUtil.setBackgroundAlpha((Activity) context, 1);
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }

    @Override
    public void closeWindow() {
        dismiss();
    }

    @Override
    public void getDone() {

    }
}
