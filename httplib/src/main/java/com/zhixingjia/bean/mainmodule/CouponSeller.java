package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
* @description 卖家优惠券列表
* @author chenbin
* @date 2018/11/30 9:45
* @version v3.2.0
*/
public class CouponSeller extends BaseObservable implements Serializable {
    private String coupon_id;           //优惠券ID，分页、编辑的时候需要用到
    private String title;               //优惠券名称
    private String description;         //优惠券规则描述
    private String timetype;            //时间类型1:时间段；2:发放后有效期
    private String starttime;           //开始时间
    private String endtime;             //结束时间
    private String expireday;           //领取后生存天数
    private String cond;                //使用条件,all=无门槛 morethan=订单金额满减
    private String morethannumber;      //订单金额
    private String decrease;            //优惠券金额
    private String number;              //领取数量
    private String status;              //状态 1=开启 0=关闭
    private String validity;            //有效期说明
    private String cond_name;           //使用条件中文说明

    @Bindable
    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
        notifyPropertyChanged(BR.coupon_id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
        notifyPropertyChanged(BR.timetype);
    }

    @Bindable
    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
        notifyPropertyChanged(BR.starttime);
    }

    @Bindable
    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
        notifyPropertyChanged(BR.endtime);
    }

    @Bindable
    public String getExpireday() {
        return expireday;
    }

    public void setExpireday(String expireday) {
        this.expireday = expireday;
        notifyPropertyChanged(BR.expireday);
    }

    @Bindable
    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
        notifyPropertyChanged(BR.cond);
    }

    @Bindable
    public String getMorethannumber() {
        return morethannumber;
    }

    public void setMorethannumber(String morethannumber) {
        this.morethannumber = morethannumber;
        notifyPropertyChanged(BR.morethannumber);
    }

    @Bindable
    public String getDecrease() {
        return decrease;
    }

    public void setDecrease(String decrease) {
        this.decrease = decrease;
        notifyPropertyChanged(BR.decrease);
    }

    @Bindable
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable
    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
        notifyPropertyChanged(BR.validity);
    }

    @Bindable
    public String getCond_name() {
        return cond_name;
    }

    public void setCond_name(String cond_name) {
        this.cond_name = cond_name;
        notifyPropertyChanged(BR.cond_name);
    }
}
