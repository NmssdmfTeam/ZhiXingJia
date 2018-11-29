package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public interface OrderListFragmentCB extends BaseRecyclerViewFragmentCB {
    void refreshAdapter(int index);
}
