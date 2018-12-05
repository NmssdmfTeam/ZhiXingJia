package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.commonlib.util.StringUtil;
import com.nmssdmf.customerviewlib.entity.MultiItemEntity;
import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/27 0027.
 */

public class ShopCar extends BaseObservable implements MultiItemEntity{

    private String provider_id;//店铺ID
    private String company_name;//店铺名称
    private String cart_sort;//这个字段客户端不需要理会，是用于失效排序之用
    private List<ProductListBean> product_list;//商品数组
    private String totalPrice = "0";//不是接口返回
    private Commodity commodity;    //推荐商品

    private boolean select;//不是接口返回

    private int type = 0;

    @Bindable
    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
        notifyPropertyChanged(BR.select);
    }


    @Bindable
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
        notifyPropertyChanged(BR.totalPrice);
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    @Bindable
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
    }

    public String getCart_sort() {
        return cart_sort;
    }

    public void setCart_sort(String cart_sort) {
        this.cart_sort = cart_sort;
    }

    public List<ProductListBean> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<ProductListBean> product_list) {
        this.product_list = product_list;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public static class ProductListBean extends BaseObservable{
        private String commodity_name;//商品标题
        private String commodity_id;//商品ID
        private String imgs;//商品图片
        private String unit;//商品单位
        private String status;//商品状态 1=正常 0=失效
        private String cart_sort;//这个字段客户端不需要理会，是用于失效排序之用
        private List<SkuListBean> sku_list;//商品规格数组

        private boolean select;//不是接口返回

        @Bindable
        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
            notifyPropertyChanged(BR.select);
        }

        @Bindable
        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
            notifyPropertyChanged(BR.commodity_name);
        }

        public String getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(String commodity_id) {
            this.commodity_id = commodity_id;
        }

        @Bindable
        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCart_sort() {
            return cart_sort;
        }

        public void setCart_sort(String cart_sort) {
            this.cart_sort = cart_sort;
        }

        public List<SkuListBean> getSku_list() {
            return sku_list;
        }

        public void setSku_list(List<SkuListBean> sku_list) {
            this.sku_list = sku_list;
        }

        public static class SkuListBean extends BaseObservable{
            private String cart_id;//购物车ID
            private String sku_price;//单价
            private String sku_sum;//购买数量
            private String product_sku_id;//规格ID
            private String sku_product_text;//规格文本名称
            private String stock;//目前的库存信息
            private boolean select;//不是接口

            @Bindable
            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
                notifyPropertyChanged(BR.select);
            }

            public String getCart_id() {
                return cart_id;
            }

            public void setCart_id(String cart_id) {
                this.cart_id = cart_id;
            }

            @Bindable
            public String getSku_price() {
                return sku_price;
            }

            public void setSku_price(String sku_price) {
                this.sku_price = sku_price;
                notifyPropertyChanged(BR.sku_price);
            }

            @Bindable
            public String getSku_sum() {
                return StringUtil.isEmpty(sku_sum) ? "0" : sku_sum;
            }

            public void setSku_sum(String sku_sum) {
                this.sku_sum = sku_sum;
                notifyPropertyChanged(BR.sku_sum);
            }

            public String getProduct_sku_id() {
                return product_sku_id;
            }

            public void setProduct_sku_id(String product_sku_id) {
                this.product_sku_id = product_sku_id;
            }

            @Bindable
            public String getSku_product_text() {
                return sku_product_text;
            }

            public void setSku_product_text(String sku_product_text) {
                this.sku_product_text = sku_product_text;
                notifyPropertyChanged(BR.sku_product_text);
            }

            @Bindable
            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
                notifyPropertyChanged(BR.stock);
            }
        }
    }
}
