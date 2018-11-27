package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.commonlib.bean.Base;
import com.zhixingjia.httplib.BR;

import java.util.List;

/**
* @description 商品评论
* @author chenbin
* @date 2018/11/27 16:03
* @version v3.2.0
*/
public class Comment extends BaseObservable {

    private String comment_id;                  //评价ID，分页的时候需要传的值
    private String nickname;                    //昵称
    private String avatar;                      //头像
    private String spec_info;                   //规格
    private String createtime;                  //规格
    private List<ContentsBean> contents;        //内容+图片

    @Bindable
    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
        notifyPropertyChanged(BR.comment_id);
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
    public String getSpec_info() {
        return spec_info;
    }

    public void setSpec_info(String spec_info) {
        this.spec_info = spec_info;
        notifyPropertyChanged(BR.spec_info);
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
    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
        notifyPropertyChanged(BR.contents);
    }

    public static class ContentsBean extends BaseObservable {

        private String note;                            //评价内容
        private List<ImgsBean> imgs;                    //图片集

        @Bindable
        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
            notifyPropertyChanged(BR.note);
        }

        @Bindable
        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
            notifyPropertyChanged(BR.imgs);
        }

        public static class ImgsBean extends BaseObservable {

            private String m_url;                       //中图
            private String l_url;                       //大图，点击放大的图片

            @Bindable
            public String getM_url() {
                return m_url;
            }

            public void setM_url(String m_url) {
                this.m_url = m_url;
                notifyPropertyChanged(BR.m_url);
            }

            @Bindable
            public String getL_url() {
                return l_url;
            }

            public void setL_url(String l_url) {
                this.l_url = l_url;
                notifyPropertyChanged(BR.l_url);
            }
        }
    }
}
