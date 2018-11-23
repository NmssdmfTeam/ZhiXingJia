package com.zhihangjia.mainmodule.viewmodel;

import android.os.Bundle;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseRecyclerViewFragmentVM;
import com.zhihangjia.mainmodule.callback.OrderListFragmentCB;

/**
 * Created by ${nmssdmf} on 2018/11/20 0020.
 */

public class OrderListFragmentVM extends BaseRecyclerViewFragmentVM {
    private OrderListFragmentCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public OrderListFragmentVM(OrderListFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    @Override
    public void initData(boolean isRefresh) {


        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
    }
}
