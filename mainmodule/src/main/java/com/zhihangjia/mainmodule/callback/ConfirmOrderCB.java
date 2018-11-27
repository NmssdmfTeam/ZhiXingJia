package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.CommodityComfirm;

import java.util.List;

public interface ConfirmOrderCB extends BaseCB {

    void setData(List<CommodityComfirm.InfoListBean> infoListBeans, boolean isRefresh);

}
