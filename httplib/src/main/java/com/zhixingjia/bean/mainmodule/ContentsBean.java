package com.zhixingjia.bean.mainmodule;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhixingjia.httplib.BR;

import java.util.List;

/**
 * Create by chenbin on 2018/11/26
 * <p>
 * <p>
 */
public class ContentsBean extends BaseObservable {
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
