package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * 中国电信
 * Create by chenbin on 2018/12/2
 * <p>
 * <p>
 */
public class YXTelecom extends BaseObservable {
    private List<BannerInfoBean> banner_info;           //电信活动图片列表，最多只展示三个图片，不需要分页
    private List<CommodityBean> commodity;              //分类、商品数据，根据这个里数组的数量进行出现几个小点点

    public List<BannerInfoBean> getBanner_info() {
        return banner_info;
    }

    public void setBanner_info(List<BannerInfoBean> banner_info) {
        this.banner_info = banner_info;
    }

    public List<CommodityBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        this.commodity = commodity;
    }

    public static class BannerInfoBean {
        /**
         * img_url : http://h6.mobilekoudai.com/uploads/2018-11-30/5b394b4e334d9f5ca9deb8e1938986dd80.png
         * link_url :
         * jumps : 1
         */

        private String img_url;                 //图片
        private String link_url;                //链接地址，直接用此字段点击跳转相对应的模块上
        private String jumps;                   //跳转类型    1=H5页面  2=商家店铺首页   3=商品详情  4=展示中心

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getJumps() {
            return jumps;
        }

        public void setJumps(String jumps) {
            this.jumps = jumps;
        }
    }

    public static class CommodityBean {
        private String cate_name;                               //分类名称
        private String cate_id;                                 //分类ID
        private List<CommodityInfoBean> commodity_info;         //分类底下的商品，最多4个

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public List<CommodityInfoBean> getCommodity_info() {
            return commodity_info;
        }

        public void setCommodity_info(List<CommodityInfoBean> commodity_info) {
            this.commodity_info = commodity_info;
        }

        public static class CommodityInfoBean {

            private String commodity_id;                    //商品ID
            private String commodity_name;                  //商品名称
            private String imgs;                            //商品图片
            private String price;                           //商品单价

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

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
