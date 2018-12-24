package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.commonlib.util.StringUtil;
import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 * 帖子详情
 */

public class MessageDetail extends BaseObservable{
    private String cate_id; //帖子分类ID
    private String title;//标题
    private String read_sum;//阅读数量
    private String comment_sum;//评论数量
    private String give_sum="0";//点赞数量
    private String createtime;//时间
    private String cate_name;//帖子分类名称
    private String nickname;//发布者昵称
    private String avatar;//发布者头像
    private String give_info;//点赞过的信息，这块都有后台给出，这块可以根据give_sum大于0才显示出来，否则隐藏
    private String give_state;//判断当前用户是否点过该帖子，0=否  1=是
    private String comment_pages;//评论的总页数，需要弄到顶部，格式为1/5，前面是当页的页数，后面5是总页面
    private List<ContentsBean> contents;//内容数组
    private String share_url;           //分享url


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
        return StringUtil.isEmpty(read_sum) ? "0" : read_sum;
    }

    public void setRead_sum(String read_sum) {
        this.read_sum = read_sum;
        notifyPropertyChanged(BR.read_sum);
    }

    @Bindable
    public String getComment_sum() {
        return StringUtil.isEmpty(comment_sum) ? "0" : comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
        notifyPropertyChanged(BR.comment_sum);
    }

    @Bindable
    public String getGive_sum() {
        return StringUtil.isEmpty(give_sum) ? "0" : give_sum;
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

    @Bindable
    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
        notifyPropertyChanged(BR.cate_name);
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
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getGive_info() {
        return give_info;
    }

    public void setGive_info(String give_info) {
        this.give_info = give_info;
        notifyPropertyChanged(BR.give_info);
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getGive_state() {
        return give_state;
    }

    public void setGive_state(String give_state) {
        this.give_state = give_state;
    }

    public String getComment_pages() {
        return comment_pages;
    }

    public void setComment_pages(String comment_pages) {
        this.comment_pages = comment_pages;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public static class ContentsBean extends BaseObservable{
        private String note;//内容1
        private List<String> imgs;//图片集1

        @Bindable
        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
            notifyPropertyChanged(BR.note);
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
