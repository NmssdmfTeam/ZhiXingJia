package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;

/**
* @description 发布商品
* @author chenbin
* @date 2018/11/21 11:23
* @version v3.2.0
*/
public class AddOrEditProductVM extends BaseVM {
    private AddOrEditProductCB callback;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddOrEditProductVM(AddOrEditProductCB callBack) {
        super(callBack);
        this.callback = callBack;
    }

    public void onSelectBrandClick(View view) {
        callback.showBrandWindow();
    }

    public void onSelectCategoryClick(View view) {
        callback.showCategoryWindow();
    }
}
