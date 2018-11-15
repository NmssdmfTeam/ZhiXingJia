package com.zhihangjia.mainmodule.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.zhihangjia.mainmodule.anim.BottomBehaviorAnim;
import com.zhihangjia.mainmodule.anim.IBehavior;
import com.zhihangjia.mainmodule.anim.IBehaviorAnim;


public class BottomBehavior extends CommonBehavior implements IBehavior {

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    public IBehaviorAnim createBehaviorAnim(CoordinatorLayout coordinatorLayout, View child) {
        return new BottomBehaviorAnim(child);
    }
}
