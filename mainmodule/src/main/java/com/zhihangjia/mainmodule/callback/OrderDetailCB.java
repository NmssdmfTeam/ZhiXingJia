package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public interface OrderDetailCB extends BaseCB {
    void addOrderStatusBtn(String status);
    void addGoods();
}
