package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;

public class MerchantMerchandiseVM extends BaseVM {
    public static final int TYPE_MERCHANT = 0;//商家
    public static final int TYPE_MERCHANDISE = 1;//商品
    public final ObservableInt type = new ObservableInt(TYPE_MERCHANT);//0表示商家, 1：表示商品

    public final ObservableBoolean tvMerchantChooseSelect = new ObservableBoolean(false);//商家
    public final ObservableBoolean tvPopularitySelect = new ObservableBoolean(false);
    public final ObservableBoolean tvCommentSelect = new ObservableBoolean(false);
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

    public void tvMerchantChooseClick(View view) {
        tvMerchantChooseSelect.set(!tvMerchantChooseSelect.get());
    }

    public void tvPopularityClick(View view) {
        tvPopularitySelect.set(!tvPopularitySelect.get());
    }

    public void tvCommentClick(View view) {
        tvCommentSelect.set(!tvCommentSelect.get());
    }
}
