package com.zhixingjia.bean.mainmodule;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Bundle;

import com.nmssdmf.commonlib.activity.WebViewActivity;
import com.nmssdmf.commonlib.config.ActivityNameConfig;
import com.nmssdmf.commonlib.config.IntentConfig;
import com.zhixingjia.httplib.BR;

import java.io.Serializable;
import java.util.List;

public class Banner extends BaseObservable{
    private List<CommomBanner> banner_top;          //顶部广告
    private List<CommomBanner> banner_fixed;        //总首页一排三个广告
    private List<CommomBanner> banner_middle;       //总首页、家居首页中间广告

    @Bindable
    public List<CommomBanner> getBanner_top() {
        return banner_top;
    }

    public void setBanner_top(List<CommomBanner> banner_top) {
        this.banner_top = banner_top;
        notifyPropertyChanged(BR.banner_top);
    }

    @Bindable
    public List<CommomBanner> getBanner_fixed() {
        return banner_fixed;
    }

    public void setBanner_fixed(List<CommomBanner> banner_fixed) {
        this.banner_fixed = banner_fixed;
        notifyPropertyChanged(BR.banner_fixed);
    }

    @Bindable
    public List<CommomBanner> getBanner_middle() {
        return banner_middle;
    }

    public void setBanner_middle(List<CommomBanner> banner_middle) {
        this.banner_middle = banner_middle;
        notifyPropertyChanged(BR.banner_middle);
    }

    public static class CommomBanner extends BaseObservable implements Serializable {
        private String model_name;
        private String img_url;     //图片
        private String link_url;    //链接地址，如空就不需要点击跳转
        private String jumps;       //跳转类型    1=H5页面  2=商家店铺首页   3=商品详情

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

        @Bindable
        public String getModel_name() {
            return model_name;
        }

        public void setModel_name(String model_name) {
            this.model_name = model_name;
            notifyPropertyChanged(BR.model_name);
        }

        public String getJumps() {
            return jumps;
        }

        public void setJumps(String jumps) {
            this.jumps = jumps;
        }

        public static void bannerClick(CommomBanner commomBanner, Context context) {
            if ("1".equals(commomBanner.getJumps())) {
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.LINK, commomBanner.getLink_url());
                intent.putExtras(bundle);
                context.startActivity(intent);
            } else if ("2".equals(commomBanner.getJumps())) {
                Intent intent = new Intent();
                intent.setClassName(context, ActivityNameConfig.MERCHANTMAIN_ACTIVITY);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.ID, commomBanner.getLink_url());
                intent.putExtras(bundle);
            } else if ("3".equals(commomBanner.getJumps())) {
                Intent intent = new Intent();
                intent.setClassName(context, ActivityNameConfig.MERCHANDISEDETAIL_ACTIVITY);
                Bundle bundle = new Bundle();
                bundle.putString(IntentConfig.COMMODITY_ID, commomBanner.getLink_url());
                intent.putExtras(bundle);
            }
        }
    }
}
