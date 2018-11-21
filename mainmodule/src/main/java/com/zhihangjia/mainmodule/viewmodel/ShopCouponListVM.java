package com.zhihangjia.mainmodule.viewmodel;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.callback.ShopCouponListCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/21 0021.
 */

public class ShopCouponListVM extends BaseVM {
    private ShopCouponListCB cb;
    private List<Base> list = new ArrayList<>();
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ShopCouponListVM(ShopCouponListCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void initData(){
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

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
    }
}
