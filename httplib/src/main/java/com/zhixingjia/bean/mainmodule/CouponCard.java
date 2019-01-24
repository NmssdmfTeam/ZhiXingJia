package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
* @description 我的卡券列表
* @author chenbin
* @date 2019/1/24 14:43
* @version v3.2.0
*/
public class CouponCard extends BaseObservable {

    private String code_id;                 //优惠券ID，用于分页传输
    private String title;                   //卡券名称
    private String coupon_img;              //卡券图片
    private String company_name;            //卡券发布者
    private String decrease;                //卡券优惠券金额
    private String morethannumber;          //满减需要达到的金额
    private String cond;                    //卡券类型 all=全额购 morethan=满减券
    private String qrcode;                  //二维码图片流
    private int type;                       //类型 ， 0=未使用 1=已使用 2=已失效

    @Bindable
    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
        notifyPropertyChanged(BR.code_id);
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
    public String getCoupon_img() {
        return coupon_img;
    }

    public void setCoupon_img(String coupon_img) {
        this.coupon_img = coupon_img;
        notifyPropertyChanged(BR.coupon_img);
    }

    @Bindable
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
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
    public String getMorethannumber() {
        return morethannumber;
    }

    public void setMorethannumber(String morethannumber) {
        this.morethannumber = morethannumber;
        notifyPropertyChanged(BR.morethannumber);
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
    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
        notifyPropertyChanged(BR.qrcode);
    }

    @Bindable
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
}
