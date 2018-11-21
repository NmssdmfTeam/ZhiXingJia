package com.nmssdmf.commonlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.nmssdmf.commonlib.R;

public class TagView extends android.support.v7.widget.AppCompatTextView {
    private boolean isSelected;
    private int unSelectedTextColor;
    private int selectedTextColor;
    private Drawable selectedBackground;
    private Drawable unSelectedBackground;

    public TagView(Context context) {
        super(context);
        initView(null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagView);
        initView(typedArray);
    }

    private void initView(TypedArray typedArray) {
        if (typedArray == null) {
            selectedBackground = typedArray.getDrawable(R.styleable.TagView_selected_background);
            unSelectedBackground = typedArray.getDrawable(R.styleable.TagView_unselected_background);
            selectedTextColor = typedArray.getColor(R.styleable.TagView_selected_textcolor, getResources().getColor(R.color.text_orange));
            unSelectedTextColor = typedArray.getColor(R.styleable.TagView_unselected_textcolor, getResources().getColor(R.color.text_black));
        } else {
            selectedBackground = getResources().getDrawable(R.drawable.shape_standard_tag);
            unSelectedBackground = getResources().getDrawable(R.drawable.shape_choose_unselect);
            selectedTextColor = getResources().getColor(R.color.text_orange);
            unSelectedTextColor = getResources().getColor(R.color.text_black);
        }
        setTextColor(unSelectedTextColor);
        setBackgroundDrawable(unSelectedBackground);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelected) {
                    setTextColor(unSelectedTextColor);
                    setBackgroundDrawable(unSelectedBackground);
                } else {
                    setTextColor(selectedTextColor);
                    setBackgroundDrawable(selectedBackground);
                }
                isSelected = !isSelected;
            }
        });
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
