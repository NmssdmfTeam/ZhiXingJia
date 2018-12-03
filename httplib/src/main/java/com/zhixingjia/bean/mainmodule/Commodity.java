package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
 * Created by ${nmssdmf} on 2018/11/26 0026.
 */

public class Commodity extends BaseObservable implements Serializable{
    private String commodity_id;//商品ID
    private String commodity_name;//商品标题
    private String imgs;//商品图片
    private String company_name;//商家公司名称
    private String price;//单价
    private String unit;//单位
    private String sold;//销量

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    @Bindable
    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
        notifyPropertyChanged(BR.commodity_name);
    }

    @Bindable
    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
        notifyPropertyChanged(BR.imgs);
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
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Bindable
    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
        notifyPropertyChanged(BR.sold);
    }
}
