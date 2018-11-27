package com.zhihangjia.mainmodule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zhihangjia.mainmodule.BR;

/**
 * 建材家居分类
 * Create by chenbin on 2018/11/15
 * <p>
 * <p>
 */
public class MaterialsCategoryBean extends BaseObservable{
    private int resIconId;              //资源图标id
    private String title;                  //名称

    @Bindable
    public int getResIconId() {
        return resIconId;
    }

    public void setResIconId(int resIconId) {
        this.resIconId = resIconId;
        notifyPropertyChanged(BR.resIconId);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
}
