package com.zhihangjia.mainmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zhihangjia.mainmodule.R;
import com.zhihangjia.mainmodule.databinding.ViewNumAddMinusBinding;

import java.math.BigDecimal;


/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class AddMinusView extends LinearLayout {
    private int addImageOn;
    private int addImageOff;
    private int minusImageOn;
    private int minusImageOff;
    private int maxNum = 0;
    private int currentNum = 0;

    public AddMinusView(Context context) {
        super(context);
        initView(null);
    }

    public AddMinusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddMinusView);

        initView(typedArray);
    }

    private void initView(TypedArray typedArray){

        if (typedArray!= null) {
            maxNum = typedArray.getInteger(R.styleable.AddMinusView_maxNum, 0);
            currentNum = typedArray.getInteger(R.styleable.AddMinusView_currentNum, 0);
            addImageOn = typedArray.getResourceId(R.styleable.AddMinusView_addImageOn, R.drawable.icon_shop_car_add_on);
            addImageOff = typedArray.getResourceId(R.styleable.AddMinusView_addImageOff, R.drawable.icon_shop_car_add_off);
            minusImageOn = typedArray.getResourceId(R.styleable.AddMinusView_minusImageOn, R.drawable.icon_shop_car_minus_on);
            minusImageOff = typedArray.getResourceId(R.styleable.AddMinusView_minusImageOff, R.drawable.icon_shop_car_minus_off);
        }

        final ViewNumAddMinusBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_num_add_minus, null, false);
        addView(binding.getRoot());

        binding.tvNum.setText(String.valueOf(currentNum));
        binding.ivAdd.setImageResource(addImageOn);
        binding.ivMinus.setImageResource(minusImageOff);

        binding.ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvNum.setText(new BigDecimal(binding.tvNum.getText().toString()).add(new BigDecimal(1)).toString());
            }
        });

        binding.ivMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvNum.setText(new BigDecimal(binding.tvNum.getText().toString()).subtract(new BigDecimal(1)).toString());
            }
        });

        binding.tvNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (new BigDecimal(s.toString()).intValue() <= 0) {
                    binding.ivMinus.setEnabled(false);
                    binding.ivMinus.setImageResource(minusImageOff);
                } else {
                    binding.ivMinus.setEnabled(true);
                    binding.ivMinus.setImageResource(minusImageOn);
                }

                if (new BigDecimal(s.toString()).intValue() >= maxNum) {
                    binding.ivAdd.setEnabled(false);
                    binding.ivAdd.setImageResource(addImageOff);
                } else {
                    binding.ivAdd.setEnabled(true);
                    binding.ivAdd.setImageResource(addImageOn);
                }
            }
        });
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    @BindingAdapter(value = {"maxNum", "currentNum"}, requireAll = false)
    public void setMaxNum(final AddMinusView view, int maxNum, int currentNum){
        setMaxNum(maxNum);
        setCurrentNum(currentNum);
    }

}
