package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
 * 添加地址返回结果
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public class AddressInsertResult extends BaseObservable implements Serializable {
    private String addr_id;         //地址ID号，在订单结算页面中添加地址时需要用到

    @Bindable
    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
        notifyPropertyChanged(BR.addr_id);
    }
}
