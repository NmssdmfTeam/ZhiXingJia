package com.zhihangjia.mainmodule.fragment;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.OrderListWaitForPayFragmentCB;

import java.util.ArrayList;
import java.util.List;

public class OrderListWaitForPayFragmentVM extends BaseVM {
    private OrderListWaitForPayFragmentCB cb;
    private List<Base> list = new ArrayList<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderListWaitForPayFragmentVM(OrderListWaitForPayFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
    }
}
