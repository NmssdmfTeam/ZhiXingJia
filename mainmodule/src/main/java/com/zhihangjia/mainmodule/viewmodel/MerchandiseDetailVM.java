package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.MerchandiseDetailCB;

/**
* @description 商品详情viewmodel
* @author chenbin
* @date 2018/11/27 17:15
* @version v3.2.0
*/
public class MerchandiseDetailVM extends BaseVM {
    private MerchandiseDetailCB callback;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public MerchandiseDetailVM(MerchandiseDetailCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    public void onAddCartClick(View view) {
        callback.addCart();
    }
}
