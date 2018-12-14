package com.zhixingjia.goodsmanagemodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public interface GoodManageFragmentCB extends BaseRecyclerViewFragmentCB {
    String getLastPageId();
    void removeItem(int position);
}
