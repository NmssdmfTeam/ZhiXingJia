package com.nmssdmf.commonlib.callback;

import java.util.List;

public interface BaseRecyclerViewFragmentCB extends BaseCB {
    void refreshAdapter(boolean isRefresh, List dataList);
}
