package com.zhixingjia.goodsmanagemodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhixingjia.goodsmanagemodule.activity.SelectStandardActivity;
import com.zhixingjia.goodsmanagemodule.callback.AddOrEditProductCB;

/**
* @description 发布商品
* @author chenbin
* @date 2018/11/21 11:23
* @version v3.2.0
*/
public class AddOrEditProductVM extends BaseVM {
    private AddOrEditProductCB cb;

    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public AddOrEditProductVM(AddOrEditProductCB callBack) {
        super(callBack);
        this.cb = callBack;
    }

    public void onSelectBrandClick(View view) {
        cb.showBrandWindow();
    }

    public void onSelectCategoryClick(View view) {
        cb.showCategoryWindow();
    }

    public void selectSpecificationClick(View view) {
        cb.doIntent(SelectStandardActivity.class, null);
    }
}
