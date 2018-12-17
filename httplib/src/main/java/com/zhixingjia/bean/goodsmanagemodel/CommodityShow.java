package com.zhixingjia.bean.goodsmanagemodel;

import android.databinding.BaseObservable;

import java.io.Serializable;
import java.util.List;

/**
* @description 商品编辑详情
* @author chenbin
* @date 2018/12/17 14:19
* @version v3.2.0
*/
public class CommodityShow extends BaseObservable implements Serializable {
    private String commodity_id;            //商品ID
    private String commodity_name;          //商品名称
    private String bn;                      //货号
    private List<ImageBean> imgs;           //图片集
    private String price;                   //单价，当无规格的时候使用
    private String unit;                    //单位
    private String cost_freight;            //配送费
    private String cate_id;                 //分类
    private String brand;                   //品牌
    private String stock;                   //库存，当无规格的时候使用
    private List<DescibeContent> content;   //内容集
    private List<SepcPriceStockSet> sku;    //规格数据，该商品已有的规格数据
    private List<SepcVal> sepc_val;         //规格信息，商品的已有规格的信息，要在规格列表中展示下面的数据并选中的状态

    public static class ImageBean implements Serializable {
        private String image_id;
        private String imgs_url;

        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
        }

        public String getImgs_url() {
            return imgs_url;
        }

        public void setImgs_url(String imgs_url) {
            this.imgs_url = imgs_url;
        }
    }

    public static class DescibeContent implements Serializable {
        private String note;            //文字内容
        private List<ImageBean> imgs;   //

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public List<ImageBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImageBean> imgs) {
            this.imgs = imgs;
        }
    }

    public static class SepcVal implements Serializable {
        private String norms_id;        //规格ID
        private String sepc_name;       //规格名称
        private List<String> child;     //规格值，下面是循环

        public String getSepc_name() {
            return sepc_name;
        }

        public void setSepc_name(String sepc_name) {
            this.sepc_name = sepc_name;
        }

        public List<String> getChild() {
            return child;
        }

        public void setChild(List<String> child) {
            this.child = child;
        }

        public String getNorms_id() {
            return norms_id;
        }

        public void setNorms_id(String norms_id) {
            this.norms_id = norms_id;
        }
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public List<ImageBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImageBean> imgs) {
        this.imgs = imgs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCost_freight() {
        return cost_freight;
    }

    public void setCost_freight(String cost_freight) {
        this.cost_freight = cost_freight;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<DescibeContent> getContent() {
        return content;
    }

    public void setContent(List<DescibeContent> content) {
        this.content = content;
    }

    public List<SepcPriceStockSet> getSku() {
        return sku;
    }

    public void setSku(List<SepcPriceStockSet> sku) {
        this.sku = sku;
    }

    public List<SepcVal> getSepc_val() {
        return sepc_val;
    }

    public void setSepc_val(List<SepcVal> sepc_val) {
        this.sepc_val = sepc_val;
    }
}
