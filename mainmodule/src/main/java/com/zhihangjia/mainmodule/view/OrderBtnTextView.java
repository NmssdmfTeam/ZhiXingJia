package com.zhihangjia.mainmodule.view;

import android.content.Context;
import android.view.Gravity;
import com.nmssdmf.commonlib.util.DensityUtil;
import com.zhihangjia.mainmodule.R;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class OrderBtnTextView extends android.support.v7.widget.AppCompatTextView {
    public OrderBtnTextView(Context context){
        super(context);
        init(context);
    }

    public void init(Context context){
        setHeight(DensityUtil.dpToPx(context, 27));
        setWidth(DensityUtil.dpToPx(context, 75));
        setGravity(Gravity.CENTER);
        setTextColor(context.getResources().getColor(R.color.main_color));
        setBackgroundResource(R.drawable.shape_order_btn);
    }
}
