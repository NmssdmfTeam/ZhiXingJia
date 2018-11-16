package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchantMainCB;

public class MerchantMainVM extends BaseVM {
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchantMainVM(MerchantMainCB callBack) {
        super(callBack);
    }
}
