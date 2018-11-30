package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * Created by ${nmssdmf} on 2018/11/30 0030.
 */

public class Coupon extends BaseObservable{
    private String code_id;//优惠码ID，分页、使用都需要传这个值
    private String validity;//有效期
    private String company_name;//使用说明
    private String description;//规则描述
    private String decrease;//优惠金额
    private String cond_name;//使用条件

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
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
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    public String getCond_name() {
        return cond_name;
    }

    public void setCond_name(String cond_name) {
        this.cond_name = cond_name;
        notifyPropertyChanged(BR.cond_name);
    }
}
