package com.zhihangjia.mainmodule.window;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.nmssdmf.commonlib.util.WindowUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.adapter.ChooseCouponAdater;
import com.zhihangjia.mainmodule.databinding.WindowChooseCouponBinding;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class ChooseCouponWindow extends PopupWindow{
    public static final String TYPE_PLATFORM = "TYPE_PLATFORM";//平台优惠券
    public static final String TYPE_MERCHANT = "TYPE_MERCHANT";//商家优惠券

    /**
     *
     * @param context
     * @param type 区分商家优惠券还是平台优惠券
     * @param list 优惠券数据
     */
    public ChooseCouponWindow (final Context context, String type, List<Base> list){
        WindowChooseCouponBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.window_choose_coupon, null, false);
        setContentView(binding.getRoot());
        setHeight(DensityUtil.dpToPx(context, 371.5f));
        setWidth(MATCH_PARENT);
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        switch (type) {
            case TYPE_PLATFORM:{
                binding.tvTitle.setText("平台优惠券");
                break;
            }
            case TYPE_MERCHANT:{
                binding.tvTitle.setText("商家优惠券");
                break;
            }
        }

        //测试
        list = new ArrayList<>();
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        if (list != null && list.size() > 0) {
            ChooseCouponAdater adater = new ChooseCouponAdater(list);
            binding.crv.setAdapter(adater);
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowUtil.setBackgroundAlpha((Activity) context, 1f);
            }
        });

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowUtil.setBackgroundAlpha((Activity) parent.getContext(), 0.5f);
    }
}
