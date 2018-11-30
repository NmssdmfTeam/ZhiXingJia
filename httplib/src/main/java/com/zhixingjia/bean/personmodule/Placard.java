package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

/**
 * 帖子
 * Create by chenbin on 2018/11/30
 * <p>
 * <p>
 */
public class Placard extends BaseObservable {
    private String bbs_id;                  //帖子ID，分页跟跳到帖子详情要用到
    private String title;                   //标题
    private String read_sum;                //阅读数量
    private String comment_sum;             //评论数量
    private String give_sum;                //点赞数量
    private String createtime;              //日期

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
    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
        notifyPropertyChanged(BR.comment_sum);
    }

    @Bindable
    public String getGive_sum() {
        return give_sum;
    }

    public void setGive_sum(String give_sum) {
        this.give_sum = give_sum;
        notifyPropertyChanged(BR.give_sum);
    }

    @Bindable
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        notifyPropertyChanged(BR.createtime);
    }
}
