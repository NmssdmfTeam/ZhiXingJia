package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.commonlib.bean.Base;
import com.zhixingjia.httplib.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Create by chenbin on 2018/11/14
 * <p>
 * <p>
 */
public class IndexBean extends BaseObservable implements Serializable {

    private List<BannersBean> banners;              //顶部滚动广告位
    private List<BannerFixedBean> banner_fixed;     //固定广告位
    private List<ArticleBean> article;              //宜兴头条
    private List<SellerBean> seller;                //优秀商家
    private List<CommodityBean> commodity;          //商品推荐
    private List<ForumBean> forum;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<BannerFixedBean> getBanner_fixed() {
        return banner_fixed;
    }

    public void setBanner_fixed(List<BannerFixedBean> banner_fixed) {
        this.banner_fixed = banner_fixed;
    }

    public List<ArticleBean> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleBean> article) {
        this.article = article;
    }

    public List<SellerBean> getSeller() {
        return seller;
    }

    public void setSeller(List<SellerBean> seller) {
        this.seller = seller;
    }

    public List<CommodityBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        this.commodity = commodity;
    }

    public List<ForumBean> getForum() {
        return forum;
    }

    public void setForum(List<ForumBean> forum) {
        this.forum = forum;
    }

    public static class BannersBean {
        /**
         * img_url : http://h5.mobilekoudai.com/uploads/banner.png
         * link_url :
         */

        private String img_url;
        private String link_url;

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
    }

    public static class BannerFixedBean extends BaseObservable {
        /**
         * model_name : left
         * img_url : http://h5.mobilekoudai.com/uploads/4.png
         * link_url :
         */

        private String model_name;
        private String img_url;
        private String link_url;

        @Bindable
        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
            notifyPropertyChanged(BR.model_name);
        }

        @Bindable
        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
            notifyPropertyChanged(BR.img_url);
        }

        @Bindable
        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
            notifyPropertyChanged(BR.link_url);
        }
    }

    public static class ArticleBean {
        /**
         * id : 4
         * title : 头条数据4
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class SellerBean extends BaseObservable {
        /**
         * member_id : 1
         * company : 志邦沙发
         * score : 4.8
         * imgs : http://h5.mobilekoudai.com/uploads/2.jpg
         */

        private String member_id;
        private String company;
        private String score;
        private String imgs;

        @Bindable
        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
            notifyPropertyChanged(BR.member_id);
        }

        @Bindable
        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
            notifyPropertyChanged(BR.company);
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
        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }
    }

    public static class CommodityBean extends BaseObservable {
        /**
         * commodity_id : 1
         * commodity_name : 很好的商品标题一切要用
         * imgs : http://h5.mobilekoudai.com/uploads/2.jpg
         * company : 志邦沙发
         * price : 34.5
         */

        private String commodity_id;
        private String commodity_name;
        private String imgs;
        private String company;
        private String price;

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
        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
            notifyPropertyChanged(BR.company);
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

    public static class ForumBean extends BaseObservable {
        /**
         * types : 1
         * forum_id : 1
         * title : 很好的商品标题一切要用
         * imgs : []
         * member_name : 成长之人
         * read_number : 123
         * createtime : 5分钟前
         */

        private String types;
        private String forum_id;
        private String title;
        private String member_name;
        private String read_number;
        private String createtime;
        private List<String> imgs;

        @Bindable
        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
            notifyPropertyChanged(BR.types);
        }

        @Bindable
        public String getForum_id() {
            return forum_id;
        }

        public void setForum_id(String forum_id) {
            this.forum_id = forum_id;
            notifyPropertyChanged(BR.forum_id);
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
        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
            notifyPropertyChanged(BR.member_name);
        }

        @Bindable
        public String getRead_number() {
            return read_number;
        }

        public void setRead_number(String read_number) {
            this.read_number = read_number;
            notifyPropertyChanged(BR.read_number);
        }

        @Bindable
        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
            notifyPropertyChanged(BR.createtime);
        }

        @Bindable
        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }
    }
}
