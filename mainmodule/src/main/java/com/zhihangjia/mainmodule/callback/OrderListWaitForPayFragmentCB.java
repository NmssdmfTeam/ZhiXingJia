package com.zhihangjia.mainmodule.callback;

import com.nmssdmf.commonlib.callback.BaseCB;
import com.zhixingjia.bean.mainmodule.Order;

import java.util.List;

public interface OrderListWaitForPayFragmentCB extends BaseCB {
    void refreshAdapter(boolean isRefresh, List<Order> list);
    void cancelOrder();
    void nofityItem(int index);
    List<String> getSelectedOrderIds();
}
