package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.zhihangjia.mainmodule.callback.ChooseShopCouponWindowCB;

public class ChooseShopCouponWindowVM {
    private ChooseShopCouponWindowCB cb;
    public ChooseShopCouponWindowVM(ChooseShopCouponWindowCB callback){
        cb = callback;
    }
    public void ivCloseClick(View view) {
        cb.closeWindow();
    }

    public void tvDoneClick(View view){
        cb.getDone();
    }
}
