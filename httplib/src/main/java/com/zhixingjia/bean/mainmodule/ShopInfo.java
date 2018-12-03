package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;
import java.util.List;

/**
* @description 店铺首页
* @author chenbin
* @date 2018/12/3 10:04
* @version v3.2.0
*/
public class ShopInfo extends BaseObservable {

    private MemberBean member;                          //店铺信息
    private List<Banner.CommomBanner> banners;                  //广告位，如果只有一张的，就不需要滑动，有两张或者以上才出现滑动
    private List<Commodity> commodity_info;     //热卖商品

    @Bindable
    public MemberBean getMember() {
        return member;
    }

    public void setMember(MemberBean member) {
        this.member = member;
        notifyPropertyChanged(BR.member);
    }

    @Bindable
    public List<Banner.CommomBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner.CommomBanner> banners) {
        this.banners = banners;
        notifyPropertyChanged(BR.banners);
    }

    @Bindable
    public List<Commodity> getCommodity_info() {
        return commodity_info;
    }

    public void setCommodity_info(List<Commodity> commodity_info) {
        this.commodity_info = commodity_info;
        notifyPropertyChanged(BR.commodity_info);
    }

    public static class MemberBean extends BaseObservable implements Serializable {

        private String company_name;                //商品名称
        private String billboard;                   //顶部店铺招牌，只有一张
        private String main_camp;                   //经营产品
        private String score;                       //评分
        private String longitude;                   //地址经度
        private String latitude;                    //地址纬度
        private String co_addr;                     //地址
        private String co_phone;                    //联系电话
        private String commodity_score;             //商品描述评分
        private String service_score;               //卖家服务评分
        private String logistics_score;             //物流服务评分
        private String avatar;                      //头像
        private String distance;                    //距离

        @Bindable
        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
            notifyPropertyChanged(BR.company_name);
        }

        @Bindable
        public String getBillboard() {
            return billboard;
        }

        public void setBillboard(String billboard) {
            this.billboard = billboard;
            notifyPropertyChanged(BR.billboard);
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
        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
            notifyPropertyChanged(BR.score);
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
        public String getCo_addr() {
            return co_addr;
        }

        public void setCo_addr(String co_addr) {
            this.co_addr = co_addr;
            notifyPropertyChanged(BR.co_addr);
        }

        @Bindable
        public String getCo_phone() {
            return co_phone;
        }

        public void setCo_phone(String co_phone) {
            this.co_phone = co_phone;
            notifyPropertyChanged(BR.co_phone);
        }

        @Bindable
        public String getCommodity_score() {
            return commodity_score;
        }

        public void setCommodity_score(String commodity_score) {
            this.commodity_score = commodity_score;
            notifyPropertyChanged(BR.commodity_score);
        }

        @Bindable
        public String getService_score() {
            return service_score;
        }

        public void setService_score(String service_score) {
            this.service_score = service_score;
            notifyPropertyChanged(BR.service_score);
        }

        @Bindable
        public String getLogistics_score() {
            return logistics_score;
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

        @Bindable
        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
            notifyPropertyChanged(BR.distance);
        }
    }
}
