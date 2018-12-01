package com.zhixingjia.personmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.zhixingjia.personmodule.BR;

/**
 * 成为卖家申请数据
 * Create by chenbin on 2018/12/1
 * <p>
 * <p>
 */
public class ApplySupplierBean extends BaseObservable {
    private String company_name;        //店铺名称
    private String trade_area;          //所在商圈
    private String trade_area_name;     //所在商圈名称
    private String co_addr;             //店铺地址
    private String deputy;              //法人代表
    private String co_phone;            //店铺电话
    private String main_camp;           //主管产品
    private String card_front;          //身份证正面
    private String card_back;           //身份证反面
    private String license_img;         //营业执照
    private String main_camp_name;      //主营产品用于显示

    @Bindable
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
    }

    @Bindable
    public String getTrade_area() {
        return trade_area;
    }

    public void setTrade_area(String trade_area) {
        this.trade_area = trade_area;
        notifyPropertyChanged(BR.trade_area);
    }

    @Bindable
    public String getCo_addr() {
        return co_addr;
    }

    public void setCo_addr(String co_addr) {
        this.co_addr = co_addr;
        notifyPropertyChanged(BR.co_addr);
    }

    @Bindable
    public String getDeputy() {
        return deputy;
    }

    public void setDeputy(String deputy) {
        this.deputy = deputy;
        notifyPropertyChanged(BR.deputy);
    }

    @Bindable
    public String getCo_phone() {
        return co_phone;
    }

    public void setCo_phone(String co_phone) {
        this.co_phone = co_phone;
        notifyPropertyChanged(BR.co_phone);
    }

    @Bindable
    public String getMain_camp() {
        return main_camp;
    }

    public void setMain_camp(String main_camp) {
        this.main_camp = main_camp;
        notifyPropertyChanged(BR.main_camp);
    }

    @Bindable
    public String getCard_front() {
        return card_front;
    }

    public void setCard_front(String card_front) {
        this.card_front = card_front;
        notifyPropertyChanged(BR.card_front);
    }

    @Bindable
    public String getCard_back() {
        return card_back;
    }

    public void setCard_back(String card_back) {
        this.card_back = card_back;
        notifyPropertyChanged(BR.card_back);
    }

    @Bindable
    public String getLicense_img() {
        return license_img;
    }

    public void setLicense_img(String license_img) {
        this.license_img = license_img;
        notifyPropertyChanged(BR.license_img);
    }

    @Bindable
    public String getMain_camp_name() {
        return main_camp_name;
    }

    public void setMain_camp_name(String main_camp_name) {
        this.main_camp_name = main_camp_name;
        notifyPropertyChanged(BR.main_camp_name);
    }

    @Bindable
    public String getTrade_area_name() {
        return trade_area_name;
    }

    public void setTrade_area_name(String trade_area_name) {
        this.trade_area_name = trade_area_name;
        notifyPropertyChanged(BR.trade_area_name);
    }
}
