package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public class Address extends BaseObservable implements Serializable {
    private String addr_id;

    private String names;

    private String mobile;

    private String area;

    private String addr;

    private String is_default;


    @Bindable
    public String getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(String addr_id) {
        this.addr_id = addr_id;
        notifyPropertyChanged(BR.addr_id);
    }

    @Bindable
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
        notifyPropertyChanged(BR.names);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
        notifyPropertyChanged(BR.area);
    }

    @Bindable
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
        notifyPropertyChanged(BR.addr);
    }

    @Bindable
    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
        notifyPropertyChanged(BR.is_default);
    }
}
