package com.zhixingjia.personmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhixingjia.personmodule.R;
import com.zhixingjia.personmodule.databinding.WindowCouponDescriptionBinding;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class CouponDescriptionWindow extends PopupWindow {
    private WindowCouponDescriptionBinding binding;

    public CouponDescriptionWindow (final Context context, String description){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_coupon_description, null, false);
        setContentView(binding.getRoot());
        setWidth(DensityUtil.dpToPx(context, 280));
        setHeight(DensityUtil.dpToPx(context, 186));

        binding.tvDescription.setText(description == null ? "" : description);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowUtil.setBackgroundAlpha((Activity) context, 1f);
            }
        });

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setText(String description){
        binding.tvDescription.setText(description == null ? "" : description);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }
}
