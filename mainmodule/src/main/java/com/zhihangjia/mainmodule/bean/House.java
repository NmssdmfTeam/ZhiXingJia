package com.zhihangjia.mainmodule.bean;

import com.nmssdmf.customerviewlib.entity.MultiItemEntity;
import com.zhixingjia.bean.mainmodule.HouseBean;

import java.util.List;

/**
* @description 首页建材家具列表数据
* @author chenbin
* @date 2018/11/26 13:38
* @version v3.2.0
*/
public class House implements MultiItemEntity {
    private int itemType;
    private List<HouseBean.CateBean> cate;
    private List<HouseBean.BrandsBean> brands;
    private List<HouseBean.SellerBean> seller;
    private List<HouseBean.ProductBean> product;
    

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<HouseBean.CateBean> getCate() {
        return cate;
    }

    public void setCate(List<HouseBean.CateBean> cate) {
        this.cate = cate;
    }

    public List<HouseBean.BrandsBean> getBrands() {
        return brands;
    }

    public void setBrands(List<HouseBean.BrandsBean> brands) {
        this.brands = brands;
    }

    public List<HouseBean.SellerBean> getSeller() {
        return seller;
    }

    public void setSeller(List<HouseBean.SellerBean> seller) {
        this.seller = seller;
    }

    public List<HouseBean.ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<HouseBean.ProductBean> product) {
        this.product = product;
    }
}
