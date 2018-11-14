package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;

public class MerchantMerchandiseVM extends BaseVM {
    public static final int TYPE_MERCHANT = 0;
    public static final int TYPE_MERCHANDISE = 1;
    public final ObservableInt type = new ObservableInt(0);//0表示商家, 1：表示商品

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMerchandiseVM(MerchantMerchandiseCB callBack) {
        super(callBack);
    }

    public void tvMerchantClick(View view) {
        type.set(TYPE_MERCHANT);
    }

    public void tvMerchandiseClick(View view) {
        type.set(TYPE_MERCHANDISE);
    }
}
