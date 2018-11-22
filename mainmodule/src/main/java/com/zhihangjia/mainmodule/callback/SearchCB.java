package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 */

public interface SearchCB extends BaseCB {
    void setMaterialsMerchantHotHistory(List<String> list);
    void setMaterialsMerchandiseHotHistory(List<String> list);
    void setInformationCenterHotHistory(List<String> list);
}
