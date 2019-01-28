package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

public interface CaptureCB extends BaseCB {
    void scanError();
    void checkResult();
}
