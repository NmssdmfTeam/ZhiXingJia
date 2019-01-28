package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * Created by ${nmssdmf} on 2018/11/28 0028.
 */

public class New extends BaseObservable{
    private String id;//ID号，分页需要传的值
    private String title;//标题
    private String read_sum;//阅读数量
    private String comment_sum;//评论数量 - 只针对宜兴头条，政务公告不需要管此字段
    private String title_img;//标题图片，同时此字段也是用于判断展示类型
    private String createtime;//时间
    private String link_url;//链接地址，就广告位一样，直接用此字段点击跳转到H5页面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    public String getTitle_img() {
        return title_img;
    }

    public void setTitle_img(String title_img) {
        this.title_img = title_img;
        notifyPropertyChanged(BR.title_img);
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
    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
        notifyPropertyChanged(BR.comment_sum);
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }
}
