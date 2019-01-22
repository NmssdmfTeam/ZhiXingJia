package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseRecyclerViewFragmentCB;

public interface MyPostCB extends BaseRecyclerViewFragmentCB {
    void removeItemNotify(int position);
}
