package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.YXTelecom;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/23 0023.
 */

public interface XYTelecomCB extends BaseCB {
    void setData(List<YXTelecom> telecoms, boolean isRefresh);

    void endFresh();
}
