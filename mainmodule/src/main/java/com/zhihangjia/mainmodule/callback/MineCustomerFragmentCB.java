package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public interface MineCustomerFragmentCB extends BaseCB {
    void bindVM();

    void endFresh();

    void initView();
}
