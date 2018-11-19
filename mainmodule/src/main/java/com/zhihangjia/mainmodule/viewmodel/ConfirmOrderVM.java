package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.ConfirmOrderCB;

import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderVM extends BaseVM {
    private ConfirmOrderCB cb;

    private List<Base> list = new ArrayList<>();//商品数据
    private List<Base> couponList = new ArrayList<>();//优惠券数据
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ConfirmOrderVM(ConfirmOrderCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData(){
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
    }

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
    }

    public List<Base> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Base> couponList) {
        this.couponList = couponList;
    }
}
