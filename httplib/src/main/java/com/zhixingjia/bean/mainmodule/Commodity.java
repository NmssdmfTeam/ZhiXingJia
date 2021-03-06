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
    private String sku_spec_info;//规格数据

    private boolean is_opened;//是否展开

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

    @Bindable
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        notifyPropertyChanged(BR.unit);
    }

    @Bindable
    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
        notifyPropertyChanged(BR.sold);
    }

    @Bindable
    public String getSku_spec_info() {
        return sku_spec_info;
    }

    public void setSku_spec_info(String sku_spec_info) {
        this.sku_spec_info = sku_spec_info;
        notifyPropertyChanged(BR.sku_spec_info);
    }

    @Bindable
    public boolean isIs_opened() {
        return is_opened;
    }

    public void setIs_opened(boolean is_opened) {
        this.is_opened = is_opened;
        notifyPropertyChanged(BR.is_opened);
    }
}
