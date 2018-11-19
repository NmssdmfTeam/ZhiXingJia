package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.WindowDeliveryMethodBinding;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class DeliveryMethodWindow extends PopupWindow {

    public DeliveryMethodWindow(final Context context) {

        WindowDeliveryMethodBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_delivery_method, null, false);
        setContentView(binding.getRoot());
        setHeight(DensityUtil.dpToPx(context, 256.5f));
        setWidth(MATCH_PARENT);

        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        List<String> list = new ArrayList<>();
        list.add("商家配送");
        list.add("上门自提");
        binding.wp.setData(list);

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
}
