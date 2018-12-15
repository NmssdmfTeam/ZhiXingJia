package com.zhixingjia.goodsmanagemodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.goodsmanagemodule.BR;

import java.io.Serializable;

/**
 * 价格库存
 * Create by chenbin on 2018/12/15
 * <p>
 * <p>
 */
public class PriceSet extends BaseObservable implements Serializable {
    private String price;           //价格
    private String stock;           //库存
    private String unit;            //销售单位

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
        notifyPropertyChanged(BR.stock);
    }

    @Bindable
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        notifyPropertyChanged(BR.unit);
    }
}
