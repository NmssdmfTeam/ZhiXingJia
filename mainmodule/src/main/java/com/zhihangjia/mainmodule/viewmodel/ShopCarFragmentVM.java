package com.zhihangjia.mainmodule.viewmodel;

import android.view.View;

import com.nmssdmf.commonlib.bean.Base;
import com.nmssdmf.commonlib.viewmodel.BaseVM;
import com.zhihangjia.mainmodule.activity.ConfirmOrderActivity;
import com.zhihangjia.mainmodule.callback.ShopCarFragmentCB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/19 0019.
 */

public class ShopCarFragmentVM extends BaseVM {
    private List<Base> list = new ArrayList<>();
    private ShopCarFragmentCB cb;
    /**
     * 不需要callback可以传null
     *
     * @param callBack
     */
    public ShopCarFragmentVM(ShopCarFragmentCB callBack) {
        super(callBack);
        cb = callBack;
    }

    public void getData(){
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
        list.add(new Base());
    }

    public void tvSettleAccountsClick(View view){
        cb.doIntent(ConfirmOrderActivity.class, null);
    }

    public List<Base> getList() {
        return list;
    }

    public void setList(List<Base> list) {
        this.list = list;
    }
}
