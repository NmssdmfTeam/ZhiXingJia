package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
* @description 未读消息
* @author chenbin
* @date 2018/12/7 13:19
* @version v3.2.0
*/
public class MessageUnread extends BaseObservable {
    private String all_message;         //全部消息未读数量，主要在首页、我的首页这里展示小红点用
    private String sys_message;         //系统消息未读数量
    private String order_message;       //订单消息未读数量

    @Bindable
    public String getAll_message() {
        return all_message;
    }

    public void setAll_message(String all_message) {
        this.all_message = all_message;
        notifyPropertyChanged(BR.all_message);
    }

    @Bindable
    public String getSys_message() {
        return sys_message;
    }

    public void setSys_message(String sys_message) {
        this.sys_message = sys_message;
        notifyPropertyChanged(BR.sys_message);
    }

    @Bindable
    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
        notifyPropertyChanged(BR.order_message);
    }
}
