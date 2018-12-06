package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * 用户信息
 * Create by chenbin on 2018/11/24
 * <p>
 * <p>
 */
public class UserInfo extends BaseObservable {

    private String member_id;           //会员ID
    private String avatar;              //会员头像
    private String realname;            //真实姓名
    private String nickname;            //昵称
    private String sex;                 //性别 0=男  1=女
    private String mobile;              // 账号
    private String house_provider;      //是否是供应商 0=否 1=是
    private String verify_status;       //供应商状态 未申请、审核成功、审核中、审核不通过
    private String wallet;              //钱包余额  - 供应商的信息
    private String sales_today;         //今日销售额  - 供应商的信息
    private String sales_month;         //本月销售额  - 供应商的信息
    private String service_tel;         //客服热线

    @Bindable
    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
        notifyPropertyChanged(BR.member_id);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
        notifyPropertyChanged(BR.realname);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
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
    public String getHouse_provider() {
        return house_provider;
    }

    public void setHouse_provider(String house_provider) {
        this.house_provider = house_provider;
        notifyPropertyChanged(BR.house_provider);
    }

    @Bindable
    public String getVerify_status() {
        return verify_status;
    }

    public void setVerify_status(String verify_status) {
        this.verify_status = verify_status;
        notifyPropertyChanged(BR.verify_status);
    }

    @Bindable
    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
        notifyPropertyChanged(BR.wallet);
    }

    @Bindable
    public String getSales_today() {
        return sales_today;
    }

    public void setSales_today(String sales_today) {
        this.sales_today = sales_today;
        notifyPropertyChanged(BR.sales_today);
    }

    @Bindable
    public String getSales_month() {
        return sales_month;
    }

    public void setSales_month(String sales_month) {
        this.sales_month = sales_month;
        notifyPropertyChanged(BR.sales_month);
    }

    public String getService_tel() {
        return service_tel;
    }

    public void setService_tel(String service_tel) {
        this.service_tel = service_tel;
    }
}
