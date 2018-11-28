package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.zhihangjia.mainmodule.callback.ChooseSpecificationWindowCB;

public class ChooseSpecificationWindowVM {
    private ChooseSpecificationWindowCB cb;

    public ChooseSpecificationWindowVM(ChooseSpecificationWindowCB callback){
        this.cb = callback;
    }

    public void ivCloseClick(View view) {
        cb.closeWindow();
    }

    public void onAddCartClick(View view) {
        cb.addCart();
    }

    public void onBuyClick(View view) {
        cb.buyNow();
    }
}
