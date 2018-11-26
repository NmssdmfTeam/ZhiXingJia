package com.zhihangjia.mainmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.nmssdmf.commonlib.util.DensityUtil;
import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ViewMyMoneyBinding;


/**
* @description 我的金额布局
* @author chenbin
* @date 2018/11/20 15:38
* @version v3.2.0
*/
public class MyMoneyView extends FrameLayout {
    private int topTextcolor;
    private int topUnitTextcolor;
    private int bottomTextcolor;
    private float topTextsize;
    private float topUnitTextsize;
    private float bottomTextsize;
    private String topText;
    private String bottomText;
    private ViewMyMoneyBinding binding;

    public MyMoneyView(Context context) {
        super(context);
        initView(null);
    }

    public MyMoneyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyMoneyView);
        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {
        if (typedArray != null) {
            topTextcolor = typedArray.getColor(R.styleable.MyMoneyView_top_textcolor, getContext().getResources().getColor(R.color.text_red));
            topUnitTextcolor = typedArray.getColor(R.styleable.MyMoneyView_top_unit_textcolor, getContext().getResources().getColor(R.color.text_red));
            bottomTextcolor = typedArray.getColor(R.styleable.MyMoneyView_bottom_textcolor, getContext().getResources().getColor(R.color.text_money_gray));
            topTextsize = typedArray.getDimension(R.styleable.MyMoneyView_top_textsize, DensityUtil.dpToPx(getContext(),17));
            topUnitTextsize = typedArray.getDimension(R.styleable.MyMoneyView_top_unit_textsize, DensityUtil.dpToPx(getContext(),11));
            bottomTextsize = typedArray.getDimension(R.styleable.MyMoneyView_bottom_textsize, DensityUtil.dpToPx(getContext(),11));
            topText = typedArray.getString(R.styleable.MyMoneyView_top_text);
            bottomText = typedArray.getString(R.styleable.MyMoneyView_bottom_text);
        } else {
            topTextcolor = getContext().getResources().getColor(R.color.text_red);
            topUnitTextcolor = getContext().getResources().getColor(R.color.text_red);
            bottomTextcolor = getContext().getResources().getColor(R.color.text_money_gray);
            topTextsize = DensityUtil.dpToPx(getContext(),17);
            topUnitTextsize = DensityUtil.dpToPx(getContext(),11);
            bottomTextsize = DensityUtil.dpToPx(getContext(),11);
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_my_money, null, false);
        addView(binding.getRoot());
        binding.tvUnit.setTextColor(topUnitTextcolor);
        binding.tvAmount.setTextColor(topTextcolor);
        binding.tvBottom.setTextColor(bottomTextcolor);
        binding.tvUnit.setTextSize(TypedValue.COMPLEX_UNIT_PX,topUnitTextsize);
        binding.tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_PX,topTextsize);
        binding.tvBottom.setTextSize(TypedValue.COMPLEX_UNIT_PX,bottomTextsize);
        binding.tvAmount.setText(topText);
        binding.tvBottom.setText(bottomText);
    }

    public void setTopText(String amount) {
        binding.tvAmount.setText(amount);
    }

    @BindingAdapter(value = {"top_text"}, requireAll = false)
    public static void setMoneyText(final MyMoneyView view, String topText){
        view.setTopText(topText);
    }
}
