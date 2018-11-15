package com.zhihangjia.mainmodule.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchantMerchandiseCB;

public class MerchantMerchandiseVM extends BaseVM {
    public static final int TYPE_MERCHANT = 0;//商家
    public static final int TYPE_MERCHANDISE = 1;//商品
    public final ObservableInt type = new ObservableInt(TYPE_MERCHANT);//0表示商家, 1：表示商品

    public static final int DOWN = 0;//向下
    public static final int UP = 1;//向上
    public final ObservableInt tvMerchantChooseStatus = new ObservableInt(DOWN);//商家
    public final ObservableInt tvPopularityStatus = new ObservableInt(DOWN);//人气
    public final ObservableInt tvCommentStatus = new ObservableInt(DOWN);//好评

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
        if (tvMerchantChooseStatus.get() == DOWN) {
            tvMerchantChooseStatus.set(UP);
        } else {
            tvMerchantChooseStatus.set(DOWN);
        }
    }

    public void tvPopularityClick(View view) {
        if (tvPopularityStatus.get() == DOWN) {
            tvPopularityStatus.set(UP);
        } else {
            tvPopularityStatus.set(DOWN);
        }
    }

    public void tvCommentClick(View view) {
        if (tvCommentStatus.get() == DOWN) {
            tvCommentStatus.set(UP);
        } else {
            tvCommentStatus.set(DOWN);
        }
    }
}
