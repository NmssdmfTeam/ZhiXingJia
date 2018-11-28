package com.zhihangjia.mainmodule.bean;

import java.io.Serializable;
import java.util.List;

public class ProductBean implements Serializable {
    private String provider_id;                 //商家ID
    private String dispatching_type;            //配送方式 0=商家配送  1=自提
    private String note;                        //备注
    private String coupon_code;                 //优惠券ID
    private List<GoodsInfo> goods_info;         //商品数组

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getDispatching_type() {
        return dispatching_type;
    }

    public void setDispatching_type(String dispatching_type) {
        this.dispatching_type = dispatching_type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public List<GoodsInfo> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(List<GoodsInfo> goods_info) {
        this.goods_info = goods_info;
    }

    public static class GoodsInfo {
        private String commodity_id;                //商品ID
        private String cart_id;                     //购物车ID，如果从立即购买或者询价，此处为0
        private String product_sku_id;              //规格ID，无规格填0
        private String number;                      //购买数量

        public String getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(String commodity_id) {
            this.commodity_id = commodity_id;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getProduct_sku_id() {
            return product_sku_id;
        }

        public void setProduct_sku_id(String product_sku_id) {
            this.product_sku_id = product_sku_id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
