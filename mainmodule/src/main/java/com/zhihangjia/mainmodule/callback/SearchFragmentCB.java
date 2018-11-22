package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

/**
 * Created by ${nmssdmf} on 2018/11/14 0014.
 */

public interface SearchFragmentCB extends BaseCB {
    void clearHistory();
    void refreshHistory();
}
