package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Created by ${nmssdmf} on 2018/11/22 0022.
 * 帖子评论列表
 */

public class MessageComment extends BaseObservable{

    private String comment_id;//评论ID
    private String nickname;//评论者昵称
    private String avatar;//评论者头像
    private String createtime;//时间
    private String give_sum;//点赞数量
    private String quote;//引用评论
    private List<ContentsBean> contents;//评论内容,目前只有一条，list是后台为了统一数据格式

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
        notifyPropertyChanged(BR.createtime);
    }

    public String getGive_sum() {
        return give_sum;
    }

    public void setGive_sum(String give_sum) {
        this.give_sum = give_sum;
    }

    @Bindable
    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
        notifyPropertyChanged(BR.quote);
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class ContentsBean extends BaseObservable{

        private String note;//内容
        private List<ImgsBean> imgs;//图片集

        @Bindable
        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
            notifyPropertyChanged(BR.note);
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            private String m_url;//中图，在列表展示
            private String l_url;//大图，在点击中图后展示大图

            public String getM_url() {
                return m_url;
            }

            public void setM_url(String m_url) {
                this.m_url = m_url;
            }

            public String getL_url() {
                return l_url;
            }

            public void setL_url(String l_url) {
                this.l_url = l_url;
            }
        }
    }
}
