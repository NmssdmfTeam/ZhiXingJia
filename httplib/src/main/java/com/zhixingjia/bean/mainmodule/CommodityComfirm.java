package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
* @description 确认订单页面数据
* @author chenbin
* @date 2018/11/27 19:37
* @version v3.2.0
*/
public class CommodityComfirm extends BaseObservable {

    private AddressInfoBean address_info;               //收货地址相关数所
    private String order_pay_amount;                    //支付金额
    private List<InfoListBean> info_list;               //商家数组（包括商品等相关数据）

    @Bindable
    public CommodityComfirm.AddressInfoBean getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressInfoBean address_info) {
        this.address_info = address_info;
        notifyPropertyChanged(BR.address_info);
    }

    @Bindable
    public String getOrder_pay_amount() {
        return order_pay_amount;
    }

    public void setOrder_pay_amount(String order_pay_amount) {
        this.order_pay_amount = order_pay_amount;
        notifyPropertyChanged(BR.order_pay_amount);
    }

    @Bindable
    public List<InfoListBean> getInfo_list() {
        return info_list;
    }

    public void setInfo_list(List<InfoListBean> info_list) {
        this.info_list = info_list;
        notifyPropertyChanged(BR.info_list);
    }

    public static class AddressInfoBean extends BaseObservable {

        private String addr_id;                 //收货地址ID
        private String name;                    //名称
        private String mobile;                  //手机号码
        private String location;                //地址

        @Bindable
        public String getAddr_id() {
            return addr_id;
        }

        public void setAddr_id(String addr_id) {
            this.addr_id = addr_id;
            notifyPropertyChanged(BR.addr_id);
        }

        @Bindable
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            notifyPropertyChanged(BR.name);
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
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
            notifyPropertyChanged(BR.location);
        }
    }

    public static class InfoListBean extends BaseObservable {

        private String provider_id;                     //商家ID
        private String company_name;                    //商家名称
        private String shop_coupon;                     //商家优惠券可用张数
        private String cost_freight;                    //运费
        private String sum_total;                       //商品总件数
        private String product_amount;                  //商品总金额（包括运费）
        private List<ListInfoBean> list_info;           //商品数组

        @Bindable
        public String getProvider_id() {
            return provider_id;
        }

        public void setProvider_id(String provider_id) {
            this.provider_id = provider_id;
            notifyPropertyChanged(BR.provider_id);
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
        public String getShop_coupon() {
            return shop_coupon;
        }

        public void setShop_coupon(String shop_coupon) {
            this.shop_coupon = shop_coupon;
            notifyPropertyChanged(BR.shop_coupon);
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
        public String getSum_total() {
            return sum_total;
        }

        public void setSum_total(String sum_total) {
            this.sum_total = sum_total;
            notifyPropertyChanged(BR.sum_total);
        }

        @Bindable
        public String getProduct_amount() {
            return product_amount;
        }

        public void setProduct_amount(String product_amount) {
            this.product_amount = product_amount;
            notifyPropertyChanged(BR.product_amount);
        }

        @Bindable
        public List<ListInfoBean> getList_info() {
            return list_info;
        }

        public void setList_info(List<ListInfoBean> list_info) {
            this.list_info = list_info;
            notifyPropertyChanged(BR.list_info);
        }

        public static class ListInfoBean extends BaseObservable {

            private String commodity_name;                  //商品标题
            private String commodity_id;                    //商品ID
            private String cart_id;                         //购物车ID，立即购买此处为0
            private String sku_sum;                         //购买数量
            private String product_sku_id;                  //规格ID，无规格为0
            private String imgs;                            //规格图片
            private String sku_price;                       //单价
            private String sku_product_text;                //规格信息

            public String getCommodity_name() {
                return commodity_name;
            }

            public void setCommodity_name(String commodity_name) {
                this.commodity_name = commodity_name;
            }

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

            public String getSku_sum() {
                return sku_sum;
            }

            public void setSku_sum(String sku_sum) {
                this.sku_sum = sku_sum;
            }

            public String getProduct_sku_id() {
                return product_sku_id;
            }

            public void setProduct_sku_id(String product_sku_id) {
                this.product_sku_id = product_sku_id;
            }

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getSku_price() {
                return sku_price;
            }

            public void setSku_price(String sku_price) {
                this.sku_price = sku_price;
            }

            public String getSku_product_text() {
                return sku_product_text;
            }

            public void setSku_product_text(String sku_product_text) {
                this.sku_product_text = sku_product_text;
            }
        }
    }
}
