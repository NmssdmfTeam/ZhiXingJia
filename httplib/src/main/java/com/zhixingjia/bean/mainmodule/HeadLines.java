package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;

import java.util.List;

/**
* @description 宜兴头条详情
* @author chenbin
* @date 2019/1/25 19:15
* @version v3.2.0
*/
public class HeadLines extends BaseObservable {

    private String title;                           //标题
    private String read_sum;                        //阅读数量
    private String createtime;                      //时间
    private String comment_sum;                     //评论数量
    private String give_sum;                        //点赞数量
    private String give_info;                       //点赞过的信息，这块都有后台给出，这块可以根据give_sum大于0才显示出来，否则隐藏
    private String give_state;                      //判断当前用户是否点过该帖子，0=否  1=是
    private String share_url;                       //分享的地址
    private List<ContentsBean> contents;            //内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRead_sum() {
        return read_sum;
    }

    public void setRead_sum(String read_sum) {
        this.read_sum = read_sum;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
    }

    public String getGive_sum() {
        return give_sum;
    }

    public void setGive_sum(String give_sum) {
        this.give_sum = give_sum;
    }

    public String getGive_info() {
        return give_info;
    }

    public void setGive_info(String give_info) {
        this.give_info = give_info;
    }

    public String getGive_state() {
        return give_state;
    }

    public void setGive_state(String give_state) {
        this.give_state = give_state;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean {

        private String note;                    //内容文字
        private List<String> imgs;              //内容图片

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
