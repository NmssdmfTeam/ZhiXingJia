package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailFragmentCB;

public class MerchandiseDetailFragmentVM extends BaseVM {
    private MerchandiseDetailFragmentCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseDetailFragmentVM(MerchandiseDetailFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void ivShareClick(View view){

    }

    public void ivAddShoppingCarClick(View view){

    }

    public void ivBackClick(View view){
        cb.onBack();
    }

    public void gotoCommentList(View view){
        cb.gotoCommentDetail();
    }

    public void getCoupon(View view){
        cb.showChooseCouponWindow();
    }

    public void chooseSpecification(View view){
        cb.showChooseSpecificationWindow();
    }
}
