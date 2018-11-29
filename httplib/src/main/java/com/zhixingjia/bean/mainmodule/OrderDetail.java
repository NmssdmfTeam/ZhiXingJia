package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class OrderDetail extends BaseObservable {

    private String order_status;//订单状态 0=待支付 1=待发货 2=待收货 3=已收货 4=已完成 6=取消订单
    private String order_id;//订单号
    private String consignee;//收货人姓名
    private String consignee_address;//收货人地址
    private String consignee_phone;//收货人手机号码
    private String goods_amount;//商品总金额
    private String cost_freight;//运费
    private String order_amount;//支付金额
    private String provider_id;//商家ID
    private String discount_amount;//优惠金额
    private String nickname;//昵称
    private List<ItemBean> item;//商品数组
    private List<String> order_log;//操作明细日志，按照这个循环下去就可以
    private String remaining_time;//订单详情说明，最顶部那段话
    private String order_status_name;

    @Bindable
    public String getOrder_status_name() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
        notifyPropertyChanged(BR.order_status_name);
    }

    @Bindable
    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
        notifyPropertyChanged(BR.order_status);
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @Bindable
    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
        notifyPropertyChanged(BR.consignee);
    }

    @Bindable
    public String getConsignee_address() {
        return consignee_address;
    }

    public void setConsignee_address(String consignee_address) {
        this.consignee_address = consignee_address;
        notifyPropertyChanged(BR.consignee_address);
    }

    @Bindable
    public String getConsignee_phone() {
        return consignee_phone;
    }

    public void setConsignee_phone(String consignee_phone) {
        this.consignee_phone = consignee_phone;
        notifyPropertyChanged(BR.consignee_phone);
    }

    @Bindable
    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
        notifyPropertyChanged(BR.goods_amount);
    }

    @Bindable
    public String getCost_freight() {
        return cost_freight;
    }

    public void setCost_freight(String cost_freight) {
        this.cost_freight = cost_freight;
        notifyPropertyChanged(BR.cost_freight);
    }

    @Bindable
    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
        notifyPropertyChanged(BR.order_amount);
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }

    public List<String> getOrder_log() {
        return order_log;
    }

    public void setOrder_log(List<String> order_log) {
        this.order_log = order_log;
    }

    @Bindable
    public String getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
        notifyPropertyChanged(BR.remaining_time);
    }

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public static class ItemBean extends BaseObservable {
        private String commodity_name;//商品名称
        private String item_img;//图片
        private String price;//单价
        private String number;//数量
        private String spec_info;//规格信息
        private String item_id; //订单明细ID

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

        @Bindable
        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
            notifyPropertyChanged(BR.item_id);
        }
    }

    public static String fomatScore(int score) {
        String scoreTx = "";
        switch (score) {
            case 1:
                scoreTx = "非常差";
                break;
            case 2:
                scoreTx = "差";
                break;
            case 3:
                scoreTx = "一般";
                break;
            case 4:
                scoreTx = "好";
                break;
            case 5:
                scoreTx = "非常好";
            default:
        }
        return scoreTx;
    }
}
