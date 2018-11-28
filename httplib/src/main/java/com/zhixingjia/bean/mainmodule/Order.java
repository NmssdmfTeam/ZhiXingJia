package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class Order extends BaseObservable{
    private String id;//订单ID，分页的时候需要传输
    private String order_id;//订单号，查看详情需要传此字段
    private String provider_id;//供应商ID
    private String order_status;//订单状态 0=待支付 1=待发货 2=待收货 3=待评价 4=已完成  99=到店付
    private String order_amount;//支付金额
    private String nickname;//昵称
    private String good_sum;//商品总数量
    private String order_status_name;//状态中文名称
    private List<ItemBean> item;//商品数组

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Bindable
    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
        notifyPropertyChanged(BR.order_amount);
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
    public String getGood_sum() {
        return good_sum;
    }

    public void setGood_sum(String good_sum) {
        this.good_sum = good_sum;
        notifyPropertyChanged(BR.good_sum);
    }

    @Bindable
    public String getOrder_status_name() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
        notifyPropertyChanged(BR.order_status_name);
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean extends BaseObservable{

        private String commodity_name;//商品名称
        private String item_img;//图片
        private String price;//单价
        private String number;//数量
        private String spec_info;//规格信息

        @Bindable
        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
            notifyPropertyChanged(BR.commodity_name);
        }

        @Bindable
        public String getItem_img() {
            return item_img;
        }

        public void setItem_img(String item_img) {
            this.item_img = item_img;
            notifyPropertyChanged(BR.item_img);
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
        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
            notifyPropertyChanged(BR.number);
        }

        @Bindable
        public String getSpec_info() {
            return spec_info;
        }

        public void setSpec_info(String spec_info) {
            this.spec_info = spec_info;
            notifyPropertyChanged(BR.spec_info);
        }
    }
}
