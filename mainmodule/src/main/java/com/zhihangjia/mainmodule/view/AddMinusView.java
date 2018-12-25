package com.zhihangjia.mainmodule.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
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
    private int minNum = 0;
    private String currentNum = "0";
    private boolean isEditEnable = true;

    private AddMinusViewListener listener;

    private ViewNumAddMinusBinding binding;
    private EditText tvNum;

    public AddMinusView(Context context) {
        super(context);
        initView(null);
    }

    public AddMinusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddMinusView);

        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {

        if (typedArray != null) {
            maxNum = typedArray.getInteger(R.styleable.AddMinusView_maxNum, 0);
            minNum = typedArray.getInteger(R.styleable.AddMinusView_minNum, 0);
            currentNum = typedArray.getString(R.styleable.AddMinusView_currentNum);
            addImageOn = typedArray.getResourceId(R.styleable.AddMinusView_addImageOn, R.drawable.icon_shop_car_add_on);
            addImageOff = typedArray.getResourceId(R.styleable.AddMinusView_addImageOff, R.drawable.icon_shop_car_add_off);
            minusImageOn = typedArray.getResourceId(R.styleable.AddMinusView_minusImageOn, R.drawable.icon_shop_car_minus_on);
            minusImageOff = typedArray.getResourceId(R.styleable.AddMinusView_minusImageOff, R.drawable.icon_shop_car_minus_off);
        }

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.view_num_add_minus, null, false);
        tvNum = binding.tvNum;
        addView(binding.getRoot());
        if (TextUtils.isEmpty(currentNum) || Integer.valueOf(currentNum) < minNum)
            binding.tvNum.setText(String.valueOf(minNum));
        else
            binding.tvNum.setText(String.valueOf(currentNum));
        binding.ivAdd.setImageResource(addImageOn);
        binding.ivMinus.setImageResource(minusImageOff);

        binding.ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditEnable)
                    return;
                if (Integer.valueOf(binding.tvNum.getText().toString()) == maxNum) {
                    return;
                }
                binding.tvNum.setText(new BigDecimal(binding.tvNum.getText().toString()).add(new BigDecimal(1)).toString());
            }
        });

        binding.ivMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditEnable)
                    return;
                if (Integer.valueOf(binding.tvNum.getText().toString()) == minNum) {
                    return;
                }
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
                if (TextUtils.isEmpty(s.toString()) || currentNum.equals(s.toString())) {
                    return;
                }
                currentNum = s.toString();
                if (Integer.valueOf(currentNum) <= minNum) {
                    binding.ivMinus.setEnabled(false);
                    binding.ivMinus.setImageResource(minusImageOff);
                    currentNum = String.valueOf(minNum);
//                    binding.tvNum.setText(currentNum);
                } else {
                    binding.ivMinus.setEnabled(true);
                    binding.ivMinus.setImageResource(minusImageOn);
                }

                if (Integer.valueOf(currentNum) >= maxNum) {
                    binding.ivAdd.setEnabled(false);
                    binding.ivAdd.setImageResource(addImageOff);
                    currentNum = String.valueOf(maxNum);
//                    binding.tvNum.setText(currentNum);
                } else {
                    binding.ivAdd.setEnabled(true);
                    binding.ivAdd.setImageResource(addImageOn);
                }
                if (listener != null)
                    listener.currentNumChange(currentNum);
            }
        });
    }

    public ViewNumAddMinusBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewNumAddMinusBinding binding) {
        this.binding = binding;
    }

    public AddMinusViewListener getListener() {
        return listener;
    }

    public void setListener(AddMinusViewListener listener) {
        this.listener = listener;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        if (TextUtils.isEmpty(currentNum))
            return;
        this.currentNum = currentNum;
        if (Integer.valueOf(currentNum) > minNum) {
            binding.ivMinus.setImageResource(minusImageOn);
            binding.ivMinus.setEnabled(true);
        } else {
            binding.ivMinus.setImageResource(minusImageOff);
            binding.ivMinus.setEnabled(false);
        }
        if (Integer.valueOf(currentNum) < maxNum) {
            binding.ivAdd.setImageResource(addImageOn);
            binding.ivAdd.setEnabled(true);
        } else {
            binding.ivAdd.setImageResource(addImageOff);
            binding.ivAdd.setEnabled(false);
        }
        binding.tvNum.setText(currentNum);
    }

    public interface AddMinusViewListener{
        void currentNumChange(String currentNum);
    }

    public EditText getTvNum() {
        return tvNum;
    }

    public void setTvNum(EditText tvNum) {
        this.tvNum = tvNum;
    }

    public boolean isEditEnable() {
        return isEditEnable;
    }

    public void setEditEnable(boolean editEnable) {
        binding.tvNum.setEnabled(editEnable);
        isEditEnable = editEnable;
    }

    @BindingAdapter(value = {"maxNum", "currentNum"}, requireAll = false)
    public static void setMaxNum(final AddMinusView view, int maxNum, String currentNum){
        view.maxNum = maxNum;
        view.setCurrentNum(currentNum);
        view.getBinding().tvNum.setText(currentNum);
    }

}
