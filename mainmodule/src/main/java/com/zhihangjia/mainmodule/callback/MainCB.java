package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
* @description 首页activity callback
* @author chenbin
* @date 2018/11/26 14:26
* @version v3.2.0
*/
public interface MainCB extends BaseCB {

    void switchFragment(int index);
    void initTab();
    void setShopCarNumber(String num);
}
