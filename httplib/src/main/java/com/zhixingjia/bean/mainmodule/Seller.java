package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.commonlib.util.StringUtil;
import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/26 0026.
 */

public class Seller extends BaseObservable {
    private String member_id;//商家ID
    private String company_name;//商家名称
    private String avatar;//商家头像
    private String score;//评分
    private String main_camp;//主营产品
    private String co_addr;//联系地址
    private String longitude;//地址经度 - 来计算距离
    private String latitude;//地址纬度 - 来计算距离
    private List<GoodsInfoBean> goods_info;//商品列表，共三个，如果没有商品的是出现空的数组

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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
        return StringUtil.isEmpty(score) ? "0" : score;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Bindable
    public List<GoodsInfoBean> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(List<GoodsInfoBean> goods_info) {
        this.goods_info = goods_info;
        notifyPropertyChanged(BR.goods_info);
    }

    public static class GoodsInfoBean extends BaseObservable{
        private String imgs;//图片
        private String commodity_id;//商品ID
        private String commodity_name;//商品标题

        @Bindable
        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }

        public String getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(String commodity_id) {
            this.commodity_id = commodity_id;
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
