package com.zhixingjia.bean.personmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.zhixingjia.httplib.BR;

/**
 * 我的帖子回帖
 * Create by chenbin on 2018/11/30
 * <p>
 * <p>
 */
public class Reply extends BaseObservable {
    private String comment_id;              //评论ID，分页的时候需要用到
    private String bbs_id;                  //帖子ID，分页跟跳到帖子详情要用到
    private String title;                   //标题
    private String read_sum;                //阅读数量
    private String nickname;                //昵称
    private String createtime;              //日期

    @Bindable
    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
        notifyPropertyChanged(BR.comment_id);
    }

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
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
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
