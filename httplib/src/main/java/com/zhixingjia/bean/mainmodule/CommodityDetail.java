package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
* @description 商品详情
* @author cbb
* @date 2018/11/26 9:35 PM
* @version v3.2.0
*/

public class CommodityDetail extends BaseObservable {

    private String commodity_id;                    //商品ID
    private String commodity_name;                  //商品名称
    private String provider_id;                     //商家ID
    private String bn;                              //货号
    private String price;                           //单价
    private String unit;                            //单位
    private String cost_freight;                    //运费
    private String brand;                           //品牌名称
    private String stock;                           //总库存
    private String sold;                            //已售数量
    private String coupon_tab;                      //优惠券说明Tab
    private List<ContentsBean> content;             //内容循环
    private ProviderInfoBean provider_info;         //供应商信息
    private String sepc_text;                       //规格小注释，无规格时，此处为空
    private List<String> imgs;                      //图片集
    private List<SellerCoupon> seller_coupon;                //商家优惠券，先为空，后面再接上去
    private List<SkuBean> sku;                      //规格数据，用于比例，也供展示库存与价格，购买时需要传规格ID，无规格为空
    private List<SepcValBean> sepc_val;             //规格信息，这个是展示作用，供用户选择购买
    private List<OrderComment> order_comment;       //评价列表，最多5条
    private String share_url;                       //用于分享的地址

    @Bindable
    public String getCoupon_tab() {
        return coupon_tab;
    }

    public void setCoupon_tab(String coupon_tab) {
        this.coupon_tab = coupon_tab;
        notifyPropertyChanged(BR.coupon_tab);
    }

    public List<SellerCoupon> getSeller_coupon() {
        return seller_coupon;
    }

    public void setSeller_coupon(List<SellerCoupon> seller_coupon) {
        this.seller_coupon = seller_coupon;
    }

    @Bindable
    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
        notifyPropertyChanged(BR.commodity_id);
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
    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
        notifyPropertyChanged(BR.provider_id);
    }

    @Bindable
    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
        notifyPropertyChanged(BR.bn);
    }

    @Bindable
    public String getPrice() {
        return price==null?"":price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getUnit() {
        return unit==null?"":unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
        notifyPropertyChanged(BR.unit);
    }

    @Bindable
    public String getCost_freight() {
        return cost_freight==null?"":cost_freight;
    }

    public void setCost_freight(String cost_freight) {
        this.cost_freight = cost_freight;
        notifyPropertyChanged(BR.cost_freight);
    }

    @Bindable
    public String getBrand() {
        return brand==null?"":brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
        notifyPropertyChanged(BR.brand);
    }

    @Bindable
    public String getStock() {
        return stock==null?"":stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
        notifyPropertyChanged(BR.stock);
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
    public List<ContentsBean> getContent() {
        return content;
    }

    public void setContent(List<ContentsBean> content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public ProviderInfoBean getProvider_info() {
        return provider_info;
    }

    public void setProvider_info(ProviderInfoBean provider_info) {
        this.provider_info = provider_info;
        notifyPropertyChanged(BR.provider_info);
    }

    @Bindable
    public String getSepc_text() {
        return sepc_text;
    }

    public void setSepc_text(String sepc_text) {
        this.sepc_text = sepc_text;
        notifyPropertyChanged(BR.sepc_text);
    }

    @Bindable
    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
        notifyPropertyChanged(BR.imgs);
    }

    @Bindable
    public List<SkuBean> getSku() {
        return sku;
    }

    public void setSku(List<SkuBean> sku) {
        this.sku = sku;
        notifyPropertyChanged(BR.sku);
    }

    @Bindable
    public List<SepcValBean> getSepc_val() {
        return sepc_val;
    }

    public void setSepc_val(List<SepcValBean> sepc_val) {
        this.sepc_val = sepc_val;
        notifyPropertyChanged(BR.sepc_val);
    }

    @Bindable
    public List<OrderComment> getOrder_comment() {
        return order_comment;
    }

    public void setOrder_comment(List<OrderComment> order_comment) {
        this.order_comment = order_comment;
        notifyPropertyChanged(BR.order_comment);
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public static class ProviderInfoBean extends BaseObservable {

        private String company_name;                //公司名称
        private String score;                       //总评分
        private String commodity_score;             //商品描述评分
        private String service_score;               //卖家服务评分
        private String logistics_score;             //物流服务评分
        private String avatar;                      //头像
        private String co_phone;                    //公司电话

        @Bindable
        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
            notifyPropertyChanged(BR.company_name);
        }

        @Bindable
        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
            notifyPropertyChanged(BR.score);
        }

        @Bindable
        public String getCommodity_score() {
            return commodity_score == null?"":commodity_score;
        }

        public void setCommodity_score(String commodity_score) {
            this.commodity_score = commodity_score;
            notifyPropertyChanged(BR.commodity_score);
        }

        @Bindable
        public String getService_score() {
            return service_score == null?"":service_score;
        }

        public void setService_score(String service_score) {
            this.service_score = service_score;
            notifyPropertyChanged(BR.service_score);
        }

        @Bindable
        public String getLogistics_score() {
            return logistics_score == null?"":logistics_score;
        }

        public void setLogistics_score(String logistics_score) {
            this.logistics_score = logistics_score;
            notifyPropertyChanged(BR.logistics_score);
        }

        @Bindable
        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
            notifyPropertyChanged(BR.avatar);
        }

        public String getCo_phone() {
            return co_phone;
        }

        public void setCo_phone(String co_phone) {
            this.co_phone = co_phone;
        }
    }

    public static class SepcValBean extends BaseObservable {
        private String sepc_name;                   //规格名称
        private List<String> child;                 //规格值，下面是循环

        @Bindable
        public String getSepc_name() {
            return sepc_name;
        }

        public void setSepc_name(String sepc_name) {
            this.sepc_name = sepc_name;
            notifyPropertyChanged(BR.sepc_name);
        }

        @Bindable
        public List<String> getChild() {
            return child;
        }

        public void setChild(List<String> child) {
            this.child = child;
            notifyPropertyChanged(BR.child);
        }
    }

    public static class OrderComment extends BaseObservable {
        private String nickname;        //昵称
        private String avatar;          //头像
        private String contents;        //评价内容

        @Bindable
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
            notifyPropertyChanged(BR.nickname);
        }

        @Bindable
        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
            notifyPropertyChanged(BR.avatar);
        }

        @Bindable
        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
            notifyPropertyChanged(BR.contents);
        }
    }

    public static class SellerCoupon extends BaseObservable {
        private String coupon_id;//优惠券ID
        private String decrease;//优惠金额
        private String validity;//有效期
        private String cond_name;//使用条件

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        @Bindable
        public String getDecrease() {
            return decrease;
        }

        public void setDecrease(String decrease) {
            this.decrease = decrease;
            notifyPropertyChanged(BR.decrease);
        }

        @Bindable
        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
            notifyPropertyChanged(BR.validity);
        }

        @Bindable
        public String getCond_name() {
            return cond_name;
        }

        public void setCond_name(String cond_name) {
            this.cond_name = cond_name;
            notifyPropertyChanged(BR.cond_name);
        }
    }
}
