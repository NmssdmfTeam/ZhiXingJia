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
    private List<ArticleBean> article;              //宜兴头条
    private List<SellerBean> seller;                //优秀商家
    private List<CommodityBean> commodity;          //商品推荐
    private List<ForumBean> forum;                  //万家灯火

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
        private String company_name;
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
        private String unit;
        private String company_name;
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
        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
            notifyPropertyChanged(BR.unit);
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

        private String bbs_id;
        private String title;
        private String read_sum;
        private String createtime;
        private String nickname;
        private String types;
        private List<String> imgs;

        @Bindable
        public String getBbs_id() {
            return bbs_id;
        }

        public void setBbs_id(String bbs_id) {
            this.bbs_id = bbs_id;
            notifyPropertyChanged(BR.bbs_id);
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
        public String getRead_sum() {
            return read_sum;
        }

        public void setRead_sum(String read_sum) {
            this.read_sum = read_sum;
            notifyPropertyChanged(BR.read_sum);
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
        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
            notifyPropertyChanged(BR.nickname);
        }

        @Bindable
        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
            notifyPropertyChanged(BR.types);
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
