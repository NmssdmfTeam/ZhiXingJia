package com.nmssdmf.commonlib.rollviewpager.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * Created by Mr.Jude on 2016/1/10.
 */
public class IconHintView extends ShapeHintView {
    private int focusResId;
    private int normalResId;


    public IconHintView(Context context, @DrawableRes int focusResId, @DrawableRes int normalResId) {
        super(context);
        this.focusResId = focusResId;
        this.normalResId = normalResId;
    }


    @Override
    public Drawable makeFocusDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(focusResId);
        return drawable;
    }

    @Override
    public Drawable makeNormalDrawable() {
        Drawable drawable = getContext().getResources().getDrawable(normalResId);
        return drawable;
    }
}
