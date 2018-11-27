package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.customerviewlib.entity.MultiItemEntity;
import com.zhihangjia.mainmodule.BR;
import com.zhixingjia.bean.mainmodule.Banner;
import com.zhixingjia.bean.mainmodule.IndexBean;

import java.util.List;

/**
 * 首页列表数据bean
 * Create by chenbin on 2018/11/13
 * <p>
 * <p>
 */
public class MainBean extends BaseObservable implements MultiItemEntity {
    private List<IndexBean.SellerBean> sellerBeans;
    private List<IndexBean.CommodityBean> commodityBeans;
    private Banner.CommomBanner bannerMiddle;
    private List<IndexBean.ForumBean> forumBeans;


    private int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Bindable
    public List<IndexBean.SellerBean> getSellerBeans() {
        return sellerBeans;
    }

    public void setSellerBeans(List<IndexBean.SellerBean> sellerBeans) {
        this.sellerBeans = sellerBeans;
        notifyPropertyChanged(BR.sellerBeans);
    }

    @Bindable
    public List<IndexBean.CommodityBean> getCommodityBeans() {
        return commodityBeans;
    }

    public void setCommodityBeans(List<IndexBean.CommodityBean> commodityBeans) {
        this.commodityBeans = commodityBeans;
        notifyPropertyChanged(BR.commodityBeans);
    }

    @Bindable
    public Banner.CommomBanner getBannerMiddle() {
        return bannerMiddle;
    }

    public void setBannerMiddle(Banner.CommomBanner bannerMiddle) {
        this.bannerMiddle = bannerMiddle;
        notifyPropertyChanged(BR.bannerMiddle);
    }

    @Bindable
    public List<IndexBean.ForumBean> getForumBeans() {
        return forumBeans;
    }

    public void setForumBeans(List<IndexBean.ForumBean> forumBeans) {
        this.forumBeans = forumBeans;
        notifyPropertyChanged(BR.forumBeans);
    }
}
