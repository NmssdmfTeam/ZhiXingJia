package com.zhixingjia.goodsmanagemodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.goodsmanagemodule.BR;

import java.io.Serializable;
import java.util.List;

/**
 * 有规格库存设置提交类
 * Create by chenbin on 2018/12/16
 * <p>
 * <p>
 */
public class SepcPriceStockSet extends BaseObservable implements Serializable {
    private String sku_product_id;      //商品规格ID，在新增的时候此处传0，修改的时候此处相对应的规格ID号，
    private String price;               //规格对应的单价
    private String stock;               //规格对应的库存
    private List<SpecInfo> spec_info;   //规格数据

    @Bindable
    public String getSku_product_id() {
        return sku_product_id;
    }

    public void setSku_product_id(String sku_product_id) {
        this.sku_product_id = sku_product_id;
        notifyPropertyChanged(BR.sku_product_id);
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
    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
        notifyPropertyChanged(BR.stock);
    }

    @Bindable
    public List<SpecInfo> getSpec_info() {
        return spec_info;
    }

    public void setSpec_info(List<SpecInfo> spec_info) {
        this.spec_info = spec_info;
        notifyPropertyChanged(BR.spec_info);
    }

    public static class SpecInfo extends BaseObservable implements Serializable {
        private String norms_id;        //key值对应的ID
        private String key;
        private String value;

        @Bindable
        public String getNorms_id() {
            return norms_id;
        }

        public void setNorms_id(String norms_id) {
            this.norms_id = norms_id;
            notifyPropertyChanged(BR.norms_id);
        }

        @Bindable
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
            notifyPropertyChanged(BR.key);
        }

        @Bindable
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
            notifyPropertyChanged(BR.value);
        }
    }
}
