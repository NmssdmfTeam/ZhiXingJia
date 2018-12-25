package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

public interface MerchantMerchandiseCB extends BaseCB {
    void changeViewpager();
    void refreshAreaAdapter();
    void changeSelectType(int select);
}
