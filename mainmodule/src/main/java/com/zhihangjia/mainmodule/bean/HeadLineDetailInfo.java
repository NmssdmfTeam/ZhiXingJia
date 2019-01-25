package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.nmssdmf.customerviewlib.entity.MultiItemEntity;
import com.zhihangjia.mainmodule.BR;
import com.zhixingjia.bean.mainmodule.HeadLines;
import com.zhixingjia.bean.mainmodule.MessageComment;

public class HeadLineDetailInfo extends BaseObservable implements MultiItemEntity {
    private MessageComment messageComment;
    private HeadLines headLines;                                    //宜兴头条详情内容数据
    private String giveNames;                                       //点赞人
    private String note;                                            //内容文字
    private String image;                                           //内容图片
    private String zanNum;                                          //点赞个数
    private int type;

    @Override
    public int getItemType() {
        return 0;
    }

    @Bindable
    public MessageComment getMessageComment() {
        return messageComment;
    }

    public void setMessageComment(MessageComment messageComment) {
        this.messageComment = messageComment;
        notifyPropertyChanged(BR.messageComment);
    }

    @Bindable
    public HeadLines getHeadLines() {
        return headLines;
    }

    public void setHeadLines(HeadLines headLines) {
        this.headLines = headLines;
        notifyPropertyChanged(BR.headLines);
    }

    @Bindable
    public String getGiveNames() {
        return giveNames;
    }

    public void setGiveNames(String giveNames) {
        this.giveNames = giveNames;
        notifyPropertyChanged(BR.giveNames);
    }

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getZanNum() {
        return zanNum;
    }

    public void setZanNum(String zanNum) {
        this.zanNum = zanNum;
        notifyPropertyChanged(BR.zanNum);
    }
}
