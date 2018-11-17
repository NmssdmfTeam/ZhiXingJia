package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

public interface MerchandiseDetailFragmentCB extends BaseCB {
    void onBack();
    void showChooseCouponWindow();
    void showChooseSpecificationWindow();
}
