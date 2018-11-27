package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * 规格对应表
 * 规格数据，用于比例，也供展示库存与价格，购买时需要传规格ID，无规格为空
 * Create by chenbin on 2018/11/26
 * <p>
 * <p>
 */
public class SkuBean extends BaseObservable {
    private String product_id;                  //规格ID
    private String price;                       //价格
    private String stock;                       //库存
    private List<SpecInfoBean> spec_info;       //规格值，用于当用户选择规格信息时进行比例，从而得知规格ID、价格、库存信息

    @Bindable
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
        notifyPropertyChanged(BR.product_id);
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
    public List<SpecInfoBean> getSpec_info() {
        return spec_info;
    }

    public void setSpec_info(List<SpecInfoBean> spec_info) {
        this.spec_info = spec_info;
        notifyPropertyChanged(BR.spec_info);
    }

    public static class SpecInfoBean extends BaseObservable {

        private String key;
        private String value;

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
