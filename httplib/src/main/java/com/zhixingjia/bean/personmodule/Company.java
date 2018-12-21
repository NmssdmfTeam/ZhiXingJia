package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.io.Serializable;

/**
 * 商家信息
 * Create by chenbin on 2018/12/21
 * <p>
 * <p>
 */
public class Company extends BaseObservable implements Serializable {
    private String company_name;            //店铺名称
    private String trade_area;              //所在商圈
    private String co_addr;                 //联系地址
    private String deputy;                  //法人姓名
    private String co_phone;                //联系方式
    private String main_camp;               //主营产品，用于审核不通过重新填写时候传
    private CardImageBean card_front;       //身份证正面，图片点击可放大看
    private CardImageBean card_back;         //身份证反面，图片点击可放大看
    private CardImageBean license_img;     //营业执照，图片点击可放大看
    private String main_camp_name;          //主营产品，用于展示
    private String trade_area_name;         //所在商圈中文名称，用于展示

    @Bindable
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
        notifyPropertyChanged(BR.company_name);
    }

    @Bindable
    public String getTrade_area() {
        return trade_area;
    }

    public void setTrade_area(String trade_area) {
        this.trade_area = trade_area;
        notifyPropertyChanged(BR.trade_area);
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
    public String getDeputy() {
        return deputy;
    }

    public void setDeputy(String deputy) {
        this.deputy = deputy;
        notifyPropertyChanged(BR.deputy);
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
    public String getMain_camp() {
        return main_camp;
    }

    public void setMain_camp(String main_camp) {
        this.main_camp = main_camp;
        notifyPropertyChanged(BR.main_camp);
    }

    @Bindable
    public String getMain_camp_name() {
        return main_camp_name;
    }

    public void setMain_camp_name(String main_camp_name) {
        this.main_camp_name = main_camp_name;
        notifyPropertyChanged(BR.main_camp_name);
    }

    @Bindable
    public String getTrade_area_name() {
        return trade_area_name;
    }

    public void setTrade_area_name(String trade_area_name) {
        this.trade_area_name = trade_area_name;
        notifyPropertyChanged(BR.trade_area_name);
    }

    @Bindable
    public CardImageBean getCard_front() {
        return card_front;
    }

    public void setCard_front(CardImageBean card_front) {
        this.card_front = card_front;
        notifyPropertyChanged(BR.card_front);
    }

    @Bindable
    public CardImageBean getCard_back() {
        return card_back;
    }

    public void setCard_back(CardImageBean card_back) {
        this.card_back = card_back;
        notifyPropertyChanged(BR.card_back);
    }

    @Bindable
    public CardImageBean getLicense_img() {
        return license_img;
    }

    public void setLicense_img(CardImageBean license_img) {
        this.license_img = license_img;
        notifyPropertyChanged(BR.license_img);
    }

    public static class CardImageBean extends BaseObservable implements Serializable {
        /**
         * image_id : 0bda55fb1a8abb3cc8924bcbdee8e19d
         * m_url :
         * l_url :
         */

        private String image_id;        //图片ID
        private String m_url;           //中图
        private String l_url;           //大图

        @Bindable
        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
            notifyPropertyChanged(BR.image_id);
        }

        @Bindable
        public String getM_url() {
            return m_url;
        }

        public void setM_url(String m_url) {
            this.m_url = m_url;
            notifyPropertyChanged(BR.m_url);
        }

        @Bindable
        public String getL_url() {
            return l_url;
        }

        public void setL_url(String l_url) {
            this.l_url = l_url;
            notifyPropertyChanged(BR.l_url);
        }
    }
}
