package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhihangjia.mainmodule.BR;

/**
* @description 内容
* @author chenbin
* @date 2018/12/24 15:17
* @version v3.2.0
*/
public class ContentBean extends BaseObservable {
    private String note;            //文本
    private String img;             //图片

    @Bindable
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
        notifyPropertyChanged(BR.note);
    }

    @Bindable
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
        notifyPropertyChanged(BR.img);
    }
}
