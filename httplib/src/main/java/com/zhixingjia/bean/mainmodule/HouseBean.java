package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;
import java.util.List;

/**
* @description 建材家具首页
* @author chenbin
* @date 2018/11/26 11:35
* @version v3.2.0
*/
public class HouseBean extends BaseObservable {

    private List<CateBean> cate;            //推荐分类
    private List<BrandsBean> brands;        //品牌信息
    private List<SellerBean> seller;        //推荐商家
    private List<ProductBean> product;      //推荐商品

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public List<BrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandsBean> brands) {
        this.brands = brands;
    }

    public List<SellerBean> getSeller() {
        return seller;
    }

    public void setSeller(List<SellerBean> seller) {
        this.seller = seller;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class CateBean extends BaseObservable implements Serializable {
        /**
         * cate_id : 2
         * cate_name : 家居2
         * cate_img :
         */

        private String cate_id;     //分类ID
        private String cate_name;   //分类名称
        private String cate_img;    //分类图片
        private boolean select;     //是否选中

        @Bindable
        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
            notifyPropertyChanged(BR.cate_id);
        }

        @Bindable
        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
            notifyPropertyChanged(BR.cate_name);
        }

        @Bindable
        public String getCate_img() {
            return cate_img;
        }

        public void setCate_img(String cate_img) {
            this.cate_img = cate_img;
            notifyPropertyChanged(BR.cate_img);
        }

        @Bindable
        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
            notifyPropertyChanged(BR.select);
        }
    }

    public static class BrandsBean extends BaseObservable {
        /**
         * id : 8
         * title : 欧派橱柜
         * imgs :
         */

        private String id;      //品牌ID
        private String title;   //品牌名称
        private String imgs;    //品牌图片

        @Bindable
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
            notifyPropertyChanged(BR.id);
        }

        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
            notifyPropertyChanged(BR.title);
        }

        @Bindable
        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }
    }

    public static class SellerBean extends BaseObservable {
        /**
         * member_id : 18
         * company_name : 洁洁家具
         * avatar : http://h6.mobilekoudai.com/uploads/2018-11-21/018f26a5a570c9474900cc7a0e292eadcc.png
         * score : 5
         * main_camp : 家居2
         * co_addr : 新教小学路983号19号
         * longitude : 119.851661
         * latitude : 31.363838
         * goods_info : []
         */

        private String member_id;       //商家ID
        private String company_name;    //商家名称
        private String avatar;          //商家头像
        private String score;           //评分
        private String main_camp;       //主营产品
        private String co_addr;         //联系地址
        private String longitude;       //地址经度 - 来计算距离
        private String latitude;        //地址纬度 - 来计算距离
        private List<GoodsInfo> goods_info;     //商品列表，共三个，如果没有商品的是出现空的数组

        @Bindable
        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
            notifyPropertyChanged(BR.member_id);
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
        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
            notifyPropertyChanged(BR.avatar);
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
        public String getMain_camp() {
            return main_camp;
        }

        public void setMain_camp(String main_camp) {
            this.main_camp = main_camp;
            notifyPropertyChanged(BR.main_camp);
        }

        @Bindable
        public String getCo_addr() {
            return co_addr;
        }

        public void setCo_addr(String co_addr) {
            this.co_addr = co_addr;
            notifyPropertyChanged(BR.co_addr);
        }

        @Bindable
        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
            notifyPropertyChanged(BR.longitude);
        }

        @Bindable
        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
            notifyPropertyChanged(BR.latitude);
        }

        @Bindable
        public List<GoodsInfo> getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(List<GoodsInfo> goods_info) {
            this.goods_info = goods_info;
            notifyPropertyChanged(BR.goods_info);
        }

        public static class GoodsInfo extends BaseObservable {
            private String imgs;                //图片
            private String commodity_id;        //商品ID
            private String commodity_name;      //商品标题

            @Bindable
            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
                notifyPropertyChanged(BR.imgs);
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
        }
    }

    public static class ProductBean extends BaseObservable{
        /**
         * commodity_id : 3
         * commodity_name : 新中式吊灯客厅灯具中国风餐厅灯酒店大厅别墅工程灯复式楼大吊灯
         * imgs : http://h6.mobilekoudai.com/uploads/2018-11-21/018f26a5a570c9474900cc7a0e292eadcc.png
         * unit : 个
         * price : 45.6
         */

        private String commodity_id;        //商品ID
        private String commodity_name;      //商品名称
        private String imgs;                //图片
        private String unit;                //单位
        private String price;               //单价，程序上已去掉小数后多余的0，客户端不需要处理

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
        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
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
        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
            notifyPropertyChanged(BR.price);
        }
    }
}
