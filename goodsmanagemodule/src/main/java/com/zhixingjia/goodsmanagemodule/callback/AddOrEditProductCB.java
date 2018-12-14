package com.zhixingjia.goodsmanagemodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 添加编辑商品callback
* @author chenbin
* @date 2018/11/21 15:16
* @version v3.2.0
*/
public interface AddOrEditProductCB extends BaseCB {

    void showBrandWindow();

    void showCategoryWindow();

    void initData();
}
